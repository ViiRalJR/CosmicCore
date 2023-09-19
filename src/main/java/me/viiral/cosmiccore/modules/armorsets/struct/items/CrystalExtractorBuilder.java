package me.viiral.cosmiccore.modules.armorsets.struct.items;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.viiral.cosmiccore.modules.enchantments.struct.items.CustomItem;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

import static me.viiral.cosmiccore.modules.NbtTags.CRYSTAL_EXTRACTOR;

public class CrystalExtractorBuilder extends CustomItem {


    private int percent;

    public CrystalExtractorBuilder(ItemStack itemStack) {
        super(CRYSTAL_EXTRACTOR);
        this.nbtItem = new NBTItem(itemStack);
        this.percent = getPercentage();
    }

    public CrystalExtractorBuilder(int percentage) {
        super(CRYSTAL_EXTRACTOR);

        int percent = percentage;
        if (percent >= 101) percent = 100;
        else if (percent <= 0) percent = 1;

        ItemStack itemStack = new ItemBuilder(Material.GHAST_TEAR)
                .setName("&6&lCrystal Extractor")
                .addLore(
                        "&7Removes 1 random crystal from",
                        "&7an armor piece and converts it",
                        "&7into its applicable form:",
                        "&f&l * &6&lArmor Crystal (&f" + percent + "%&6&l)"
                )
                .colorize()
                .build();

        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString("randomUUID", String.valueOf(UUID.randomUUID()));
        nbtItem.setInteger("percentage", percent);
        super.nbtItem = nbtItem;
        super.applyID();
    }



    public int getPercentage() {
        return this.nbtItem.getInteger("percentage");
    }
}
