package ru.ispras.modis.flint.classifiers

import ru.ispras.modis.flint.instances.Instance

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/23/13
 * Time: 9:13 PM
 */
class APrioryKnowledgeClassifier[LabelType] private[classifiers](theMostCommonLabel: LabelType, confidence: Double) extends Classifier[LabelType] {
<<<<<<< HEAD




  def apply(instance: Instance): ClassificationResult[LabelType] = new ClassificationResult[LabelType](theMostCommonLabel, Some(confidence))
=======
 def apply(instance: Instance): ClassificationResult[LabelType] = new ClassificationResult[LabelType](theMostCommonLabel, Some(confidence))
>>>>>>> 2906509a764dd0d48a15847eb8b2b2513d79e631
}
