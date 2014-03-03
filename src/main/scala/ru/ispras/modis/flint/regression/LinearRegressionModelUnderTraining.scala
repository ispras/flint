package ru.ispras.modis.flint.regression

import ru.ispras.modis.flint.instances.LabelledInstance
import breeze.linalg.DenseVector

/**
 * Created with IntelliJ IDEA.
 * User: saylars
 * Date: 06.12.13
 * Time: 18:52
 * To change this template use File | Settings | File Templates.
 */
class LinearRegressionModelUnderTraining(weights: DenseVector[Double]) extends LinearRegressionModel(weights){

    def squareError(instance: LabelledInstance[Double]): Double = {
        val residual = instance.label - apply(instance)
        residual * residual
    }
}
