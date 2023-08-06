package me.viiral.cosmiccore.modules.enchantments.struct.enums;

import me.viiral.cosmiccore.utils.MiscUtils;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum EnchantType {
    HELMET(Material.LEATHER_HELMET, Material.IRON_HELMET, Material.CHAINMAIL_HELMET, Material.GOLDEN_HELMET, Material.DIAMOND_HELMET),
    CHESTPLATE(Material.LEATHER_CHESTPLATE, Material.IRON_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE, Material.GOLDEN_CHESTPLATE, Material.DIAMOND_CHESTPLATE),
    LEGGINGS(Material.LEATHER_LEGGINGS, Material.IRON_LEGGINGS, Material.CHAINMAIL_LEGGINGS, Material.GOLDEN_LEGGINGS, Material.DIAMOND_LEGGINGS),
    BOOTS(Material.LEATHER_BOOTS, Material.IRON_BOOTS, Material.CHAINMAIL_BOOTS, Material.GOLDEN_BOOTS, Material.DIAMOND_BOOTS),
    SWORD(Material.WOODEN_SWORD, Material.IRON_SWORD, Material.STONE_SWORD, Material.GOLDEN_SWORD, Material.DIAMOND_SWORD),
    BOW(Material.BOW),
    PICKAXE(Material.WOODEN_PICKAXE, Material.IRON_PICKAXE, Material.STONE_PICKAXE, Material.GOLDEN_PICKAXE, Material.DIAMOND_PICKAXE),
    SHOVEL(Material.WOODEN_SHOVEL, Material.IRON_SHOVEL, Material.STONE_SHOVEL, Material.GOLDEN_SHOVEL, Material.DIAMOND_SHOVEL),
    AXE(Material.WOODEN_AXE, Material.IRON_AXE, Material.STONE_AXE, Material.GOLDEN_AXE, Material.DIAMOND_AXE),
    HOE(Material.WOODEN_HOE, Material.IRON_HOE, Material.STONE_HOE, Material.GOLDEN_HOE, Material.DIAMOND_HOE),
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
