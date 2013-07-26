package ru.ispras.modis.flint.datapreprocessors

import spark.RDD
import ru.ispras.modis.flint.instances.Instance

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 7:52 PM
 */
trait InstancePreprocessor {
    def apply(data: RDD[Instance]): RDD[Instance]
}

object InstancePreprocessor {
    implicit def InstancePreprocessor2LabelledInstancePreprocessor[LabelType](preprocessor: InstancePreprocessor) =
        new InstancePreprocessorBasedLabelledInstancePreprocessor[LabelType](preprocessor)

    implicit def InstancePreprocessor2InstancePreprocessorComposition(preprocessor: InstancePreprocessor) = new InstancePreprocessorComposition(Seq(preprocessor))
}