package me.cybersteve.equiplib.util;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class CommonHooks {
    public static boolean checkEffects(LivingEntity entity, EffectList effects) {
        for (var effectEntry : effects.data().entrySet()) {
            Holder<MobEffect> effect = effectEntry.getKey();
            EffectMeta meta = effectEntry.getValue();
            MobEffectInstance instance = entity.getEffect(effect);
            if (instance != null) {
                if ((instance.isInfiniteDuration() && instance.getAmplifier() != meta.amplifier()) || instance.getAmplifier() < meta.amplifier()) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    public static void addEffects(LivingEntity entity, EffectList effects, LivingEntity attacker) {
        for (var effectEntry : effects.data().entrySet()) {
            Holder<MobEffect> effect = effectEntry.getKey();
            EffectMeta meta = effectEntry.getValue();
            if (effect != null && !effect.value().isInstantenous()) {
                if (entity.hasEffect(effect)) {
                    entity.removeEffect(effect);
                }
                entity.addEffect(new MobEffectInstance(effect, meta.duration(), meta.amplifier(),
                        meta.ambient(), meta.showParticles(), meta.showIcon()), attacker);
            }
        }
    }

    public static void addEffects(LivingEntity entity, EffectList effects) {
        addEffects(entity, effects, null);
    }

    public static void removeInfiniteEffects(LivingEntity entity, EffectList effects) {
        for (var effectEntry : effects.data().entrySet()) {
            Holder<MobEffect> effect = effectEntry.getKey();
            EffectMeta meta = effectEntry.getValue();
            if (effect != null && entity.hasEffect(effect) && entity.getEffect(effect).isInfiniteDuration() &&
                    entity.getEffect(effect).getAmplifier() == meta.amplifier()) {
                entity.removeEffect(effect);
            }
        }
    }
}
