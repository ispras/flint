package ru.ispras.modis.flint.strictclustring

import org.apache.spark.rdd.RDD
import ru.ispras.modis.flint.instances.{WeightedFeature, Instance}
import org.apache.spark.SparkContext.rddToPairRDDFunctions

/**
 * Created with IntelliJ IDEA.
 * User: saylars
 * Date: 12.12.13
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */

class Kmeans(private val k: Int, // KMeans
             private val eps:Double,
             private val seed: Int = 0, // what seed? randomSeed! And it should be current time by default.
             private val distance: Distance = EuclideanDistance) extends StrictClustering{

    def apply(data: RDD[Instance]): RDD[(Int, Seq[Instance])] = {

        val calcDistance = distance // WTF? distance was not good enough?

        val centers = data.takeSample(false, k, seed)

        var Dist = 0d // why so capitalized?

        do {

            val closest = data.map(point => {
                // closest what?
                var bestIdx = 0 // index of what?
                var closest = Double.PositiveInfinity // closest what? Centroid? I doubt
                for (i <- 0 until centers.length) {
                    // at this point a new Range will be instantiated every single time.
                    // whether rewrite this as loop or create a single Range and broadcast it to slaves.
                    val tmpDist = calcDistance(point, centers(i)) // damn! Every time you use 'tmp' a God reduces a boob.
                    if (tmpDist < closest) {
                        closest = tmpDist
                        bestIdx = i
                    }
                }
                (bestIdx, point)
            })

            val countPoints = closest.countByKey()

            val newCenters = closest.reduceByKey((x:Instance, y:Instance) => {
                val tmp = new Array[Double](x.length) //FIXME: rewrite to smth like EuclideDistance // I second this.
                for (i <- x) {
                    tmp(i.featureId) += i.featureWeight
                }
                for (i <- y) {
                    tmp(i.featureId) += i.featureWeight
                }
                new Instance(tmp.zipWithIndex.map {
                    case (weight, id) => new WeightedFeature(id, weight)
                }.toIndexedSeq)
            }).map(point => {
                // case(bla,bla)

                (point._1, new Instance(point._2.map{ tmp => new WeightedFeature(tmp.featureId, tmp.featureWeight / countPoints(point._1))}.toIndexedSeq))

            }).collectAsMap()

            Dist = 0.0 //0d

            for (i <- 0 until k) {
                // the same stuff. A new Range for each call.

                Dist += calcDistance(centers(i), newCenters(i))
            }

            for (i <- newCenters) {
                centers(i._1) = i._2
            }

        } while (Dist > eps)

        val closest = data.map(point => {
            // this looks like code duplication. Create a function for that stuff.
            // and divide the algorithm into methods -- calculate closest centroids, find new centroids
            var bestIdx = 0
            var closest = Double.PositiveInfinity
            for (i <- 0 until centers.length) {
                val tmpDist = calcDistance(point, centers(i))
                if (tmpDist < closest) {
                    closest = tmpDist
                    bestIdx = i
                }
            }
            (bestIdx, point)
        })

        closest.groupByKey()
    }

}
