package org.bluebikebase.core.identity

import java.security.MessageDigest

actual fun generateHash(bytes: ByteArray): ByteArray =
    MessageDigest.getInstance(HashAlgorithms.SHA256).digest(bytes)

object HashAlgorithms {
    const val SHA256 = "SHA-256"
}
