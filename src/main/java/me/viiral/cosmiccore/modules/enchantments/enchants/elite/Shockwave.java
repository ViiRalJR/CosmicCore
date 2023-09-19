package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Relation;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;
import me.viiral.cosmiccore.modules.mask.MaskAPI;
import me.viiral.cosmiccore.modules.mask.struct.MaskRegister;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Shockwave extends ArmorIncomingPVPDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.004;
    @ConfigValue
    private int cooldown = 100;
    @ConfigValue
    private int range = 5;

    public Shockwave() {
        super("Shockwave", EnchantTier.ELITE, false, 5, EnchantType.CHESTPLATE, "Chance of explosive knockback", "surrounding players when you are hit.");
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (super.isOnCooldown(victim)) return;

        if (MaskAPI.hasMaskOn((Player) attacker, MaskRegister.getInstance().getMaskFromName("Terminator")))
            if ((new Random().nextInt(100) + 1) > 50) return;


        if (Math.random() < procChance * enchantInfo.getLevel()) {
            FPlayer fPlayer = FPlayers.getInstance().getByPlayer(victim);
            victim.getNearbyEntities(range, range, range).forEach(entity -> {
                if (!(entity instanceof Player)) return;
                Player nearbyPlayer = (Player) entity;


                if (nearbyPlayer.getGameMode() != GameMode.SURVIVAL) return;
                if (!PVPUtils.canPvPInRegion(nearbyPlayer)) return;
                FPlayer fPlayerNearby = FPlayers.getInstance().getByPlayer(nearbyPlayer);
                if (fPlayer.getRelationTo(fPlayerNearby).isAtLeast(Relation.TRUCE)) return;
                nearbyPlayer.setVelocity(victim.getLocation().toVector().subtract(nearbyPlayer.getLocation().toVector()).normalize().multiply(3));
            });
            victim.playSound(victim.getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 0.1f);
            super.registerCooldown(victim, cooldown);
        }
    }
}
