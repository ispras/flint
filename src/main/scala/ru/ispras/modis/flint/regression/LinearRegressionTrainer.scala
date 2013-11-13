package ru.ispras.modis.flint.regression

import ru.ispras.modis.flint.instances.LabelledInstance
import org.apache.spark.rdd.RDD

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/25/13
 * Time: 11:13 PM
 */

class LinearRegressionTrainer(l2regularization: Double) extends RegressionTrainer {

    def sqr(x:Double):Double = {x * x}

    def apply(data: RDD[LabelledInstance[Double]]): LinearRegressionModel = {

        val data1 = data.cache()
        val randarr = new Array[Double](data1.first().length)
        for (i <- 0 to randarr.length - 1)
            randarr(i) = java.lang.Math.random()
        var tmp = new LinearRegressionModel(randarr)

        var err = data1.map(p => sqr(p.label - tmp.predicts(p))).reduce(_ + _)
        var preverr = 0.0

        var i = 0
        while (math.abs(err - preverr) > 0.01 && i < 100) {

            val gradient:LinearRegressionModel = data1.map {
                p => {
                    val sum = p.label - tmp.predicts(p)
                    val t = new Array[Double](p.length)
                    for (j <- 0 to p.length - 1)
                        t(j) = p(j).featureWeight * sum
                    new LinearRegressionModel(t)
                }
            }.reduce(_ + _)

            var alpha = 1.0
            preverr = err
            val delta = 0.5
            var sumgrad = 0.0

            for (j <- 0 to gradient.length - 1){
                sumgrad += sqr(gradient(j))
            }

            var tmp1:LinearRegressionModel = tmp.copy()
            do {
                alpha = alpha * delta
                tmp1 = tmp + (gradient + tmp * (-1 * l2regularization)) * alpha
                err = data1.map(p => sqr(p.label - tmp1.predicts(p))).reduce(_ + _)
            } while (err - preverr > 0.01 * alpha * sumgrad)

            tmp = tmp1.copy()
            i += 1
        }
        return tmp
    }
}
