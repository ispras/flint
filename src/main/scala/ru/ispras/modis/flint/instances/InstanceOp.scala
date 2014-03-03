package ru.ispras.modis.flint.instances

import breeze.linalg._
import breeze.collection.mutable.SparseArray

/**
 * Created with IntelliJ IDEA.
 * User: saylars
 * Date: 19.02.14
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */
object InstanceOp {

    def sum(first:Instance, second:Instance):Instance = {

        val firstMap = instanceToMap(first)
        val secondMap = instanceToMap(second)

        val r:SparseArray[Double]
        for (i <- first)
            r(i.featureId) += i.featureWeight
        for (i <- second)
            r(i.featureId) += i.featureWeight



        val x = (firstMap.keySet ++ secondMap.keySet).map(i => (i, firstMap.getOrElse(i, 0d) + secondMap.getOrElse(i, 0d))).toMap

        new Instance(x.map(feature => new WeightedFeature(feature._1, feature._2)).toIndexedSeq)

    }

    def instanceToMap(first: Instance) = {
        first.map(feature => (feature.featureId, feature.featureWeight)).toMap.withDefaultValue(0d)
    }
}
