package ru.ispras.modis.flint.classifiers

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/26/13
 * Time: 8:49 PM
 */
trait DensityEstimator {
    def apply(value: Double): Double
}
