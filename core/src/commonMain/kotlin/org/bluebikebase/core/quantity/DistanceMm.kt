package org.bluebikebase.core.quantity

import org.bluebikebase.core.algebra.extensions.asLowerLimit
import org.bluebikebase.core.algebra.extensions.inclusiveDiscipline
import org.bluebikebase.core.foundation.ScalarD
import org.bluebikebase.core.error.B3InvalidValidationException
import org.bluebikebase.core.error.B3Exception
import org.bluebikebase.core.rule.validate
import kotlin.jvm.JvmInline

@JvmInline
value class DistanceMm private constructor(val mm: ScalarD) {
    companion object {
        val ZERO = DistanceMm(mm = ScalarD.ZERO)

        @Throws(B3InvalidValidationException::class, B3Exception::class)
        fun of(rawMm: ScalarD): DistanceMm {
            val validMm = rawMm.validate(
                requirement = ScalarD.ZERO.asLowerLimit.inclusiveDiscipline,
                lazyMessage = { "Distance magnitude must be a non-negative: $it." },
            )

            return DistanceMm(mm = validMm)
        }
    }

    // --- 具象への視点（単位変換） ---
    val cm: ScalarD get() = this.mm / ScalarD.DECA
    val m: ScalarD get() = this.cm / ScalarD.HECTO
    val km: ScalarD get() = this.m / ScalarD.KILO

    // --- 空間の代謝（演算） ---
    @Throws(B3InvalidValidationException::class)
    operator fun plus(other: DistanceMm): DistanceMm = of(rawMm = this.mm + other.mm)
    @Throws(B3InvalidValidationException::class)
    operator fun minus(other: DistanceMm): DistanceMm = of(rawMm = this.mm - other.mm)
    /**
     * 倍率計算（距離 × スカラー）
     */
    @Throws(B3InvalidValidationException::class)
    operator fun times(factor: ScalarD): DistanceMm = of(rawMm = this.mm * factor)
    /**
     * 倍率計算（距離 / スカラー）
     */
    @Throws(B3InvalidValidationException::class)
    operator fun div(factor: ScalarD): DistanceMm = of(rawMm = this.mm / factor)
}
