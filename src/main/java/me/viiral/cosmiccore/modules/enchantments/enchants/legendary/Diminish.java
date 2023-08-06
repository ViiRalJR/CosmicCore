package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.enchantscache.DiminishCache;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.utils.CacheUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.text.DecimalFormat;

public class Diminish extends ArmorIncomingPVPDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.0015;
    @ConfigValue
    private String message = "&e&l* DIMINISH [&eDMG {damage}&l] *";
    private DecimalFormat df;

    public Diminish() {
        super("Diminish", EnchantTier.LEGENDARY, false, 6, EnchantType.CHESTPLATE, "When this effect procs, the next", "attack dealt to you cannot deal more", "than the total amount of damage / 2", "you took from the previous attack.");
        this.df = new DecimalFormat("#.##");
    }

    //TODO: finish message

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        DiminishCache diminishCache = CacheUtils.getDiminishLastAttackCache(victim);

        if (diminishCache.getLastDamage() != -1.0) {
            final double maxDmg = diminishCache.getLastDamage() / 2;
            if (event.getDamage() > maxDmg) {
                super.getDamageHandler().setDamage(event, maxDmg, super.getName());
                diminishCache.setLastDamage(-1.0);
                return;
            }
        }

        if (Math.random() < this.procChance * enchantInfo.getLevel()) {
            diminishCache.setLastDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE));
            super.sendMessage(victim, this.message, str -> str.replace("{damage}", String.valueOf(this.df.format(Math.max(0.0, event.getFinalDamage())))));
        }
    }
}
