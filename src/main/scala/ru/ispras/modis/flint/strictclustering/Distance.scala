package ru.ispras.modis.flint.strictclustring

import ru.ispras.modis.flint.instances.Instance

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/26/13
 * Time: 11:59 PM
 */
trait Distance {
    def apply(first: Instance, second: Instance): Double
}
