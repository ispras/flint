package ru.ispras.modis.flint.instances


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
class Instance(private val featuresToWeights: IndexedSeq[Feature]) extends IndexedSeq[Feature] {
    override def iterator: Iterator[Feature] = featuresToWeights.iterator

    def length: Int = featuresToWeights.length

    def apply(idx: Int): Feature = featuresToWeights(idx)
}

