package ru.ispras.modis.flint.strictclustering

import org.apache.spark.rdd.RDD
import ru.ispras.modis.flint.instances.Instance

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/26/13
 * Time: 11:58 PM
 */
trait StrictClustering {
    def apply(data: RDD[Instance]): RDD[(Int, Seq[Instance])]
}
