package me.cybersteve.equiplib.platform;

import me.cybersteve.equiplib.platform.services.IRegistryHelper;
import me.cybersteve.equiplib.registry.EffectArmorSetRegistry;

public class FabricRegistryHelper implements IRegistryHelper {
    @Override
    public void addCustomRegistry() {
        EffectArmorSetRegistry.getRegistryForRegistration();
    }
}
