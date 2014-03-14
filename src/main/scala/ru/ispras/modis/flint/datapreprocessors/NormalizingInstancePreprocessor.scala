package ru.ispras.modis.flint.datapreprocessors

import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext.rddToPairRDDFunctions
import ru.ispras.modis.flint.instances.{InstanceFactory, InstanceBuilder, WeightedFeature, Instance}
import scala.reflect.ClassTag

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 7:54 PM
 */
class NormalizingInstancePreprocessor[T <: Instance] extends InstancePreprocessor[T] {
    def apply(data: RDD[T])(implicit manifest: ClassTag[T], instanceBuilder: InstanceBuilder[T]): RDD[T] = {
/*
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
*/
        val dataSetSize = data.count()
        val meanValues = data.reduce(_ + _).divByAlpha(dataSetSize)
        data.map(instance => (instance - meanValues).divByAlpha((instance - meanValues).norml2()))
    }
}
