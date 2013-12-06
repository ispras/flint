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


class ArmijoStepper(private val lambda: Double = 1e-5,
                    private val alpha: Double = 1.0,
                    private val delta: Double = 0.5,
                    private val eps: Double = 1e-3) extends Stepper{

    def nextStep(data: RDD[LabelledInstance[Double]],
                 model : LinearRegressionModelUnderTraining,
                 grad : DenseVector[Double],
                 oldSquareErr: Double) = {

        var shift = alpha

        val normGrad = grad.norm(2)

        var shiftedModel = new LinearRegressionModelUnderTraining(DenseVector.zeros[Double](model.weights.length))  //

        var newSquareErr = 0d

        do {

            shift *= delta

            shiftedModel = new LinearRegressionModelUnderTraining(model.weights :+ ((grad :- (model.weights  * lambda)) * shift))

            newSquareErr = data.map(point => shiftedModel.squareError(point)).reduce(_ + _)

        } while (newSquareErr - oldSquareErr > eps * shift * normGrad)

        (shiftedModel, newSquareErr)
    }
}
