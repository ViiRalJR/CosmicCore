package me.viiral.cosmiccore.modules.enchantments.enchants.ultimate;

import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorPlayerDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class CreeperArmor extends ArmorPlayerDamageEventEnchant {

    public CreeperArmor() {
        super("Creeper Armor", EnchantTier.ULTIMATE, false,1, EnchantType.ARMOR, "&fWearer gains immunity to explosions.");
    }


    @Override
    public void runOnDamage(EntityDamageEvent event, Player player, EnchantInfo enchantInfo) {
        if (event.getCause() != EntityDamageEvent.DamageCause.BLOCK_EXPLOSION && event.getCause() != EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) return;
        event.setCancelled(true);
    }
}
