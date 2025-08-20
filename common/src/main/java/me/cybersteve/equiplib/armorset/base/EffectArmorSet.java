package me.cybersteve.equiplib.armorset.base;

import net.minecraft.resources.ResourceLocation;

public abstract class EffectArmorSet implements IEffectArmorSetExtension {
    protected ResourceLocation id;

    public EffectArmorSet(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof EffectArmorSet other && other.id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
