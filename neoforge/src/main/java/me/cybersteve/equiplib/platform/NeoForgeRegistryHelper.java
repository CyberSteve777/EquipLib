package me.cybersteve.equiplib.platform;

import me.cybersteve.equiplib.platform.services.IRegistryHelper;
import me.cybersteve.equiplib.registry.EffectArmorSetRegistry;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.NewRegistryEvent;

public class NeoForgeRegistryHelper implements IRegistryHelper {
    @Override
    public void addCustomRegistry() {
        NeoForge.EVENT_BUS.addListener(NeoForgeRegistryHelper::registerRegistry);
    }

    private static void registerRegistry(NewRegistryEvent event) {
        event.register(EffectArmorSetRegistry.getRegistryForRegistration());
    }
}
