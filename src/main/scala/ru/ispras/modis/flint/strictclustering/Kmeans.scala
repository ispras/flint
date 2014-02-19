package ru.ispras.modis.flint.strictclustring

import org.apache.spark.rdd.RDD
import ru.ispras.modis.flint.instances.{Instance, WeightedFeature}
import org.apache.spark.SparkContext.rddToPairRDDFunctions

import java.lang.System

/**
 * Created with IntelliJ IDEA.
 * User: saylars
 * Date: 12.12.13
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */

class KMeans(private val k: Int,
             private val eps:Double,
             private val randomSeed: Int = System.currentTimeMillis().asInstanceOf[Int], // dont know how to get current time in Int. It looks like unsafe
             private val distance: Distance = EuclideanDistance) extends StrictClustering with Serializable{


    private def calcClosestCentroids(data: RDD[Instance],
                                     centroids:Array[Instance],
                                     kMeansRange:Range):RDD[(Int, Instance)] ={
        data.map(point => {
            var bestIdxOfCentroids = 0
            var closestDist = Double.PositiveInfinity
            for (i <- kMeansRange) {
                val currDist = distance(point, centroids(i))
                if (currDist < closestDist) {
                    closestDist = currDist
                    bestIdxOfCentroids = i
                }
            }
            (bestIdxOfCentroids, point)
        })
    }

    private def findNewCentroids(closestCentroid:RDD[(Int, Instance)]) = {

        val countPoints = closestCentroid.countByKey()

        closestCentroid.reduceByKey((x:Instance, y:Instance) => {
            val sum = new Array[Double](x.length) //FIXME: rewrite to smth like EuclideDistance // I second this. // dont know how to fix it
            for (i <- x) {
                sum(i.featureId) += i.featureWeight
            }
            for (i <- y) {
                sum(i.featureId) += i.featureWeight
            }
            new Instance(sum.zipWithIndex.map {
                case (weight, id) => new WeightedFeature(id, weight)
            }.toIndexedSeq)
        }).map(point => {
            //case(bla, bla) //WTF?

            (point._1, new Instance(point._2.map{ feature => new WeightedFeature(feature.featureId, feature.featureWeight / countPoints(point._1))}.toIndexedSeq))

        }).collectAsMap()
    }

    def apply(data: RDD[Instance]): RDD[(Int, Seq[Instance])] = {

        val centroids = data.takeSample(false, k, randomSeed)

        val kMeansRange = 0 until k
        var dist = 0d
        do {

            val closestCentroid = calcClosestCentroids(data, centroids, kMeansRange)

            val newCenters = findNewCentroids(closestCentroid)

            dist = 0d

            for (i <- kMeansRange) {

                dist += distance(centroids(i), newCenters(i))
            }

            for (i <- newCenters) {
                centroids(i._1) = i._2
            }

        } while (dist > eps)

        val closestCentroid = calcClosestCentroids(data, centroids, kMeansRange)

        closestCentroid.groupByKey()
    }

}
