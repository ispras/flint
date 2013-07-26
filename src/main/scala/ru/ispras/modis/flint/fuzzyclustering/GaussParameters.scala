package ru.ispras.modis.flint.fuzzyclustering

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/27/13
 * Time: 12:12 AM
 */
class GaussParameters(meanValue: Double, sigmaValue: Double) extends DistributionParameters(Array(meanValue, sigmaValue)) {
    def mean = parameters(0)

    def sigma = parameters(1)
}
