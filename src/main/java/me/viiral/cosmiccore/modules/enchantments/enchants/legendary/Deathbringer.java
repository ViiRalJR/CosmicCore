package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorOutgoingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Heroicable;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Deathbringer extends ArmorOutgoingPVPDamageEventEnchant implements Heroicable {

    @ConfigValue
    private double procChance = 0.03;
    @ConfigValue
    private double damageBuff = 25;

    public Deathbringer() {
        super("Deathbringer", EnchantTier.LEGENDARY, false, 3, EnchantType.ARMOR, "Has a chance to deal double damage", "to your target.");
    }

    @Override
    public void runOutgoingDamageEvent(EntityDamageByEntityEvent event, Player attacker, LivingEntity victim, EnchantInfo enchantInfo) {
        if (Math.random() < procChance * enchantInfo.getLevel()) {
            super.getDamageHandler().increaseDamage(damageBuff, event, this.getName());
        }
    }

    @Override
    public Enchantment getHeroicEnchant() {
        return CosmicCore.getInstance().getEnchantRegister().getEnchantmentFromName("Planetary Deathbringer");
    }

}
