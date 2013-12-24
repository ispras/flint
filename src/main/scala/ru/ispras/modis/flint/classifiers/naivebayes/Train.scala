package modis.flint.classifiers.naivebayes

import org.apache.spark.SparkContext
import scala.io.Source
import ru.ispras.modis.flint.instances.{BinaryFeature, WeightedFeature, LabelledInstance,Instance}

import ru.ispras.modis.flint.crossvalidation.ClassifierCrossValidator
import org.uncommons.maths.random.DefaultSeedGenerator
import ru.ispras.modis.flint.random.MersenneTwistProvider
import java.io.File
import ru.ispras.modis.flint.classifiers.naivebayes.BayesEstimator

object Train extends App {

  val sc = new SparkContext("local[4]","")

  val spect_dataset = Source.fromFile(new File("/Users/lena/Downloads/SPECT.train")).getLines()
       //spect_dataset.foreach(println)
  val data = sc.parallelize(spect_dataset.map(line => {

         val label :: features = line.split(",").map(_.toInt).toList

     new LabelledInstance[Int](features.map(_.toDouble).zipWithIndex.map{case (weight, id) => new WeightedFeature(id,weight)}.toIndexedSeq, label)}).toSeq)


   /*val test_data = Source.fromFile(new File("/Users/lena/Downloads/SPECT.train")).getLines()

    val datatest = sc.parallelize(test_data.map(line => {

    val features = line.split(",").map(_.toDouble).toList

      val feats = features.drop(1)

      new Instance(feats.zipWithIndex.map{case (weight, id) => new WeightedFeature(id,weight)}.toIndexedSeq)}).toSeq)*/

      val train = new BayesEstimator[Int]
            println(train)
     // val trainClassifier = new BayesClassifierTrainer[Int](train).apply(data) //return classifier

      //val classifier = datatest.map(instance => trainClassifier.apply(instance))

     // classifier.foreach(println)
 // new ClassifierCrossValidator[Int](0.8,60,new java.util.Random()).apply(new BayesClassifierTrainer[Int](train),data)


}