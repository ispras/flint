package ru.ispras.modis.flint.textprocessing

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/26/13
 * Time: 11:40 PM
 */
class TextPreprocessorComposition(private val preprocessors: Seq[TextPreprocessor]) extends TextPreprocessor {
    def apply(text: String): String = preprocessors.foldLeft(text)((text, preprocessor) => preprocessor(text))

    def :+(preprocessor: TextPreprocessor) = new TextPreprocessorComposition(preprocessors :+ preprocessor)

    def +:(preprocessor: TextPreprocessor) = new TextPreprocessorComposition(preprocessor +: preprocessors)
}

object TextPreprocessorComposition {
    implicit def Preprocessor2Composition(preprocessor: TextPreprocessor) = new TextPreprocessorComposition(Seq(preprocessor))
}