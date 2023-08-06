package me.viiral.cosmiccore.modules.enchantments.struct.items;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static me.viiral.cosmiccore.modules.NbtTags.DUST_DATA_STRING;


@Getter
public class DustBuilder extends CustomItem {

    private int percent;

    public DustBuilder(EnchantTier enchantTier, int percent) {
        super(DUST_DATA_STRING);
        this.percent = percent;

        boolean isPrimal = percent >= 10;
        Material material = isPrimal ? Material.GLOWSTONE_DUST : Material.SUGAR;
        String name = isPrimal ? enchantTier.getColor() + "&l" + enchantTier.getFormatedName() + " Primal Dust" : enchantTier.getColor() + enchantTier.getFormatedName() + " Dust";
        String isBold = isPrimal ? "&l" : "";

        ItemStack itemStack = new ItemBuilder(material)
                .setName(name)
                .addLore(
                        "&a" + isBold + "+" + percent + "% SUCCESS",
                        "&7Apply to a " + enchantTier.getColor() + isBold + enchantTier.getFormatedName() + " &7Enchanted Book",
                        "&7to increase its success rate by " + enchantTier.getColor() + isBold + percent + "%",
                        "",
                        "&7Place dust on enchantment book."
                )
                .colorize()
                .build();

        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString("randomUUID", String.valueOf(UUID.randomUUID()));
        nbtItem.setInteger("dust-percent", percent);
        nbtItem.setString("dust-tier", enchantTier.name());
        super.nbtItem = nbtItem;
        super.applyID();
    }

    public DustBuilder(EnchantTier enchantTier) {
        this(enchantTier, ThreadLocalRandom.current().nextInt(1, 15));
        this.percent = this.getPercent();
    }

    public DustBuilder(ItemStack itemStack) {
        super(DUST_DATA_STRING);
        this.nbtItem = new NBTItem(itemStack);
        this.percent = this.getPercent();
    }

    public int getPercent() {
        return this.nbtItem.getInteger("dust-percent");
    }

    public EnchantTier getEnchantTier() {
        return EnchantTier.valueOf(this.nbtItem.getString("dust-tier"));
    }

    public DustBuilder setPercent(int percent) {
        this.percent = percent;
        this.nbtItem.setInteger("dust-percent", percent);
        return this;
    }
}
