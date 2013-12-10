package ru.ispras.modis.flint.classifiers.naivebayes


import scala.{Double, Int}


trait AddOneToMap[K] extends Map[K,Long] {

    abstract override def get(key: K) = super.get(key)+1.0//smth wrong with add 1

}


