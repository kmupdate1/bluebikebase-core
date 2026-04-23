package org.bluebikebase.core.error

class InvalidValidationException(
    override val message: String,
    override val cause: Throwable? = null,
) : CoreException(message = message, cause = cause)
