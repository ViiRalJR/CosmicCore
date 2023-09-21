package me.viiral.cosmiccore.modules.enchantments.enchants.ultimate;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Relation;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.enchantscache.BleedStacksCache;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Heroicable;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.modules.mask.MaskAPI;
import me.viiral.cosmiccore.modules.mask.struct.MaskRegister;
import me.viiral.cosmiccore.modules.user.User;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.CacheUtils;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Bleed extends WeaponDamageEventEnchant implements Heroicable {

    @ConfigValue
    private double procChance = 0.006;

    public Bleed() {
        super("Bleed", EnchantTier.ULTIMATE, 6, EnchantType.AXE, "Applies bleed stacks to enemies that", "decrease their movement speed.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (!(victim instanceof Player)) return;

        User user = CosmicCore.getInstance().getUserManager().getUsers().get(victim.getUniqueId());

        if (user != null && user.types.contains(EffectType.IMMUNE_TO_DEEPBLEED_SLOWNESS)) return;

        int level = enchantedItemBuilder.getEnchantmentLevel(this);
        int deepWoundsLevel = enchantedItemBuilder.getEnchantmentLevel("deepwounds");
        if (Math.random() < (this.procChance * (level + deepWoundsLevel))) {
            Player victimPlayer = (Player) victim;
            BleedStacksCache victimBleedStack = CacheUtils.getBleedStackCache(victimPlayer);

            if (victimBleedStack.getLastBleedStackTime() + 30000 < System.currentTimeMillis()) {
                victimBleedStack.updateLastBleedStackTime();
                victimBleedStack.resetBleedStack();
                return;
            }

            if (!MaskAPI.hasMaskOn((Player) victim, MaskRegister.getInstance().getMaskFromName("Holy"))) {
                this.addPotionEffect((Player) victim, PotionEffectType.SLOW, (victimBleedStack.getBleedStack() + 1) * 20, Math.min(victimBleedStack.getBleedStack() - 1, 1));
            }

            victim.getWorld().playEffect(victim.getEyeLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
            victimBleedStack.incrementBleedStack();
            victimBleedStack.updateLastBleedStackTime();

            FPlayer fPlayer = FPlayers.getInstance().getByPlayer(victimPlayer);
            for (Entity entity : victim.getNearbyEntities(7, 7, 7)) {
                if (!(entity instanceof Player)) continue;
                Player nearbyPlayer = (Player) entity;
                if (nearbyPlayer.getGameMode() != GameMode.SURVIVAL) continue;
                if (!PVPUtils.canPvPInRegion(nearbyPlayer)) continue;
                FPlayer fPlayerNearby = FPlayers.getInstance().getByPlayer(nearbyPlayer);
                if (fPlayer.getRelationTo(fPlayerNearby).isAtLeast(Relation.TRUCE)) continue;
                Enchantment enchantment = CosmicCore.getInstance().getEnchantRegister().getEnchantmentFromID("bloodlust");
                if (EnchantsAPI.hasEnchantment(nearbyPlayer, enchantment))
                    super.getDamageHandler().healEntity(nearbyPlayer, 2, "Blood Lust");
            }
        }
    }

    @Override
    public Enchantment getHeroicEnchant() {
        return CosmicCore.getInstance().getEnchantRegister().getEnchantmentFromName("Deep Bleed");
    }
}
