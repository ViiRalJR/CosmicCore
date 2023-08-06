package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import me.viiral.cosmiccore.modules.enchantments.events.EnchantProcOnEntityEvent;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.BlindingEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class Clarity extends Enchantment {

    public Clarity() {
        super("Clarity", EnchantTier.LEGENDARY, false, 3, EnchantType.ARMOR, "Immune to Blindness up to level of clarity enchantment.");
    }

    @EventHandler
    public void runEnchantDamageProcEvent(EnchantProcOnEntityEvent event) {
        if (!(event.getEnchantment() instanceof BlindingEnchant)) return;
        if (!(event.getVictim() instanceof Player)) return;
        if (EnchantsAPI.hasEnchantment((Player) event.getVictim(), this)) {
            event.setCancelled(true);
        }
    }
}
