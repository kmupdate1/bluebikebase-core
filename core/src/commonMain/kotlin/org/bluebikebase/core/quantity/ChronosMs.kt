package org.bluebikebase.core.quantity

import org.bluebikebase.core.algebra.extensions.asLowerLimit
import org.bluebikebase.core.algebra.extensions.inclusiveDiscipline
import org.bluebikebase.core.foundation.ScalarD
import org.bluebikebase.core.error.B3InvalidValidationException
import org.bluebikebase.core.error.B3Exception
import org.bluebikebase.core.rule.validate
import kotlin.jvm.JvmInline

@JvmInline
value class ChronosMs
@Throws(B3Exception::class)
private constructor(val ms: ScalarD) {
    companion object {
        val SECOND: ChronosMs = of(ms = ScalarD.KILO)
        val MINUTE: ChronosMs = of(ms = ScalarD.KILO * ScalarD.SEXA)
        val HOUR: ChronosMs = of(ms = ScalarD.KILO * ScalarD.SEXA * ScalarD.SEXA)

        @Throws(B3InvalidValidationException::class, B3Exception::class)
        fun of(ms: ScalarD): ChronosMs {
            val validMs = ms.validate(
                requirement = ScalarD.ZERO.asLowerLimit.inclusiveDiscipline,
                lazyMessage = { "ChronosMs must be non-negative. Input: $it." }
            )

            return ChronosMs(ms = validMs)
        }

        @Throws(B3InvalidValidationException::class)
        fun fromRaw(rawMs: Double): ChronosMs = of(ms = ScalarD.of(raw = rawMs))
    }

    val s: ScalarD get() = ms / ScalarD.KILO
    val min: ScalarD get() = s / ScalarD.SEXA
    val hour: ScalarD get() = min / ScalarD.SEXA

    override fun toString(): String = "${ms}ms"

    @Throws(B3InvalidValidationException::class)
    operator fun plus(other: ChronosMs): ChronosMs = ChronosMs(ms = this.ms + other.ms)
    @Throws(B3InvalidValidationException::class)
    operator fun minus(other: ChronosMs): ChronosMs = ChronosMs(ms = this.ms - other.ms)
    @Throws(B3InvalidValidationException::class)
    operator fun times(scalarD: ScalarD): ChronosMs = of(ms = this.ms * scalarD)
    @Throws(B3InvalidValidationException::class)
    operator fun div(scalar: ScalarD): ChronosMs = of(this.ms / scalar)
}
