package me.viiral.cosmiccore.modules.enchantments.enchants.ultimate;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Relation;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Cleave extends WeaponDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.05;
    @ConfigValue
    private int damageAmount = 3;

    public Cleave() {
        super("Cleave", EnchantTier.ULTIMATE, 7, EnchantType.AXE, "Damages players within a radius that", "increases with the level of enchant.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (Math.random() < procChance) {
            FPlayer fPlayer = FPlayers.getInstance().getByPlayer(attacker);
            int level = enchantedItemBuilder.getEnchantmentLevel(this);
            attacker.getNearbyEntities(level, 10.0, level).forEach(entity -> {
                if (!(entity instanceof Player nearbyPlayer)) return;
                if (nearbyPlayer.getGameMode() != GameMode.SURVIVAL) return;
                if (!PVPUtils.canPvPInRegion(nearbyPlayer)) return;
                FPlayer fPlayerNearby = FPlayers.getInstance().getByPlayer(nearbyPlayer);
                if (fPlayer.getRelationTo(fPlayerNearby).isAtLeast(Relation.TRUCE)) return;
                super.getDamageHandler().damage(nearbyPlayer, damageAmount, this.getName());
            });

        }
    }
}
