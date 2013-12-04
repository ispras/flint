package ru.ispras.modis.flint.classifiers.naivebayes

import ru.ispras.modis.flint.classifiers.{DensityEstimator, DensityEstimation}
import ru.ispras.modis.flint.instances.Instance

class BinaryEstimation[LabelType](private val logFeatureProbability: Map[(LabelType,Int,Boolean), Double]) extends DensityEstimation[LabelType] with Serializable{

  override def apply(label: LabelType,instance: Instance) : Double = {

           instance.foldLeft(0.0)((result,feature) => result + logFeatureProbability(label,feature.featureId,feature.featureWeight))


    }


}
