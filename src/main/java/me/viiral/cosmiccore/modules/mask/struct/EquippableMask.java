package me.viiral.cosmiccore.modules.mask.struct;

import org.bukkit.entity.Player;

public interface EquippableMask {
    void onEquip(Player player);
    void onUnequip(Player player);
}
