package ru.ispras.modis.flint.instances

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/26/13
 * Time: 7:00 PM
 */
trait InstanceBuilder[T <: Instance] extends Serializable {
    def apply(toMakeFrom: T, newFeatures: IndexedSeq[Feature]): T
}

object InstanceBuilder {
    implicit def instanceBuilder = new InstanceBuilder[Instance] {
        def apply(toMakeFrom: Instance, newFeatures: IndexedSeq[Feature]) = new Instance(newFeatures)
    }

    implicit def labelledInstanceBuilder[LabelType] = new InstanceBuilder[LabelledInstance[LabelType]] {
        def apply(toMakeFrom: LabelledInstance[LabelType], newFeatures: IndexedSeq[Feature]): LabelledInstance[LabelType] = new LabelledInstance[LabelType](newFeatures, toMakeFrom.label)
    }
}