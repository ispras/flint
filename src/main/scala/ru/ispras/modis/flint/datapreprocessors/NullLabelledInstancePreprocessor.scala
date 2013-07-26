package ru.ispras.modis.flint.datapreprocessors

import spark.RDD
import ru.ispras.modis.flint.instances.LabelledInstance

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 10:38 PM
 */
class NullLabelledInstancePreprocessor[LabelType] extends LabelledInstancePreprocessor[LabelType] {
    def apply(data: RDD[LabelledInstance[LabelType]]): RDD[LabelledInstance[LabelType]] = data
}
