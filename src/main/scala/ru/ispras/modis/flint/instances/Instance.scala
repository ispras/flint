package ru.ispras.modis.flint.instances

import breeze.linalg.SparseVector

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/22/13
 * Time: 5:08 PM
 *
 * This class represents a data instance
 *
 * Do not forget to implement InstanceBuilder trait for every
 * inheritor and to implement the corresponding implicit method in InstanceBuilder object
 */
class Instance(private[instances] val points: SparseVector[Double]) extends Serializable {

    def iterator: Iterator[(Int, Double)] = points.iterator

    def size: Int = points.activeSize

    def numDimentions = points.length

    def apply(idx: Int): Double = points(idx)

}

