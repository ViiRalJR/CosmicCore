package me.viiral.cosmiccore.modules.enchantments.struct.items;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static me.viiral.cosmiccore.modules.NbtTags.RENAME_TAG_DATA_STRING;


public class ItemRenameTagBuilder extends CustomItem {

    public ItemRenameTagBuilder() {
        super(RENAME_TAG_DATA_STRING);

        ItemStack itemStack = new ItemBuilder(Material.NAME_TAG)
                .setName("&6&lItem NameTag")
                .addLore(
                        "&7Rename and customize your equipment"
                )
                .colorize()
                .build();

        super.nbtItem = new NBTItem(itemStack);
        super.applyID();
    }
}
