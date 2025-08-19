package me.cybersteve.equiplib.item.armor.impl;

import me.cybersteve.equiplib.item.armor.base.EffectArmorSet;
import me.cybersteve.equiplib.util.EffectList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;


public class SimpleEffectArmorSet extends EffectArmorSet {
    protected EffectList whenWearing;
    protected EffectList onHitForAttacker;
    protected EffectList onHitForSelf;

    public SimpleEffectArmorSet(ResourceLocation id,
                                EffectList whenWearing,
                                EffectList onHitForAttacker,
                                EffectList onHitForSelf) {
        super(id);
        this.whenWearing = whenWearing;
        this.onHitForAttacker = onHitForAttacker;
        this.onHitForSelf = onHitForSelf;
    }

    @Override
    public EffectList getEffectsWhenWearing(LivingEntity entity) {
        return whenWearing;
    }

    @Override
    public EffectList getEffectsForSelfWhenHit(DamageSource source, float amount) {
        return onHitForSelf;
    }

    @Override
    public EffectList getEffectsForAttackerWhenHit(DamageSource source, float amount) {
        return onHitForAttacker;
    }
}
