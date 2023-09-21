package me.viiral.cosmiccore.modules.armorsets.struct.armorsets;

import me.viiral.cosmiccore.modules.armorsets.struct.ArmorSet;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.CC;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import me.viiral.cosmiccore.utils.potion.PotionEffectUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DragonArmorSet extends ArmorSet {
    @Override
    public String getName() {
        return "Dragon";
    }

    @Override
    public String getColor() {
        return CC.Yellow;
    }

    public ItemStack getHelmet() {
        return (new ItemBuilder(Material.DIAMOND_HELMET))
                .setName(CC.translate("&e&lDecapitated Dragon Skull"))
                .addLore(CC.translate("&eThe mythical helmet of a Slayer of Dragons."))
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .build();
    }

    public ItemStack getChestplate() {
        return (new ItemBuilder(Material.DIAMOND_CHESTPLATE))
                .setName(CC.translate("&e&lFirey Chestplate of Dragons"))
                .addLore(CC.translate("&eThe mythical chestplate of a Slayer of Dragons."))
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .build();
    }

    public ItemStack getLeggings() {
        return (new ItemBuilder(Material.DIAMOND_LEGGINGS))
                .setName(CC.translate("&e&lScorched Leggings of Dragons"))
                .addLore(CC.translate("&eThe mythical leggings of a Slayer of Dragons."))
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .build();
    }

    public ItemStack getBoots() {
        return (new ItemBuilder(Material.DIAMOND_BOOTS))
                .setName(CC.translate("&e&lDragon Slayer Battle Boots"))
                .addLore(CC.translate("&eThe mythical boots of a Slayer of Dragons."))
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .build();
    }

    @Override
    public List<String> getEquipMessage() {
        List<String> message = new ArrayList<>();
        message.add("");
        message.add(CC.translate("&e&lDRAGON SLAYER SET BONUS:"));
        message.add(CC.translate("&8* &e+15% OUTGOING DAMAGE"));
        message.add(CC.translate("&8* &e-20% INCOMING DAMAGE"));
        message.add(CC.translate("&8* &eImmune to Freezes"));
        message.add(CC.translate("&8* &eNegate 75% of Enemy Silence"));
        message.add("");
        return message;
    }

    @Override
    public List<String> getArmorLore() {
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate(""));
        lore.add(CC.translate("&e&lDRAGON SLAYER SET BONUS:"));
        lore.add(CC.translate("&8* &e+15% OUTGOING DAMAGE"));
        lore.add(CC.translate("&8* &e-20% INCOMING DAMAGE"));
        lore.add(CC.translate("&8* &eImmune to Freezes"));
        lore.add(CC.translate("&8* &eNegate 75% of Enemy Silence"));
        lore.add(CC.translate("&7(&oRequires all 4 koth items.&7)"));
        return lore;
    }

    @Override
    public List<String> getCrystalLore() {
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&e&l Dragon Slayer"));
        lore.add(CC.translate("&e&l  *&e +10% Outgoing Damage"));
        lore.add(CC.translate("&e&l  *&e -10% Incoming Damage"));
        return lore;
    }

    @Override
    public void onAttackCrystal(Player attacker, Entity attacked, int crystalAmount, EntityDamageByEntityEvent event) {
        super.getDamageHandler().increaseDamage(10 * crystalAmount, event, "DragonCrystal");
    }

    @Override
    public void onAttackedCrystal(Player attacked, Entity attacker, int crystalAmount, EntityDamageByEntityEvent event) {
        super.getDamageHandler().reduceDamage(10 * crystalAmount, event, "DragonCrystal");
    }

    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        super.getDamageHandler().increaseDamage(15, event, "DragonSet");
    }

    @Override
    public void onAttacked(Player attacked, Entity attacker, EntityDamageByEntityEvent event) {
        super.getDamageHandler().reduceDamage(20, event, "DragonSet");

    }

    public void onEquip(Player player) {
        PotionEffectUtils.bless(player);
        addEffect(player, EffectType.IMMUNE_TO_FREEZES);
    }
}
