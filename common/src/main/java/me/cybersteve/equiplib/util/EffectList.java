package me.cybersteve.equiplib.util;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffect;

import java.util.concurrent.ConcurrentHashMap;

public record EffectList(ConcurrentHashMap<Holder<MobEffect>, EffectMeta> data) {
    public static class Builder {
        private final ConcurrentHashMap<Holder<MobEffect>, EffectMeta> data;

        public Builder() {this.data = new ConcurrentHashMap<>();}

        public Builder addCustomVisibleEffect(Holder<MobEffect> effect, int duration, int amplifier,
                                              boolean ambient, boolean showParticles, boolean showIcon) {
            if (data.containsKey(effect)) {
                throw new IllegalArgumentException("Duplicate key: " + effect);
            }
            data.put(effect, new EffectMeta(duration, amplifier, ambient, showParticles, showIcon));
            return this;
        }

        public Builder addFullyVisibleEffect(Holder<MobEffect> effect, int duration, int amplifier) {
            if (data.containsKey(effect)) {
                throw new IllegalArgumentException("Duplicate key: " + effect);
            }
            data.put(effect, EffectMeta.fullyVisible(duration, amplifier));
            return this;
        }

        public Builder addPartiallyVisibleEffect(Holder<MobEffect> effect, int duration, int amplifier) {
            if (data.containsKey(effect)) {
                throw new IllegalArgumentException("Duplicate key: " + effect);
            }
            data.put(effect, EffectMeta.partiallyVisible(duration, amplifier));
            return this;
        }

        public EffectList build() {
            return new EffectList(data);
        }
    }

    public static final StreamCodec<?, EffectList> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.map(ConcurrentHashMap::new,
                    ByteBufCodecs.holderRegistry(Registries.MOB_EFFECT),
                    EffectMeta.STREAM_CODEC),
            EffectList::data,
            EffectList::new
    );
}
