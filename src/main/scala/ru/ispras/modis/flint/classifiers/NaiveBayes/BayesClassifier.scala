package flint.src.main.scala.ru.ispras.modis.flint.classifiers.NaiveBayes // package name from flint?

import ru.ispras.modis.flint.classifiers.{ClassifierTrainer, ClassificationResult, Classifier, DensityEstimation}
import ru.ispras.modis.flint.instances.{LabelledInstance, Instance}
/**
 * Created with IntelliJ IDEA.
 * User: lena
 * Date: 18.11.13
 * Time: 13:44
 * To change this template use File | Settings | File Templates.
 */


class BayesClassifier[LabelType](/*private val*/labelProb: Map[LabelType,Double], /*private val*/sample: DensityEstimation[LabelType]) extends Classifier[LabelType] {
                                                /*apriory probability? Don't use shorthands*/   /*you should have named it densityEstimator*/

  override def apply(instance: Instance): ClassificationResult[LabelType] = {

    val result = labelProb.map{case (label, aprioryProb) => (label, aprioryProb + sample.apply(label, instance))}.maxBy(_._2); //omit semicolons

    new ClassificationResult[LabelType](result._1,Some(result._2 /*it's better to return probability, not log*/))

  }
}
