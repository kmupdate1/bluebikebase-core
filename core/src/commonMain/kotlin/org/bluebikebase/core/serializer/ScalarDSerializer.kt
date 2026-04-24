package org.bluebikebase.core.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bluebikebase.core.foundation.ScalarD

object ScalarDSerializer : KSerializer<ScalarD> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("ScalarD", PrimitiveKind.DOUBLE)

    override fun serialize(encoder: Encoder, value: ScalarD) {
        encoder.encodeDouble(value.value)
    }

    override fun deserialize(decoder: Decoder): ScalarD =
        ScalarD.of(decoder.decodeDouble())
}
