package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Relation;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Destruction extends ArmorIncomingPVPDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.08;
    @ConfigValue
    private double range = 7.0;
    @ConfigValue
    private int witherLevel = 1;
    @ConfigValue
    private int cooldown = 30;

    public Destruction() {
        super("Destruction", EnchantTier.LEGENDARY, false, 5, EnchantType.HELMET, "Automatically damages and debuffs all nearby enemies.");
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (super.isOnCooldown(victim)) return;
        if (Math.random() < procChance) {
            int level = enchantInfo.getLevel();
            FPlayer fPlayer = FPlayers.getInstance().getByPlayer(victim);
            victim.getNearbyEntities(range, range, range).forEach(entity -> {
                if (!(entity instanceof Player)) return;
                Player nearbyPlayer = (Player) entity;
                if (nearbyPlayer.getGameMode() != GameMode.SURVIVAL) return;
                if (!PVPUtils.canPvPInRegion(nearbyPlayer)) return;
                FPlayer fPlayerNearby = FPlayers.getInstance().getByPlayer(nearbyPlayer);
                if (fPlayer.getRelationTo(fPlayerNearby).isAtLeast(Relation.TRUCE)) return;
                super.getDamageHandler().damage(nearbyPlayer, level > 4 ? 2 : 1, this.getName());
                if (enchantInfo.getLevel() > 4 && Math.random() < 0.55) {
                    nearbyPlayer.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, enchantInfo.getLevel() * 20, witherLevel, true));
                }
            });
            super.registerCooldown(victim, cooldown);
        }
    }
}
