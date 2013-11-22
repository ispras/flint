package ru.ispras.modis.flint.regression

import ru.ispras.modis.flint.instances.LabelledInstance
import org.apache.spark.rdd.RDD
import ru.ispras.modis.flint.random.RandomGeneratorProvider
import org.uncommons.maths.random.SeedGenerator
import scalala.tensor.dense.DenseVector

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 11:13 PM
 */

class LinearRegressionTrainer(private val l2regularization: Double,
                              private val seedGenerator: SeedGenerator,
                              private val randomGeneratorProvider: RandomGeneratorProvider) extends RegressionTrainer with Stepper{

    def apply(data: RDD[LabelledInstance[Double]]): LinearRegressionModel = {

        val random = randomGeneratorProvider(seedGenerator)

        val dataSize = data.count()

        val randArray = DenseVector.zeros[Double](data.first().length)
        for (i <- 0 until randArray.length)
            randArray(i) = random.nextDouble()

        var currentModel = new LinearRegressionModel(randArray)

        var newSquareErr = data.map(point => {
            val x = point.label - currentModel.predicts(point)
            x * x
        }).reduce(_ + _)

        var oldSquareErr = 0.0

        var i = 0

        do {

            val gradient: DenseVector[Double] = data.map {
                point => {
                    val sum = (point.label - currentModel.predicts(point)) / dataSize
                    val t = DenseVector.zeros[Double](point.length)
                    for (j <- 0 until point.length)
                        t(j) = point(j).featureWeight * sum
                    t
                }
            }.reduce(_.:+(_))

            oldSquareErr = newSquareErr

            val tmp =  nextStep(data, currentModel, gradient, l2regularization, oldSquareErr)

            newSquareErr = tmp._1
            currentModel = tmp._2
            i += 1
        } while (oldSquareErr - newSquareErr > 0.001)
        println(i)
        currentModel
    }
}
