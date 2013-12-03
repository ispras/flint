package ru.ispras.modis.flint.regression

import ru.ispras.modis.flint.instances.Instance
import scalala.tensor.dense.DenseVector

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 11:11 PM
 */
class LinearRegressionModel(private[regression] val /*this data looks like weights*/ data: DenseVector[Double]) extends RegressionModel with Iterable[Double] {
    // why the hell is LinearRegressionModel Iterable? It's not a collection -- it's something that can predict an outcome for Instance
    def predicts(instance: Instance): Double = {
        var sum = 0.0
        for (i <- instance)
            sum += data(i.featureId) * i.featureWeight
        return sum // delete "return" -- just "sum"
    }

    def apply(idx: Int): Double = data(idx)

    def iterator: Iterator[Double] = data.iterator

    def length: Int = data.length // dimensionality, not length. And R U convinced in this method necessity?
}
