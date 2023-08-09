package me.viiral.cosmiccore.modules.enchantments.enchants.unique;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;

import me.viiral.cosmiccore.modules.mask.MaskAPI;
import me.viiral.cosmiccore.modules.mask.struct.MaskRegister;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Ragdoll extends ArmorIncomingPVPDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.05;

    public Ragdoll() {
        super("Ragdoll", EnchantTier.UNIQUE, false, 4, EnchantType.ARMOR, "Whenever you take damage you are pushed far back.");
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (attacker instanceof IronGolem) return;
//        Player user = (Player) attacker;
//        if (EliteAPI.hasMask(user.getInventory().getHelmet()) && user.getInventory().getHelmet() != null && EliteAPI.hasMaskEffect(user.getInventory().getHelmet(), "Terminator")) {
//            if (ThreadLocalRandom.current().nextInt(101) <= 50) {
//                return;
//            }
//        }
        if (Math.random() < procChance * enchantInfo.getLevel()) {

            if (MaskAPI.hasMaskOn((Player) attacker, MaskRegister.getInstance().getMaskFromName("Terminator")))
                if ((new Random().nextInt(100) + 1) > 50) return;

            pushAwayEntity(attacker, victim, 1.5 + 0.5 * enchantInfo.getLevel());
        }
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
