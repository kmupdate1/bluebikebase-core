package org.bluebikebase.core.error

class LawOfB3Exception(
    override val message: String,
    override val cause: Throwable? = null
) : CoreDomainException(message = message, cause = cause)
