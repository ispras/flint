package ru.ispras.modis.flint.strictclustring

import org.apache.spark.rdd.RDD
import ru.ispras.modis.flint.instances.{WeightedFeature, Instance, LabelledInstance}
import ru.ispras.modis.flint.random.MersenneTwistProvider
import org.uncommons.maths.random.DefaultSeedGenerator
import org.uncommons.maths.random.SeedGenerator
import ru.ispras.modis.flint.random.RandomGeneratorProvider
import org.apache.spark.SparkContext.rddToPairRDDFunctions

/**
 * Created with IntelliJ IDEA.
 * User: saylars
 * Date: 12.12.13
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */

class Kmeans(private val k: Int,
             private val eps:Double,
             private val seed:Int = 0,
             private val distance: Distance = EuclideanDistance) extends StrictClustering{

    def apply(data: RDD[Instance]): RDD[(Int, Seq[Instance])] = {

        val calcDistance = distance

        val centers = data.takeSample(false, k, seed)

        var Dist = 0d

        do {

            val closest = data.map(point => {
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

            val countPoints = closest.countByKey()

            val newCenters = closest.reduceByKey((x:Instance, y:Instance) => {
                val tmp = new Array[Double](x.length)                   //FIXME: rewrite to smth like EuclideDistance
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

                (point._1, new Instance(point._2.map{ tmp => new WeightedFeature(tmp.featureId, tmp.featureWeight / countPoints(point._1))}.toIndexedSeq))

            }).collectAsMap()

            Dist = 0.0

            for (i <- 0 until k) {

                Dist += calcDistance(centers(i), newCenters(i))
            }

            for (i <- newCenters) {
                centers(i._1) = i._2
            }

        } while (Dist > eps)

        val closest = data.map(point => {
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
