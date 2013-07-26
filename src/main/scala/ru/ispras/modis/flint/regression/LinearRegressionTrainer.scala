package ru.ispras.modis.flint.regression

import spark.RDD
import ru.ispras.modis.flint.instances.LabelledInstance
import org.apache.commons.lang.NotImplementedException

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 11:13 PM
 */
class LinearRegressionTrainer extends RegressionTrainer {
    def apply(data: RDD[LabelledInstance[Double]]): RegressionModel = throw new NotImplementedException()
}
