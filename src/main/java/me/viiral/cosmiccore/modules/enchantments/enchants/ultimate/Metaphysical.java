package me.viiral.cosmiccore.modules.enchantments.enchants.ultimate;

import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import me.viiral.cosmiccore.modules.enchantments.events.SlownessEnchantProc;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class Metaphysical extends Enchantment {

    @ConfigValue
    private double procChance = 0.23;
    @ConfigValue
    private String message = "&8&l** METAPHYSICAL (&8{blocked-enchantment} blocked&l) **";

    public Metaphysical() {
        super("Metaphysical", EnchantTier.ULTIMATE, false, 4, EnchantType.BOOTS, "A chance to resist the slowness given by enemy Trap, Snare, and Pummel enchantments.", "At max level, you will only be affected approx. 10% of the time.");
    }

    @EventHandler
    public void runEnchantDamageProcEvent(SlownessEnchantProc event) {
        if (!(event.getVictim() instanceof Player)) return;
        if (EnchantsAPI.hasEnchantment(((Player) event.getVictim()), this)) {
            if (Math.random() < procChance * EnchantsAPI.getEnchantmentLevel((Player) event.getVictim(), this)) {
                event.setCancelled(true);
                super.sendMessage(event.getVictim(), message, str -> str.replace("{blocked-enchantment}", event.getEnchantment().getName()));
            }
        }
    }
}
