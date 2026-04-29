package org.bluebikebase.core.identity

import org.bluebikebase.core.quantity.HeartBeat

@ConsistentCopyVisibility
data class B3Hash private constructor(val id: ByteArray) : UniqueID {
    companion object {
        fun fromBytes(bytes: ByteArray): B3Hash = B3Hash(generateHash(bytes))

        fun gen(): UniqueID {
            val now = HeartBeat.now()
            return B3Hash(generateHash(now.toString().encodeToByteArray()))
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is B3Hash) return false
        return this.id.contentEquals(other.id)
    }

    override fun hashCode(): Int = id.contentHashCode()
    override fun toString(): String = id.contentToString()
}
