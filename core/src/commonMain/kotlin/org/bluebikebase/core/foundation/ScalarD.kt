package org.bluebikebase.core.foundation

import kotlinx.serialization.Serializable
import org.bluebikebase.core.error.B3InvalidValidationException
import org.bluebikebase.core.rule.validate
import org.bluebikebase.core.serializer.ScalarDSerializer
import kotlin.jvm.JvmInline
import kotlin.math.abs
import kotlin.math.roundToLong

@Serializable(with = ScalarDSerializer::class)
@JvmInline
value class ScalarD private constructor(val value: Double) : Scalable<ScalarD>, Calculatable<ScalarD> {
    companion object {
        // 演算子の混入は今後検討
        val ZERO: ScalarD = ScalarD(0.0)
        val ONE: ScalarD = ScalarD(1.0)
        val DECA: ScalarD = ScalarD(10.0)
        val HECTO: ScalarD = ScalarD(100.0)
        val KILO: ScalarD = ScalarD(1_000.0)
        val MEGA = ScalarD(1_000_000.0)
        val GIGA = ScalarD(1_000_000_000.0)
        val DECI: ScalarD  = ScalarD(0.1)
        val CENTI: ScalarD = ScalarD(0.01)
        val MILLI: ScalarD = ScalarD(0.001)
        val MICRO = ScalarD(0.000_001)
        val NANO = ScalarD(0.000_000_001)
        val SEXA: ScalarD = ScalarD(60.0) // 60 times
        val NEGATIVE_ONE: ScalarD = ScalarD(-1.0)

        @Throws(B3InvalidValidationException::class)
        fun of(raw: Double): ScalarD {
            val validRaw = raw
                .validate(requirement = { !it.isNaN() }, lazyMessage = { "No definition (NaN)." })
                .validate(requirement = { !it.isInfinite() }, lazyMessage = { "No infinite definition (Infinite)." })

            return ScalarD(value = validRaw)
        }
    }
    /**
     * 最も近い整数へ丸めて Long に変換する。
     * 「個体数」や「円」などの不連続な具象へ変換する際の標準ルート。
     * @throws B3InvalidValidationException
     */
    @Throws(B3InvalidValidationException::class)
    fun toWholeNumber(): ScalarL = ScalarL.of(raw = value.roundToLong())

    override fun toString(): String = value.toString()

    // --- Scalable 物理的実体への変換（責任範囲の追加） ---
    override val isPositive: Boolean get() = value > ZERO.value
    override val isNegative: Boolean get() = value < ZERO.value
    override val isZero: Boolean get() = value == ZERO.value

    /**
     * 絶対値
     * @throws B3InvalidValidationException 理の外の値（NaN/Inf）が生成される場合に送出
     */
    override val abs: ScalarD get() = of(raw = abs(x = this.value))

    /**
     * 符号反転
     * @throws B3InvalidValidationException 理の外の値（NaN/Inf）が生成される場合に送出
     */
    override val inversion: ScalarD get() = of(raw = -this.value)

    // --- Calculatable 代謝（演算） ---
    override val zero: ScalarD get() = ZERO
    override val one: ScalarD get() = ONE
    override operator fun plus(other: ScalarD): ScalarD = of(raw = this.value + other.value)
    override operator fun minus(other: ScalarD): ScalarD = of(raw = this.value - other.value)
    override operator fun times(other: ScalarD): ScalarD = of(raw = this.value * other.value)
    override operator fun div(other: ScalarD): ScalarD = of(raw = this.value / other.value)
    override operator fun compareTo(other: ScalarD): Int = this.value.compareTo(other.value)
}
