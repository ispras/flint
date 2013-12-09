package ru.ispras.modis.flint.classifiers.naivebayes

import ru.ispras.modis.flint.classifiers.{DensityEstimation, DensityEstimator}
import ru.ispras.modis.flint.instances.{LabelledInstance, Feature}
import scala.math.log
import spark.RDD
import org.scalacheck.Prop.{True, False}
import scala.math.abs
class BayesEstimator[LabelType: ClassManifest] extends DensityEstimator[LabelType]{

  override def apply(data: RDD[LabelledInstance[LabelType]]) : DensityEstimation[LabelType] = {

    val labelCount = data.map(instance => instance.label).countByValue()

    val labelIdWeight: Map[(LabelType,Int,Double),Long] = data.flatMap(instance =>
      instance.map(feature =>  (instance.label, feature.featureId, feature.featureWeight))).countByValue().toMap

    labelIdWeight.map{case ((label,id,weight),value) =>

      if(!labelIdWeight.contains((label,id,1.0-weight))) {labelIdWeight += ((label,id,weight) -> 1.0)}} //this don't compile because of +=. how can i add the pair to map another way?

     val eps = 0.000001

     val addEps = labelIdWeight.map{case ((label,id,weight),prob) => ((label,id, weight + eps),prob)}

     val labelIdFeatureBoolean = labelIdWeight.map{case((label,id,weight),prob) =>

        if(abs(weight - 1.0) < eps) {((label,id,true),prob)} else {((label,id,false),prob)}}

    val featureLogProb  :Map[(LabelType,Int,Boolean), Double] = labelIdFeatureBoolean.map{case ((label, featureId, weight), value) =>

      if (value != 0) {((label, featureId,weight),log(value.toDouble/labelCount(label)))} else {((label, featureId,weight),log(1.0/labelCount(label)))}}


    new BinaryEstimation[LabelType](featureLogProb)
 }

}
