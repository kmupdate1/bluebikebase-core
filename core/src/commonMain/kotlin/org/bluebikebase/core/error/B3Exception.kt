package org.bluebikebase.core.error

class B3Exception(
    override val message: String,
    override val cause: Throwable? = null
) : CoreException(message = message, cause = cause)
