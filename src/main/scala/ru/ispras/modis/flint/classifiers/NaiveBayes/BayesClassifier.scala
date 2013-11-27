package flint.src.main.scala.ru.ispras.modis.flint.classifiers.NaiveBayes

import ru.ispras.modis.flint.classifiers.{ClassifierTrainer, ClassificationResult, Classifier, DensityEstimation}
import ru.ispras.modis.flint.instances.{LabelledInstance, Instance}
import org.apache.spark.rdd.RDD
import scala.math.log
/**
 * Created with IntelliJ IDEA.
 * User: lena
 * Date: 18.11.13
 * Time: 13:44
 * To change this template use File | Settings | File Templates.
 */


class BayesClassifier[LabelType](frequency: (LabelType,Long)) extends Classifier[LabelType] {

   def classify(labelProb: Map[LabelType,Double], sample: DensityEstimation[LabelType]) = {


   }
}