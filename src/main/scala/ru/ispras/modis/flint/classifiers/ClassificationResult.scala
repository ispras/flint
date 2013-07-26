package ru.ispras.modis.flint.classifiers

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/23/13
 * Time: 9:09 PM
 */
class ClassificationResult[LabelType](val label: LabelType, val confidence: Option[Double] = None)
