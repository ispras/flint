package ru.ispras.modis.flint.classifiers.naivebayes
import scala.{Double, Int}

trait AddOneToMap[K] extends Map[K,Long] {

    abstract override def get(key: K): Option[Long] = super.get(key).map(_+1)

    abstract  override def +[B1 >: Long](kv: (K,B1)): Map[K,B1] = super.+(kv)


}


