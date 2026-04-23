package terakoyalabo.core.domain.identity.model

import terakoyalabo.core.error.InvalidValidationException
import terakoyalabo.core.function.validate
import kotlin.jvm.JvmInline
import kotlin.time.Clock
import kotlin.time.Instant

@JvmInline
value class HeartBeat private constructor(val at: Instant) {
    companion object {
        @Throws(InvalidValidationException::class)
        fun fromString(at: String): HeartBeat {
            val validTime = at.validate(
                run = { Instant.parse(it) },
                lazyMessage = { "Failed to convert to heat-beat (input: $it)." },
            )

            return HeartBeat(at = validTime)
        }

        fun now() = HeartBeat(Clock.System.now())
    }

    /**
     * 1. 「宇宙の絶対座標」
     * 例: 2026-02-02T07:30:00Z (シリアライズ用)
     */
    val iso8601: String get() = at.toString()

    /**
     * 2. 「地球の暦（こよみ）」
     * 寺子屋の活動記録（サツマイモの収穫日など）で使う
     * 例: 20260202
     */
    val calendarKey: String
        get() = at.toString().take(10).replace("-", "")

    /**
     * 3. 「生命の刻（とき）」
     * カフェの注文やゲートウェイの入館ログなど、日常的な識別用
     * 例: 07:30:00
     */
    val timeKey: String
        get() = at.toString().substringAfter('T').take(8)

    /**
     * 4. 「原始的なタイムスタンプ」
     * DB保存や物理層との通信用
     */
    val epochMillis: Long get() = at.toEpochMilliseconds()

    override fun toString(): String = iso8601
}
