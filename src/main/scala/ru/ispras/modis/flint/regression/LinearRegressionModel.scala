package ru.ispras.modis.flint.regression

import ru.ispras.modis.flint.instances.Instance

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 11:11 PM
 */
class LinearRegressionModel(private val data: Array[Double]) extends RegressionModel with Iterable[Double]{
    def predicts(instance: Instance): Double = {
        var sum = 0.0
        for (i <- instance)
            sum  += data(i.featureId) * i.featureWeight
        return sum
    }
    def copy() = new LinearRegressionModel(this.data.clone())
    def apply(idx: Int): Double = data(idx)
    def iterator: Iterator[Double] = data.iterator
    def length: Int = data.length
    def +(that: LinearRegressionModel) = new LinearRegressionModel((data zip that map {case (x, y) => x + y}).toArray)
    def *(that: Double) = new LinearRegressionModel(data.map(_ * that))
}
