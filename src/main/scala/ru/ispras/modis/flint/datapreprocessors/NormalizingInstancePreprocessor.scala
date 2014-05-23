package ru.ispras.modis.flint.datapreprocessors

import org.apache.spark.rdd.RDD
import ru.ispras.modis.flint.instances.{InstanceBuilder, Instance}
import scala.reflect.ClassTag

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 7:54 PM
 */
class NormalizingInstancePreprocessor[T <: Instance] extends InstancePreprocessor[T] with Serializable{  //FIXME Serializable

    def r(x:T, y:T):T= {
        x.asInstanceOf[Instance].+(y.asInstanceOf[Instance]).asInstanceOf[T]
    }

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

        //val r: (T) => T = T.plus

        val dataSetSize = data.count()
        val meanValues = data.reduce(r).divByAlpha(dataSetSize) //dosent work and i dont know why
        // it does not work because you are to guarantee that every T<: Instance (every inheritor of Instance)
        // implements method + (T,T) => T
        // I have no idea how to achieve that.
        // If you want to, come along with me my friend! say the words and you'll be free... ^W^W^W^W^W^W^W^W^W^W^W
        // If you want to, just cast Instances to SparseVectors.
        data.map(instance => instanceBuilder(instance, (instance - meanValues).divByAlpha((instance - meanValues).normL2()))) // same
    }
}
