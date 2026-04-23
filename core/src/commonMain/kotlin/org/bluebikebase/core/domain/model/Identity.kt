package terakoyalabo.core.domain.identity.model

import terakoyalabo.core.error.InvalidValidationException
import terakoyalabo.core.function.validate
import kotlin.jvm.JvmInline
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@JvmInline
value class Identity @OptIn(ExperimentalUuidApi::class)
@Throws(InvalidValidationException::class)
private constructor(val id: Uuid) {
    companion object {
        @OptIn(ExperimentalUuidApi::class)
        fun fromString(id: String): Identity {
            val validId = id.validate(
                run = { Uuid.parse(it) },
                lazyMessage = { "Failed to generate identity (input: $it)." },
            )

            return Identity(id = validId)
        }

        @OptIn(ExperimentalUuidApi::class)
        fun gen(): Identity = Identity(Uuid.random())
    }

    /**
     * 1. 「完全な真名」
     * 例: 550e8400-e29b-41d4-a716-446655440000
     */
    val full: String @OptIn(ExperimentalUuidApi::class) get() = id.toString()

    /**
     * 2. 「短縮名称（ラベル）」
     * ログやデバッグ時に、パッと見で個体を識別するために使う。
     * 例: 550e8400 (最初の8文字)
     */
    val short8: String @OptIn(ExperimentalUuidApi::class) get() = full.take(8)

    /**
     * 3. 「圧縮された識別子」
     * ハイフンを除去したもの。DBのキーやURLパス、ファイル名に使いやすい。
     * 例: 550e8400e29b41d4a716446655440000
     */
    val compact: String @OptIn(ExperimentalUuidApi::class) get() = full.replace("-", "")

    @OptIn(ExperimentalUuidApi::class)
    override fun toString(): String = full
}
