package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Reloadable;import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.texture.BlockTexture;

public class Execute extends WeaponDamageEventEnchant implements Reloadable {

    @ConfigValue
    private double procChance = 0.03;
    @ConfigValue
    private double damageBuff = 12.5;

    private ParticleBuilder particle;

    @ConfigValue
    private int particleAmount = 50;
    @ConfigValue
    private double particleYoffset = 0.7;
    @ConfigValue
    private double particleSpeed = 0.1;

    public Execute() {
        super("Execute", EnchantTier.ELITE, 7, EnchantType.WEAPON, "Damage buff when your target is at low hp.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (!(victim instanceof Player)) return;
        if (victim.getHealth() > 10) return;
        if (Math.random() < procChance * enchantedItemBuilder.getEnchantmentLevel(this)) {
            Player victimPlayer = (Player) victim;
            super.getDamageHandler().increaseDamage(damageBuff, event, this.getName());
            victimPlayer.getWorld().playEffect(victimPlayer.getEyeLocation(), Effect.STEP_SOUND, Material.REDSTONE_WIRE);
            this.particle.setLocation(victimPlayer.getLocation()).display(PVPUtils.getNearbyPlayersExceptPlayer(victimPlayer, 20));
        }
    }

    @Override
    public void reloadValues() {
        this.particle = new ParticleBuilder(ParticleEffect.BLOCK_DUST)
                .setParticleData(new BlockTexture(Material.REDSTONE_WIRE))
                .setAmount(particleAmount)
                .setOffsetY((float) particleYoffset)
                .setSpeed((float) particleSpeed);
    }
}
