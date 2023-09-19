package me.viiral.cosmiccore.modules.enchantments.enchants.ultimate;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.enchantscache.DominateCache;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Reloadable;import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.utils.CacheUtils;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

public class Dominate extends WeaponDamageEventEnchant implements Reloadable {

    @ConfigValue
    private double procChance = 0.04;
    @ConfigValue
    private int cooldown = 30;
    @ConfigValue
    private String message = "&c&l* DOMINATED [-{dmg-reduction}% DMG for {time}s] *";

    private ParticleBuilder particle;

    @ConfigValue
    private int particleAmount = 50;
    @ConfigValue
    private double particleYoffset = 0.7;
    @ConfigValue
    private double particleSpeed = 0.1;

    public Dominate() {
        super("Dominate", EnchantTier.ULTIMATE, 4, EnchantType.SWORD, "Chance to weaken enemy players on hit, causing them", "to deal (level x 5%) less damage for (level x 2) seconds.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (super.isOnCooldown(attacker)) return;
        if (Math.random() < this.procChance) {
            if (!(victim instanceof Player)) return;
            Player victimPlayer = (Player) victim;
            DominateCache dominateCache = CacheUtils.getDominateCache(victimPlayer);
            dominateCache.procDominate(enchantedItemBuilder.getEnchantmentLevel(this));
            super.sendMessage(victim, this.message, str -> str
                            .replace("{dmg-reduction}", String.valueOf(dominateCache.getDamageReduction()))
                            .replace("{time}", String.valueOf(dominateCache.getDuration()))
            );
            this.particle.setLocation(victimPlayer.getLocation()).display(PVPUtils.getNearbyPlayersExceptPlayer(victimPlayer, 30));
            super.registerCooldown(attacker, this.cooldown);
        }
    }

    @EventHandler
    public void onDamageWhileDominated(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (!(event.getDamager() instanceof Player)) return;
        Player damager = (Player) event.getDamager();
        DominateCache dominateCache = CacheUtils.getDominateCache(damager);
        if (!dominateCache.isDominateActive()) return;
        super.getDamageHandler().reduceDamage(dominateCache.getDamageReduction(), event, this.getName());
    }

    @Override
    public void reloadValues() {
        this.particle = new ParticleBuilder(ParticleEffect.ENCHANTMENT_TABLE)
                .setAmount(this.particleAmount)
                .setOffsetY((float) this.particleYoffset)
                .setSpeed((float) this.particleSpeed);
    }
}
