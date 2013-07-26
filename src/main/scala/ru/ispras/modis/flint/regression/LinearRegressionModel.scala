package ru.ispras.modis.flint.regression

import ru.ispras.modis.flint.instances.Instance
import org.apache.commons.lang.NotImplementedException

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 11:11 PM
 */
class LinearRegressionModel extends RegressionModel {
    def predicts(instance: Instance): Double = throw new NotImplementedException()
}
