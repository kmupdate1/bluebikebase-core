package org.bluebikebase.core.identity

import org.kotlincrypto.hash.sha2.SHA256

actual fun generateHash(bytes: ByteArray): ByteArray = SHA256().digest(bytes)
