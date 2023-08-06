package me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct;

import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import org.bukkit.entity.Player;

public abstract class ArmorEquipEventEnchant extends Enchantment {

    public ArmorEquipEventEnchant(String name, EnchantTier tier, boolean stackable, int max, EnchantType type, String... description) {
        super(name, tier, stackable, max, type, description);
    }

    public abstract void runArmorEquipEvent(Player player, int level);
    public abstract void runArmorUnEquipEvent(Player player, int level);
}
