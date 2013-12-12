package ru.ispras.modis.flint.classifiers.naivebayes
import scala.{Double, Int}
import scala.collection.immutable.HashMap


class AddOneToMap[K] extends Map[K,Long] {

  override def get(key: K): Option[Long] =  {

   super.get(key) match {

     case Some(oldFrequency) => Some(oldFrequency + 1)

     case None => Some(1)
    }
  }


  override def iterator: Iterator[(K, Long)] = super.iterator

  override def + [B1 >: Long](kv: (K,B1)): Map[K,B1] = super.+(kv)

  override def -(key: K): Map[K,Long] = super.-(key)
}


