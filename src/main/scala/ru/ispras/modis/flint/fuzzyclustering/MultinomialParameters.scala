package ru.ispras.modis.flint.fuzzyclustering

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/27/13
 * Time: 12:14 AM
 */
class MultinomialParameters(probabilitiesValues: Array[Double]) extends DistributionParameters(probabilitiesValues) {
    def probabilities = parameters
}
