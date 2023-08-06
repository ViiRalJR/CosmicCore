package me.viiral.cosmiccore.modules.enchantments.struct.items;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

import static me.viiral.cosmiccore.modules.NbtTags.COSMIC_DATA_STRING;


public abstract class CustomItem {

    protected NBTItem nbtItem;
    protected final String id;

    public CustomItem(String id) {
        this.id = id;
    }

    protected void applyID() {
        this.nbtItem.setString(COSMIC_DATA_STRING, id);
    }

    protected void applyData(String value) {
        this.nbtItem.getOrCreateCompound(COSMIC_DATA_STRING).setString(id, value);
    }

    protected String getData() {
        return this.nbtItem.getOrCreateCompound(COSMIC_DATA_STRING).getString(id);
    }

    public String getID() {
        return this.id;
    }

    public ItemStack build() {
        return this.nbtItem.getItem();
    }
}
