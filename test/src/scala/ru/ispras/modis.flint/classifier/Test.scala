package ru.ispras.modis.flint.classifier

import scala.io.Source
import java.io.File
import ru.ispras.modis.flint.datapreprocessors.NormalizingInstancePreprocessor
import ru.ispras.modis.flint.instances.{WeightedFeature, LabelledInstance}
import ru.ispras.modis.flint.regression.LinearRegressionTrainer
import ru.ispras.modis.flint.regression.LinearRegressionModel
import org.apache.spark.SparkContext
/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/23/13
 * Time: 9:46 PM
 */
object Test extends App {
    val sc = new SparkContext("local[4]", "")
    val data = sc.parallelize(Source.fromFile(new File("/home/saylars/smth/lineartest1.txt")).getLines().map(line => {
        val features = line.split(" ").map(_.toDouble).toList
        new LabelledInstance[Double](features.init.zipWithIndex.map {
            case (weight, id) => new WeightedFeature(id, weight)
        }.toIndexedSeq, features.last)
    }).toSeq)
    //val data1 = (new NormalizingInstancePreprocessor[LabelledInstance[Double]]() :+ new NormalizingInstancePreprocessor[LabelledInstance[Double]]()).apply(data)
    val data2 = (new LinearRegressionTrainer(0.0)).apply(data);
    for (i <- data2)
        println(i)
    //    println(new ClassifierCrossValidator[Int](0.2, 50, DefaultSeedGenerator.getInstance(), MersenneTwistProvider).apply(new APrioryKnowledgeClassifierTrainer[Int](), data))

}
