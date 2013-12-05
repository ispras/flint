package ru.ispras.modis.flint.regression

import org.apache.spark.rdd.RDD
import ru.ispras.modis.flint.instances.LabelledInstance
import scalala.tensor.dense.DenseVector

/**
 * Created with IntelliJ IDEA.
 * User: saylars
 * Date: 03.12.13
 * Time: 17:44
 * To change this template use File | Settings | File Templates.
 */


class ArmijoStepper(private val lambda: Double = 1e-6,
                    private val alpha: Double = 1.0,
                    private val delta: Double = 0.5,
                    private val eta: Double = 1e-3) extends Stepper{     // eta? wat's that?


    def nextStep(data: RDD[LabelledInstance[Double]],
                 model : LinearRegressionModel,
                 grad : DenseVector[Double],
                 oldSquareErr: Double) = {

        var shift = alpha

        val normGrad = (grad :* grad).sum         //FIXME grad.norm(2) was not good enough?    and it's squared norm

        var shiftedModel = new LinearRegressionModel(DenseVector.zeros[Double](model.weights.length))  //

        var newSquareErr = 0d

        do {

            shift *= delta

            shiftedModel = new LinearRegressionModel(model.weights :+ ((grad :- (model.weights  * lambda)) * shift))

            newSquareErr = data.map(point => shiftedModel.squareError(point)).reduce(_ + _)

        } while (newSquareErr - oldSquareErr > eta * shift * normGrad)

        (shiftedModel, newSquareErr)
    }
}
