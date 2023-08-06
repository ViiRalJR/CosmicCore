package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DeathGod extends ArmorIncomingPVPDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.07;
    @ConfigValue
    private int cooldown = 30;
    @ConfigValue
    private String message = "&6&l* DEATH GOD [&6{heal-amount} HP&l] *";

    public DeathGod() {
        super("Death God", EnchantTier.LEGENDARY, false, 3, EnchantType.HELMET, "Attacks that bring your HP to (level+4) hearts or lower", "have a chance to heal you for (level+5) hearts instead.");
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (super.isOnCooldown(victim)) return;
        if (victim.getHealth() > enchantInfo.getLevel() + 4) return;
        if (Math.random() < procChance * enchantInfo.getLevel()) {
            super.getDamageHandler().healEntity(victim, enchantInfo.getLevel() + 5, this.getName());
            super.registerCooldown(victim, cooldown);
            super.sendMessage(victim, message, str -> str.replace("{heal-amount}", String.valueOf(enchantInfo.getLevel() + 5)));
        }
    }
}
