package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.SlownessEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Frozen extends ArmorIncomingPVPDamageEventEnchant implements SlownessEnchant {

    @ConfigValue
    private double procChance = 0.003;

    public Frozen() {
        super("Frozen", EnchantTier.ELITE, false, 3, EnchantType.ARMOR, "Can cause slowness to the attacker", "when attacked.");
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (Math.random() < procChance * enchantInfo.getLevel()) {
            attacker.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 0, true));
        }
    }
}
