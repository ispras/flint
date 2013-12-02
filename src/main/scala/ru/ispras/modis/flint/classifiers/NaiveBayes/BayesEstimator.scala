package flint.src.main.scala.ru.ispras.modis.flint.classifiers.NaiveBayes

import ru.ispras.modis.flint.classifiers.{DensityEstimation, DensityEstimator}
import ru.ispras.modis.flint.instances.{LabelledInstance, Feature}
import scala.math.log
import spark.RDD


class BayesEstimator[LabelType: ClassManifest](data: RDD[LabelledInstance[LabelType]]) extends DensityEstimator[LabelType]{

 val frequency = data.map(instance => instance.label).countByValue()

  override def apply(data: RDD[LabelledInstance[LabelType]]) : DensityEstimation[LabelType] = {

    val LabelIdWeight :Map[(LabelType,Int,Double), Long] = data.flatMap(instance =>
      instance.map(feature => (instance.label, feature.featureId, feature.featureWeight ))).countByValue().toMap

    val labelidProb :Map[(LabelType,Int,Double), Double] = LabelIdWeight.map{
      case ((label, featureId, weight), value) => ((label, featureId,weight),log(value.toDouble/frequency(label)))}

   new BayesEstimation[LabelType](labelidProb)
 }

}
