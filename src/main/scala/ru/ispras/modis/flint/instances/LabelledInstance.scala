package ru.ispras.modis.flint.instances

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/22/13
 * Time: 5:56 PM
 */
class LabelledInstance[LabelType](private val featuresToWeights: IndexedSeq[Feature],
                                  val label: LabelType) extends Instance(featuresToWeights: IndexedSeq[Feature])
