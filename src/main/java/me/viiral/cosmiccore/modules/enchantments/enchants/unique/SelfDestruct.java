package me.viiral.cosmiccore.modules.enchantments.enchants.unique;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Relation;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.Random;

public class SelfDestruct extends ArmorIncomingPVPDamageEventEnchant {

    public SelfDestruct() {
        super("Self Destruct", EnchantTier.UNIQUE, false, 3, EnchantType.ARMOR, "When close to death buffed TnT spawns around you.");
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (super.isOnCooldown(victim)) return;
        if (victim.getHealth() - event.getFinalDamage() <= 0.0) {
            super.registerCooldown(victim, 200);
            victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2.0f, 0.75f);
            int fuseTicks = 120 - enchantInfo.getLevel() * 20;
            for (int tntAmount = enchantInfo.getLevel() * 2; tntAmount > 0; --tntAmount) {
                this.spawnExplosion(victim, this.getNearbyLocation(victim.getLocation(), 3, 1), fuseTicks);
            }
        }
    }

    @EventHandler(priority= EventPriority.LOWEST)
    public void onBlockExplode(EntityExplodeEvent event) {
        if (event.getEntity().hasMetadata("SelfDestructTNT")) {
            event.setYield(0.0f);
            event.blockList().clear();
            if (!PVPUtils.canPvPInRegion(event.getLocation())) return;
            Location loc = event.getLocation();
            loc.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);

            String exploder = event.getEntity().getMetadata("SelfDestructTNT").get(0).asString();
            Player pExploder = Bukkit.getPlayer(exploder);
            FPlayer fp = pExploder != null ? FPlayers.getInstance().getByPlayer(pExploder) : null;
            for (Entity ent : event.getEntity().getNearbyEntities(4, 4, 4)) {
                if (!(ent instanceof LivingEntity) || ent instanceof Player && fp != null && fp.getRelationTo(FPlayers.getInstance().getByPlayer((Player) ent)).isAtLeast(Relation.TRUCE) || !PVPUtils.canPvPInRegion(ent.getLocation())) continue;
                ((LivingEntity)ent).damage(16.0);
                ent.setFireTicks(40);
                this.pushAwayEntity((LivingEntity)ent, event.getEntity(), 1.7f);
            }
            event.getEntity().remove();
        }
    }

    private void spawnExplosion(Player exploder, Location location, int fuseTicks) {
        if (location.getBlock().getType().isSolid()) {
            return;
        }
        TNTPrimed tnt = (TNTPrimed)location.getWorld().spawnEntity(location, EntityType.PRIMED_TNT);
        tnt.setFuseTicks(fuseTicks);
        tnt.setMetadata("SelfDestructTNT", new FixedMetadataValue(CosmicCore.getInstance(), (Object)exploder.getName()));
    }

    private Location getNearbyLocation(Location location, int radius, int y_boost) {
        Random rand = new Random();
        Location rand_loc = location.clone();
        rand_loc.add((rand.nextBoolean() ? 1 : -1) * (rand.nextInt(radius) + 1), 0.0, (rand.nextBoolean() ? 1 : -1) * (rand.nextInt(radius) + 1));
        rand_loc.add(0.5, y_boost, 0.5);
        while ((rand_loc.getBlock().getType() != Material.AIR || rand_loc.getBlock().getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType() != Material.AIR) && rand_loc.getY() < 255.0) {
            rand_loc = rand_loc.add(0.0, 1.0, 0.0);
        }
        return rand_loc;
    }

    public void pushAwayEntity(final LivingEntity center, final Entity entity, final double speed) {
        pushAwayEntity(center.getLocation(), entity, speed);
    }

    public void pushAwayEntity(final Location center, final Entity entity, final double speed) {
        final Vector unitVector = entity.getLocation().toVector().subtract(center.toVector());
        if (unitVector.length() != 0.0) {
            unitVector.normalize();
        }
        entity.setVelocity(unitVector.multiply(speed));
    }
}
