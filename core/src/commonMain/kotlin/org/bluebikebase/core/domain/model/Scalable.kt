package terakoyalabo.core.domain.primitive.model

/**
 * 計量可能な「数」としての共通規格。
 * 連続体(D)であれ離散体(L)であれ、比較可能であり、
 * 符号を持ち、ゼロや正負の概念を持つという「理」を定義する。
 */
interface Scalable<T> : Comparable<T> {
    val isPositive: Boolean
    val isNegative: Boolean
    val isZero: Boolean

    val abs: T
    val inversion: T
}
