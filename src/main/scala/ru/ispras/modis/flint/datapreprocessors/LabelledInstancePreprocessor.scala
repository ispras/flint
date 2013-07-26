package ru.ispras.modis.flint.datapreprocessors

import spark.RDD
import ru.ispras.modis.flint.instances.LabelledInstance

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 10:24 PM
 */
trait LabelledInstancePreprocessor[LabelType] {
    def apply(data: RDD[LabelledInstance[LabelType]]): RDD[LabelledInstance[LabelType]]
}

object LabelledInstancePreprocessor {
    implicit def LabelledInstancePreprocessor2LabelledInstancePreprocessorComposition[LabelType](preprocessor: LabelledInstancePreprocessor[LabelType]) =
        new LabelledInstancePreprocessorComposition[LabelType](Seq(preprocessor))
}
