package org.bluebikebase.core.identity

import org.bluebikebase.core.quantity.HeartBeat
import kotlin.jvm.JvmInline

@JvmInline
value class B3Hash private constructor(val id: ByteArray) : UniqueID {
    companion object {
        fun gen(): UniqueID {
            val now = HeartBeat.now()
            return B3Hash(generateHash(now.toString().encodeToByteArray()))
        }
    }
}
