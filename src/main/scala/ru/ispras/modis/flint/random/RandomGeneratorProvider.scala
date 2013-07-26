package ru.ispras.modis.flint.random

import org.uncommons.maths.random.SeedGenerator
import java.util.Random

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/24/13
 * Time: 11:22 PM
 */
trait RandomGeneratorProvider {
    def apply(seedGenerator: SeedGenerator): Random
}
