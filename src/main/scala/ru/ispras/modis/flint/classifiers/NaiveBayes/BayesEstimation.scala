package flint.src.main.scala.ru.ispras.modis.flint.classifiers.NaiveBayes

import ru.ispras.modis.flint.classifiers.{DensityEstimator, DensityEstimation}
import org.apache.spark.rdd.RDD
import ru.ispras.modis.flint.instances.Instance
import scala.math.log

/**
 * Created with IntelliJ IDEA.
 * User: lena
 * Date: 18.11.13
 * Time: 13:39
 * To change this template use File | Settings | File Templates.
 */
class BayesEstimation[LabelType](features: Map[(LabelType,Int,Double),Long]) extends DensityEstimation[LabelType] {

    override def apply(label: LabelType,featureId: Int, weight: Double) : Double = {

      val featureSum = features.map(_._2).sum

      val featureprob = features.map{case (feature, weight) => log(weight.toDouble/featureSum)}

      val prioritySum = featureprob.foldLeft(Double)((sum,element: Double) => sum + element)

    }

}
