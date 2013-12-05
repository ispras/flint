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

    def squareError(instance: LabelledInstance[Double]): Double = {   // a class should be able to do one simple thing.
        /**
         * Probably, a good solution is to crate an inheritor: LinearRegressionUnderTraining.
         * And create LinearRegressionModel instance only in the very end of training method.
         */
        val diff /*you mean, residual?*/ = instance.label - apply(instance)
        diff * diff
    }
}
