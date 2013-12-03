package ru.ispras.modis.flint.classifiers.naivebayes

import ru.ispras.modis.flint.classifiers.{DensityEstimation, DensityEstimator}
import ru.ispras.modis.flint.instances.{LabelledInstance, Feature}
import scala.math.log
import spark.RDD

class BayesEstimator[LabelType: ClassManifest] extends DensityEstimator[LabelType]{

  override def apply(data: RDD[LabelledInstance[LabelType]]) : DensityEstimation[LabelType] = {

    val labelCount = data.map(instance => instance.label).countByValue()

    val labelIdWeight :Map[(LabelType,Int,Double), Long] = data.flatMap(instance =>
      instance.map(feature => (instance.label, feature.featureId, feature.featureWeight ))).countByValue().toMap

    val featureLogProb  :Map[(LabelType,Int,Double), Double] = labelIdWeight.map{case ((label, featureId, weight), value) => ((label, featureId,weight),log(value.toDouble/labelCount(label)))}

    new BayesEstimation[LabelType](featureLogProb)
 }

}
