package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDeathEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

public class Inquisitive extends WeaponDeathEventEnchant {

    @ConfigValue
    private int expMultiplier = 2;

    public Inquisitive() {
        super("Inquisitive", EnchantTier.LEGENDARY, 4, EnchantType.SWORD, "Increases EXP drops from mobs.");
    }

    @Override
    public void runEntityDeathEvent(EntityDeathEvent event, Player killer, LivingEntity victim, EnchantedItemBuilder enchantedItemBuilder) {
        int level = enchantedItemBuilder.getEnchantmentLevel(this);
        event.setDroppedExp((event.getDroppedExp() * level) * expMultiplier);
    }
}
