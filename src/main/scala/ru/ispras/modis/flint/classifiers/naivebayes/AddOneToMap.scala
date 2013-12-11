package ru.ispras.modis.flint.classifiers.naivebayes
import scala.{Double, Int}


trait AddOneToMap[K] extends Map[K,Long] {

  abstract override def get(key: K): Option[Long] =  {

    val x: Option[Long] = super.get(key)

    if (x.isEmpty) Some(1)

      else x.map(_+1)
  }

  abstract override def iterator: Iterator[(K, Long)] = super.iterator

  abstract override def + [B1 >: Long](kv: (K,B1)): Map[K,B1] = super.+(kv)

  abstract override def -(key: K): Map[K,Long] = super.-(key)
}


