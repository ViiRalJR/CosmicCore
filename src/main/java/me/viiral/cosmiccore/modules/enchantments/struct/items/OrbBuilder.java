package me.viiral.cosmiccore.modules.enchantments.struct.items;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.utils.StringUtils;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;
import java.util.UUID;

import static me.viiral.cosmiccore.modules.NbtTags.*;


public class OrbBuilder extends CustomItem {

    public OrbBuilder(OrbType orbType, int slots, int successRate) {
        super(ORB_DATA_STRING);

        String loreFix = orbType == OrbType.ARMOR ? "slots on a piece of armor by " : "slots on a weapon by ";

        ItemStack itemStack = new ItemBuilder(Material.ENDER_EYE)
                .setName("&6&l" + orbType.getFancyName() + " Enchantment Orb [&a&n" + slots + "&6&l]")
                .addLore(
                        "&a" + successRate + "% Success Rate",
                        "",
                        "&6+" + (slots - 9) + " Enchantment Slot",
                        "&6" + slots + " Max Enchantment Slots",
                        "",
                        "&eIncrease the amount of enchantment",
                        "&e" + loreFix + (slots - 9) + ".",
                        "&eUp to a maximum of " + slots + ".",
                        "&7Drag n' Drop onto " + orbType.name().toLowerCase(Locale.ROOT) + " to apply."
                )
                .colorize()
                .build();

        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString("randomUUID", String.valueOf(UUID.randomUUID()));
        nbtItem.setInteger(SUCCESS_RATE_STRING, successRate);
        nbtItem.setInteger(SLOTS_AMOUNT_STRING, slots);
        nbtItem.setString(ORB_TYPE_STRING, orbType.name());
        super.nbtItem = nbtItem;
        super.applyID();
    }

    public OrbBuilder(ItemStack itemStack) {
        super(ORB_DATA_STRING);
        super.nbtItem = new NBTItem(itemStack);
    }

    public int getSlots() {
        return this.nbtItem.getInteger(SLOTS_AMOUNT_STRING);
    }

    public int getSuccessRate() {
        return this.nbtItem.getInteger(SUCCESS_RATE_STRING);
    }

    public OrbBuilder setSuccessRate(int successRate) {
        this.nbtItem.setInteger(SUCCESS_RATE_STRING, successRate);

        this.nbtItem = new NBTItem(new ItemBuilder(this.nbtItem.getItem())
                .setLoreLine("&a" + successRate + "% Success Rate", 0)
                .colorize()
                .build());
        return this;
    }

    public OrbBuilder addSuccessRate(int amount) {
        this.setSuccessRate(Math.min(100, this.getSuccessRate() + amount));
        return this;
    }

    public OrbType getOrbType() {
        return OrbType.valueOf(this.nbtItem.getString(ORB_TYPE_STRING));
    }

    public enum OrbType {
        WEAPON(EnchantType.WEAPON),
        ARMOR(EnchantType.ARMOR);

        final EnchantType applicableItems;

        OrbType(EnchantType applicableItems) {
            this.applicableItems = applicableItems;
        }

        public boolean canBeAppliedToItem(ItemStack itemStack) {
            return this.applicableItems.getItems().contains(itemStack.getType());
        }

        public String getFancyName() {
            return StringUtils.toNiceString(this.name());
        }
    }
}
