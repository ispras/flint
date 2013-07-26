package ru.ispras.modis.flint.datapreprocessors

import spark.RDD
import ru.ispras.modis.flint.instances.{Instance, LabelledInstance}

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 10:26 PM
 */
class InstancePreprocessorBasedLabelledInstancePreprocessor[LabelType](private val innerPreprocessor: InstancePreprocessor) extends LabelledInstancePreprocessor[LabelType] {
    def apply(data: RDD[LabelledInstance[LabelType]]) =
        innerPreprocessor(data.map(_.asInstanceOf[Instance])).map(_.asInstanceOf[LabelledInstance[LabelType]])
}
