package ru.ispras.modis.flint.strictclustering


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
             private val randomSeed: Int = System.currentTimeMillis().asInstanceOf[Int],
             private val distance: Distance = EuclideanDistance) extends StrictClustering with Serializable{


    private def calcClosestCentroids(data: RDD[Instance],
                                     centroids:Array[Instance],
                                     centroidsIndexes: Range): RDD[(Int, Instance)] = {
        data.map(point => {
            var bestIdxOfCentroids = 0
            var closestDist = Double.PositiveInfinity
            for (i <- centroidsIndexes) {
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

        val sizeOfClusters = closestCentroid.countByKey()

        closestCentroid.reduceByKey(_ + _).map{
            case (index, summarizedPoint) =>

            (index, new Instance(summarizedPoint.map{ feature => new WeightedFeature(feature.featureId, feature.featureWeight / sizeOfClusters(index))}.toIndexedSeq))

        }.collectAsMap()

    }

    def apply(data: RDD[Instance]): RDD[(Int, Seq[Instance])] = {

        val centroids = data.takeSample(false, k, randomSeed)

        val centroidsIndexes = 0 until k

        var dist = 0d

        do {

            val closestCentroid = calcClosestCentroids(data, centroids, centroidsIndexes)

            val newCenters = findNewCentroids(closestCentroid)

            dist = 0d // create a function like getEnergy. And I think var dist should be renamed.
            //MIKE : dont understand what you want

            for (i <- centroidsIndexes) {
                dist += distance(centroids(i), newCenters(i))
            }

            for (i <- newCenters) {
                centroids(i._1) = i._2
            }

        } while (dist > eps)

        val closestCentroid = calcClosestCentroids(data, centroids, centroidsIndexes)

        closestCentroid.groupByKey()
    }

}
