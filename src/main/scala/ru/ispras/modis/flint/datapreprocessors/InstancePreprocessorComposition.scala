package ru.ispras.modis.flint.datapreprocessors

import spark.RDD
import ru.ispras.modis.flint.instances.{InstanceBuilder, Instance}

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 10:30 PM
 */
class InstancePreprocessorComposition[T <: Instance](private val preprocessors: Seq[InstancePreprocessor[T]]) extends InstancePreprocessor[T] {
    def apply(data: RDD[T])(implicit arg0: ClassManifest[T], instanceBuilder: InstanceBuilder[T]): RDD[T]
    = preprocessors.foldLeft(data)((data, preprocessor) => preprocessor(data))

    def :+(preprocessor: InstancePreprocessor[T]) = new InstancePreprocessorComposition(preprocessors :+ preprocessor)

    def +:(preprocessor: InstancePreprocessor[T]) = new InstancePreprocessorComposition(preprocessor +: preprocessors)
}
