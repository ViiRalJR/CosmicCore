package me.viiral.cosmiccore.modules.enchantments.enchants.unique;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.utils.XPUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class SkillSwipe extends WeaponDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.4;

    public SkillSwipe() {
        super("Skill Swipe", EnchantTier.UNIQUE, 5, EnchantType.SWORD, "A chance to steal some of your enemy's EXP every time you damage them.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (!(victim instanceof Player)) return;

        Player victimPlayer = ((Player) victim);
        int level = enchantedItemBuilder.getEnchantmentLevel(this);
        int amountToSteal = level * 6;
        int targetXp = XPUtils.getTotalExperience(victimPlayer);

        if (targetXp - amountToSteal < 0) {
            amountToSteal = targetXp;
        }

        if (amountToSteal <= 0) {
            return;
        }

        if (Math.random() < procChance) {
            XPUtils.setTotalExperience(victimPlayer, targetXp - amountToSteal);
            XPUtils.setTotalExperience(attacker, XPUtils.getTotalExperience(attacker) + amountToSteal);
        }
    }
}
