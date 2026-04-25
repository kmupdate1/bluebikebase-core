package org.bluebikebase.core.algebra

import org.bluebikebase.core.algebra.extensions.asLowerLimit
import org.bluebikebase.core.algebra.extensions.discipline
import org.bluebikebase.core.error.B3InvalidValidationException
import org.bluebikebase.core.error.B3Exception
import org.bluebikebase.core.rule.validate
import org.bluebikebase.core.foundation.ScalarD
import org.bluebikebase.core.foundation.Signum

data class Vector
@Throws(B3InvalidValidationException::class, B3Exception::class)
constructor(val magnitude: ScalarD, val direction: Signum) {
    companion object {
        val STATIONARY: Vector = Vector(magnitude = ScalarD.ZERO, direction = Signum.NEUTRAL)

        @Throws(B3InvalidValidationException::class)
        fun of(rawMag: ScalarD): Vector = rawMag.run {
            Vector(
                magnitude = abs,
                direction = Signum.of(scalar = this),
            )
        }
    }

    init {
        magnitude.validate(
            requirement = ScalarD.ZERO.asLowerLimit.discipline,
            lazyMessage = { "Magnitude must be positive: $it." },
        )
    }
}
