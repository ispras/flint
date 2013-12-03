package ru.ispras.modis.flint.classifiers.naivebayes

import ru.ispras.modis.flint.classifiers.{ClassifierTrainer, ClassificationResult, Classifier, DensityEstimation}
import ru.ispras.modis.flint.instances.{LabelledInstance, Instance}
import scala.math.exp

class BayesClassifier[LabelType](private val aprioryProbability: Map[LabelType,Double], private val densityEstimator: DensityEstimation[LabelType]) extends Classifier[LabelType] {

  override def apply(instance: Instance): ClassificationResult[LabelType] = {

    val posteriorProb = aprioryProbability.map{case (label, aprioryProb) => (label, aprioryProb + densityEstimator.apply(label, instance))}.maxBy(_._2) //omit semicolons

    new ClassificationResult[LabelType](posteriorProb._1,Some(exp(posteriorProb._2)))

  }
}