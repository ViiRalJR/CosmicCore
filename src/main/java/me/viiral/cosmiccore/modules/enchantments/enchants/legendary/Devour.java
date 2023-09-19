package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.enchantscache.BleedStacksCache;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Reloadable;import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.utils.CacheUtils;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.texture.BlockTexture;

public class Devour extends WeaponDamageEventEnchant implements Reloadable {

    private ParticleBuilder particle;

    @ConfigValue
    private int particleAmount = 50;
    @ConfigValue
    private double particleYoffset = 0.7;
    @ConfigValue
    private double particleSpeed = 0.1;

    public Devour() {
        super("Devour", EnchantTier.LEGENDARY, 4, EnchantType.AXE, "Multiplies damage dealt to players with active bleed stacks", "from the Bleed enchantment.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (!(victim instanceof Player)) return;
        Player victimPlayer = (Player) victim;

        BleedStacksCache victimBleedStack = CacheUtils.getBleedStackCache(victimPlayer);
        this.particle.setLocation(victim.getLocation()).display(PVPUtils.getNearbyPlayersExceptPlayer(victimPlayer, 20));
        super.getDamageHandler().increaseDamage(victimBleedStack.getBleedStack() * 10, event, this.getName());
    }

    @Override
    public void reloadValues() {
        this.particle = new ParticleBuilder(ParticleEffect.BLOCK_DUST)
                .setParticleData(new BlockTexture(Material.DIRT))
                .setAmount(particleAmount)
                .setOffsetY((float) particleYoffset)
                .setSpeed((float) particleSpeed);
    }
}
