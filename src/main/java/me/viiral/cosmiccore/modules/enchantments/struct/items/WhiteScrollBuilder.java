package me.viiral.cosmiccore.modules.enchantments.struct.items;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static me.viiral.cosmiccore.modules.NbtTags.*;


public class WhiteScrollBuilder extends CustomItem {

    public WhiteScrollBuilder() {
        super(WHITE_SCROLL_DATA_STRING);

        ItemStack itemStack = new ItemBuilder(Material.MAP)
                .setName("&fWhite Scroll")
                .addLore(
                        "&fPrevent an item from being destroyer",
                        "&fdue to a failed Enchantment Book.",
                        "&ePlace scroll on item to apply"
                )
                .colorize()
                .build();

        super.nbtItem = new NBTItem(itemStack);
        super.applyID();
    }
}
