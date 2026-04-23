package org.bluebikebase.core.algebra

import org.bluebikebase.core.algebra.extensions.asLowerLimit
import org.bluebikebase.core.algebra.extensions.discipline
import org.bluebikebase.core.error.InvalidValidationException
import org.bluebikebase.core.error.LawOfB3Exception
import org.bluebikebase.core.rule.validate
import org.bluebikebase.core.kernel.ScalarD
import org.bluebikebase.core.kernel.Signum

data class Vector
@Throws(InvalidValidationException::class, LawOfB3Exception::class)
constructor(val magnitude: ScalarD, val direction: Signum) {
    companion object {
        val STATIONARY: Vector = Vector(magnitude = ScalarD.ZERO, direction = Signum.NEUTRAL)

        @Throws(InvalidValidationException::class)
        fun of(rawMag: ScalarD): Vector = rawMag.let { mag ->
            Vector(
                magnitude = mag.abs,
                direction = Signum.of(scalar = mag),
            )
        }
    }

    init {
        magnitude
            .validate(
                requirement = ScalarD.ZERO.asLowerLimit.discipline,
                lazyMessage = { "Magnitude must be positive: $it." },
            )
            .validate(
                requirement = { it != ScalarD.ZERO && direction == Signum.NEUTRAL },
                lazyMessage = {
                    "Logical conflict detected: Magnitude is $it but direction is NEUTRAL. " +
                            "A non-zero magnitude must have an active polarity (POSITIVE or NEGATIVE). " +
                            "Please ensure the magnitude is zero if you intend to represent a balanced/stationary state."
                },
            )
    }
}
