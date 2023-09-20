package me.viiral.cosmiccore.modules.enchantments.enchants.heroic;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.enchantscache.AntiGankCache;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.HeroicEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Heroicable;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.utils.CacheUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class RighteousAntiGank extends WeaponDamageEventEnchant implements HeroicEnchant {

    public RighteousAntiGank() {
        super("Righteous Anti Gank", EnchantTier.HEROIC, 4, EnchantType.AXE, "Heroic Enchant.", "If more than (6-level) enemies hit you in a short period,", "your outgoing damage will be multiplied by up to 1.75x", "depending on the amount of enemies nearby.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (!(victim instanceof Player)) return;

        Player victimPlayer = ((Player) victim);
        AntiGankCache antiGankCache = CacheUtils.getAntiGankCache(attacker);

        antiGankCache.addPlayerToList(victimPlayer);

        if (!antiGankCache.isStillActive()) {
            antiGankCache.updateHitTime();
            antiGankCache.clearPlayers();
            return;
        }

        if (antiGankCache.getAmountOfPlayers() >= 6 - enchantedItemBuilder.getEnchantmentLevel(this)) {
            super.getDamageHandler().increaseDamage(75, event, this.getName());
        }
    }

    @Override
    public Enchantment getNonHeroicEnchant() {
        return CosmicCore.getInstance().getEnchantRegister().getEnchantmentFromName("Anti Gank");
    }
}
