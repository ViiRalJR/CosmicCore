package me.viiral.cosmiccore.modules.enchantments.enchants.ultimate;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Heroicable;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Assassin extends WeaponDamageEventEnchant implements Heroicable {

    public Assassin() {
        super("Assassin", EnchantTier.ULTIMATE, 5, EnchantType.SWORD, "The closer you are to your enemy,", "the more damage you deal (up to 1.25x).", "However, if you are more than 2 blocks away,", "you will deal LESS damage than normal.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        final double distance = victim.getLocation().distance(attacker.getLocation());

        NavigableMap<Double, Double> distanceToDamageMap = new TreeMap<>();
        distanceToDamageMap.put(0.25, 25.0);
        distanceToDamageMap.put(0.5, 15.0);
        distanceToDamageMap.put(0.75, 10.0);
        distanceToDamageMap.put(1.0, 8.0);
        distanceToDamageMap.put(2.0, 5.0);

        Map.Entry<Double, Double> applicableEntry = distanceToDamageMap.floorEntry(distance);
        if (applicableEntry != null) {
            super.getDamageHandler().increaseDamage(applicableEntry.getValue(), event, this.getName());
        }
    }

    @Override
    public Enchantment getHeroicEnchant() {
        return CosmicCore.getInstance().getEnchantRegister().getEnchantmentFromName("Shadow Assassin");
    }
}
