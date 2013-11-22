package ru.ispras.modis.flint.crossvalidation

import ru.ispras.modis.flint.regression.RegressionTrainer
import org.apache.spark.rdd.RDD
import ru.ispras.modis.flint.instances.LabelledInstance
import org.uncommons.maths.random.SeedGenerator
import ru.ispras.modis.flint.random.RandomGeneratorProvider

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 11:14 PM
 */

object RegressionCrossValidator extends CrossValidationUtils[Double]{
    private def makeIteration(regressionTrainer: RegressionTrainer, train: RDD[LabelledInstance[Double]], test: RDD[LabelledInstance[Double]]) = {
        val model = regressionTrainer(train)
        test.map(labelled => (labelled.label, model.predicts(labelled))).map {
            case (actual, predicted) => math.pow(actual - predicted, 2)
        }.aggregate(0d)((sum, element) => sum + element, combOp) / test.count()
    }

}

class RegressionCrossValidator(private val fractionToTrainOn: Double,
                               private val numberOfIterations: Int,
                               private val seedGenerator: SeedGenerator,
                               private val randomGeneratorProvider: RandomGeneratorProvider) extends CrossValidationUtils[Double] {
    def apply(regressionTrainer: RegressionTrainer, data: RDD[LabelledInstance[Double]]) = {
        val rmse = (0 until numberOfIterations).map(iteration => {
            val (train, test) = split(data, fractionToTrainOn, randomGeneratorProvider(seedGenerator))
            RegressionCrossValidator.makeIteration(regressionTrainer, train, test)
        }).sum / numberOfIterations

        new RegressionCrossValidationResult(rmse)
    }

}