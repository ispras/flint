package flint.src.main.scala.ru.ispras.modis.flint.classifiers.NaiveBayes

import org.apache.spark.rdd.RDD
import ru.ispras.modis.flint.classifiers.{DensityEstimation, Classifier, ClassifierTrainer}
import ru.ispras.modis.flint.instances
import ru.ispras.modis.flint.instances
import scala.math.log
import ru.ispras.modis.flint.instances.LabelledInstance
import spark.RDD

/**
 * Created with IntelliJ IDEA.
 * User: lena
 * Date: 18.11.13
 * Time: 17:41
 * To change this template use File | Settings | File Templates.
 */
class BayesClassifierTrainer[LabelType](private val sample: RDD[LabelledInstance[LabelType]]) extends ClassifierTrainer[LabelType] {

  override def apply(sample: RDD[LabelledInstance[LabelType]]) : Classifier[LabelType] = {

    val frequency = sample.map(instance => instance.label).countByValue()
    val size = frequency.map(_._2).sum
      frequency.map{case(label, freq) => (label, math.log(freq.toDouble / size)) }

    new BayesClassifier[LabelType](frequency)


  }

}
