package org.bluebikebase.math.domain.primitive

import org.bluebikebase.core.algebra.extensions.sVal
import org.bluebikebase.core.foundation.Calculatable
import org.bluebikebase.core.foundation.Scalable
import org.bluebikebase.core.foundation.ScalarD
import org.bluebikebase.core.foundation.ScalarD.Companion.DECA
import org.bluebikebase.core.foundation.ScalarD.Companion.ZERO
import org.bluebikebase.core.foundation.ScalarD.Companion.of
import org.bluebikebase.core.foundation.ScalarL
import org.bluebikebase.core.error.B3InvalidValidationException
import kotlin.math.pow

/**
 * 冪乗の理（バイナリ法）
 * どんな Calculatable な型でも、指数が ScalarL であればこの手順で計算できる
 */
@Throws(B3InvalidValidationException::class)
fun <T> T.power(exponent: ScalarL): T where T : Calculatable<T>, T : Scalable<T> {
    // 境界条件の理
    if (this.isNegative) throw B3InvalidValidationException("Imaginary number is not supported.")
    if (exponent.isNegative) throw B3InvalidValidationException("Negative exponent is not supported.")

    var res = this.one
    var base = this
    var exp = exponent.value

    while (exp > 0) {
        if (exp % 2 == 1L) res *= base
        if (exp > 1) base *= base
        exp /= 2
    }

    return res
}

/**
 * 冪乗を計算する。（根号を許容）
 * @throws B3InvalidValidationException 理の外の値（NaN/Inf）が生成される場合に送出
 */
@Throws(B3InvalidValidationException::class)
fun ScalarD.power(exponent: ScalarD): ScalarD {
    if (this.isNegative) throw B3InvalidValidationException("Imaginary number is not supported.")
    return of(raw = this.value.pow(exponent.value))
}

// math モジュールにて、こんなふうに「知恵」を並べられます
fun ScalarD.sqrt(): ScalarD = this.power(exponent = 0.5.sVal)
fun ScalarD.cbrt(): ScalarD = this.power(exponent = 1.0.sVal / 3.0.sVal)

/**
 * 指定された精度で丸めた新しい Scalar を返す。
 * 表示用や、特定の精度を求める計算の途中で使用。
 * @throws B3InvalidValidationException 理の外の値（NaN/Inf）が生成される場合に送出
 */
@Throws(B3InvalidValidationException::class)
fun ScalarD.round(precision: ScalarD = ZERO): ScalarD {
    val factor = DECA.power(exponent = precision)
    return of(raw = (this * factor / factor).value)
}

/**
 * @throws B3InvalidValidationException 理の外の値（NaN/Inf）が生成される場合に送出
 */
@Throws(B3InvalidValidationException::class)
fun ScalarD.approximatedRound(precision: ScalarD = ZERO): ScalarD {
    val factor = DECA.power(exponent = precision)
    return of(raw = (this * factor).toWholeNumber().value / factor.value)
}
