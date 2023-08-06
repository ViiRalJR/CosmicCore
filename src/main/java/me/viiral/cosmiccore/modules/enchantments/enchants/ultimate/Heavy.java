package me.viiral.cosmiccore.modules.enchantments.enchants.ultimate;

import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Heavy extends ArmorIncomingPVPDamageEventEnchant {

    public Heavy() {
        super("Heavy", EnchantTier.ULTIMATE, true, 5, EnchantType.ARMOR, "Decreases damage from enemy bows by 2% per level.");
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        //TODO: this won't work, needs fixing
        //super.getDamageHandler().reduceDamage(enchantInfo.getLevel() * 2, event, this.getName());
    }
}
