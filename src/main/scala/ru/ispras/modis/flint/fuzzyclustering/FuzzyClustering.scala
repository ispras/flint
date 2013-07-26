package ru.ispras.modis.flint.fuzzyclustering

import spark.RDD
import ru.ispras.modis.flint.instances.Instance

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/27/13
 * Time: 12:10 AM
 */
trait FuzzyClustering {
    def apply(data: RDD[Instance]): (RDD[Mixture], Seq[DistributionParameters])
}
