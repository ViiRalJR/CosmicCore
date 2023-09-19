package me.viiral.cosmiccore.modules.enchantments.struct.enums;

import me.viiral.cosmiccore.utils.MiscUtils;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum EnchantType {
    HELMET(Material.LEATHER_HELMET, Material.IRON_HELMET, Material.CHAINMAIL_HELMET, Material.GOLD_HELMET, Material.DIAMOND_HELMET),
    CHESTPLATE(Material.LEATHER_CHESTPLATE, Material.IRON_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE, Material.GOLD_CHESTPLATE, Material.DIAMOND_CHESTPLATE),
    LEGGINGS(Material.LEATHER_LEGGINGS, Material.IRON_LEGGINGS, Material.CHAINMAIL_LEGGINGS, Material.GOLD_LEGGINGS, Material.DIAMOND_LEGGINGS),
    BOOTS(Material.LEATHER_BOOTS, Material.IRON_BOOTS, Material.CHAINMAIL_BOOTS, Material.GOLD_BOOTS, Material.DIAMOND_BOOTS),
    SWORD(Material.WOOD_SWORD, Material.IRON_SWORD, Material.STONE_SWORD, Material.GOLD_SWORD, Material.DIAMOND_SWORD),
    BOW(Material.BOW),
    PICKAXE(Material.WOOD_PICKAXE, Material.IRON_PICKAXE, Material.STONE_PICKAXE, Material.GOLD_PICKAXE, Material.DIAMOND_PICKAXE),
    SHOVEL(Material.WOOD_SPADE, Material.IRON_SPADE, Material.STONE_SPADE, Material.GOLD_SPADE, Material.DIAMOND_SPADE),
    AXE(Material.WOOD_AXE, Material.IRON_AXE, Material.STONE_AXE, Material.GOLD_AXE, Material.DIAMOND_AXE),
    HOE(Material.WOOD_HOE, Material.IRON_HOE, Material.STONE_HOE, Material.GOLD_HOE, Material.DIAMOND_HOE),
    TOOL(EnchantType.PICKAXE, EnchantType.SHOVEL, EnchantType.AXE, EnchantType.HOE),
    ARMOR(EnchantType.HELMET, EnchantType.CHESTPLATE, EnchantType.LEGGINGS, EnchantType.BOOTS),
    BOOTS_AND_HELMET(EnchantType.BOOTS, EnchantType.HELMET),
    WEAPON(EnchantType.SWORD, EnchantType.AXE),
    WEAPONS_AND_TOOLS(EnchantType.WEAPON, EnchantType.TOOL)
    ;



    private final List<Material> items = new ArrayList<>();

    EnchantType(Material... itemsStack) {
        items.addAll(Arrays.asList(itemsStack));
    }

    EnchantType(EnchantType... enchantTypes) {
        for (EnchantType enchantType : enchantTypes) {
            items.addAll(enchantType.getItems());
        }
    }

    public List<Material> getItems() {
        return items;
    }

    public String getFormatedName() {
        return MiscUtils.toNiceString(this.name());
    }
}