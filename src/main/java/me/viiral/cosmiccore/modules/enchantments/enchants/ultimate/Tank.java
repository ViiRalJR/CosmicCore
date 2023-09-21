package me.viiral.cosmiccore.modules.enchantments.enchants.ultimate;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.user.User;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Tank extends ArmorIncomingPVPDamageEventEnchant {

    @ConfigValue
    private double damageNegation = 1.85;

    public Tank() {
        super("Tank", EnchantTier.ULTIMATE, true, 4, EnchantType.ARMOR, "Decreases damage from enemy axes by", "x1.85 per level");
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (!(attacker instanceof Player)) return;
        if (!EnchantType.AXE.getItems().contains(((Player) attacker).getItemInHand().getType())) return;

        User user = CosmicCore.getInstance().getUserManager().getUsers().get(victim.getUniqueId());

        if (user != null && user.types.contains(EffectType.IMMUNE_TO_TANK)) return;

        super.getDamageHandler().reduceDamage(damageNegation * enchantInfo.getLevel(), event, this.getName());
    }
}
