package me.viiral.cosmiccore.modules.enchantments.struct.items;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.viiral.cosmiccore.modules.enchantments.struct.EnchantRegister;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.HeroicEnchant;
import me.viiral.cosmiccore.utils.RomanNumeral;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static me.viiral.cosmiccore.modules.NbtTags.*;


public class BookBuilder extends CustomItem {

    private int success;
    private int destroy;

    public BookBuilder(Enchantment enchantment, int level) {
        super(ENCHANT_BOOK_DATA_STRING);
        this.success = generateChance();
        this.destroy = generateChance();

        List<String> description = enchantment.getDescription().stream().map(str -> "&f" + str).collect(Collectors.toList());

        ItemStack itemStack = new ItemBuilder(Material.BOOK,
                enchantment.getTier().getColor() + "&l&n" + enchantment.getName() + " " + RomanNumeral.convertToRoman(level),
                "&a" + success + "% Success Rate",
                "&c" + destroy + "% Destroy Rate",
                "&6Max Level: &f" + enchantment.getMax(),
                "&7")
                .addLore(description)
                .addLore("&7",
                "&fStackable: " + (enchantment.isStackable() ? "&aYes" : "&cNo"),
                "&7" + enchantment.getType().getFormatedName() + " Enchantment",
                "&7Drag n' Drop onto an item to enchant it.").colorize().build();

        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString("randomUUID", String.valueOf(UUID.randomUUID()));
        nbtItem.setString("enchantment", enchantment.getID());
        nbtItem.setInteger("level", level);
        nbtItem.setInteger("dust-value", generateDustValue());
        nbtItem.setInteger(SUCCESS_RATE_STRING, success);
        nbtItem.setInteger(DESTROY_RATE_STRING, destroy);
        nbtItem.setBoolean("heroic", enchantment.isHeroic());
        if (enchantment instanceof HeroicEnchant e) {
            nbtItem.setString("required", e.getNonHeroicEnchant().getID());
        }
        this.nbtItem = nbtItem;
        super.applyID();
    }

    public BookBuilder(Enchantment enchantment) {
        this(enchantment, ThreadLocalRandom.current().nextInt(1, enchantment.getMax() + 1));
    }

    public BookBuilder(ItemStack itemStack) {
        super(ENCHANT_BOOK_DATA_STRING);
        this.nbtItem = new NBTItem(itemStack);

        this.success = this.getSuccessRate();
        this.destroy = this.getDestroyRate();
    }

    public BookBuilder(NBTItem nbtItem) {
        super(ENCHANT_BOOK_DATA_STRING);
        this.nbtItem = nbtItem;

        this.success = this.getSuccessRate();
        this.destroy = this.getDestroyRate();
    }

    private int generateChance() {
        Random random = new Random();
        return random.nextInt(101);
    }

    private int generateDustValue() {
        Random random = new Random();
        return random.nextInt(16);
    }

    public int getDustValue() {
        return this.nbtItem.getInteger("dust-value");
    }

    public int getSuccessRate() {
        return this.nbtItem.getInteger(SUCCESS_RATE_STRING);
    }

    public int getDestroyRate() {
        return this.nbtItem.getInteger(DESTROY_RATE_STRING);
    }

    public BookBuilder setSuccessRate(int successRate) {
        this.success = successRate;
        this.nbtItem.setInteger(SUCCESS_RATE_STRING, success);

        this.nbtItem = new NBTItem(new ItemBuilder(this.nbtItem.getItem())
                .setLoreLine("&a" + success + "% Success Rate", 0)
                .colorize()
                .build());
        return this;
    }

    public BookBuilder addSuccessRate(int amount) {
        setSuccessRate(Math.min(100, this.getSuccessRate() + amount));
        return this;
    }

    public BookBuilder setDestroyRate(int destroyRate) {
        this.destroy = destroyRate;
        this.nbtItem.setInteger(DESTROY_RATE_STRING, destroy);

        this.nbtItem = new NBTItem(new ItemBuilder(this.nbtItem.getItem())
                .setLoreLine("&c" + destroy + "% Destroy Rate", 1)
                .colorize()
                .build());
        return this;
    }

    public Enchantment getBookEnchantment() {
        return EnchantRegister.getInstance().getEnchantmentFromID(this.nbtItem.getString("enchantment"));
    }

    public int getBookLevel() {
        return this.nbtItem.getInteger("level");
    }

    public boolean isHeroic() {
        return this.nbtItem.getBoolean("heroic");
    }

    public String getRequired() {
        return this.nbtItem.getString("required");
    }

}
