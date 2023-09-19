package me.viiral.cosmiccore.modules.enchantments.enchants.soul;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Relation;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.DrainSoulEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Reloadable;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.utils.CacheUtils;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.List;

public class DivineImmolation extends WeaponDamageEventEnchant implements DrainSoulEnchant, Reloadable {

    @ConfigValue
    private String damageFormula = "level * 1.1";
    @ConfigValue
    private String message = "&c&l** DIVINE IMMOLATION **";
    private Expression damageExpression;
    private ParticleBuilder particle;

    public DivineImmolation() {
        super("Divine Immolation", EnchantTier.SOUL, 4, EnchantType.SWORD, "Active soul enchant. Your weapons are imbued with divine fire,", "turning all your physical attacks into Area of Effect spells", "and igniting divine fire upon all nearby enemies.");
        this.reloadValues();
        this.particle = new ParticleBuilder(ParticleEffect.LAVA)
                .setAmount(50)
                .setOffsetY(1)
                .setSpeed(0.15f);
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (!(victim instanceof Player)) return;
        int level = enchantedItemBuilder.getEnchantmentLevel(this);
        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(attacker);
        List<Entity> nearbyPlayers = victim.getNearbyEntities(level, level, level);
        nearbyPlayers.add(victim);

        for (Entity nearbyEntity : nearbyPlayers) {
            if (nearbyEntity.equals(attacker)) continue;
            if (!(nearbyEntity instanceof Player nearbyPlayer)) continue;
            if (nearbyPlayer.getGameMode() != GameMode.SURVIVAL) continue;
            if (!PVPUtils.canPvPInRegion(nearbyPlayer)) return;

            FPlayer fPlayerNearby = FPlayers.getInstance().getByPlayer(nearbyPlayer);

            if (fPlayer.getRelationTo(fPlayerNearby).isAtLeast(Relation.TRUCE)) continue;
            this.addPotionEffect(nearbyPlayer, PotionEffectType.WITHER, 60, 1);
            nearbyPlayer.playSound(nearbyEntity.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1.0f, 0.3f);
            nearbyPlayer.playSound(nearbyEntity.getLocation(), Sound.ENTITY_PIGLIN_ANGRY, 0.8f, 0.5f);
            CacheUtils.getDivineCache(nearbyPlayer).updateLastProcTime();
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (event.getCause() != EntityDamageEvent.DamageCause.WITHER) return;

        if (CacheUtils.getDivineCache(player).isDivineActive()) {
            super.getDamageHandler().damage(player, this.damageExpression.setVariable("level", 4).evaluate(), "&c&lDivine Tick");
            player.playSound(player.getLocation(), Sound.ENTITY_PIGLIN_ANGRY, 0.8f, 0.5f);
            super.sendMessage(player, this.message);
            this.particle.setLocation(player.getLocation()).display(PVPUtils.getNearbyPlayersExceptPlayer(player, 30));
        }
    }

    @Override
    public int getSoulCost() {
        return 5;
    }

    @Override
    public void reloadValues() {
        this.damageExpression = new ExpressionBuilder(this.damageFormula).variable("level").build();
    }
}
