package ru.ispras.modis.flint.regression

import spark.RDD
import ru.ispras.modis.flint.instances.LabelledInstance

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 11:12 PM
 */
trait RegressionTrainer {
    def apply(data: RDD[LabelledInstance[Double]]): RegressionModel
}
