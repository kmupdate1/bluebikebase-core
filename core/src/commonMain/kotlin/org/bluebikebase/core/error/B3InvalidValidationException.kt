package org.bluebikebase.core.error

open class B3InvalidValidationException(
    override val message: String,
    override val cause: Throwable? = null,
) : B3Exception(message = message, cause = cause)
