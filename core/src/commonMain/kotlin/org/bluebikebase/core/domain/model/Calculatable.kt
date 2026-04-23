package org.bluebikebase.core.domain.model

interface Calculatable<T> {
    val zero: T
    val one: T

    operator fun plus(other: T): T
    operator fun minus(other: T): T
    operator fun times(other: T): T
    operator fun div(other: T): T
}
