package me.cybersteve.equiplib;

import net.minecraftforge.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class EquipLibForge {
    
    public EquipLibForge() {
    
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
    
        // Use Forge to bootstrap the Common mod.
        EquipLib.init();
        
    }
}