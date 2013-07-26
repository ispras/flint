package ru.ispras.modis.flint.instances

import spark.RDD

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/22/13
 * Time: 5:57 PM
 */
object InstanceMerger {
    def apply(first: RDD[Instance], second: RDD[Instance]) = {
        val maxInFirst = first.map(_.maxBy(_.featureId).featureId).aggregate(Int.MinValue)((maxSoFar, id) => math.max(id, maxSoFar), (maxSoFar, id) => math.max(id, maxSoFar))

        first.zip(second).map {
            case (firstInstance, secondInstance) =>
                new Instance(IndexedSeq[Feature]() ++ firstInstance ++ secondInstance.map(feature => new WeightedFeature(feature.featureId + maxInFirst + 1, feature.featureWeight)))
        }
    }
}