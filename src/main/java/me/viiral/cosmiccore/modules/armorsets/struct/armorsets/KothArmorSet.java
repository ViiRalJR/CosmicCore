package me.viiral.cosmiccore.modules.armorsets.struct.armorsets;

import me.viiral.cosmiccore.modules.armorsets.struct.ArmorSet;
import me.viiral.cosmiccore.utils.CC;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import me.viiral.cosmiccore.utils.player.PotionEffectUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class KothArmorSet extends ArmorSet {

    @Override
    public String getName() {
        return "KOTH";
    }

    @Override
    public String getColor() {
        return CC.LightPurple;
    }

    public ItemStack getHelmet() {
        return (new ItemBuilder(Material.DIAMOND_HELMET)).setName(CC.translate("&f&l&k;&r &c&lK&6&l.&e&lO&a&l.&b&lT&5&l.&d&lH &f&l&k;&r &f&l&nHelmet&r")).addLore(
                CC.translate("&dReceived from opening the koth lootbag.")
        ).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getChestplate() {
        return (new ItemBuilder(Material.DIAMOND_CHESTPLATE)).setName(CC.translate("&f&l&k;&r &c&lK&6&l.&e&lO&a&l.&b&lT&5&l.&d&lH &f&l&k;&r &f&l&nChestplate&r")).addLore(CC.translate("&dReceived from opening the koth lootbag.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getLeggings() {
        return (new ItemBuilder(Material.DIAMOND_LEGGINGS)).setName(CC.translate("&f&l&k;&r &c&lK&6&l.&e&lO&a&l.&b&lT&5&l.&d&lH &f&l&k;&r &f&l&nLeggings&r")).addLore(CC.translate("&dReceived from opening the koth lootbag.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getBoots() {
        return (new ItemBuilder(Material.DIAMOND_BOOTS)).setName(CC.translate("&f&l&k;&r &c&lK&6&l.&e&lO&a&l.&b&lT&5&l.&d&lH &f&l&k;&r &f&l&nBoots&r")).addLore(CC.translate("&dReceived from opening the koth lootbag.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    @Override
    public List<String> getEquipMessage() {
        List<String> message = new ArrayList<>();
        message.add("");
        message.add(CC.translate("&c&lK&6&l.&e&lO&a&l.&b&lT&5&l.&d&lH &f&lSET BONUS"));
        message.add(CC.translate("&8* &bDeal +20% damage to all enemies."));
        message.add(CC.translate("&8* &bDeal +50% damage to mobs."));
        message.add(CC.translate("&8* &bAuto Bless."));
        message.add("");
        return message;
    }

    public List<String> getArmorLore() {
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate(""));
        lore.add(CC.translate("&c&lK&6&l.&e&lO&a&l.&b&lT&5&l.&d&lH &f&lSET BONUS"));
        lore.add(CC.translate("&8* &bDeal +20% damage to all enemies."));
        lore.add(CC.translate("&8* &bDeal +50% damage to mobs."));
        lore.add(CC.translate("&8* &bAuto Bless."));
        lore.add(CC.translate("&7(&oRequires all 4 koth items.&7)"));
        return lore;
    }

    @Override
    public List<String> getCrystalLore() {
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&c&l K&6&l.&e&lO&a&l.&b&lT&5&l.&d&lH"));
        lore.add(CC.translate("&d&l  *&d +5% Outgoing Damage"));
        lore.add(CC.translate("&d&l  *&d -12.5% Incoming Damage"));
        return lore;
    }

    @Override
    public void onAttackCrystal(Player attacker, Entity attacked, int crystalAmount, EntityDamageByEntityEvent event) {
        super.getDamageHandler().increaseDamage(5 * crystalAmount, event, "KothCrystal");
    }

    @Override
    public void onAttackedCrystal(Player attacked, Entity attacker, int crystalAmount, EntityDamageByEntityEvent event) {
        super.getDamageHandler().reduceDamage(12.5 * crystalAmount, event, "KothCrystal");
    }

    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        if (attacked instanceof Player) {
            super.getDamageHandler().increaseDamage(20, event, "KothSet");
        } else {
            super.getDamageHandler().increaseDamage(50, event, "KothSetMob");
        }
    }

    public void onEquip(Player player) {
        PotionEffectUtils.bless(player);
    }
}
