package ru.ispras.modis.flint.regression

import ru.ispras.modis.flint.instances.Instance

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 11:10 PM
 */
trait RegressionModel extends Serializable{
    def apply(instance: Instance): Double
}
