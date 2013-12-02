package flint.src.main.scala.ru.ispras.modis.flint.classifiers.NaiveBayes

import ru.ispras.modis.flint.classifiers.{DensityEstimation, Classifier, ClassifierTrainer}
import ru.ispras.modis.flint.instances
import ru.ispras.modis.flint.instances
import ru.ispras.modis.flint.instances.LabelledInstance
import scala.math.log
import spark.RDD

/**
 * Created with IntelliJ IDEA.
 * User: lena
 * Date: 18.11.13
 * Time: 17:41
 * To change this template use File | Settings | File Templates.
 */
class BayesClassifierTrainer[LabelType: ClassManifest](private val data: RDD[LabelledInstance[LabelType]], sample: DensityEstimation[LabelType]) extends ClassifierTrainer[LabelType] {

  override def apply(data: RDD[LabelledInstance[LabelType]]) : Classifier[LabelType] = {

    val freq = new BayesEstimator[LabelType](data).frequency

      val size = freq.map(_._2).sum

      val labelProb: Map[LabelType,Double] = freq.map{case(label, freq) => (label, math.log(freq.toDouble / size))}.toMap

      new BayesClassifier[LabelType](labelProb,sample)


  }

}
