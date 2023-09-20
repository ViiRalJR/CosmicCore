package me.viiral.cosmiccore.modules.enchantments.enchants.heroic;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.HeroicEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ReflectiveBlock extends WeaponDamageEventEnchant implements HeroicEnchant {

    @ConfigValue
    private double procChance = 0.045;
    @ConfigValue
    private int cooldown = 10;
    @ConfigValue
    private int damageDecrease = 40;

    public ReflectiveBlock() {
        super("Reflective Block", EnchantTier.HEROIC, 3, EnchantType.SWORD, "Heroic Enchant.", "A chance to increase damage and redirect an attack. (Deflect up to 40%)");
        setHeroic();
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (!(victim instanceof Player)) return;
        if (super.isOnCooldown(attacker)) return;

        if (Math.random() < this.procChance * enchantedItemBuilder.getEnchantmentLevel(this)) {
            Player victimPlayer = ((Player) victim);
            super.getDamageHandler().reduceDamage(this.damageDecrease, event, this.getName());
            super.registerCooldown(attacker, this.cooldown);
            if (Math.random() < this.procChance * enchantedItemBuilder.getEnchantmentLevel(this) && victimPlayer.isBlocking()) {
                victimPlayer.playSound(victimPlayer.getLocation(), Sound.ITEM_BREAK, 0.7f, 0.2f);
            }
        }
    }

    @Override
    public Enchantment getNonHeroicEnchant() {
        return CosmicCore.getInstance().getEnchantRegister().getEnchantmentFromName("Block");
    }
}
