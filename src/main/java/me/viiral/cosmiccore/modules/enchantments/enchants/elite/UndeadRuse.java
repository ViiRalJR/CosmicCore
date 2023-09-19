package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class UndeadRuse extends ArmorIncomingPVPDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.005;
    @ConfigValue
    private int cooldown = 200;

    public UndeadRuse() {
        super("Undead Ruse", EnchantTier.ELITE, false, 10, EnchantType.ARMOR, "When hit you have a chance to spawn zombie hordes", "to distract and disorient your opponents.");
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (!(attacker instanceof Player)) return;
        if (super.isOnCooldown(victim)) return;

        if (Math.random() < procChance * enchantInfo.getLevel()) {
            Player target = (Player) attacker;
            super.registerCooldown(victim, cooldown);
        }
    }

    @EventHandler
    public void onEntityTargetPlayer(EntityTargetLivingEntityEvent event) {
        if (event.getEntityType() != EntityType.ZOMBIE) return;
        if (!(event.getTarget() instanceof Player)) return;
        if (!event.getEntity().hasMetadata("zombie-target")) return;
        String uuid = event.getEntity().getMetadata("zombie-target").get(0).asString();
        if (!uuid.equals(event.getTarget().getUniqueId().toString())) event.setCancelled(true);
    }
}
