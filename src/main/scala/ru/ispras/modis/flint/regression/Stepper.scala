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

    def nextStep(data: RDD[LabelledInstance[Double]],
                 model : LinearRegressionModel, //FIXME
                 grad : DenseVector[Double],
                 oldSquareErr: Double) : (LinearRegressionModel, Double)
}
