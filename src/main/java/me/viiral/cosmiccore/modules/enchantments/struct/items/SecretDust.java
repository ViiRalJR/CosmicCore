package me.viiral.cosmiccore.modules.enchantments.struct.items;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

import static me.viiral.cosmiccore.modules.NbtTags.SECRET_DUST_DATA_STRING;


public class SecretDust extends CustomItem {

    public SecretDust(EnchantTier enchantTier, int maxPercent) {
        super(SECRET_DUST_DATA_STRING);

        ItemStack itemStack = new ItemBuilder(Material.FIRE_CHARGE)
                .setName(enchantTier.getColor() + enchantTier.getFormatedName() + " Secret Dust &7(Right Click)")
                .addLore(
                        "&aSuccess: +0-" + maxPercent + "%",
                        "&7Contains: &bMagic, &ePrimal,&7 or &fMystery&7 dust.",
                        "&7An unidentified satchel of dust."
                )
                .colorize()
                .build();

        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString("dust-tier", enchantTier.name());
        nbtItem.setInteger("dust-max-percent", maxPercent);
        super.nbtItem = nbtItem;
        super.applyID();
    }

    public SecretDust(ItemStack itemStack) {
        super(SECRET_DUST_DATA_STRING);
        super.nbtItem = new NBTItem(itemStack);
    }

    public int getMaxPercent() {
        return nbtItem.getInteger("dust-max-percent");
    }

    public EnchantTier getEnchantTier() {
        return EnchantTier.valueOf(nbtItem.getString("dust-tier"));
    }

    public int generateRandomPercent() {
        Random random = new Random();
        return random.nextInt(this.getMaxPercent() + 1) + 1;
    }

    public ItemStack getDust() {
        if (Math.random() < 0.7) return MiscItems.getMysteryDust();
        return new DustBuilder(this.getEnchantTier(), this.generateRandomPercent()).build();
    }
}
