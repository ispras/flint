package ru.ispras.modis.flint.crossvalidation

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/24/13
 * Time: 10:23 PM
 */
class CrossValidationResult[LabelType](private val results: Map[LabelType, CrossValidationResultByOneLabel],
                                       private val accuracy: Double) {
    override def toString = results.mkString("\n") + "\naccuracy " + accuracy
}

object CrossValidationResult {
    def average[LabelType](results: Seq[CrossValidationResult[LabelType]]) = {
        new CrossValidationResult[LabelType](
            results.flatMap(_.results.toSeq).groupBy(_._1).map {
                case (label, resultsByLabel) =>
                    (label, CrossValidationResultByOneLabel.average(resultsByLabel.map(_._2)))
            }.toMap,
            results.map(_.accuracy).sum / results.size
        )
    }
}