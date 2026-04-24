package org.bluebikebase.core.error

abstract class CoreException(
    override val message: String?,
    override val cause: Throwable?,
) : RuntimeException(message, cause)
