package ru.ispras.modis.flint.datapreprocessors

import spark.RDD
import ru.ispras.modis.flint.instances.LabelledInstance

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 10:33 PM
 */
class LabelledInstancePreprocessorComposition[LabelType](private val preprocessors: Seq[LabelledInstancePreprocessor[LabelType]]) extends LabelledInstancePreprocessor[LabelType] {
    def apply(data: RDD[LabelledInstance[LabelType]]): RDD[LabelledInstance[LabelType]] = preprocessors.foldLeft(data)((data, preprocessor) => preprocessor(data))

    def :+(preprocessor: LabelledInstancePreprocessor[LabelType]) = new LabelledInstancePreprocessorComposition[LabelType](preprocessors :+ preprocessor)
}
