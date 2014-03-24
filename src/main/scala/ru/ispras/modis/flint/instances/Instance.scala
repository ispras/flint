package ru.ispras.modis.flint.instances

import breeze.generic.CanMapValues
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

    def lenght = points.length // pay attention to IDEA spell check.

    def apply(idx: Int): Double = points(idx)

    def foreach[B](f: Double => B) = points.foreach(f)

    /**
     * I would also suggest to implement activeForeach -- foreach that ignores zero values. You can easily employ activeIterator
     *
     * And if possible avoid using      indexAt(i: Int) and   valueAt(i: Int) while working with Instance
     */

    def indexAt(i: Int) = points.indexAt(i)

    def valueAt(i: Int) = points.valueAt(i)

    def +(x: Instance): Instance = new Instance(this.points :+ x.points)

    //
    //
    def divByAlpha(x: Double): Instance = new Instance(this.points / x)

    // replace by operator *  //
    //
    def -(x: Instance): Instance = new Instance(this.points :- x.points)

    //
    //
    def norml2() = {
        //rename normL2                                                                              //
        for (i <- points) //
            print(i + " ") //O_o                                                                        //  FIXME: PLEASE!!!
        println() //
        println(this.points.norm(2)) //
        this.points.norm(2) //
    }

    //
    //
    def *(x: Instance): Instance = new Instance(this.points :* x.points)

    // rename -- dot or dotProducut                        //
    //
    def append(x: Instance) = new Instance(SparseVector.vertcat(this.points, x.points)) //
    // FIXME: appends? //no -- append

    def map[E2, That](f: (Double) => E2)(implicit canMapValues: CanMapValues[SparseVector[Double], Double, E2, That]) = points.map(f)

}

