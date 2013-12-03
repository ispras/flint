package ru.ispras.modis.flint.classifiers.naivebayes

import ru.ispras.modis.flint.classifiers.{DensityEstimator, DensityEstimation, Classifier, ClassifierTrainer}
import ru.ispras.modis.flint.instances.LabelledInstance
import scala.math.log
import spark.RDD



class BayesClassifierTrainer[LabelType: ClassManifest](private val data: RDD[LabelledInstance[LabelType]], private val densityEstimator: DensityEstimator[LabelType]) extends ClassifierTrainer[LabelType] {

  override def apply(data: RDD[LabelledInstance[LabelType]]) : Classifier[LabelType] = {

    val labelCount = data.map(instance => instance.label).countByValue()

      val trainData = new BayesEstimator[LabelType].apply(data)

      val size = labelCount.map(_._2).sum

      val aprioryProbability: Map[LabelType,Double] = labelCount.map{case(label, freq) => (label, math.log(freq.toDouble/size))}.toMap

      new BayesClassifier[LabelType](aprioryProbability,trainData)
  }

}
