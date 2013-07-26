package ru.ispras.modis.flint.classifiers

import ru.ispras.modis.flint.instances.Instance

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/23/13
 * Time: 9:06 PM
 */
trait Classifier[LabelType] extends Serializable {
    def apply(instance: Instance): ClassificationResult[LabelType]
}

