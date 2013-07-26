package ru.ispras.modis.flint.datapreprocessors

import spark.RDD
import ru.ispras.modis.flint.instances.Instance

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 7:53 PM
 */
class NullInstancePreprocessor extends InstancePreprocessor {
    def apply(data: RDD[Instance]): RDD[Instance] = data
}
