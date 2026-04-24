package org.bluebikebase.core.algebra

import org.bluebikebase.core.error.B3InvalidValidationException
import org.bluebikebase.core.rule.validate
import org.bluebikebase.core.foundation.ScalarD
import kotlin.jvm.JvmInline

// Rate: 0.0以上の実数を保証する「率」
@JvmInline
value class Rate
@Throws(B3InvalidValidationException::class)
constructor(override val scalar: ScalarD) : Rateable {
    init {
        scalar.validate(
            requirement = { it.isZero or it.isPositive },
            lazyMessage = { "Rate must be a non-negative number." },
        )
    }
}
