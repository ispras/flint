package ru.ispras.modis.flint.strictclustering

import ru.ispras.modis.flint.instances.Instance

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/27/13
 * Time: 12:00 AM
 */
object EuclideanDistance extends Distance with Serializable {        // why Serializable?
    def apply(first: Instance, second: Instance): Double = {
        val firstMap = instanceToMap(first)
        val secondMap = instanceToMap(second)

        math.sqrt(firstMap.keySet.union(secondMap.keySet).foldLeft(0d)((sum, index) => sum +
            math.pow(firstMap(index) - secondMap(index), 2)
        ))
    }

    def instanceToMap(first: Instance) = {
        first.map(feature => (feature.featureId, feature.featureWeight)).toMap.withDefaultValue(0d)
    }
}
