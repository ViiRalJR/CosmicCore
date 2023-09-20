package me.viiral.cosmiccore.modules.enchantments.enchants.heroic;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.HeroicEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.modules.enchantments.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class BrutalBarbarian extends WeaponDamageEventEnchant implements HeroicEnchant {

    @ConfigValue
    private int damageBuff = 35;

    public BrutalBarbarian() {
        super("Brutal Barbarian", EnchantTier.HEROIC, 4, EnchantType.AXE, "Heroic Enchant.", "Multiplies damage against players who are wielding an AXE", " at the time they are hit.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (!(victim instanceof Player)) return;

        Player playerVictim = (Player) victim;

        ItemStack heldItem = playerVictim.getItemInHand();

        if (heldItem.getType() == Material.AIR) return;
        if (!ItemUtils.isAxe(heldItem)) return;

        super.getDamageHandler().increaseDamage(damageBuff, event, this.getName());
    }

    @Override
    public Enchantment getNonHeroicEnchant() {
        return CosmicCore.getInstance().getEnchantRegister().getEnchantmentFromName("Barbarian");
    }
}