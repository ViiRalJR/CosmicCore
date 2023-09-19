package me.viiral.cosmiccore.modules.enchantments.enchants.unique;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Relation;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class PlagueCarrier extends ArmorIncomingPVPDamageEventEnchant {

    public PlagueCarrier() {
        super("Plague Carrier", EnchantTier.UNIQUE, false, 8, EnchantType.LEGGINGS, "When near death summons creepers and debuffs to avenge you.");
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        double dmg = event.getFinalDamage();
        if (victim.getHealth() - dmg <= 0.0) {
            int radius = Math.round((float) enchantInfo.getLevel() * 1.5f);
            ArrayList<LivingEntity> victims = new ArrayList<>();
            FPlayer fp = FPlayers.getInstance().getByPlayer(victim);
            for (Entity ent : victim.getNearbyEntities(radius, radius, radius)) {
                if (!(ent instanceof LivingEntity) || ent instanceof Player && FPlayers.getInstance().getByPlayer((Player) ent).getRelationTo(fp).isAtLeast(Relation.TRUCE))
                    continue;
                victims.add((LivingEntity) ent);
            }

            victim.getWorld().playSound(victim.getLocation(), Sound.CREEPER_HISS, 2.0f, 0.75f);
            for (LivingEntity livingEntity : victims) {
                if (!(livingEntity instanceof Player)) continue;
                livingEntity.getWorld().playSound(livingEntity.getLocation(), Sound.CREEPER_HISS, 1.6f, 0.75f);
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, (2 + enchantInfo.getLevel()) * 20, enchantInfo.getLevel() >= 7 ? 1 : 0));
            }
            for (int plagueCarriers = radius / 2; plagueCarriers > 0; --plagueCarriers) {
                this.spawnPlagueCreeper(victim.getLocation().add(0.0, 1.0, 0.0), enchantInfo.getLevel());
            }

        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.getEntity().hasMetadata("plagueCarrier")) {
            event.setYield(0.0f);
            int level = event.getEntity().getMetadata("plagueCarrier").get(0).asInt();
            Location loc = event.getLocation();
            loc.getWorld().playSound(loc, Sound.EXPLODE, 1.0f, 1.0f);

            for (Entity ent : event.getEntity().getNearbyEntities(5.0, 4.0, 5.0)) {
                if (!(ent instanceof LivingEntity)) continue;
                LivingEntity le = (LivingEntity)ent;
                le.addPotionEffect(new PotionEffect(PotionEffectType.POISON, level * 2 * 20, level == 8 ? 2 : (level >= 4 ? 1 : 0)));
                if (level >= 3) {
                    le.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, level * 2 * 20, level == 8 ? 2 : (level >= 4 ? 1 : 0)));
                }
                if (level >= 6) {
                    le.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, level * 2 * 20, level == 8 ? 2 : 1));
                }
                if (level != 8) continue;
                le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, level * 2 * 20, 2));
            }
            event.getEntity().remove();
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().hasMetadata("plagueCarrier")) {
            e.setDroppedExp(0);
            e.getDrops().clear();
            e.getEntity().remove();
        }
    }

    private void spawnPlagueCreeper(Location location, int level) {
        Creeper creeper = (Creeper) location.getWorld().spawnEntity(location, EntityType.CREEPER);
        creeper.setMetadata("plagueCarrier", new FixedMetadataValue(CosmicCore.getInstance(), level));
        creeper.setCustomName(ChatColor.LIGHT_PURPLE + "Plague Carrier");
        creeper.setCanPickupItems(false);
        creeper.setCustomNameVisible(true);
        creeper.setPowered(true);

        if (!creeper.isDead()) {
            Bukkit.getScheduler().runTaskLater(CosmicCore.getInstance(), creeper::remove, 600L);
        }
    }
}
