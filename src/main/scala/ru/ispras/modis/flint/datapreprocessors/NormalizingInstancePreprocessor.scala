package ru.ispras.modis.flint.datapreprocessors

import spark.RDD
import spark.SparkContext.rddToPairRDDFunctions
import ru.ispras.modis.flint.instances.{WeightedFeature, Instance}

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 7:54 PM
 */
class NormalizingInstancePreprocessor extends InstancePreprocessor {
    def apply(data: RDD[Instance]): RDD[Instance] = {
        val dataSetSize = data.count()
        val gropedByFeatureId: RDD[(Int, Seq[Double])] = data.flatMap(_.map(feature => (feature.featureId, feature.featureWeight))).groupByKey()

        val meanValues = gropedByFeatureId.map {
            case (featureId, weights) =>
                (featureId, weights.sum / dataSetSize)
        }.collect().toMap

        val rootMeanSquareDeviations = gropedByFeatureId.map {
            case (featureId, weights) =>
                (featureId,
                    math.sqrt(weights.foldLeft(0d)((sum, weight) => sum + math.pow(weight - meanValues.getOrElse(featureId, 0d), 2)) / dataSetSize))
        }.collect().toMap

        data.map(instance =>
            new Instance(instance.map(feature => new WeightedFeature(feature.featureId, (feature.featureWeight - meanValues(feature.featureId)) / rootMeanSquareDeviations(feature.featureId))))
        )
    }
}
