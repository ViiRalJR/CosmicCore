package me.viiral.cosmiccore.modules.enchantments.struct.items;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MiscItems {

    public static ItemStack getTinkererDisplayXpBottle(int amountOfXP) {
        ItemStack itemStack = new ItemBuilder(Material.EXP_BOTTLE)
                .setName("&a&lExperience Bottle&7 (Throw)")
                .setLore(
                        "&dValue &f" + amountOfXP + " XP",
                        "&dEnchanter &fCosmic Tinkerer"
                )
                .colorize()
                .build();

        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setInteger("xp-value", amountOfXP);
        return nbtItem.getItem();
    }

    public static ItemStack getMysteryDust() {
        return new ItemBuilder(Material.SULPHUR)
                .setName("&fMystery Dust")
                .setLore(
                        "&7The failed bi-product of",
                        "&7Magic and Primal dust."
                )
                .colorize()
                .build();
    }
}
