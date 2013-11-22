package ru.ispras.modis.flint.classifier

import ru.ispras.modis.flint.random.MersenneTwistProvider
import ru.ispras.modis.flint.datapreprocessors.NormalizingInstancePreprocessor
import ru.ispras.modis.flint.instances.{WeightedFeature, LabelledInstance}
import ru.ispras.modis.flint.regression.LinearRegressionTrainer
import org.apache.spark.SparkContext
import org.uncommons.maths.random.DefaultSeedGenerator
import ru.ispras.modis.flint.crossvalidation.RegressionCrossValidator

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/23/13
 * Time: 9:46 PM
 */

object Test extends App {
    val sc = new SparkContext("local[4]", "")

    val data = sc.textFile("/home/saylars/smth/test1.csv").map(line => {
        var features = line.split(",").map(_.toDouble).toList
        features = 1.0 :: features
        new LabelledInstance[Double](features.init.zipWithIndex.map {
            case (weight, id) => new WeightedFeature(id, weight)
        }.toIndexedSeq, features.last)
    })
    //val data1 = (new NormalizingInstancePreprocessor[LabelledInstance[Double]]() :+ new NormalizingInstancePreprocessor[LabelledInstance[Double]]()).apply(data)
    val now = System.nanoTime()
    val x = new RegressionCrossValidator(0.8, 10, DefaultSeedGenerator.getInstance(), MersenneTwistProvider).apply(new LinearRegressionTrainer(0.000001, DefaultSeedGenerator.getInstance(), MersenneTwistProvider), data.cache())
    println(x.rootMeanSquareDeviation)
    println((System.nanoTime() - now)/1e9)
}
