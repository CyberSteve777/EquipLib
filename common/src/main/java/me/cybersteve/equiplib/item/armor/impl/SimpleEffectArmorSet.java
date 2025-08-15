package me.cybersteve.equiplib.item.armor.impl;

import me.cybersteve.equiplib.item.armor.base.ArmorSet;
import me.cybersteve.equiplib.item.armor.base.EffectArmorSet;
import me.cybersteve.equiplib.item.armor.base.IEffectArmorSetExtension;
import me.cybersteve.equiplib.util.EffectList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;


public class SimpleEffectArmorSet extends EffectArmorSet implements IEffectArmorSetExtension {

    protected EffectList whenWearing;
    protected EffectList onHitForAttacker;
    protected EffectList onHitForSelf;

    public SimpleEffectArmorSet(ArmorSet armorData, EffectList whenWearing,
                                   EffectList onHitForAttacker, EffectList onHitForSelf) {
        super(armorData);
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
