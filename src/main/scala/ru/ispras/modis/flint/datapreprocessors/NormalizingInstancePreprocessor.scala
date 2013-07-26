package ru.ispras.modis.flint.datapreprocessors

import spark.RDD
import spark.SparkContext.rddToPairRDDFunctions
import ru.ispras.modis.flint.instances.{InstanceBuilder, WeightedFeature, Instance}

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 7:54 PM
 */
class NormalizingInstancePreprocessor[T <: Instance] extends InstancePreprocessor[T] {
    def apply(data: RDD[T])(implicit arg0: ClassManifest[T], instanceBuilder: InstanceBuilder[T]): RDD[T] = {
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
            instanceBuilder(instance, instance.map(feature => new WeightedFeature(feature.featureId, (feature.featureWeight - meanValues(feature.featureId)) / rootMeanSquareDeviations(feature.featureId))))
        )
    }
}
