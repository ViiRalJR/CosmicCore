package me.viiral.cosmiccore.modules.enchantments.enchants.ultimate;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.Sound;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class Spirits extends ArmorIncomingPVPDamageEventEnchant {

    public Spirits() {
        super("Spirits", EnchantTier.ULTIMATE,false,  10, EnchantType.ARMOR, "&fChance to spawn blazes that attack", "&fyour target.");
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (super.isOnCooldown(victim)) return;

        if (!(attacker instanceof Player)) return;

        if (Math.random() < 0.03) {
            victim.playSound(victim.getLocation(), Sound.ENTITY_BLAZE_DEATH, 1.0F, 0.5F);
            Player attackerPlayer = ((Player) attacker);

            for (int i = 0; i < 3; i++) {
                Creature blaze = (Creature) victim.getWorld().spawnEntity(victim.getLocation(), EntityType.BLAZE);
                blaze.setTarget(attacker);
                blaze.setCustomName(CC.translate("&e" + victim.getName() + "'s Blaze"));
                blaze.setMetadata("spirits-target", new FixedMetadataValue(CosmicCore.getInstance(), attackerPlayer.getUniqueId()));
            }

            super.registerCooldown(victim, 200);
        }
    }

    @EventHandler
    public void onEntityTargetPlayer(EntityTargetLivingEntityEvent event) {
        if (event.getEntityType() != EntityType.BLAZE) return;
        if (!(event.getTarget() instanceof Player)) return;
        if (!event.getEntity().hasMetadata("spirits-target")) return;
        String uuid = String.valueOf(event.getEntity().getMetadata("spirits-target").get(0).asString());
        if (!uuid.equals(event.getTarget().getUniqueId().toString())) event.setCancelled(true);
    }
}
