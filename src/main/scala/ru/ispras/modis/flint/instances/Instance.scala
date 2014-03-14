package ru.ispras.modis.flint.instances

import breeze.linalg.SparseVector
import breeze.generic.CanMapValues
import breeze.linalg.VectorBuilder
import breeze.linalg._

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/22/13
 * Time: 5:08 PM
 *
 * This class represents a data instance
 *
 * Do not forget to implement InstanceBuilder trait for every
 * inheritor and to implement the corresponding implicit method in InstanceBuilder object
 */
class Instance(private[instances] val points: SparseVector[Double]) extends Serializable {

    def iterator: Iterator[(Int, Double)] = points.iterator

    def activeSize: Int = points.activeSize

    def lenght = points.length

    def apply(idx: Int): Double = points(idx)

    def foreach[B](f: Double => B) = points.foreach(f)

    def indexAt(i: Int) = points.indexAt(i)

    def valueAt(i: Int) = points.valueAt(i)

    def +(x : Instance):Instance = new Instance(this.points :+ x.points)                          //
                                                                                                  //
    def divByAlpha(x : Double):Instance = new Instance(this.points / x)                           //
                                                                                                  //
    def -(x : Instance):Instance = new Instance(this.points :- x.points)                          //
                                                                                                  //
    def norml2() = {                                                                              //
        for (i <- points)                                                                         //
            print(i + " ")                                                                        //  FIXME: PLEASE!!!
        println()                                                                                 //
        println(this.points.norm(2))                                                              //
        this.points.norm(2)                                                                       //
    }                                                                                             //
                                                                                                  //
    def *(x : Instance):Instance = new Instance(this.points :* x.points)                          //
                                                                                                  //
    def append(x:Instance) = new Instance(SparseVector.vertcat(this.points, x.points))          //
    // FIXME: appends?

    def map[E2, That](f: (Double) => E2)(implicit canMapValues: CanMapValues[SparseVector[Double], Double, E2, That]) = points.map(f)

}

