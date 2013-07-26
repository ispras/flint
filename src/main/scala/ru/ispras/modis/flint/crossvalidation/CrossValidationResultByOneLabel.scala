package ru.ispras.modis.flint.crossvalidation

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/23/13
 * Time: 10:10 PM
 */
class CrossValidationResultByOneLabel(private val truePositive: Double,
                                      private val trueNegative: Double,
                                      private val falsePositive: Double,
                                      private val falseNegative: Double) {
    def precision: Double = truePositive / (truePositive + falsePositive)

    def recall: Double = truePositive / (truePositive + trueNegative)

    def FMeasure: Double = 2 * precision * recall / (precision + recall)

    override def toString = "precision " + precision + " recall " + recall + " FMeasure " + FMeasure
}

object CrossValidationResultByOneLabel {
    def average(results: Seq[CrossValidationResultByOneLabel]) = {
        val (tpSum, tnSum, fpSum, fnSum) = results.foldLeft((0d, 0d, 0d, 0d)) {
            case ((tp, tn, fp, fn), result) =>
                (tp + result.truePositive, tn + result.trueNegative, fp + result.falsePositive, fn + result.falseNegative)
        }
        val (tp, tn, fp, fn) = (tpSum / results.size, tnSum / results.size, fpSum / results.size, fnSum / results.size)

        new CrossValidationResultByOneLabel(tp, tn, fp, fn)
    }
}