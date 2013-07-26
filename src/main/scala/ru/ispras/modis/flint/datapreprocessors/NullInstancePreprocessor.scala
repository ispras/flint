package ru.ispras.modis.flint.datapreprocessors

import spark.RDD
import ru.ispras.modis.flint.instances.{InstanceBuilder, Instance}

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 7:53 PM
 */
class NullInstancePreprocessor extends InstancePreprocessor {
    def apply[T <: Instance](data: RDD[T])(implicit arg0: ClassManifest[T], instanceBuilder: InstanceBuilder[T]): RDD[T] = data
}
