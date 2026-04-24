package org.bluebikebase.core.algebra

import org.bluebikebase.core.foundation.ScalarD

interface Rateable {
    val scalar: ScalarD

    /**
    * @throws org.bluebikebase.core.error.B3InvalidValidationException
    **/
    val percent: ScalarD get() = scalar * ScalarD.HECTO
}
