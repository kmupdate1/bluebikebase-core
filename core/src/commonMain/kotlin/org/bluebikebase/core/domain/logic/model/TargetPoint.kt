package org.bluebikebase.core.domain.logic.model

import org.bluebikebase.core.domain.logic.inclusiveDisciplineBy
import org.bluebikebase.core.domain.logic.asLowerLimit
import org.bluebikebase.core.domain.model.ScalarD
import org.bluebikebase.core.error.LawOfB3Exception

data class TargetPoint(val at: ScalarD, val epsilon: ScalarD = ScalarD.MILLI) {
    @Throws(LawOfB3Exception::class)
    fun isReachedBy(current: ScalarD): Boolean =
        epsilon.asLowerLimit.inclusiveDisciplineBy(current = (current - at).abs)
}
