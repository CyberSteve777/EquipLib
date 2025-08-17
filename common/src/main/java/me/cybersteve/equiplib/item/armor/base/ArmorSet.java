package me.cybersteve.equiplib.item.armor.base;


import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ArmorSet {
    protected HashSet<Holder<ArmorMaterial>> VALID_ARMOR_MATERIALS;
    protected HashSet<ArmorItem> HELMETS;
    protected HashSet<ArmorItem> CHESTPLATES;
    protected HashSet<ArmorItem> LEGGINGS;
    protected HashSet<ArmorItem> BOOTS;
    protected HashSet<ArmorItem> GENERIC_BODY_ARMOR; // non-humanoid armor


    protected ArmorSet(HashSet<Holder<ArmorMaterial>> validArmorMaterials,
                       HashSet<ArmorItem> helmets,
                       HashSet<ArmorItem> chestplates,
                       HashSet<ArmorItem> leggings,
                       HashSet<ArmorItem> boots,
                       HashSet<ArmorItem> generic_body_armor) {
        VALID_ARMOR_MATERIALS = validArmorMaterials;
        HELMETS = helmets;
        CHESTPLATES = chestplates;
        LEGGINGS = leggings;
        BOOTS = boots;
        GENERIC_BODY_ARMOR = generic_body_armor;
    }

    public static class Builder {
        protected HashSet<Holder<ArmorMaterial>> VALID_ARMOR_MATERIALS = new HashSet<>();
        protected HashSet<ArmorItem> HELMETS = new HashSet<>();
        protected HashSet<ArmorItem> CHESTPLATES = new HashSet<>();
        protected HashSet<ArmorItem> LEGGINGS = new HashSet<>();
        protected HashSet<ArmorItem> BOOTS = new HashSet<>();
        protected HashSet<ArmorItem> GENERIC_BODY_ARMOR = new HashSet<>(); // non-humanoid armor


        @SafeVarargs
        public final Builder addArmorMaterials(Holder<ArmorMaterial> material, Holder<ArmorMaterial>... friendlyMaterials) {
            VALID_ARMOR_MATERIALS.add(material);
            VALID_ARMOR_MATERIALS.addAll(Arrays.asList(friendlyMaterials));
            return this;
        }

        protected void addArmorItem(Supplier<ArmorItem> armorItemSupplier) {
            ArmorItem item = armorItemSupplier.get();
            if (!VALID_ARMOR_MATERIALS.contains(item.getMaterial())) {
                throw new IllegalArgumentException("ArmorMaterial %s of ArmorItem %s is not in valid armor materials".formatted(item.getMaterial().value(), item));
            }
            switch (item.getType()) {
                case HELMET -> HELMETS.add(item);
                case CHESTPLATE -> CHESTPLATES.add(item);
                case LEGGINGS -> LEGGINGS.add(item);
                case BOOTS -> BOOTS.add(item);
                case BODY -> GENERIC_BODY_ARMOR.add(item);
            }
        }

        @SafeVarargs
        public final Builder addArmorItems(Supplier<ArmorItem> armorItemSupplier, Supplier<ArmorItem>... otherArmorItemSuppliers) {
            addArmorItem(armorItemSupplier);
            for (var supplier : otherArmorItemSuppliers) {
                addArmorItem(supplier);
            }
            return this;
        }

        public ArmorSet build() {
            if (VALID_ARMOR_MATERIALS.isEmpty()) {
                throw new IllegalStateException("No Armor Materials were provided");
            }
            if (HELMETS.size() + CHESTPLATES.size() + LEGGINGS.size() + BOOTS.size() + GENERIC_BODY_ARMOR.size() == 0) {
                throw new IllegalStateException("At least one Armor Item should be added to set");
            }
            return new ArmorSet(VALID_ARMOR_MATERIALS, HELMETS,
                    CHESTPLATES, LEGGINGS, BOOTS,
                    GENERIC_BODY_ARMOR);
        }
    }

    public boolean hasArmorItemInSet(Item item) {
        return item instanceof ArmorItem armorItem && VALID_ARMOR_MATERIALS.contains(armorItem.getMaterial()) && (
                Stream.of(HELMETS, CHESTPLATES, LEGGINGS, BOOTS, GENERIC_BODY_ARMOR).anyMatch(
                        armorItems -> armorItems.contains(armorItem)));
    }
}
