package ru.ispras.modis.flint.classifiers

import ru.ispras.modis.flint.instances.LabelledInstance
import spark.RDD

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/26/13
 * Time: 8:51 PM
 */
trait DensityEstimatorTrainer[LabelType] {
    def apply(data: RDD[LabelledInstance[LabelType]]): DensityEstimator[LabelType]
}
