package org.bluebikebase.core.domain.quantity.model

import org.bluebikebase.core.domain.logic.asLowerLimit
import org.bluebikebase.core.domain.logic.inclusiveDiscipline
import org.bluebikebase.core.domain.model.ScalarD
import org.bluebikebase.core.error.InvalidValidationException
import org.bluebikebase.core.error.LawOfB3Exception
import org.bluebikebase.core.function.validate
import kotlin.jvm.JvmInline

@JvmInline
value class ChronosMs
@Throws(LawOfB3Exception::class)
private constructor(val ms: ScalarD) {
    companion object {
        val SECOND: ChronosMs = of(ms = ScalarD.KILO)
        val MINUTE: ChronosMs = of(ms = ScalarD.KILO * ScalarD.SEXA)
        val HOUR: ChronosMs = of(ms = ScalarD.KILO * ScalarD.SEXA * ScalarD.SEXA)

        @Throws(InvalidValidationException::class, LawOfB3Exception::class)
        fun of(ms: ScalarD): ChronosMs {
            val validMs = ms.validate(
                requirement = ScalarD.ZERO.asLowerLimit.inclusiveDiscipline,
                lazyMessage = { "ChronosMs must be non-negative. Input: $it." }
            )

            return ChronosMs(ms = validMs)
        }

        @Throws(InvalidValidationException::class)
        fun fromRaw(rawMs: Double): ChronosMs = of(ms = ScalarD.of(raw = rawMs))
    }

    val s: ScalarD get() = ms / ScalarD.KILO
    val min: ScalarD get() = s / ScalarD.SEXA
    val hour: ScalarD get() = min / ScalarD.SEXA

    override fun toString(): String = "${ms}ms"

    @Throws(InvalidValidationException::class)
    operator fun plus(other: ChronosMs): ChronosMs = ChronosMs(ms = this.ms + other.ms)
    @Throws(InvalidValidationException::class)
    operator fun minus(other: ChronosMs): ChronosMs = ChronosMs(ms = this.ms - other.ms)
    @Throws(InvalidValidationException::class)
    operator fun times(scalarD: ScalarD): ChronosMs = of(ms = this.ms * scalarD)
    @Throws(InvalidValidationException::class)
    operator fun div(scalar: ScalarD): ChronosMs = of(this.ms / scalar)
}
