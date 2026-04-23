package terakoyalabo.core.domain.primitive.model

import terakoyalabo.core.error.InvalidValidationException
import terakoyalabo.core.error.LawOfTerakoyaException
import kotlin.jvm.JvmInline
import kotlin.math.abs

@JvmInline
value class ScalarL private constructor(val value: Long) : Scalable<ScalarL>, Calculatable<ScalarL> {
    companion object {
        val ZERO: ScalarL = ScalarL(value = 0L)
        val ONE = ScalarL(value = 1L)
        val NEGATIVE = ScalarL(-1L)

        @Throws(InvalidValidationException::class)
        fun of(raw: Long): ScalarL = ScalarL(value = raw)
    }

    override fun toString(): String = value.toString()

    // Scalable
    override val isPositive: Boolean get() = value > 0L
    override val isNegative: Boolean get() = value < 0L
    override val isZero: Boolean get() = value == 0L

    /**
     * @throws InvalidValidationException
     */
    override val abs: ScalarL get() = if (value == Long.MIN_VALUE) {
        throw LawOfTerakoyaException("Overflow in absolute value.")
    } else of(raw = abs(value))

    /**
     * @throws InvalidValidationException
     */
    override val inversion: ScalarL get() = if (value == Long.MIN_VALUE) {
        throw LawOfTerakoyaException("Overflow in inversion: Long.MIN_VALUE cannot be inverted.")
    } else of(raw = -value)


    // Calculatable
    override val zero: ScalarL get() = ZERO
    override val one: ScalarL get() = ONE

    override operator fun plus(other: ScalarL): ScalarL {
        val res = this.value + other.value
        // 加算の理：正＋正＝負、または 負＋負＝正 になったらオーバーフロー
        if (((this.value xor res) and (other.value xor res)) < ZERO.value) {
            throw LawOfTerakoyaException("Long overflow in addition: $value + ${other.value}")
        }
        return of(raw = res)
    }
    override operator fun minus(other: ScalarL): ScalarL {
        val res = this.value - other.value
        // 減算の理：符号が異なるもの同士を引いて、結果の符号が引かれる数と異なればオーバーフロー
        if (((this.value xor other.value) and (this.value xor res)) < ZERO.value) {
            throw LawOfTerakoyaException("Long overflow in subtraction: $value - ${other.value}")
        }
        return of(raw = res)
    }
    override operator fun times(other: ScalarL): ScalarL {
        if (other.value == ZERO.value) return ZERO
        val res = this.value * other.value
        // 乗算の理：結果を片方で割って元に戻らなければオーバーフロー
        if (res / other.value != this.value) {
            throw LawOfTerakoyaException("Long overflow in multiplication: $value * ${other.value}")
        }
        return of(raw = res)
    }
    override operator fun div(other: ScalarL): ScalarL {
        if (other.isZero) throw LawOfTerakoyaException("Division by zero.")
        // Long.MIN_VALUE / -1 だけはオーバーフローする唯一の除算
        if (this.value == Long.MIN_VALUE && other.value == NEGATIVE.value) {
            throw LawOfTerakoyaException("Long overflow in division.")
        }
        return of(raw = this.value / other.value)
    }
    override operator fun compareTo(other: ScalarL): Int = value.compareTo(other.value)
}
