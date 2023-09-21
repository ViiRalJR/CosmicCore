package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Reloadable;import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;
import me.viiral.cosmiccore.modules.mask.MaskAPI;
import me.viiral.cosmiccore.modules.mask.struct.MaskRegister;

import me.viiral.cosmiccore.modules.user.User;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.texture.BlockTexture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RocketEscape extends ArmorIncomingPVPDamageEventEnchant implements Reloadable {

    @ConfigValue
    private double procChance = 0.2;
    @ConfigValue
    private int cooldown = 150;
    @ConfigValue
    private List<String> message = Arrays.asList("&r ", "&a&l(!) &ayour Rocket Escape boots have activated, recover while they last!", "&r ");

    private ParticleBuilder particle;
    @ConfigValue
    private int particleAmount = 50;
    @ConfigValue
    private double particleYoffset = 0.7;
    @ConfigValue
    private double particleSpeed = 0.05;

    private final List<Player> noFallDamage;

    public RocketEscape() {
        super("Rocket Escape", EnchantTier.ELITE, false, 3, EnchantType.BOOTS, "Chance to blast off into the air", "at low health.");
        this.noFallDamage = new ArrayList<>();
        this.reloadValues();
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (victim.getHealth() - event.getFinalDamage() > 2) return;
        if (super.isOnCooldown(victim)) return;

        User user = CosmicCore.getInstance().getUserManager().getUsers().get(attacker.getUniqueId());

        if (user != null && user.types.contains(EffectType.BLOCK_ROCKET_ESCAPE)) return;


        if (Math.random() < this.procChance * enchantInfo.getLevel()) {
            super.getDamageHandler().cancelDamage(event, this.getName());
            victim.setVelocity(new Vector(0, (enchantInfo.getLevel() + 2) * 3, 0));
            this.addPotionEffect(victim, PotionEffectType.REGENERATION, 20 * (enchantInfo.getLevel() + 2), 19);
            super.registerCooldown(victim, this.cooldown);
            this.particle.setLocation(victim.getLocation()).display(PVPUtils.getNearbyPlayersExceptPlayer(victim, 30));
            super.sendMessage(victim, this.message);
            this.noFallDamage.add(victim);
            Bukkit.getScheduler().runTaskLater(CosmicCore.getInstance(), () -> this.noFallDamage.remove(victim), 160L);
        }
    }

    @EventHandler
    public void onFallDamageAfterRocketEscape(EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) return;
        if (event.getDamage() <= 0) return;
        if (!(event.getEntity() instanceof Player)) return;
        if (!this.noFallDamage.contains((Player) event.getEntity())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onLeaveWhileInNoFallDamageList(PlayerQuitEvent event) {
        this.noFallDamage.remove(event.getPlayer());
    }

    @Override
    public void reloadValues() {
        this.particle = new ParticleBuilder(ParticleEffect.BLOCK_DUST)
                .setParticleData(new BlockTexture(Material.CACTUS))
                .setAmount(this.particleAmount)
                .setOffsetY((float) this.particleYoffset)
                .setSpeed((float) this.particleSpeed);

    }
}
