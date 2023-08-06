package me.viiral.cosmiccore.modules.enchantments.struct.enums;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.viiral.cosmiccore.utils.CC;
import me.viiral.cosmiccore.utils.MiscUtils;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;
import java.util.Locale;

import static me.viiral.cosmiccore.modules.NbtTags.*;

@AllArgsConstructor
@Getter
public enum EnchantTier {

    SIMPLE(CC.Gray, (short) 0, 6, 1),
    UNIQUE(CC.Green, (short) 5, 5, 2),
    ELITE(CC.Aqua, (short) 3, 4, 5),
    ULTIMATE(CC.Yellow, (short) 4, 3, 10),
    LEGENDARY(CC.Gold, (short) 1, 2, 20),
    SOUL(CC.Red, (short) 14, 1, 0),
    HEROIC(CC.LightPurple, (short) 15, 0, 0);
    ;

    private final String color;
    private final short guiItemGlassColor;
    private final int weight;
    private final int soulValue;

    public ItemStack getGuiItem(int cost) {
        return new ItemBuilder(Material.LEGACY_STAINED_GLASS_PANE, 1, this.guiItemGlassColor)
                .setName(this.color + CC.Bold + MiscUtils.toNiceString(this.name()) + " Enchantment &7(Right Click)")
                .setLore(
                        "&7Examine to receive a random",
                        this.color + this.name().toLowerCase(Locale.ROOT) + "&7 enchantment book",
                        "",
                        "&7Use " + this.color + "/help enchants " + this.name().toLowerCase(Locale.ROOT) + "&7 to view a list",
                        "&7of possible enchantments you could unlock!",
                        "",
                        "&b&lCOST&f " + NumberFormat.getIntegerInstance().format(cost) + " EXP"
                )
                .colorize()
                .build();
    }

    public static ItemStack createMysteryBook(EnchantTier tier, int amount) {
        ItemStack itemStack = new ItemBuilder(Material.BOOK, amount, (short) 0,
                tier.getColor() + tier.getFormatedName() + " Enchantment Book &7(Right Click)",
                "&7Examine to recieve a random",
                tier.getColor() + tier.name().toLowerCase(Locale.ROOT) + "&r&7 enchantment book.")
                .colorize()
                .build();

        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString(COSMIC_DATA_STRING, BOOK_DATA_STRING);
        nbtItem.setString(BOOK_TIER_DATA_STRING, tier.getFormatedName().toUpperCase());
        return nbtItem.getItem();
    }

    public String getFormatedName() {
        return MiscUtils.toNiceString(this.name());
    }
}
