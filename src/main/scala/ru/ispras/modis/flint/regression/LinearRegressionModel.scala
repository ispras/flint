package ru.ispras.modis.flint.regression

import ru.ispras.modis.flint.instances.Instance
import scalala.tensor.dense.DenseVector
import ru.ispras.modis.flint.instances.LabelledInstance

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 11:11 PM
 */
class LinearRegressionModel(private[regression] val weights: DenseVector[Double]) extends RegressionModel {

    def apply(instance: Instance): Double = {
        var sum = 0.0
        for (i <- instance)
            sum  += weights(i.featureId) * i.featureWeight
        sum
    }

    def squareError(instance: LabelledInstance[Double]): Double = {
        val diff = instance.label - apply(instance)
        diff * diff
    }
}
