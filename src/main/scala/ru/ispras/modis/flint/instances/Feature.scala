package ru.ispras.modis.flint.instances

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/22/13
 * Time: 5:38 PM
 */
trait Feature extends Serializable {
    def featureId: Int

    def featureWeight: Double
}
