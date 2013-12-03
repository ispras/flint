package ru.ispras.modis.flint.regression

import ru.ispras.modis.flint.instances.Instance
import scalala.tensor.dense.DenseVector

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 11:11 PM
 */
class LinearRegressionModel(private[regression] val data: DenseVector[Double]) extends RegressionModel with Iterable[Double]{

    def predicts(instance: Instance): Double = {
        var sum = 0.0
        for (i <- instance)
            sum  += data(i.featureId) * i.featureWeight
        return sum
    }

    def apply(idx: Int): Double = data(idx)

    def iterator: Iterator[Double] = data.iterator

    def length: Int = data.length
}
