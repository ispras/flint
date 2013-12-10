package ru.ispras.modis.flint.classifiers.naivebayes
import scala.{Double, Int}

trait AddOneToMap[K] extends Map[K,Long] {

    abstract override def get(key: K): Option[Long] = super.get(key).map(_+1L)

}


