package me.viiral.cosmiccore.modules.skins.struct;

import org.bukkit.entity.Player;

public interface EquipableSkin {
    void onEquip(Player player);
    void onUnequip(Player player);
}
