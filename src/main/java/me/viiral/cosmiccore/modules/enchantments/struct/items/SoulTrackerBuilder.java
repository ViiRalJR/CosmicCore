package me.viiral.cosmiccore.modules.enchantments.struct.items;

import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import static me.viiral.cosmiccore.modules.NbtTags.*;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;


public class SoulTrackerBuilder  extends CustomItem {

    public SoulTrackerBuilder(EnchantTier enchantTier) {
        super(SOUL_TRACKER_DATA_STRING);

        ItemStack itemStack = new ItemBuilder(Material.PAPER)
                .setName(enchantTier.getColor() + enchantTier.getFormatedName() + " Soul Tracker")
                .addLore(
                        "&7Apply to weapons to start tracking",
                        enchantTier.getColor() + enchantTier.getFormatedName() + " Souls&7 collected from kills.",
                        "",
                        "&c&lWARNING:&7 Previously tracked souls",
                        "&7will be released on apply."
                )
                .colorize()
                .build();

        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString("randomUUID", String.valueOf(UUID.randomUUID()));
        nbtItem.setString("tracker-tier", enchantTier.name());
        super.nbtItem = nbtItem;
        super.applyID();
    }

    public SoulTrackerBuilder(ItemStack itemStack) {
        super(SOUL_TRACKER_DATA_STRING);
        super.nbtItem = new NBTItem(itemStack);
    }

    public EnchantTier getEnchantTier() {
        return EnchantTier.valueOf(this.nbtItem.getString("tracker-tier"));
    }
}
