package me.cybersteve.equiplib.util;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.concurrent.ConcurrentHashMap;

/**
 * The Effect list class that stores EffectMeta for each effect.
 */
public record EffectList(ConcurrentHashMap<MobEffect, EffectMeta> data) {

    /**
     * Gets empty EffectList.
     *
     * @return the emptyEffectList
     */
    public static EffectList getEmptyList() {
        return new EffectList.Builder().build();
    }

    /**
     * Sets new meta for effect in list.
     *
     * @param effect the effect
     * @param meta   the meta
     */
    public void setNewMetaForEffect(MobEffect effect, EffectMeta meta) {
        if (!data.containsKey(effect)) {
            throw new IllegalArgumentException("Effect " + effect + " isn't present in list");
        }
        data.put(effect, meta);
    }

    /**
     * Remove effect from list.
     *
     * @param effect the effect
     */
    public void removeEffect(MobEffect effect) {
        data.remove(effect);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof EffectList list && data.equals(list.data());
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    /**
     * Method to check if EffectList is empty.
     *
     * @return boolean representing emptiness of the list
     */
    public boolean isEmpty() {
        return data.isEmpty();
    }


    /**
     * The Builder for EffectList.
     */
    public static class Builder {
        private final ConcurrentHashMap<MobEffect, EffectMeta> data;

        /**
         * Instantiates a new Builder.
         */
        public Builder() {this.data = new ConcurrentHashMap<>();}

        /**
         * Add effect with meta to builder.
         *
         * @param effect the effect
         * @param meta   the meta
         * @return the builder
         */
        public Builder addEffectWithMeta(MobEffect effect, EffectMeta meta) {
            if (data.containsKey(effect)) {
                throw new IllegalArgumentException("Duplicate key: " + effect);
            }
            data.put(effect, meta);
            return this;
        }

        /**
         * Add effect to builder. Makes
         *
         * @param effect        the effect
         * @param duration      the duration
         * @param amplifier     the amplifier
         * @param ambient       the ambient
         * @param showParticles the show particles
         * @param showIcon      the show icon
         * @return the builder
         */
        public Builder addEffect(MobEffect effect, int duration, int amplifier,
                                              boolean ambient, boolean showParticles, boolean showIcon) {
            return this.addEffectWithMeta(effect, new EffectMeta(duration, amplifier, ambient, showParticles, showIcon));
        }

        /**
         * Add effect with infinite duration to builder.
         *
         * @param effect        the effect
         * @param amplifier     the amplifier
         * @param ambient       the ambient
         * @param showParticles the show particles
         * @param showIcon      the show icon
         * @return the builder
         */
        public Builder addInfiniteEffect(MobEffect effect, int amplifier,
                                         boolean ambient, boolean showParticles, boolean showIcon) {
            return this.addEffect(effect, MobEffectInstance.INFINITE_DURATION, amplifier, ambient,
                    showParticles, showIcon);
        }

        /**
         * Add effect from mob effect instance to builder.
         *
         * @param instance the MobEffectInstance
         * @return the builder
         */
        public Builder addFromMobEffectInstance(MobEffectInstance instance) {
            MobEffect effect = instance.getEffect();
            return this.addEffectWithMeta(effect, new EffectMeta(instance.getDuration(), instance.getAmplifier(),
                    instance.isAmbient(), instance.isVisible(), instance.showIcon()));
        }

        /**
         * Build effect list.
         *
         * @return the effect list
         */
        public EffectList build() {
            return new EffectList(data);
        }
    }
}
