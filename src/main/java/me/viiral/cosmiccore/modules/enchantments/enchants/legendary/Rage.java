package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.enchantscache.RageStacksCache;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Reloadable;import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.utils.CacheUtils;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.texture.BlockTexture;

public class Rage extends WeaponDamageEventEnchant implements Reloadable {

    @ConfigValue
    private String damageFormula = "(level * rageStack) * 2";
    private Expression damageExpression;
    private ParticleBuilder particle;

    @ConfigValue
    private int particleAmount = 1000;
    @ConfigValue
    private double particleYoffset = 1.2;
    @ConfigValue
    private double particleSpeed = 0.05;

    public Rage() {
        super("Rage", EnchantTier.LEGENDARY, 6, EnchantType.WEAPON, "More consecutive hits cause more", "damage to be dealt.");
        this.reloadValues();
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (!(victim instanceof Player victimPlayer)) return;

        RageStacksCache rageStacksCache = CacheUtils.getRageStackCache(attacker);

        if (rageStacksCache.getLastRageStackTime() + 30000 < System.currentTimeMillis() || !rageStacksCache.isSameVictim(victimPlayer)) {
            rageStacksCache.updateLastRageStackTime();
            rageStacksCache.resetRageStack();
            rageStacksCache.updateVictim(victimPlayer);
            return;
        }

        super.getDamageHandler().increaseDamage(this.damageExpression
                .setVariable("level", enchantedItemBuilder.getEnchantmentLevel(this))
                .setVariable("rageStack", rageStacksCache.getRageStack())
                .evaluate(), event, this.getName());

        if (rageStacksCache.getRageStack() > 1) {
            this.particle.setLocation(victim.getLocation()).display(PVPUtils.getNearbyPlayersExceptPlayer(victimPlayer, 20));
        }

        rageStacksCache.incrementRageStack();
        rageStacksCache.updateLastRageStackTime();
        rageStacksCache.updateVictim(victimPlayer);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player && event.getDamager() instanceof Player) {
            RageStacksCache victimRageStack = CacheUtils.getRageStackCache(player);
            if (victimRageStack.getRageStack() > 0) {
                victimRageStack.resetRageStack();
            }

        }
    }

    @Override
    public void reloadValues() {
        this.damageExpression = new ExpressionBuilder(this.damageFormula).variable("level").variable("rageStack").build();
        this.particle = new ParticleBuilder(ParticleEffect.BLOCK_DUST)
                .setParticleData(new BlockTexture(Material.REDSTONE_BLOCK))
                .setAmount(this.particleAmount)
                .setOffsetY((float) this.particleYoffset)
                .setSpeed((float) this.particleSpeed);
    }
}
