package ru.ispras.modis.flint.instances

import org.apache.spark.rdd.RDD

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/22/13
 * Time: 5:57 PM
 */
object InstanceMerger {
    def apply(first: RDD[Instance], second: RDD[Instance]) = {

        first.zip(second).map {
            case (firstInstance, secondInstance) =>
                firstInstance.append(secondInstance)
        }
    }
}