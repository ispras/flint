package ru.ispras.modis.flint.classifiers

import ru.ispras.modis.flint.instances.Instance

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/26/13
 * Time: 8:49 PM
 */
trait DensityEstimation[LabelType] {
    def apply(label: LabelType, instance : Instance): Double
}
