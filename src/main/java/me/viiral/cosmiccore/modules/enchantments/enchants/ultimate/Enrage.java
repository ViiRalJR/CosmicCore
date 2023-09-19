package me.viiral.cosmiccore.modules.enchantments.enchants.ultimate;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Objects;

public class Enrage extends WeaponDamageEventEnchant {

    public Enrage() {
        super("Enrage", EnchantTier.ULTIMATE, 3, EnchantType.SWORD, "The lower your HP is, the more damage you deal.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        double maxHealth = Objects.requireNonNull(attacker.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();
        double hpPercent = attacker.getHealth() / maxHealth;
        double multi = 0;

        if (hpPercent <= 0.75D) multi += 10;
        if (hpPercent <= 0.5D) multi += 10;
        if (hpPercent <= 0.25D) multi += 10;

        super.getDamageHandler().increaseDamage(multi, event, this.getName());
    }
}
