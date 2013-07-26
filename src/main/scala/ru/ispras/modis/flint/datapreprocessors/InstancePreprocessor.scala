package ru.ispras.modis.flint.datapreprocessors

import spark.RDD
import ru.ispras.modis.flint.instances.{InstanceBuilder, Instance}

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 7:52 PM
 */
trait InstancePreprocessor[T <: Instance] {
    def apply(data: RDD[T])(implicit manifest: ClassManifest[T], instanceBuilder: InstanceBuilder[T]): RDD[T]
}

object InstancePreprocessor {
    implicit def InstancePreprocessor2InstancePreprocessorComposition[T <: Instance](preprocessor: InstancePreprocessor[T]) = new InstancePreprocessorComposition(Seq(preprocessor))
}