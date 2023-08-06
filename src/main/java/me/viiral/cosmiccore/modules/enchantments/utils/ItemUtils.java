package me.viiral.cosmiccore.modules.enchantments.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class ItemUtils {

    private ItemUtils() {

    }

    public static boolean isBoots(ItemStack itemStack) {
        return (itemStack != null && (itemStack.getType().name().contains("_BOOTS")));
    }

    public static boolean isLeggings(ItemStack itemStack) {
        return (itemStack != null && (itemStack.getType().name().contains("_LEGGINGS")));
    }

    public static boolean isChestplate(ItemStack itemStack) {
        return (itemStack != null && (itemStack.getType().name().contains("_CHESTPLATE")));
    }

    public static boolean isHelmet(ItemStack itemStack) {
        return (itemStack != null && (itemStack.getType().name().contains("_HELMET")));
    }

    public static boolean isArmor(ItemStack itemStack) {
        return (itemStack != null && (itemStack
                .getType().name().contains("_HELMET") || itemStack
                .getType().name().contains("_CHESTPLATE") || itemStack
                .getType().name().contains("_LEGGINGS") || itemStack
                .getType().name().contains("_BOOTS")));
    }

    public static boolean isTool(ItemStack itemStack) {
        return (itemStack != null && (itemStack
                .getType().name().contains("_SHOVEL") || itemStack
                .getType().name().contains("_HOE") || itemStack
                .getType().name().contains("_PICKAXE")));
    }

    public static boolean isWeapon(ItemStack itemStack) {
        return (itemStack != null && (itemStack
                .getType().name().contains("_SWORD") || itemStack
                .getType().name().contains("_AXE")));
    }

    public static boolean isSword(ItemStack itemStack) {
        return (itemStack != null && itemStack
                .getType().name().contains("_SWORD"));
    }

    public static boolean isAxe(ItemStack itemStack) {
        return (itemStack != null && itemStack
                .getType().name().contains("_AXE"));
    }

    public static boolean isEnchantable(ItemStack itemStack) {
        if (itemStack == null) return false;
        return (isTool(itemStack) ||
                isArmor(itemStack) ||
                isWeapon(itemStack) ||
                itemStack.getType() == Material.BOW);
    }
}
