package me.cybersteve.equiplib.item.handheld.base;

import me.cybersteve.equiplib.util.EffectList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

/**
 * The interface Effect handheld item.
 */
public interface IEffectHandHeldItem {
    /**
     * Gets effects when in hand.
     *
     * @param entity the LivingEntity item holder
     * @return the effects when in hand
     */
    EffectList getEffectsWhenInHand(LivingEntity entity);

    /**
     * Gets effects for self on attack.
     *
     * @param source the source
     * @param owner  the owner of item
     * @param amount the amount of damage is about to deal
     * @return the effects for self on attack
     */
    EffectList getEffectsForSelfOnAttack(DamageSource source, LivingEntity owner, float amount);

    /**
     * Gets effects for target on attack.
     *
     * @param source the source
     * @param owner  the owner of item
     * @param amount the amount of damage is about to deal
     * @return the effects for target on attack
     */
    EffectList getEffectsForTargetOnAttack(DamageSource source, LivingEntity owner, float amount);
}
