package ru.ispras.modis.flint.classifier

import spark.SparkContext
import scala.io.Source
import java.io.File
import ru.ispras.modis.flint.datapreprocessors.NormalizingInstancePreprocessor
import ru.ispras.modis.flint.instances.{WeightedFeature, LabelledInstance}

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/23/13
 * Time: 9:46 PM
 */
object Test extends App {
    val sc = new SparkContext("local[4]", "")

    val data = sc.parallelize(Source.fromFile(new File("/home/valerij/iris.csv")).getLines().map(line => {
        val label :: features = line.split(",").map(_.toInt).toList
        new LabelledInstance[Int](features.zipWithIndex.map {
            case (weight, id) => new WeightedFeature(id, weight)
        }.toIndexedSeq, label)
    }).toSeq)

    val data1 = (new NormalizingInstancePreprocessor[LabelledInstance[Int]]() :+ new NormalizingInstancePreprocessor[LabelledInstance[Int]]()).apply(data)


    //    println(new ClassifierCrossValidator[Int](0.2, 50, DefaultSeedGenerator.getInstance(), MersenneTwistProvider).apply(new APrioryKnowledgeClassifierTrainer[Int](), data))

}
