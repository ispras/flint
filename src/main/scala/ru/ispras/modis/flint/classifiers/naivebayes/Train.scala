package ru.ispras.modis.flint.classifiers.naivebayes

import spark.SparkContext
import scala.io.Source
import ru.ispras.modis.flint.instances.{BinaryFeature, WeightedFeature, LabelledInstance,Instance}
import java.io.File
import ru.ispras.modis.flint.crossvalidation.ClassifierCrossValidator

object Train extends App {

  val sc = new SparkContext("local[1]","")

  val spect_dataset = Source.fromFile(new File("/home/lena/heart_dataset/SPECT.train")).getLines()

  val data = sc.parallelize(spect_dataset.map(line => {

     val labelFeatures = line.split(",").map(_.toInt).toList

     val label = labelFeatures.head

     val features = labelFeatures.tail.map(_.toDouble)

     new LabelledInstance[Int](features.zipWithIndex.map{case (weight, id) => new WeightedFeature(id,weight)}.toIndexedSeq, label)}).toSeq)


    val test_data = Source.fromFile(new File("/home/lena/heart_dataset/SPECT.test")).getLines()

    val datatest = sc.parallelize(test_data.map(line => {

    val Features = line.split(",").map(_.toDouble).toList

        new Instance(Features.zipWithIndex.map{case (weight, id) => new WeightedFeature(id,weight)}.toIndexedSeq)}).toSeq)


      val train = new BayesEstimator[Int]

      val trainClassifier = new BayesClassifierTrainer[Int](data, train).apply(data) //return classifier

      val classifier = datatest.map(instance => trainClassifier.apply(instance))
      /*printf("the result", classifier.map(res => println(res)))
      new ClassifierCrossValidator[Int]().apply(new BayesClassifierTrainer[Int](data,train),data)   */


}