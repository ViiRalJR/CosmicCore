package me.viiral.cosmiccore.modules.enchantments.struct.items;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static me.viiral.cosmiccore.modules.NbtTags.HOLY_WHITE_SCROLL_DATA_STRING;

public class HolyWhiteScrollBuilder extends CustomItem {

    public HolyWhiteScrollBuilder() {
        super(HOLY_WHITE_SCROLL_DATA_STRING);

        ItemStack itemStack = new ItemBuilder(Material.PAPER)
                .setName("&f&lHoly White Scroll")
                .addLore(
                        "&7",
                        "&fWhen applied to gear:",
                        "&7You keep the item on death",
                        "&7",
                        "&7&lNOTE: &7White Scroll must already be",
                        "&7applied to apply a Holy White Scroll. The",
                        "&7White Scroll will be consumed upon application",
                        "&7of the Holy White Scroll.",
                        "&7",
                        "&7Hint: Drag-and-drop in your inventory",
                        "&7onto the item you wish to apply it to."
                )
                .colorize()
                .build();

        super.nbtItem = new NBTItem(itemStack);
        super.applyID();
    }
}