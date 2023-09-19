package me.viiral.cosmiccore.utils.items;

import me.viiral.cosmiccore.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class ItemBuilder {

    private ItemMeta itemMeta;
    private final ItemStack itemStack;

    public ItemBuilder(String material) {
        this(Material.valueOf(material));
    }

    public ItemBuilder(Material material) {
        this(material,1);
    }

    public ItemBuilder(Material material, int amount) {
        this(new ItemStack(material, amount));
    }

    public ItemBuilder(Material material, String name, String... lore) {
        this(material, 1, (short) 0, name, lore);
    }

    public ItemBuilder(Material material, String name, List<String> lore) {
        this(material, 1, (short) 0, name);
        setLore(lore);
    }

    public ItemBuilder(Material material, int amount, short durability) {
        this(new ItemStack(material, amount, durability));
    }

    public ItemBuilder(Material material, int amount, short durability, String name, String... lore) {
        this(new ItemStack(material, amount, durability));
        setName(name);
        setLore(lore);
    }

    public ItemBuilder(ItemBuilder itemBuilder) {
        this.itemStack = itemBuilder.itemStack;
        this.itemMeta = itemBuilder.itemMeta;
    }

    public ItemBuilder(ConfigurationSection section) {
        this(new ItemBuilder(section.getString("name"))
                .setLore(section.getStringList("lore"))
                .setDurability((short) section.getInt("damage"))
                .setUnbreakable(section.getBoolean("unbreakable"))
        );
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder clone() {
        return new ItemBuilder(build());
    }

    public ItemBuilder setMaterial(String material) {
        itemStack.setType(Material.valueOf(material));
        return this;
    }

    public ItemBuilder setMaterial(Material material) {
        itemStack.setType(material);
        return this;
    }

    public ItemBuilder setDurability(short durability) {
        itemStack.setDurability(durability);
        return this;
    }

    public ItemBuilder setName(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment enchantment, int level) {
        itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder addUnsafeEnchantments(Map<Enchantment, Integer> enchantments) {
        enchantments.forEach((enchantment, level) -> itemMeta.addEnchant(enchantment, level, true));
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        itemMeta.addEnchant(enchantment, level,false);
        return this;
    }

    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
        enchantments.forEach((enchantment, level) -> itemMeta.addEnchant(enchantment, level, false));
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        itemMeta.removeEnchant(enchantment);
        return this;
    }

    public ItemBuilder removeEnchantments(Collection<Enchantment> enchantments) {
        enchantments.forEach(itemMeta::removeEnchant);
        return this;
    }

    public ItemBuilder setGlowing() {
        if (!itemMeta.hasEnchants()) {
            itemMeta.addEnchant(Enchantment.DURABILITY,1,true);
        }
        return addItemFlag(ItemFlag.HIDE_ENCHANTS);
    }

    public ItemBuilder colorize() {
        colorizeName();
        return colorizeLore();
    }

    public ItemBuilder colorizeLore() {
        if (itemMeta.hasLore()) {
            List<String> lore = getItemMeta().getLore();
            lore.replaceAll(CC::translate);
            return setLore(lore);
        }
        return this;
    }

    public ItemBuilder colorizeName() {
        if (itemMeta.hasDisplayName()) setName(CC.translate(getItemMeta().getDisplayName()));
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        itemMeta.spigot().setUnbreakable(unbreakable);
        return this;
    }

    public List<String> getLore() {
        return itemMeta.getLore();
    }

    public ItemBuilder setLore(String... lore) {
        return setLore(Arrays.asList(lore));
    }

    public ItemBuilder setLore(List<String> lore) {
        itemMeta.setLore(lore);
        return this;
    }

    public ItemBuilder removeLoreLine(String line) {
        List<String> lore = new ArrayList<>(itemMeta.getLore());
        lore.remove(line);
        itemMeta.setLore(lore);
        return this;
    }

    public ItemBuilder removeLoreLine(int index) {
        if (index < 0) return this;
        List<String> lore = new ArrayList<>(itemMeta.getLore());
        if (index > lore.size()) return this;
        lore.remove(index);
        itemMeta.setLore(lore);
        return this;
    }

    public ItemBuilder addLore(String ...lore) {
        return addLore(Arrays.asList(lore));
    }

    public ItemBuilder addLore(List<String> lore) {
        lore.forEach(this::addLoreLine);
        return this;
    }

    public ItemBuilder addLoreLine(String line) {
        List<String> lore = itemMeta.hasLore() ? new ArrayList<>(itemMeta.getLore()) : new ArrayList<>();
        lore.add(line);
        itemMeta.setLore(lore);
        return this;
    }

    public ItemBuilder setLoreLine(String line, int pos) {
        List<String> lore = new ArrayList<>(itemMeta.getLore());
        if (pos >= lore.size()) return this;
        lore.set(pos, line);
        itemMeta.setLore(lore);
        return this;
    }

    public ItemBuilder addLoreLine(String line, int pos) {
        List<String> lore = new ArrayList<>(itemMeta.getLore());
        if (pos >= lore.size()) return this;
        lore.add(pos, line);
        itemMeta.setLore(lore);
        return this;
    }

    public ItemBuilder addItemFlags(List<String> itemFlags) {
        for (String itemFlagStr : itemFlags) {
            try {
                ItemFlag itemFlag = ItemFlag.valueOf(itemFlagStr);
                addItemFlag(itemFlag);
            } catch (IllegalArgumentException ignored) {}
        }
        return this;
    }

    public ItemBuilder setItemFlags(List<ItemFlag> itemFlags) {
        itemMeta.addItemFlags(itemFlags.toArray(new ItemFlag[0]));
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag itemFlag) {
        itemMeta.addItemFlags(itemFlag);
        return this;
    }


    public ItemBuilder setLeatherArmorColor(Color color) {
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;
        if (Bukkit.getItemFactory().isApplicable(leatherArmorMeta, itemStack)) {
            leatherArmorMeta.setColor(color);
            itemMeta = leatherArmorMeta;
        }
        return this;
    }

    public ItemBuilder setSkullOwner(String owner) {
        SkullMeta skullMeta = (SkullMeta) itemMeta;
        if (Bukkit.getItemFactory().isApplicable(skullMeta, itemStack)) {
            skullMeta.setOwner(owner);
            itemMeta = skullMeta;
        }
        return this;
    }

    public ItemMeta getItemMeta() {
        return itemMeta;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}