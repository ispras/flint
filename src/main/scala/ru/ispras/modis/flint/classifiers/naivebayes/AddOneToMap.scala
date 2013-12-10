package ru.ispras.modis.flint.classifiers.naivebayes


import scala.{Double, Int}


abstract class AddOneToMap[K,Double] extends Map[K,Double] {

    private val map = Map.empty[K, Double]

    override def +[B1 >: Double](kv: (K, B1)): Map[K, B1] = map+(kv)

}
