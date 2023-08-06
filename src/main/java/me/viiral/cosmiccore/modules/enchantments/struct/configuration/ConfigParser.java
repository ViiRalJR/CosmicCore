package me.viiral.cosmiccore.modules.enchantments.struct.configuration;

import com.massivecraft.factions.shade.apache.math.NumberUtils;
import me.viiral.cosmiccore.modules.enchantments.gkits.ConfigEnchants;
import me.viiral.cosmiccore.modules.enchantments.gkits.MinMaxValue;
import me.viiral.cosmiccore.modules.enchantments.struct.EnchantRegister;
import me.viiral.cosmiccore.utils.Pair;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import me.viiral.cosmiccore.utils.items.SkullUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public final class ConfigParser {

    public static Pair<ItemStack, ConfigEnchants> parseItem(ConfigurationSection itemSection) {
        ConfigEnchants configEnchants = null;
        Material itemType = Material.matchMaterial(itemSection.getString("type"));
        short data = (short) itemSection.getInt("data");
        ItemBuilder itemBuilder = new ItemBuilder(itemType, itemSection.getInt("amount", 1), data);

        if (itemSection.contains("name")) {
            itemBuilder.setName(itemSection.getString("name"));
        }

        if (itemSection.contains("lore")) {
            itemBuilder.setLore(itemSection.getStringList("lore"));
        }

        itemBuilder.addUnsafeEnchantments(parseEnchants(itemSection.getStringList("enchants")));
        itemBuilder.addItemFlags(itemSection.getStringList("flags"));

        if (itemSection.getBoolean("glowing")) {
            itemBuilder.setGlowing();
        }

        if (itemSection.getBoolean("unbreakable")) {
            itemBuilder.setUnbreakable(true);
        }

        ItemStack item = itemBuilder.colorize().build();

        if (itemSection.contains("skull-texture") && itemType == Material.PLAYER_HEAD && data == 3) {
            item = SkullUtils.getSkull(item, itemSection.getString("skull-texture"));
        }

        if (itemSection.contains("custom-enchants")) {
            configEnchants = parseCustomEnchants(itemSection.getStringList("custom-enchants"));
        }

        if (item == null || item.getType() == Material.AIR) System.out.println("item is air dumbass");

        return new Pair<>(item, configEnchants);
    }

    public static Map<Enchantment, Integer> parseEnchants(List<String> toParse) {
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        for (String line : toParse) {
            String[] separation = line.split(":");
            if (separation.length == 0) continue;
            Enchantment enchant = Enchantment.getByName(separation[0].toUpperCase());
            if (enchant == null) {
                Bukkit.getLogger().warning(separation[0] + " is an invalid enchantment. Here are the options: " + Arrays.stream(Enchantment.values()).map(Enchantment::getName).collect(Collectors.joining(", ")));
                continue;
            }

            int level = separation.length > 1 ? NumberUtils.toInt(separation[1], 1) : 1;
            enchantments.put(enchant, level);
        }
        return enchantments;
    }

    public static ConfigEnchants parseCustomEnchants(List<String> toParse) {
        Map<me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment, MinMaxValue> enchantments = new HashMap<>();
        for (String line : toParse) {
            String[] separation = line.split(":");
            if (separation.length == 0) continue;
            me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment enchant = EnchantRegister.getInstance().getEnchantmentFromID(separation[0].toLowerCase(Locale.ROOT));
            if (enchant == null) {
                Bukkit.getLogger().warning(separation[0] + " is an invalid custom enchantment.");
                continue;
            }

            int minLevel = NumberUtils.toInt(separation[1], 1);
            int maxLevel = NumberUtils.toInt(separation[2], 1);

            enchantments.put(enchant, new MinMaxValue(minLevel, maxLevel));
        }
        return new ConfigEnchants(enchantments);
    }
}