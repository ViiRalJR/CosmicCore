package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorEquipEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RepairGuard extends ArmorEquipEventEnchant {

    @ConfigValue
    private int cooldown = 60;
    @ConfigValue
    private int duration = 8;

    public RepairGuard() {
        super("Repair Guard", EnchantTier.ELITE, false, 3, EnchantType.ARMOR, "Whenever you remove a low durability piece of armor to repair it,", "ou will get up to 10 absorption hearts (depending on level)", "while you are fixing it.");
    }

    @Override
    public void runArmorEquipEvent(Player player, int level) {

    }

    @Override
    public void runArmorUnEquipEvent(Player player, int level) {
        if (super.isOnCooldown(player)) return;
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, duration * 20, level + 1), true);
        super.registerCooldown(player, cooldown);
    }
}
