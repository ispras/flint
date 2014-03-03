package ru.ispras.modis.flint.regression

import ru.ispras.modis.flint.instances.LabelledInstance
import org.apache.spark.rdd.RDD
import ru.ispras.modis.flint.random.RandomGeneratorProvider
import org.uncommons.maths.random.SeedGenerator
import breeze.linalg.DenseVector
import org.uncommons.maths.random.DefaultSeedGenerator
import ru.ispras.modis.flint.random.MersenneTwistProvider

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 11:13 PM
 */

class LinearRegressionTrainer(private val eps: Double = 1e-2,
                              private val maxStepNum: java.lang.Integer = 500,
                              private val stepper:Stepper = new ArmijoStepper,
                              private val seedGenerator: SeedGenerator = DefaultSeedGenerator.getInstance(),
                              private val randomGeneratorProvider: RandomGeneratorProvider = MersenneTwistProvider) extends RegressionTrainer{

    def apply(data: RDD[LabelledInstance[Double]]): LinearRegressionModel = {

        val random = randomGeneratorProvider(seedGenerator)

        val dataSize = data.count()

        val numDimensions = data.reduce(
            (x, y) => if (x.last.featureId > y.last.featureId) x else y
        ).last.featureId + 1

        val randArray = DenseVector.zeros[Double](numDimensions)
        for (i <- 0 until randArray.length)
            randArray(i) = random.nextDouble()

        var currentModel = new LinearRegressionModelUnderTraining(randArray)

        var newSquareErr = data.map(point => currentModel.squareError(point)).reduce(_ + _)

        var oldSquareErr = 0d

        var i = 0

        do {

            i += 1

            val gradient: DenseVector[Double] = data.map {
                point => {

                    val sum = (point.label - currentModel(point)) / dataSize

                    val shift = DenseVector.zeros[Double](numDimensions)
                    for (j <- point)
                        shift(j.featureId) = j.featureWeight * sum

                    shift
                }
            }.reduce(_ + _)

            oldSquareErr = newSquareErr

            val tmp =  stepper.nextStep(data, currentModel, gradient, oldSquareErr)

            newSquareErr = tmp._2
            currentModel = tmp._1

        } while (i < maxStepNum && oldSquareErr - newSquareErr > eps)

        currentModel
    }
}
