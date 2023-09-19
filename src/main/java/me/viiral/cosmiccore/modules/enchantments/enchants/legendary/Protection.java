package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Relation;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;

import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Protection extends ArmorIncomingPVPDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.08;
    @ConfigValue
    private double range = 7.0;
    @ConfigValue
    private int regenerationLevel = 1;
    @ConfigValue
    private int cooldown = 20;

    public Protection() {
        super("Protection", EnchantTier.LEGENDARY, false, 5, EnchantType.ARMOR, "Automatically heals and buffs all nearby faction allies.");
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (super.isOnCooldown(victim)) return;
        if (Math.random() < procChance) {
            int level = enchantInfo.getLevel();
            FPlayer fPlayer = FPlayers.getInstance().getByPlayer(victim);
            victim.getNearbyEntities(range, range, range).forEach(entity -> {
                if (!(entity instanceof Player nearbyPlayer)) return;
                if (nearbyPlayer.getGameMode() != GameMode.SURVIVAL) return;
                if (!PVPUtils.canPvPInRegion(nearbyPlayer)) return;
                FPlayer fPlayerNearby = FPlayers.getInstance().getByPlayer(nearbyPlayer);
                if (fPlayer.getRelationTo(fPlayerNearby).isAtMost(Relation.NEUTRAL)) return;
                super.getDamageHandler().healEntity(nearbyPlayer, level > 4 ? 2 : 1, this.getName());
                if (enchantInfo.getLevel() > 3 && Math.random() < 0.5) {
                    this.addPotionEffect(nearbyPlayer, PotionEffectType.REGENERATION, enchantInfo.getLevel() * 20, regenerationLevel);
                }
            });
            super.registerCooldown(victim, cooldown);
        }
    }
}
