package org.bluebikebase.core.algebra

import org.bluebikebase.core.kernel.ScalarD

interface Rateable {
    val scalar: ScalarD

    /**
    * @throws org.bluebikebase.core.error.InvalidValidationException
    **/
    val percent: ScalarD get() = scalar * ScalarD.HECTO
}
