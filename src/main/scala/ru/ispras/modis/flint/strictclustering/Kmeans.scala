package ru.ispras.modis.flint.strictclustring

// wtf is strictclustring?

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

class KMeans(private val k: Int, //whether rename the file or the class. Names should correspond
             private val eps:Double,
             private val randomSeed: Int = System.currentTimeMillis().asInstanceOf[Int], // dont know how to get current time in Int. It looks like unsafe
             //it is not. You need a random Int. You take long by modulo 2^31

             private val distance: Distance = EuclideanDistance) extends StrictClustering with Serializable{


    private def calcClosestCentroids(data: RDD[Instance],
                                     centroids:Array[Instance],
                                     kMeansRange: Range): RDD[(Int, Instance)] = {
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

        val countPoints = closestCentroid.countByKey() // rename. e.g. to size of clusters

        closestCentroid.reduceByKey((x:Instance, y:Instance) => {
            val sum = new Array[Double](x.length) //replace with dimentionality
            for (i <- x) {
                // i? is it an index? Then rename it.
                sum(i.featureId) += i.featureWeight
            }
            for (i <- y) {
                sum(i.featureId) += i.featureWeight
            }
            new Instance(sum.zipWithIndex.map {
                //get rid of zipWithIndex. Save the memory for our children
                case (weight, id) => new WeightedFeature(id, weight)
            }.toIndexedSeq)
        }).map(point => {
            //case(bla, bla) //WTF?
            // use case(bla1, bla2) instead of messing with bla._1 and bla._2

            (point._1, new Instance(point._2.map{ feature => new WeightedFeature(feature.featureId, feature.featureWeight / countPoints(point._1))}.toIndexedSeq))

        }).collectAsMap()
    }

    def apply(data: RDD[Instance]): RDD[(Int, Seq[Instance])] = {

        val centroids = data.takeSample(false, k, randomSeed)

        val kMeansRange = 0 until k // why don you just zip with index centroids?
        // and rename. e.g. centroids indexes. Everyone knows that
        // this variable is defined in KMeans class and may easily see that it is of type range
        // and why don't you make it a field. Anyway it will never change
        var dist = 0d
        do {

            val closestCentroid = calcClosestCentroids(data, centroids, kMeansRange)

            val newCenters = findNewCentroids(closestCentroid)

            dist = 0d // create a function like getEnergy. And I think var dist should be renamed.

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
