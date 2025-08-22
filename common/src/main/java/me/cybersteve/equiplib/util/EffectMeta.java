package me.cybersteve.equiplib.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/**
 * Effect meta is like MobEffectInstance.Details, but a bit cut in functionality and with custom methods for creation
 */
public record EffectMeta(int duration, int amplifier, boolean ambient, boolean showParticles, boolean showIcon) {
    /**
     * The constant STREAM_CODEC for networking purposes.
     */
    public static final StreamCodec<ByteBuf, EffectMeta> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, EffectMeta::duration,
            ByteBufCodecs.VAR_INT, EffectMeta::amplifier,
            ByteBufCodecs.BOOL, EffectMeta::ambient,
            ByteBufCodecs.BOOL, EffectMeta::showParticles,
            ByteBufCodecs.BOOL, EffectMeta::showIcon,
            EffectMeta::new
    );
}