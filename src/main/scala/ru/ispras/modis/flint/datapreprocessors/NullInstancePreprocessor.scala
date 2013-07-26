package ru.ispras.modis.flint.datapreprocessors

import spark.RDD
import ru.ispras.modis.flint.instances.{InstanceBuilder, Instance}

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 7:53 PM
 */
class NullInstancePreprocessor[T <: Instance] extends InstancePreprocessor[T] {
    def apply(data: RDD[T])(implicit manifest: ClassManifest[T], instanceBuilder: InstanceBuilder[T]): RDD[T] = data
}
