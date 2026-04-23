package org.bluebikebase.core.domain.model

import org.bluebikebase.core.error.InvalidValidationException
import org.bluebikebase.core.function.validate
import kotlin.jvm.JvmInline

// Rate: 0.0以上の実数を保証する「率」
@JvmInline
value class Rate
@Throws(InvalidValidationException::class)
constructor(override val scalar: ScalarD) : Rateable {
    init {
        scalar.validate(
            requirement = { it.isZero or it.isPositive },
            lazyMessage = { "Rate must be a non-negative number." },
        )
    }
}
