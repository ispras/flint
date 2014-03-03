package ru.ispras.modis.flint.instances

import scala.collection.generic.Sorted


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
class Instance(private val featuresToWeights: IndexedSeq[Feature]) extends IndexedSeq[Feature] with Serializable {
    //require(featuresToWeights.view.zip(featuresToWeights.tail).forall(x => x._1 < x._2))
    require(isSorted())      //which og these methods are better?  or how to realise it?

    override def iterator: Iterator[Feature] = featuresToWeights.iterator

    def length: Int = featuresToWeights.length

    def apply(idx: Int): Feature = featuresToWeights(idx)

    def isSorted():Boolean = {
        val t = featuresToWeights.iterator
        var cur = t.next()
        do {
           val prev = cur
           cur = t.next()
           if (prev >= cur)
               return false
        } while (t.hasNext)
        true
    }
}

