package ru.ispras.modis.flint.datapreprocessors

import org.apache.spark.rdd.RDD
import ru.ispras.modis.flint.instances.{InstanceBuilder, Instance}
import scala.reflect.ClassTag

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 7:52 PM
 */
trait InstancePreprocessor[T <: Instance] {
    def apply(data: RDD[T])(implicit manifest: ClassTag[T], instanceBuilder: InstanceBuilder[T]): RDD[T]
}

object InstancePreprocessor {
    implicit def InstancePreprocessor2InstancePreprocessorComposition[T <: Instance](preprocessor: InstancePreprocessor[T]) = new InstancePreprocessorComposition(Seq(preprocessor))
}