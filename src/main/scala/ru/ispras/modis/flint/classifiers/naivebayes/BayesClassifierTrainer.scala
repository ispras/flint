package ru.ispras.modis.flint.classifiers.naivebayes

import ru.ispras.modis.flint.classifiers.{DensityEstimator, DensityEstimation, Classifier, ClassifierTrainer}
import ru.ispras.modis.flint.instances.LabelledInstance
import scala.math.log
import spark.RDD

class BayesClassifierTrainer[LabelType: ClassManifest](private val data: RDD[LabelledInstance[LabelType]], sample: DensityEstimator[LabelType]) extends ClassifierTrainer[LabelType] {



  override def apply(data: RDD[LabelledInstance[LabelType]]) : Classifier[LabelType] = {
      val labelCountValue = data.map(instance => instance.label).countByValue()

      val trainData = new BayesEstimator[LabelType].apply(data)

      val size = labelCountValue.map(_._2).sum

      val aprioryProbability: Map[LabelType,Double] = labelCountValue.map{case(label, freq) => (label, math.log(freq.toDouble/size))}.toMap

      new BayesClassifier[LabelType](aprioryProbability,trainData)



  }

}
