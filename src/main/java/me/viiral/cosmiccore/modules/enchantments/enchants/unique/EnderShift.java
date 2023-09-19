package me.viiral.cosmiccore.modules.enchantments.enchants.unique;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Reloadable;import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

public class EnderShift extends ArmorIncomingPVPDamageEventEnchant implements Reloadable {

    @ConfigValue
    private double procChance = 0.4;
    @ConfigValue
    private int cooldown = 70;
    @ConfigValue
    private String message = "&d&l(!) &dYou were about to die so you entered the Ender dimension, escape to safety!";

    private ParticleBuilder particle;

    @ConfigValue
    private int particleAmount = 50;
    @ConfigValue
    private double particleYoffset = 1.0;
    @ConfigValue
    private double particleSpeed = 0.1;

    public EnderShift() {
        super("Ender Shift", EnchantTier.UNIQUE, false, 3, EnchantType.BOOTS_AND_HELMET, "&fGives speed and health boost when on low health", "&ffor a temporary amount of time.");
        this.reloadValues();
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (victim.getHealth() - event.getFinalDamage() > 3) return;
        if (super.isOnCooldown(victim)) return;

        if (Math.random() < procChance) {
            super.getDamageHandler().cancelDamage(event, this.getName());
            victim.playSound(victim.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0F, 0.5F);
            this.addPotionEffect(victim, PotionEffectType.ABSORPTION, enchantInfo.getLevel() * 20, enchantInfo.getLevel() + 2);
            this.addPotionEffect(victim, PotionEffectType.SPEED, (enchantInfo.getLevel() + 1) * 20, enchantInfo.getLevel() + 2);
            super.registerCooldown(victim, cooldown);
            this.particle.setLocation(victim.getLocation()).display(PVPUtils.getNearbyPlayersExceptPlayer(victim, 30));
            victim.playEffect(victim.getLocation(), Effect.STEP_SOUND, Material.ICE);
        }
    }

    @Override
    public void reloadValues() {
        this.particle = new ParticleBuilder(ParticleEffect.SPELL_WITCH)
                .setAmount(particleAmount)
                .setOffsetY((float) particleYoffset)
                .setSpeed((float) particleSpeed);
    }
}
