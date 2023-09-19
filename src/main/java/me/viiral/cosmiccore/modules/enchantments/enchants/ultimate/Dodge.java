package me.viiral.cosmiccore.modules.enchantments.enchants.ultimate;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Reloadable;import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

public class Dodge extends ArmorIncomingPVPDamageEventEnchant implements Reloadable {

    @ConfigValue
    private double procChance = 0.0008;
    @ConfigValue
    private double procChanceWhenSneaking = 0.002;
    @ConfigValue
    private String message = "&e*DODGED*";

    private ParticleBuilder particle;

    @ConfigValue
    private int particleAmount = 50;
    @ConfigValue
    private double particleYoffset = 1.0;
    @ConfigValue
    private double particleSpeed = 0.1;

    public Dodge() {
        super("Dodge", EnchantTier.ULTIMATE, true, 5, EnchantType.ARMOR, "Has a chance to dodge physical", "attacks, increased chance when sneaking.");
        this.reloadValues();
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        double chance = victim.isSneaking() ? this.procChanceWhenSneaking * enchantInfo.getLevel() : this.procChance * enchantInfo.getLevel();

        if (Math.random() < chance) {
            event.setCancelled(true);
            super.sendMessage(victim, this.message);
            victim.getWorld().playSound(victim.getLocation(), Sound.BAT_TAKEOFF, 1.0f, 0.75f);
            this.particle.setLocation(victim.getLocation()).display(PVPUtils.getNearbyPlayersExceptPlayer(victim, 30));
        }
    }

    @Override
    public void reloadValues() {
        this.particle = new ParticleBuilder(ParticleEffect.CLOUD)
                .setAmount(this.particleAmount)
                .setOffsetY((float) this.particleYoffset)
                .setSpeed((float) this.particleSpeed);
    }
}
