package me.viiral.cosmiccore.modules.enchantments.struct.items;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static me.viiral.cosmiccore.modules.NbtTags.SOUL_PEARL_DATA_STRING;


public class SoulPearlBuilder extends CustomItem {

    public SoulPearlBuilder() {
        super(SOUL_PEARL_DATA_STRING);

        ItemStack itemStack = new ItemBuilder(Material.ENDER_PEARL)
                .setName("&5&lSoul Pearl")
                .addLore(
                        "&dA rare /dungeon trinket that",
                        "&dfunctions like a normal enderpearl,",
                        "&dbut costs souls instead of being,",
                        "&dconsumed on use.",
                        "",
                        "&5&nsoul Cost:&d 5/pearl"
                )
                .colorize()
                .build();

        super.nbtItem = new NBTItem(itemStack);
        super.applyID();
    }
}
