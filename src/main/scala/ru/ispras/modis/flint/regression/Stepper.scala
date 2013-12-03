package ru.ispras.modis.flint.regression

import ru.ispras.modis.flint.instances.LabelledInstance
import org.apache.spark.rdd.RDD
import scalala.tensor.dense.DenseVector

/**
 * Created with IntelliJ IDEA.
 * User: saylars
 * Date: 15.11.13
 * Time: 16:08
 */
trait Stepper {
    // move implementation to inheritor

    def nextStep(data: RDD[LabelledInstance[Double]],
                 model: LinearRegressionModel,
                 grad: DenseVector[Double],
                 l2regularization: Double,
                 oldSquareErr: Double) = {

        var alpha = 1.0 // make it constuctor parameters
        val delta = 0.5

        val normGrad = (grad :* grad).sum // may be implemented more efficiently. Just for on vector with accumulator.


        var shiftedModel = new LinearRegressionModel(DenseVector.zeros[Double](model.length)) // ? Why do you

        var newSquareErr = 0.0

        do {

            alpha = alpha * delta

            shiftedModel = new LinearRegressionModel(model.data :+ ((grad :- (model.data * l2regularization)) * alpha))

            newSquareErr = data.map(point => {
                val err = point.label - shiftedModel.predicts(point)
                err * err
            }).reduce(_ + _)

        } while (newSquareErr - oldSquareErr > 0.001 /*to constructor*/ * alpha * normGrad)

        (newSquareErr, shiftedModel)
    }
}