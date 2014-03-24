package ru.ispras.modis.flint.regression

import ru.ispras.modis.flint.instances.Instance
import breeze.linalg.DenseVector

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 11:11 PM
 */
class LinearRegressionModel(private[regression] val weights: DenseVector[Double]) extends RegressionModel {

    def apply(instance: Instance): Double = {
        var sum = 0.0
        var offset = 0 // wtf? don't you reassign it? and may your god damn attention to IDEA warnings.
        while (offset < instance.activeSize) {
            val index = instance.indexAt(offset)
            val value = instance.valueAt(offset)
            if (index >= weights.length)
                return 0 // how to throw IndexOutOfBoundsException
            // like everything else -- throw new IndexOutOfBoundsException
            else
                sum += weights(index) * value
        }
        sum
    }

}
