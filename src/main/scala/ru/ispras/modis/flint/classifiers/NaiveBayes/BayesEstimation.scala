package flint.src.main.scala.ru.ispras.modis.flint.classifiers.NaiveBayes

import ru.ispras.modis.flint.classifiers.{DensityEstimator, DensityEstimation}
import org.apache.spark.rdd.RDD

/**
 * Created with IntelliJ IDEA.
 * User: lena
 * Date: 18.11.13
 * Time: 13:39
 * To change this template use File | Settings | File Templates.
 */
class BayesEstimation[LabelType](sample: RDD[(LabelType,Int,Int)]) extends DensityEstimation[LabelType] {

    override def apply(label: LabelType,featureId: Int,value: Double) : Double = value

}
