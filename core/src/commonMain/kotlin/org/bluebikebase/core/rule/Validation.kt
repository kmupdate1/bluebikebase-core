package org.bluebikebase.core.rule

import org.bluebikebase.core.error.B3InvalidValidationException
import kotlin.jvm.JvmName

@Throws(B3InvalidValidationException::class)
@JvmName("validateRequirement")
internal inline fun <T> T.validate(requirement: (T) -> Boolean, lazyMessage: (T) -> String): T {
    if (!requirement.invoke(this)) throw B3InvalidValidationException(message = lazyMessage.invoke(this))

    return this
}

@Throws(B3InvalidValidationException::class)
@JvmName("validateRun")
internal inline fun <T, R> T.validate(run: (T) -> R, lazyMessage: (T) -> String?): R =
    try { run.invoke(this) } catch (e: Exception) {
        val prefix = lazyMessage.invoke(this)?.let { "$it " }
            ?: e::class.simpleName
            ?: "Unknown"

        throw B3InvalidValidationException(message = prefix + "[${e.message}]", cause = e)
    }
