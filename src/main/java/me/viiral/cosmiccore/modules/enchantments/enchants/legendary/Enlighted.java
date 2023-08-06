package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Enlighted extends ArmorIncomingPVPDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.075;

    public Enlighted() {
        super("Enlighted", EnchantTier.LEGENDARY, true, 3, EnchantType.ARMOR, "Has a chance to heal you", "while taking damage");
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (super.isOnCooldown(victim)) return;

        if (Math.random() < procChance * enchantInfo.getLevel()) {
            super.getDamageHandler().healEntity(victim, enchantInfo.getAmount(), this.getName());
            super.registerCooldown(victim, 10);
        }
    }
}
