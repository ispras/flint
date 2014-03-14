package ru.ispras.modis.flint.strictclustering

import ru.ispras.modis.flint.instances.Instance

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/27/13
 * Time: 12:00 AM
 */
object EuclideanDistance extends Distance with Serializable {
    def apply(first: Instance, second: Instance): Double = {
        val t = first - second
        math.pow(t.norml2(), 0.5)
    }

}
