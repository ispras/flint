package ru.ispras.modis.flint.classifier

import ru.ispras.modis.flint.random.MersenneTwistProvider
import ru.ispras.modis.flint.datapreprocessors.NormalizingInstancePreprocessor
import ru.ispras.modis.flint.instances.{WeightedFeature, LabelledInstance}
import ru.ispras.modis.flint.regression.LinearRegressionTrainer
import org.apache.spark.SparkContext
import org.uncommons.maths.random.DefaultSeedGenerator
import ru.ispras.modis.flint.crossvalidation.RegressionCrossValidator
import ru.ispras.modis.flint.regression.ArmijoStepper
import ru.ispras.modis.flint.regression.SimpleStepper
import ru.ispras.modis.flint.instances.Instance
import ru.ispras.modis.flint.strictclustering.KMeans
import ru.ispras.modis.flint.instances.InstanceOp

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/23/13
 * Time: 9:46 PM
 */

object Test extends App {
    val sc = new SparkContext("local[4]", "")

/*    val data = sc.textFile("/home/saylars/smth/kmeans_dataset3.txt").map(line => {
    var features = line.split(" ").map(_.toDouble).toList
    new Instance(features.zipWithIndex.map {
        case (weight, id) => new WeightedFeature(id, weight)
    }.toIndexedSeq)
    })
    val x = new KMeans(4, 0).apply(data)
    val y = x.toArray()
    for (i <- y) {
        //println(i._1 +" "+ i._2.length)
        /*val t = InstanceOp.sum(i._2.apply(1),i._2.apply(2))
        for (q <- i._2.apply(1))
            print(q.featureWeight + " ")
        println("!")
        for (q <- i._2.apply(2))
            print(q.featureWeight + " ")
        println("!!")
        for (q <- t)
            print(q.featureWeight + " ")
        println("!!!")
*/
        for (j <- i._2) {
            for (k <- j)
                print(k.featureWeight + ",")
            println(i._1+1)
        }
    }*/


    val data = sc.textFile("/home/saylars/smth/test1.csv").map(line => {
        var features = line.split(",").map(_.toDouble).toList
        features = 1.0 :: features
        new LabelledInstance[Double](features.init.zipWithIndex.map {
            case (weight, id) => new WeightedFeature(id, weight)
        }.toIndexedSeq, features.last)
    })

    val data1 = (new NormalizingInstancePreprocessor[LabelledInstance[Double]]() :+ new NormalizingInstancePreprocessor[LabelledInstance[Double]]()).apply(data)
    val time = System.nanoTime()
    val x = new RegressionCrossValidator(0.8, 3, DefaultSeedGenerator.getInstance(), MersenneTwistProvider).apply(new LinearRegressionTrainer(0.01, 100, new ArmijoStepper, DefaultSeedGenerator.getInstance(), MersenneTwistProvider), data.cache())
    println(x.rootMeanSquareDeviation)
    println((System.nanoTime() - time)/ 1000000.0)
}
