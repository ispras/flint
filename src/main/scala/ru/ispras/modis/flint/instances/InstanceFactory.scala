package ru.ispras.modis.flint.instances

import breeze.linalg.SparseVector

/**
 * Created by saylars on 04.03.14.
 */

object InstanceFactory {
    def apply(data: Seq[Feature]): Instance = {
        data.sorted
        val indexes = new Array[Int](data.length)
        val values = new Array[Double](data.length)
        for (i <- 0 until data.length){
            indexes(i) = data(i).featureId
            values(i) = data(i).featureWeight
        }
        new Instance(new SparseVector(indexes, values, data.last.featureId + 1))
    }

}
