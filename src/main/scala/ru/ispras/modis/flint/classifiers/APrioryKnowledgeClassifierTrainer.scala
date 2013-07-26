package ru.ispras.modis.flint.classifiers

import spark.RDD
import ru.ispras.modis.flint.instances.LabelledInstance

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/23/13
 * Time: 9:24 PM
 */
class APrioryKnowledgeClassifierTrainer[LabelType: ClassManifest] extends ClassifierTrainer[LabelType] {
    def apply(data: RDD[LabelledInstance[LabelType]]): Classifier[LabelType] = {
        val (label, frequency) = data.map(_.label).countByValue().maxBy(_._2)
        new APrioryKnowledgeClassifier[LabelType](label, frequency.toDouble / data.count)
    }
}
