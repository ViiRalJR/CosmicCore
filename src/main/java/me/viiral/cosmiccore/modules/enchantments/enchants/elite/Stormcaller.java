package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Stormcaller extends ArmorIncomingPVPDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.01;

    public Stormcaller() {
        super("Stormcaller", EnchantTier.ELITE, false, 4, EnchantType.ARMOR, "Strikes lightning on attacking players. ");
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (Math.random() < procChance * enchantInfo.getLevel()) {
            attacker.getLocation().getWorld().strikeLightningEffect(attacker.getLocation());
        }
    }
}
