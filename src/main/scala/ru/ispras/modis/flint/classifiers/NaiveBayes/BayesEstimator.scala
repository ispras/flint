package flint.src.main.scala.ru.ispras.modis.flint.classifiers.NaiveBayes

import flint.src.main.scala.ru.ispras.modis.flint.classifiers.{DensityEstimation, DensityEstimator},flint.src.main.scala.ru.ispras.modis.flint.instances.LabelledInstance
import ru.ispras.modis.flint.classifiers
import ru.ispras.modis.flint.instances
import ru.ispras.modis.flint.instances.Feature
import org.apache.spark.rdd.RDD
import scala.math.log
import java.beans.FeatureDescriptor


class BayesEstimator[LabelType](data: RDD[LabelledInstance[LabelType]]) extends DensityEstimator[LabelType]{

 override def apply(data: RDD[LabelledInstance[LabelType]]) : DensityEstimation[LabelType] = {



val LabelIdWeight = data.flatMap(instance => instance.map(feature => (instance.label, feature.featureId, feature.featureWeight ))).countByValue()


 }

}
