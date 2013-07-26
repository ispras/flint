package ru.ispras.modis.flint.textprocessing

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/26/13
 * Time: 11:44 PM
 */
class WhiteSpaceTokenizer extends Tokenizer {
    def apply(text: String): IndexedSeq[String] = text.split("\\s")
}
