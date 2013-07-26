package ru.ispras.modis.flint.textprocessing

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/26/13
 * Time: 11:43 PM
 */
class NullTextPreprocessor extends TextPreprocessor {
    def apply(text: String): String = text
}
