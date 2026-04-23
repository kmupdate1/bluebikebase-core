package terakoyalabo.core.domain.logic.model

import terakoyalabo.core.domain.quantity.model.ChronosMs
import terakoyalabo.core.domain.primitive.model.ScalarD
import terakoyalabo.core.domain.quantity.ms
import terakoyalabo.core.error.InvalidValidationException

/**
 * 寺子屋 DSL: 変化の最小単位。
 * 宇宙の動態を「勢い(Vector)」と「持続(ChronosMs)」の最小次元で切り出す。
 */
data class Delta(val velocity: Vector, val durationMs: ChronosMs = 1.ms) {
    companion object {
        /**
         * 変化のない「静」の状態。
         */
        val IDLE = Delta(velocity = Vector.STATIONARY)

        @Throws(InvalidValidationException::class)
        fun of(rawVelocity: ScalarD, durationMs: ChronosMs = 1.ms): Delta =
            Delta(velocity = Vector.of(rawMag = rawVelocity), durationMs = durationMs)
    }

    /**
     * 理学的帰結: Δd = |v| * Δt
     * スカラーとしての総変化量を算出する。
     */
    val magnitude: ScalarD get() = velocity.magnitude * durationMs.ms
}
