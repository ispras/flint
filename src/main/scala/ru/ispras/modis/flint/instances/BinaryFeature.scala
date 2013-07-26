package ru.ispras.modis.flint.instances

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/22/13
 * Time: 5:51 PM
 */
class BinaryFeature(val featureId: Int) extends Feature with Serializable {
    def featureWeight: Double = 1d
}
