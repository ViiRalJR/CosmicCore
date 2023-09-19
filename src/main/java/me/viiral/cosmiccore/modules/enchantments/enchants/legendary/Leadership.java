package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Relation;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorOutgoingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Leadership extends ArmorOutgoingPVPDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.06;
    @ConfigValue
    private int range = 12;

    public Leadership() {
        super("Leadership", EnchantTier.LEGENDARY, false, 5, EnchantType.ARMOR, "The more allies near you, the more damage you deal.");
    }

    @Override
    public void runOutgoingDamageEvent(EntityDamageByEntityEvent event, Player attacker, LivingEntity victim, EnchantInfo enchantInfo) {
        if (Math.random() < this.procChance * enchantInfo.getLevel()) {

            int counter = 0;
            FPlayer fPlayer = FPlayers.getInstance().getByPlayer(attacker);
            for (Entity nearbyEntity : attacker.getNearbyEntities(this.range, this.range, this.range)) {
                if (!(nearbyEntity instanceof Player nearbyPlayer)) return;
                if (nearbyPlayer.getGameMode() != GameMode.SURVIVAL) return;
                if (!PVPUtils.canPvPInRegion(nearbyPlayer)) return;
                FPlayer fPlayerNearby = FPlayers.getInstance().getByPlayer(nearbyPlayer);
                if (fPlayer.getRelationTo(fPlayerNearby).isAtMost(Relation.NEUTRAL)) return;
                counter++;
            }

            super.getDamageHandler().increaseDamage(Math.max(counter, 5) * 4, event, this.getName());
        }
    }
}
