package org.bluebikebase.core.domain.model

import org.bluebikebase.core.error.InvalidValidationException

interface Rateable {
    val scalar: ScalarD

    /**
    * @throws InvalidValidationException
    **/
    val percent: ScalarD get() = scalar * ScalarD.HECTO
}
