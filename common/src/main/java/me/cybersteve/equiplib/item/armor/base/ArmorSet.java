package me.cybersteve.equiplib.item.armor.base;


import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ArmorSet {
    protected HashSet<Holder<ArmorMaterial>> VALID_ARMOR_MATERIALS;
    protected HashSet<Supplier<ArmorItem>> HELMETS;
    protected HashSet<Supplier<ArmorItem>> CHESTPLATES;
    protected HashSet<Supplier<ArmorItem>> LEGGINGS;
    protected HashSet<Supplier<ArmorItem>> BOOTS;
    protected HashSet<Supplier<ArmorItem>> GENERIC_BODY_ARMOR; // non-humanoid armor


    protected ArmorSet(HashSet<Holder<ArmorMaterial>> validArmorMaterials,
                       HashSet<Supplier<ArmorItem>> helmets,
                       HashSet<Supplier<ArmorItem>> chestplates,
                       HashSet<Supplier<ArmorItem>> leggings,
                       HashSet<Supplier<ArmorItem>> boots,
                       HashSet<Supplier<ArmorItem>> generic_body_armor) {
        VALID_ARMOR_MATERIALS = validArmorMaterials;
        HELMETS = helmets;
        CHESTPLATES = chestplates;
        LEGGINGS = leggings;
        BOOTS = boots;
        GENERIC_BODY_ARMOR = generic_body_armor;
    }

    public static class Builder {
        protected HashSet<Holder<ArmorMaterial>> VALID_ARMOR_MATERIALS = new HashSet<>();
        protected HashSet<Supplier<ArmorItem>> HELMETS = new HashSet<>();
        protected HashSet<Supplier<ArmorItem>> CHESTPLATES = new HashSet<>();
        protected HashSet<Supplier<ArmorItem>> LEGGINGS = new HashSet<>();
        protected HashSet<Supplier<ArmorItem>> BOOTS = new HashSet<>();
        protected HashSet<Supplier<ArmorItem>> GENERIC_BODY_ARMOR = new HashSet<>(); // non-humanoid armor


        @SafeVarargs
        public final Builder addArmorMaterials(Holder<ArmorMaterial> material, Holder<ArmorMaterial>... friendlyMaterials) {
            VALID_ARMOR_MATERIALS.add(material);
            VALID_ARMOR_MATERIALS.addAll(Arrays.asList(friendlyMaterials));
            return this;
        }

        protected void addHelmet(Supplier<ArmorItem> armorItemSupplier) {
            HELMETS.add(armorItemSupplier);
        }
        protected void addChestplate(Supplier<ArmorItem> armorItemSupplier) {
            CHESTPLATES.add(armorItemSupplier);
        }

        protected void addGreaves(Supplier<ArmorItem> armorItemSupplier) {
            LEGGINGS.add(armorItemSupplier);
        }
        protected void addShoes(Supplier<ArmorItem> armorItemSupplier) {
            BOOTS.add(armorItemSupplier);
        }

        protected void addGenericArmor(Supplier<ArmorItem> armorItemSupplier) {
            GENERIC_BODY_ARMOR.add(armorItemSupplier);
        }

        @SafeVarargs
        public final Builder addHelmets(Supplier<ArmorItem> armorItemSupplier, Supplier<ArmorItem>... otherArmorItemSuppliers) {
            addHelmet(armorItemSupplier);
            for (var supplier : otherArmorItemSuppliers) {
                addHelmet(supplier);
            }
            return this;
        }

        @SafeVarargs
        public final Builder addChestPlates(Supplier<ArmorItem> armorItemSupplier, Supplier<ArmorItem>... otherArmorItemSuppliers) {
            addChestplate(armorItemSupplier);
            for (var supplier : otherArmorItemSuppliers) {
                addChestplate(supplier);
            }
            return this;
        }

        @SafeVarargs
        public final Builder addLeggings(Supplier<ArmorItem> armorItemSupplier, Supplier<ArmorItem>... otherArmorItemSuppliers) {
            addGreaves(armorItemSupplier);
            for (var supplier : otherArmorItemSuppliers) {
                addGreaves(supplier);
            }
            return this;
        }

        @SafeVarargs
        public final Builder addBoots(Supplier<ArmorItem> armorItemSupplier, Supplier<ArmorItem>... otherArmorItemSuppliers) {
            addShoes(armorItemSupplier);
            for (var supplier : otherArmorItemSuppliers) {
                addShoes(supplier);
            }
            return this;
        }

        @SafeVarargs
        public final Builder addGenericArmorItems(Supplier<ArmorItem> armorItemSupplier, Supplier<ArmorItem>... otherArmorItemSuppliers) {
            addGenericArmor(armorItemSupplier);
            for (var supplier : otherArmorItemSuppliers) {
                addGenericArmor(supplier);
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
                        armorItems -> getter(armorItems).contains(item)));
    }

    private static HashSet<ArmorItem> getter(HashSet<Supplier<ArmorItem>> supplierHashSet) {
        HashSet<ArmorItem> result = HashSet.newHashSet(supplierHashSet.size());
        supplierHashSet.stream().map(Supplier::get).forEach(result::add);
        return result;
    }
}
