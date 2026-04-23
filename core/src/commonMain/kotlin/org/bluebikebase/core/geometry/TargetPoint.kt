package org.bluebikebase.core.geometry

import org.bluebikebase.core.algebra.extensions.inclusiveDisciplineBy
import org.bluebikebase.core.algebra.extensions.asLowerLimit
import org.bluebikebase.core.foundation.ScalarD
import org.bluebikebase.core.error.B3Exception

data class TargetPoint(val at: ScalarD, val epsilon: ScalarD = ScalarD.MILLI) {
    @Throws(B3Exception::class)
    fun isReachedBy(current: ScalarD): Boolean =
        epsilon.asLowerLimit.inclusiveDisciplineBy(current = (current - at).abs)
}
