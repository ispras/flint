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

class LinearRegressionTrainer(private val l2regularization /*l2regularisation is either a term in functional or an approach. This value is a regularisation parameter. It's also often referred as 'lambda'*/ : Double,
                              private val seedGenerator: SeedGenerator,
                              private val randomGeneratorProvider: RandomGeneratorProvider) extends RegressionTrainer with Stepper /*wtf?*/ {

    def apply(data: RDD[LabelledInstance[Double]]): LinearRegressionModel = {

        val random = randomGeneratorProvider(seedGenerator)

        val dataSize = data.count()

        val randArray = DenseVector.zeros[Double](data.first().length)
        for (i <- 0 until randArray.length)
            randArray(i) = random.nextDouble()
        // DenseVector.rand() was not good enough?

        var currentModel = new LinearRegressionModel(randArray)

        var newSquareErr = data.map(point => {
            // code duplication. I see the same stuff in stepper.
            val err = point.label - currentModel.predicts(point) // do not repeat yourself
            err * err
        }).reduce(_ + _)

        var oldSquareErr = 0.0 //it's better to use "0d" for double and 0f for float since it is more explicit

        do {

            val gradient: DenseVector[Double] = data.map {
                point => {

                    val sum = (point.label - currentModel.predicts(point)) / dataSize

                    val shift = DenseVector.zeros[Double](point.length)
                    for (j <- 0 until point.length)
                        shift(j) = point(j).featureWeight * sum

                    shift
                }
            }.reduce(_.:+(_)) // wtf??  _+_??

            oldSquareErr = newSquareErr

            val tmp = nextStep(data, currentModel, gradient, l2regularization, oldSquareErr)

            // you should have written val (newSquareErr, currentModel) = nextStep(...) -- read more about pattern matching
            // and I suggest to swap returned values in nextStep. It's common practice to return the most complex object first.
            newSquareErr = tmp._1
            currentModel = tmp._2

        } while (oldSquareErr - newSquareErr > 0.001 /*this is a magic value. You'd better make it a constructor parameter*/ )

        currentModel
    }
}
