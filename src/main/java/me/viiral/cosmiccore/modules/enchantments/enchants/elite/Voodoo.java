package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorOutgoingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Reloadable;import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

public class Voodoo extends ArmorOutgoingPVPDamageEventEnchant implements Reloadable {

    @ConfigValue
    private double procChance = 0.01;
    @ConfigValue
    private int duration = 3;
    @ConfigValue
    private int weaknessAmplifier = 0;

    private ParticleBuilder particle;

    @ConfigValue
    private int particleAmount = 300;
    @ConfigValue
    private double particleYoffset = 1.8;
    @ConfigValue
    private double particleSpeed = 0.1;

    public Voodoo() {
        super("Voodoo", EnchantTier.ELITE, false, 6, EnchantType.ARMOR, "Has a chance to deal weakness to", "your enemy.");
    }

    @Override
    public void runOutgoingDamageEvent(EntityDamageByEntityEvent event, Player attacker, LivingEntity victim, EnchantInfo enchantInfo) {
        if (!(victim instanceof Player)) return;
        if (event.getDamage() <= 0) return;
        if (Math.random() < this.procChance * enchantInfo.getLevel()) {
            Player victimPlayer = ((Player) victim);
            victim.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, this.duration * 20, this.weaknessAmplifier, true));
            this.particle.setLocation(victimPlayer.getLocation()).display(PVPUtils.getNearbyPlayersExceptPlayer(victimPlayer, 20));
        }
    }

    @Override
    public void reloadValues() {
        this.particle = new ParticleBuilder(ParticleEffect.SPELL_MOB)
                .setAmount(this.particleAmount)
                .setOffsetY((float) this.particleYoffset)
                .setSpeed((float) this.particleSpeed);
    }

}
