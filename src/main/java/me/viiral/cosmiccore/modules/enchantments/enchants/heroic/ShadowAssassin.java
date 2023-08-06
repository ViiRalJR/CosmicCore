package me.viiral.cosmiccore.modules.enchantments.enchants.heroic;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.HeroicEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ShadowAssassin extends WeaponDamageEventEnchant implements HeroicEnchant {

    public ShadowAssassin() {
        super("Shadow Assassin", EnchantTier.HEROIC, 5, EnchantType.SWORD, "The closer you are to your enemy,", "the more damage you deal (up to 2.5x).", "However, if you are more than 2 blocks away,", "you will deal LESS damage than normal.");
        setHeroic();
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        final double distance = victim.getLocation().distance(attacker.getLocation());

        if (distance < 0.25) {
            super.getDamageHandler().increaseDamage(50.0, event, this.getName());
            return;
        }
        if (distance < 0.5) {
            super.getDamageHandler().increaseDamage(30.0, event, this.getName());
            return;
        }
        if (distance < 0.75) {
            super.getDamageHandler().increaseDamage(20.0, event, this.getName());
            return;
        }
        if (distance < 1.0) {
            super.getDamageHandler().increaseDamage(16.0, event, this.getName());
            return;
        }
        if (distance < 2.0) {
            super.getDamageHandler().increaseDamage(10.0, event, this.getName());
        }
    }

    @Override
    public Enchantment getNonHeroicEnchant() {
        return CosmicCore.getInstance().getEnchantRegister().getEnchantmentFromName("Assassin");
    }
}
