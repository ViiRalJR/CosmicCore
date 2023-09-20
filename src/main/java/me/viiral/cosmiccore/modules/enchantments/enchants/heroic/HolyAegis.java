package me.viiral.cosmiccore.modules.enchantments.enchants.heroic;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.enchantscache.AegisCache;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.HeroicEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.utils.CacheUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HolyAegis extends ArmorIncomingPVPDamageEventEnchant implements HeroicEnchant {

    public HolyAegis() {
        super("Holy Aegis", EnchantTier.HEROIC, false, 6, EnchantType.CHESTPLATE, "Heroic Enchant.", "If you are taking damage from more than (8-level) enemies in a short period,", "the damage from any additional players beyond that initial group", "will be halved.");
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (!(attacker instanceof Player)) return;

        Player attackerPlayer = ((Player) attacker);
        AegisCache aegisCache = CacheUtils.getAegisCache(victim);

        aegisCache.addPlayerToList(attackerPlayer);

        if (!aegisCache.isStillActive()) {
            aegisCache.updateHitTime();
            aegisCache.clearPlayers();
            return;
        }

        if (aegisCache.getAmountOfPlayers() >= 8 - enchantInfo.getLevel()) {
            super.getDamageHandler().reduceDamage(75, event, this.getName());
        }
    }

    @Override
    public Enchantment getNonHeroicEnchant() {
        return CosmicCore.getInstance().getEnchantRegister().getEnchantmentFromName("Aegis");
    }
}
