package ru.ispras.modis.flint.regression

import ru.ispras.modis.flint.instances.LabelledInstance
import org.apache.spark.rdd.RDD
import scalala.tensor.dense.DenseVector

/**
 * Created with IntelliJ IDEA.
 * User: saylars
 * Date: 06.12.13
 * Time: 19:44
 * To change this template use File | Settings | File Templates.
 */
class SimpleStepper (private val lambda: Double = 1e-5,
                     private val shift: Double = 0.1) extends Stepper{


    def nextStep(data: RDD[LabelledInstance[Double]],
                 model : LinearRegressionModelUnderTraining,
                 grad : DenseVector[Double],
                 oldSquareErr: Double) = {

        var shiftedModel = new LinearRegressionModelUnderTraining(model.weights :+ ((grad :- (model.weights  * lambda)) * shift))
        var newSquareErr = data.map(point => shiftedModel.squareError(point)).reduce(_ + _)
        (shiftedModel, newSquareErr)
    }
}
