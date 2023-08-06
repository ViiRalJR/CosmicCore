package me.viiral.cosmiccore.modules.enchantments.struct.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.viiral.cosmiccore.utils.config.AbstractConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    @Getter
    private final Map<ConfigFile, AbstractConfig> configs = new HashMap<>();

    public ConfigManager(JavaPlugin plugin) {

        for (ConfigFile configFile : ConfigFile.values()) {
            AbstractConfig abstractConfig = new AbstractConfig(plugin, configFile.getFileName());
            configs.put(configFile, abstractConfig);
        }
    }

    public void setupFiles() {
        configs.values().forEach(AbstractConfig::setupConfigFile);
    }

    public void reloadFiles() {
        configs.values().forEach(AbstractConfig::reloadConfig);
    }

    public AbstractConfig getAbstractConfig(ConfigFile configFile) {
        return configs.get(configFile);
    }

    public FileConfiguration getConfig(ConfigFile configFile) {
        return getAbstractConfig(configFile).getConfig();
    }

    @Getter
    @RequiredArgsConstructor
    public enum ConfigFile {

        SETTINGS("settings.yml"),
        GKITS("gkits.yml"),
        ;

        private final String fileName;

    }

}
