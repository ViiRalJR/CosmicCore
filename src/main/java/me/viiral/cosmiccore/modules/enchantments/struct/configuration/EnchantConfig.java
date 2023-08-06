package me.viiral.cosmiccore.modules.enchantments.struct.configuration;

import lombok.AccessLevel;
import lombok.Getter;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Reloadable;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

@Getter
public class EnchantConfig {

    @Getter(value = AccessLevel.PRIVATE)
    private final CosmicCore plugin;
    private final String path;
    private FileConfiguration config;
    private final File configFile;
    private final Enchantment enchantment;

    public EnchantConfig(CosmicCore plugin, String path, Enchantment enchantment) {
        this.plugin = plugin;
        this.path = path;
        this.enchantment = enchantment;
        this.configFile = new File(plugin.getDataFolder(), path);
    }

    public void setupConfigFile() {
        this.createDefaultFile();
        this.config = YamlConfiguration.loadConfiguration(configFile);

        this.loadVariables();

        boolean result = this.saveConfig();
        if (result) this.loadConfigFromDisk();

    }

    public boolean saveConfig() {
        try {
            config.save(configFile);
            return true;
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save config file for " + path);
            e.printStackTrace();
        }
        return false;
    }

    public void createDefaultFile() {
        if (!configFile.exists()) {
            try {
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Could not create config file for " + path);
                e.printStackTrace();
            }
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
        this.loadVariables();
    }

    public void loadConfigFromDisk() {
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            plugin.getLogger().severe("Could not load config for " + path);
            e.printStackTrace();
        }
    }

    public void loadVariables() {
        boolean needSave = false;
        for (Field field : this.enchantment.getClass().getDeclaredFields()) {

            try {
                field.setAccessible(true);

                if (field.isAnnotationPresent(ConfigValue.class)) {
                    ConfigValue annotationValue = field.getAnnotation(ConfigValue.class);

                    String name = annotationValue.name().equals("") ? field.getName() : annotationValue.name();

                    String path = annotationValue.path().endsWith(".") ? annotationValue.path() : annotationValue.path() + ".";

                    if (!this.config.contains(path + name)) {
                        this.config.set(path + name, field.get(this.enchantment));
                        needSave = true;
                        continue;
                    }

                    Object fieldValue = this.config.get(path + name);

                    if (fieldValue instanceof String) fieldValue = CC.translate((String) fieldValue);

                    field.set(this.enchantment, fieldValue);
                }
            } catch (IllegalAccessException | NullPointerException | IllegalArgumentException e) {
                Bukkit.getConsoleSender().sendMessage(CC.translate("&c[CosmicCore] - Error invalid config value for enchant" + enchantment.getName()));
            }

            if (this.enchantment instanceof Reloadable) {
                Reloadable reloadableEnchantment = ((Reloadable) this.enchantment);
                reloadableEnchantment.reloadValues();
            }
        }
        if (needSave) {
            saveConfig();
            reloadConfig();
        }
    }

}
