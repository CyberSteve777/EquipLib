package me.cybersteve.equiplib.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record EffectMeta(int duration, int amplifier, boolean ambient, boolean showParticles, boolean showIcon) {
    public static EffectMeta fullyVisible(int duration, int amplifier) {
        return new EffectMeta(duration, amplifier, true, true, true);
    }

    public static EffectMeta partiallyVisible(int duration, int amplifier) {
        return new EffectMeta(duration, amplifier, true, false, true);
    }

    public static final StreamCodec<ByteBuf, EffectMeta> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, EffectMeta::duration,
            ByteBufCodecs.VAR_INT, EffectMeta::amplifier,
            ByteBufCodecs.BOOL, EffectMeta::ambient,
            ByteBufCodecs.BOOL, EffectMeta::showParticles,
            ByteBufCodecs.BOOL, EffectMeta::showIcon,
            EffectMeta::new
    );
}