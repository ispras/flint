package ru.ispras.modis.flint.classifiers.naivebayes


import scala.{Double, Int}


trait AddOneToMap[K,B] extends Map[K,B] {



    abstract override def +[B1 >: B](kv: (K, B1)): Map[K, B1] = super.+(kv)

}
