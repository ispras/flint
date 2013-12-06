package ru.ispras.modis.flint.regression

import ru.ispras.modis.flint.instances.LabelledInstance
import scalala.tensor.dense.DenseVector

/**
 * Created with IntelliJ IDEA.
 * User: saylars
 * Date: 06.12.13
 * Time: 18:52
 * To change this template use File | Settings | File Templates.
 */
class LinearRegressionModelUnderTraining(weights: DenseVector[Double]) extends LinearRegressionModel(weights){

    def squareError(instance: LabelledInstance[Double]): Double = {   // a class should be able to do one simple thing.
        /**
         * Probably, a good solution is to crate an inheritor: LinearRegressionUnderTraining.
         * And create LinearRegressionModel instance only in the very end of training method.
         */
        val diff /*you mean, residual?*/ = instance.label - apply(instance)
        diff * diff
    }
}
