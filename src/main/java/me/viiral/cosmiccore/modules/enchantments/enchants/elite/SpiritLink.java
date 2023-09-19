package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Relation;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class SpiritLink extends ArmorIncomingPVPDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.004;
    @ConfigValue
    private int cooldown = 100;
    @ConfigValue
    private int range = 5;
    @ConfigValue
    private int healAmount = 5;

    public SpiritLink() {
        super("Spirit Link", EnchantTier.ELITE, false, 5, EnchantType.CHESTPLATE, "Heals nearby faction/allies when you are damaged.");
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (super.isOnCooldown(victim)) return;

        if (Math.random() < procChance * enchantInfo.getLevel()) {
            FPlayer fPlayer = FPlayers.getInstance().getByPlayer(victim);
            for (Entity nearbyEntity : victim.getNearbyEntities(range, range, range)) {
                if (!(nearbyEntity instanceof Player nearbyPlayer)) continue;
                if (nearbyPlayer.getGameMode() != GameMode.SURVIVAL) continue;
                if (!PVPUtils.canPvPInRegion(nearbyPlayer)) continue;
                FPlayer fPlayerNearby = FPlayers.getInstance().getByPlayer(nearbyPlayer);
                if (fPlayer.getRelationTo(fPlayerNearby).isAtMost(Relation.NEUTRAL)) continue;
                super.getDamageHandler().healEntity(nearbyPlayer, healAmount, this.getName());
            }
            super.registerCooldown(victim, cooldown);
        }
    }
}
