package ru.ispras.modis.flint.instances

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/22/13
 * Time: 5:38 PM
 */
trait Feature extends Serializable with Ordered[Feature]{
    def featureId: Int

    def featureWeight: Double
    def compare(that: Feature): Int = this.featureId compare that.featureId
}
