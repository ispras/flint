package ru.ispras.modis.flint.random

import org.uncommons.maths.random.{MersenneTwisterRNG, SeedGenerator}
import java.util.Random

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/24/13
 * Time: 11:23 PM
 */
object MersenneTwistProvider extends RandomGeneratorProvider {
    def apply(seedGenerator: SeedGenerator): Random = new MersenneTwisterRNG(seedGenerator)
}
