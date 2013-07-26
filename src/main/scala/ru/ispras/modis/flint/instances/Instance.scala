package ru.ispras.modis.flint.instances

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/22/13
 * Time: 5:08 PM
 */
class Instance(private val featuresToWeights: IndexedSeq[Feature]) extends IndexedSeq[Feature] with Serializable {
    override def iterator: Iterator[Feature] = featuresToWeights.iterator

    def length: Int = featuresToWeights.length

    def apply(idx: Int): Feature = featuresToWeights(idx)
}

