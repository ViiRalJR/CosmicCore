package me.viiral.cosmiccore.modules.enchantments.enchants.soul;

import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Immortal extends ArmorIncomingPVPDamageEventEnchant {

    public Immortal() {
        super("Immortal", EnchantTier.SOUL, false, 4, EnchantType.ARMOR, "Prevents your armor from taking durability damage in exchange for souls.");
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {

    }
}
