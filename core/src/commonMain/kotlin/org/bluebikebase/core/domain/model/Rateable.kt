package terakoyalabo.core.domain.primitive.model

import terakoyalabo.core.error.InvalidValidationException

interface Rateable {
    val scalar: ScalarD

    /**
    * @throws InvalidValidationException
    **/
    val percent: ScalarD get() = scalar * ScalarD.HECTO
}
