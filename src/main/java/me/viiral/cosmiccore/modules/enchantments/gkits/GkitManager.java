package me.viiral.cosmiccore.modules.enchantments.gkits;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.configuration.ConfigManager;
import me.viiral.cosmiccore.modules.enchantments.struct.configuration.ConfigParser;
import me.viiral.cosmiccore.utils.Pair;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class GkitManager {

    private final Map<String, Gkit> gkits;
    private final CosmicCore plugin;

    public GkitManager(CosmicCore plugin) {
        this.plugin = plugin;
        this.gkits = new HashMap<>();
    }

    public void initialize() {
        FileConfiguration config = this.plugin.getConfigManager().getConfig(ConfigManager.ConfigFile.GKITS);
        for (String key : config.getKeys(false)) {
            this.loadGkit(config.getConfigurationSection(key));
        }
    }

    public void loadGkit(ConfigurationSection configurationSection) {
        String name = configurationSection.getString("name");
        long cooldown = configurationSection.getLong("cooldown") * 1000;
        Map<Pair<ItemStack, Double>, ConfigEnchants> lootList = new HashMap<>();

        boolean display = true;

        if (configurationSection.contains("display-in-gui")) {
            display = configurationSection.getBoolean("display-in-gui");
        }

        if (configurationSection.contains("items")) {
            ConfigurationSection itemsSection = configurationSection.getConfigurationSection("items");
            for (String key : itemsSection.getKeys(false)) {
                ConfigurationSection itemSection = itemsSection.getConfigurationSection(key);
                Pair<ItemStack, ConfigEnchants> enchantedItemPair = ConfigParser.parseItem(itemSection);

                double chance = 1.0;

                if (itemSection.contains("chance"))
                    chance = itemSection.getDouble("chance");

                lootList.put(new Pair<>(enchantedItemPair.getLeft(), chance), enchantedItemPair.getRight());
            }
        }

        List<Pair<String, Double>> commands = new ArrayList<>();
        if (configurationSection.contains("commands")) {
            for (String s : configurationSection.getStringList("commands")) {
                String[] commandAndChance = s.split(":");
                if (commandAndChance.length <= 1) {
                    commands.add(new Pair<>(s, 1.0));
                    continue;
                }

                commands.add(new Pair<>(commandAndChance[1], Double.parseDouble(commandAndChance[0])));
            }
        }

        ItemStack guiItem = ConfigParser.parseItem(configurationSection.getConfigurationSection("gui-item")).getLeft();

        Gkit gkit = new Gkit(name, cooldown, lootList,  commands, guiItem, display);

        this.gkits.put(gkit.getID(), gkit);
    }

    public void reloadGkits() {
        this.gkits.clear();
        this.initialize();
    }

    public Gkit getGkitFromID(String id) {
        return this.gkits.get(id);
    }

    public Collection<Gkit> getGkits() {
        return this.gkits.values();
    }
}
