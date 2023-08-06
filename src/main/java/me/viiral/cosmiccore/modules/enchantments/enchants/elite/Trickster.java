package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashSet;

public class Trickster extends ArmorIncomingPVPDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.0125;

    public Trickster() {
        super("Trickster", EnchantTier.ELITE, false, 8, EnchantType.ARMOR, "When hit you have a chance to teleport directly behind", "your opponent and take them by surprise.");
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (Math.random() < procChance * enchantInfo.getLevel()) {
            Location l1 = victim.getLocation().add(0.0, 1.0, 0.0);
            Location l2 = victim.getTargetBlock(null, 4).getLocation().add(0.0, 1.0, 0.0).setDirection(attacker.getLocation().getDirection());
            if (l2.getBlock().getType() == Material.AIR && l2.getBlock().getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType() == Material.AIR) {
                victim.teleport(l2, PlayerTeleportEvent.TeleportCause.PLUGIN);
                victim.getWorld().playSound(l1, Sound.BLOCK_PORTAL_TRIGGER, 0.8f, 1.4f);
            }
        }
    }
}
