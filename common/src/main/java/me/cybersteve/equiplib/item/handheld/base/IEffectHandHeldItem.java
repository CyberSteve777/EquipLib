package me.cybersteve.equiplib.item.handheld.base;

import me.cybersteve.equiplib.util.EffectList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

/**
 * The interface Effect hand held item.
 */
public interface IEffectHandHeldItem {
    /**
     * Gets effects when in hand.
     *
     * @param entity the entity
     * @return the effects when in hand
     */
    EffectList getEffectsWhenInHand(LivingEntity entity);

    /**
     * Gets effects for self when hit.
     *
     * @param source the source
     * @param amount the amount
     * @return the effects for self when hit
     */
    EffectList getEffectsForSelfWhenHit(DamageSource source, float amount);

    /**
     * Gets effects for target when hit.
     *
     * @param source the source
     * @param amount the amount
     * @return the effects for target when hit
     */
    EffectList getEffectsForTargetWhenHit(DamageSource source, float amount);
}
