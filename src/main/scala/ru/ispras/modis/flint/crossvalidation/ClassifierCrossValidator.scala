package ru.ispras.modis.flint.crossvalidation

import org.apache.spark.rdd.RDD
import ru.ispras.modis.flint.classifiers.ClassifierTrainer
import org.uncommons.maths.random.SeedGenerator
import ru.ispras.modis.flint.random.RandomGeneratorProvider
import ru.ispras.modis.flint.instances.LabelledInstance

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/23/13
 * Time: 10:08 PM
 */
class ClassifierCrossValidator[LabelType: ClassManifest](private val fractionToTrainOn: Double,
                                                         private val numberOfIterations: Int,
                                                         private val seedGenerator: SeedGenerator,
                                                         private val randomGeneratorProvider: RandomGeneratorProvider) extends CrossValidationUtils[LabelType] {
    def apply(classifierTrainer: ClassifierTrainer[LabelType], data: RDD[LabelledInstance[LabelType]]) = {
        val labels = data.map(_.label).distinct().collect()

        CrossValidationResult.average((0 until numberOfIterations).map(iteration => {
            val (train, test) = split(data, fractionToTrainOn, randomGeneratorProvider(seedGenerator))
            makeIteration(classifierTrainer, train, test, labels)
        }))
    }

    private def makeIteration(classifierTrainer: ClassifierTrainer[LabelType],
                              train: RDD[LabelledInstance[LabelType]],
                              test: RDD[LabelledInstance[LabelType]],
                              labels: Seq[LabelType]) = {
        val classifier = classifierTrainer(train)
        val results = test.map(instance => (instance.label, classifier(instance).label)).cache()

        new CrossValidationResult[LabelType](labels.map(label => {
            val tp = results.aggregate(0d)((sum, pair) => if (pair._1 == pair._2 && pair._2 == label) sum + 1 else sum, combOp)
            val tn = results.aggregate(0d)((sum, pair) => if (pair._1 == pair._2 && pair._2 != label) sum + 1 else sum, combOp)
            val fp = results.aggregate(0d)((sum, pair) => if (pair._1 != pair._2 && pair._2 == label) sum + 1 else sum, combOp)
            val fn = results.aggregate(0d)((sum, pair) => if (pair._1 != pair._2 && pair._2 != label) sum + 1 else sum, combOp)
            (label, new CrossValidationResultByOneLabel(tp, tn, fp, fn))
        }).toMap,
            results.aggregate(0d)((sum, pair) => if (pair._1 == pair._2) sum + 1 else sum, combOp) / results.count
        )
    }
}
