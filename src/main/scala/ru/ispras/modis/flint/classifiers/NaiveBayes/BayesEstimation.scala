package flint.src.main.scala.ru.ispras.modis.flint.classifiers.NaiveBayes

import ru.ispras.modis.flint.classifiers.{DensityEstimator, DensityEstimation}
import ru.ispras.modis.flint.instances.Instance

/**
 * Created with IntelliJ IDEA.
 * User: lena
 * Date: 18.11.13
 * Time: 13:39
 * To change this template use File | Settings | File Templates.
 */
class BayesEstimation[LabelType](private val /*it's not sample*/ sample: Map[(LabelType,Int,Double), Double]) extends DensityEstimation[LabelType] {


    override def apply(label: LabelType,instance: Instance) : Double = {

          instance.map(feature => sample(label,feature.featureId,feature.featureWeight)).foldLeft(0.0)((result,current) => result + current)
          // you use foldLeft in order not to use map, but you use map and foldLeft. Write this with foldLeft only
    }


}
