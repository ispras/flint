package ru.ispras.modis.flint.classifiers

import org.apache.spark.rdd.RDD
import ru.ispras.modis.flint.instances.LabelledInstance

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/23/13
 * Time: 9:14 PM
 */
trait ClassifierTrainer[LabelType] {
    def apply(data: RDD[LabelledInstance[LabelType]]): Classifier[LabelType]
}
