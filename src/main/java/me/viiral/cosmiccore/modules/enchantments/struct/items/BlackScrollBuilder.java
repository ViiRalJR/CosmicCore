package me.viiral.cosmiccore.modules.enchantments.struct.items;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static me.viiral.cosmiccore.modules.NbtTags.BLACK_SCROLL_DATA_STRING;


public class BlackScrollBuilder extends CustomItem {

    public BlackScrollBuilder(int percent) {
        super(BLACK_SCROLL_DATA_STRING);

        ItemStack itemStack = new ItemBuilder(Material.INK_SAC)
                .setName("&f&lBlack Scroll")
                .addLore(
                        "&7Removes a random enchantment",
                        "&7from an item and converts",
                        "&7it into a &f" + percent + "%&7 success book.",
                        "&fPlace scroll onto an item to extract."
                )
                .colorize()
                .build();

        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString("randomUUID", String.valueOf(UUID.randomUUID()));
        nbtItem.setInteger("scroll-percent", percent);
        super.nbtItem = nbtItem;
        super.applyID();
    }

    public BlackScrollBuilder() {
        this(ThreadLocalRandom.current().nextInt(1, 101));
    }

    public BlackScrollBuilder(ItemStack itemStack) {
        super(BLACK_SCROLL_DATA_STRING);
        this.nbtItem = new NBTItem(itemStack);
    }

    public int getPercent() {
        return this.nbtItem.getInteger("scroll-percent");
    }
}
