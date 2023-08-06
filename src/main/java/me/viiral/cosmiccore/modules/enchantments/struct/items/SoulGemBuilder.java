package me.viiral.cosmiccore.modules.enchantments.struct.items;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.viiral.cosmiccore.utils.CC;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

import static me.viiral.cosmiccore.modules.NbtTags.SOUL_GEM_DATA_STRING;


public class SoulGemBuilder extends CustomItem {

    public SoulGemBuilder(int soulAmount) {
        super(SOUL_GEM_DATA_STRING);

        ItemStack itemStack = new ItemBuilder(Material.EMERALD)
                .setName(this.getSoulGemName(soulAmount))
                .addLore(
                        "",
                        "&c&l*&r &cClick this item to toggle &nSoul Mode&r",
                        "&7While in Soul Mode your ACTIVE god tier",
                        "&7enchantment will activate and drain souls",
                        "&7for as long as the mode is enabled.",
                        "",
                        "&c&l*&r &7Use &c&n/splitsouls&r &7with this item",
                        "&7to split souls off of it.",
                        "",
                        "&c&l*&r &7Stack other &cSoul Gems&r &7ontop of this",
                        "&7one to combine their soul count."
                )
                .colorize()
                .build();

        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString("randomUUID", String.valueOf(UUID.randomUUID()));
        nbtItem.setInteger("soul-amount", soulAmount);
        super.nbtItem = nbtItem;
        super.applyID();
    }

    public SoulGemBuilder(ItemStack itemStack) {
        super(SOUL_GEM_DATA_STRING);
        this.nbtItem = new NBTItem(itemStack);
    }

    public int getSouls() {
        return super.nbtItem.getInteger("soul-amount");
    }

    public SoulGemBuilder setSouls(int soulAmount) {
        this.nbtItem.setInteger("soul-amount", soulAmount);
        this.updateSoulAmountName();
        return this;
    }

    public SoulGemBuilder addSouls(int amount) {
        this.setSouls(this.getSouls() + amount);
        return this;
    }

    public SoulGemBuilder removeSouls(int amount) {
        if (this.getSouls() - amount <= 0) {
            this.setSouls(0);
            return this;
        }

        this.setSouls(this.getSouls() - amount);
        return this;
    }

    public void updateSoulAmountName() {
        this.nbtItem = new NBTItem(new ItemBuilder(this.nbtItem.getItem())
                .setName(this.getSoulGemName(this.getSouls()))
                .build());
    }

    private String getSoulGemName(int soulAmount) {
        return CC.translate("&c&lSoul Gem [" + getColor(soulAmount) + soulAmount + "&c&l]");
    }

    private static String getColor(int amount) {
        if (amount >= 5000) {
            return ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString();
        }
        if (amount >= 1000) {
            return ChatColor.GOLD.toString() + ChatColor.BOLD.toString();
        }
        if (amount >= 500) {
            return ChatColor.AQUA.toString();
        }
        if (amount >= 100) {
            return ChatColor.GREEN.toString();
        }
        if (amount >= 0) {
            return ChatColor.WHITE.toString();
        }
        return "";
    }
}
