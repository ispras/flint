package ru.ispras.modis.flint.textprocessing

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/26/13
 * Time: 11:29 PM
 */
trait TextPreprocessor extends Serializable {
    def apply(text: String): String
}
