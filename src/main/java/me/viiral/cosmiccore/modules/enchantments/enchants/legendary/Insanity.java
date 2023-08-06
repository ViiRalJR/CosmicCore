package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Insanity extends WeaponDamageEventEnchant {

    @ConfigValue
    private double damageBuff = 4;

    public Insanity() {
        super("Insanity", EnchantTier.LEGENDARY, 8, EnchantType.AXE, "Multiplies damage against players who", "are wielding a sword at the time", "they are hit");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (!(victim instanceof Player)) return;
        Player playerVictim = (Player) victim;
        if (playerVictim.getItemInHand() == null) return;
        if (!EnchantType.SWORD.getItems().contains(playerVictim.getItemInHand().getType())) return;
        super.getDamageHandler().increaseDamage(damageBuff * enchantedItemBuilder.getEnchantmentLevel(this), event, this.getName());
    }
}
