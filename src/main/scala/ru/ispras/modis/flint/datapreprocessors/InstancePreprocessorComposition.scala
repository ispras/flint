package ru.ispras.modis.flint.datapreprocessors

import spark.RDD
import ru.ispras.modis.flint.instances.Instance

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 10:30 PM
 */
class InstancePreprocessorComposition(private val preprocessors: Seq[InstancePreprocessor]) extends InstancePreprocessor {
    def apply(data: RDD[Instance]): RDD[Instance] = preprocessors.foldLeft(data)((data, preprocessor) => preprocessor(data))

    def :+(preprocessor: InstancePreprocessor) = new InstancePreprocessorComposition(preprocessors :+ preprocessor)
}
