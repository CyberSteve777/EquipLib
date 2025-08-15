package me.cybersteve.equiplib.item.armor.base;

public abstract class EffectArmorSet implements IEffectArmorSetExtension {
    protected ArmorSet armorSet;

    public EffectArmorSet(ArmorSet armorSet) {
        this.armorSet = armorSet;
    }

    public ArmorSet getArmorSet() {
        return armorSet;
    }
}
