package me.viiral.cosmiccore.modules.enchantments.enchants.heroic;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorOutgoingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.HeroicEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlanetaryDeathbringer extends ArmorOutgoingPVPDamageEventEnchant implements HeroicEnchant {

    @ConfigValue
    private double procChance = 0.06;
    @ConfigValue
    private double damageBuff = 25;

    public PlanetaryDeathbringer() {
        super("Planetary Deathbringer", EnchantTier.HEROIC, false, 3, EnchantType.ARMOR, "Heroic Enchant.", "Has a chance to deal double damage to", "your target with double the chance.");
        setHeroic();
    }

    @Override
    public void runOutgoingDamageEvent(EntityDamageByEntityEvent event, Player attacker, LivingEntity victim, EnchantInfo enchantInfo) {
        if (Math.random() < procChance * enchantInfo.getLevel()) {
            super.getDamageHandler().increaseDamage(damageBuff, event, this.getName());
        }
    }

    @Override
    public Enchantment getNonHeroicEnchant() {
        return CosmicCore.getInstance().getEnchantRegister().getEnchantmentFromName("Deathbringer");
    }
}
