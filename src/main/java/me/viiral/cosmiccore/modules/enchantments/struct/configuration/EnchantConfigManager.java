package me.viiral.cosmiccore.modules.enchantments.struct.configuration;

import lombok.Getter;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class EnchantConfigManager {

    @Getter
    private final Map<Enchantment, EnchantConfig> configs = new HashMap<>();

    public EnchantConfigManager(CosmicCore plugin) {
        for (Enchantment enchantment : plugin.getEnchantRegister().getEnchantments()) {
            EnchantConfig enchantConfig = new EnchantConfig(plugin, "enchants/" + enchantment.getID() + ".yml", enchantment);
            configs.put(enchantment, enchantConfig);
        }
    }

    public void setupFiles() {
        configs.values().forEach(EnchantConfig::setupConfigFile);
    }

    public void reloadFiles() {
        configs.values().forEach(EnchantConfig::reloadConfig);
    }

    public EnchantConfig getAbstractConfig(Enchantment enchantment) {
        return configs.get(enchantment);
    }

    public FileConfiguration getConfig(Enchantment enchantment) {
        return getAbstractConfig(enchantment).getConfig();
    }
}
