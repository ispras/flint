package ru.ispras.modis.flint.crossvalidation

import org.apache.spark.rdd.RDD
import ru.ispras.modis.flint.instances.LabelledInstance
import java.util.Random

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 11:15 PM
 */
class CrossValidationUtils[LabelType: ClassManifest] {
    protected def split(data: RDD[LabelledInstance[LabelType]], fractionToTestOn: Double, random: Random) = {
        val dataWithAssignment = data.map((random.nextDouble() < fractionToTestOn, _)).cache()
        val train = dataWithAssignment.filter(!_._1).map(_._2)
        val test = dataWithAssignment.filter(_._1).map(_._2)
        (train, test)
    }

    protected def combOp(sumFirst: Double, sumSecond: Double) = sumFirst + sumSecond
}
