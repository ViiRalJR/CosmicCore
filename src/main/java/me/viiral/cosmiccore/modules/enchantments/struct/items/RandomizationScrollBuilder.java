package me.viiral.cosmiccore.modules.enchantments.struct.items;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static me.viiral.cosmiccore.modules.NbtTags.RANDOMIZATION_SCROLL_DATA_STRING;


public class RandomizationScrollBuilder extends CustomItem {

    public RandomizationScrollBuilder(EnchantTier tier) {
        super(RANDOMIZATION_SCROLL_DATA_STRING);

        ItemStack itemStack = new ItemBuilder(Material.PAPER)
                .setName(tier.getColor() + tier.getFormatedName() + " Randomization Scroll")
                .addLore(
                        "&7Apply to any " + tier.getFormatedName() + " Enchantment Book",
                        "&7to reroll the success and destroy rates.",
                        "",
                        "&7Drag n' drop onto &nenchantment book&7 to apply."
                )
                .colorize()
                .build();

        super.nbtItem = new NBTItem(itemStack);
        super.nbtItem.setString("tier", tier.name());
        super.applyID();
    }

    public RandomizationScrollBuilder(ItemStack itemStack) {
        super(RANDOMIZATION_SCROLL_DATA_STRING);
        super.nbtItem = new NBTItem(itemStack);
    }

    public EnchantTier getTier() {
        return EnchantTier.valueOf(super.nbtItem.getString("tier"));
    }
}
