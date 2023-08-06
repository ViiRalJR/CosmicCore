package me.viiral.cosmiccore.modules.enchantments.struct.items;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import static me.viiral.cosmiccore.modules.NbtTags.*;


public class TransmogScrollBuilder  extends CustomItem {

    public TransmogScrollBuilder() {
        super(TRANSMOG_SCROLL_DATA_STRING);

        ItemStack itemStack = new ItemBuilder(Material.PAPER)
                .setName("&c&lTransmog Scroll")
                .addLore(
                        "&7Organizes enchants by &e&nrarity&7 on item",
                        "&7and adds the &dlore &bcount&7 to name.",
                        "",
                        "&e&oPlace scroll on item to apply."
                )
                .colorize()
                .build();

        super.nbtItem = new NBTItem(itemStack);
        super.applyID();
    }
}