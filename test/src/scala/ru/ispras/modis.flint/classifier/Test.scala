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
import ru.ispras.modis.flint.strictclustring.Kmeans

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/23/13
 * Time: 9:46 PM
 */

object Test extends App {
    val sc = new SparkContext("local[4]", "")

    val data = sc.textFile("/home/saylars/smth/kmeans_dataset.txt").map(line => {
    var features = line.split(" ").map(_.toDouble).toList
    new Instance(features.zipWithIndex.map {
        case (weight, id) => new WeightedFeature(id, weight)
    }.toIndexedSeq)
    })
    val x = new Kmeans(2, 0, 156).apply(data)
    x
}
