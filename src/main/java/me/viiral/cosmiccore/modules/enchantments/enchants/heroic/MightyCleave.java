package me.viiral.cosmiccore.modules.enchantments.enchants.heroic;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Relation;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.HeroicEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class MightyCleave extends WeaponDamageEventEnchant implements HeroicEnchant {

    @ConfigValue
    private double procChance = 0.10;
    @ConfigValue
    private int damageAmount = 5;

    public MightyCleave() {
        super("Mighty Cleave", EnchantTier.HEROIC, 7, EnchantType.AXE, "Heroic Enchant.", "Damages players within a radius that", "increases with the level of enchant.", "Double chance and double damage");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (Math.random() < procChance) {
            FPlayer fPlayer = FPlayers.getInstance().getByPlayer(attacker);
            int level = enchantedItemBuilder.getEnchantmentLevel(this);
            attacker.getNearbyEntities(level, 10.0, level).forEach(entity -> {
                if (!(entity instanceof Player)) return;
                Player nearbyPlayer = (Player) entity;

                if (nearbyPlayer.getGameMode() != GameMode.SURVIVAL) return;
                if (!PVPUtils.canPvPInRegion(nearbyPlayer)) return;
                FPlayer fPlayerNearby = FPlayers.getInstance().getByPlayer(nearbyPlayer);
                if (fPlayer.getRelationTo(fPlayerNearby).isAtLeast(Relation.TRUCE)) return;
                super.getDamageHandler().damage(nearbyPlayer, damageAmount, this.getName());
            });

        }
    }

    @Override
    public Enchantment getNonHeroicEnchant() {
        return CosmicCore.getInstance().getEnchantRegister().getEnchantmentFromName("Cleave");
    }
}
