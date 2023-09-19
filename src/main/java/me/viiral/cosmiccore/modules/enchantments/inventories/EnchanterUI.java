package me.viiral.cosmiccore.modules.enchantments.inventories;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import lombok.Getter;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.language.EnchantLanguage;
import me.viiral.cosmiccore.modules.enchantments.struct.configuration.ConfigManager;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.utils.XPUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class EnchanterUI {

    @Getter
    private final Gui enchantersUI;
    
    public EnchanterUI() {
        FileConfiguration settingsConfig = CosmicCore.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS);
        ConfigurationSection enchanterSection = settingsConfig.getConfigurationSection("enchanter");
        ConfigurationSection tierSection = enchanterSection.getConfigurationSection("tier-items");

        this.enchantersUI = Gui.gui().
                title(Component.text("Enchanter"))
                .rows(enchanterSection.getInt("gui-rows"))
                .disableAllInteractions()
                .create();

        for (String enchantTierAsString : tierSection.getKeys(false)) {
            EnchantTier enchantTier;
            try {
                enchantTier = EnchantTier.valueOf(enchantTierAsString);
            } catch (Exception e) {
                continue;
            }

            ConfigurationSection specificTierSection = tierSection.getConfigurationSection(enchantTierAsString);
            int price = specificTierSection.getInt("price");


            GuiItem guiItem = new GuiItem(enchantTier.getGuiItem(price));

            guiItem.setAction(event -> {
                if (event.getClick() == ClickType.LEFT) {
                    Player player = (Player) event.getWhoClicked();
                    if (XPUtils.hasEnoughExperience(player, price)) {
                        player.getInventory().addItem(EnchantTier.createMysteryBook(enchantTier, 1));
                        XPUtils.removeExperience(player, price);
                        EnchantLanguage.REMOVE_EXP.send(player, str -> str.replace("{amount}", String.valueOf(price)));
                        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.2F);
                    } else {
                        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 0.7f, 1.4f);
                        EnchantLanguage.NOT_ENOUGH_EXP.send(player);
                    }
                }
                if (event.getClick() == ClickType.RIGHT) {
                    PaginatedGui enchantsUI = new EnchantsUI(enchantTier).getEnchantsUI();
                    enchantsUI.open(event.getWhoClicked());
                }
            });

            this.enchantersUI.setItem(specificTierSection.getInt("slot"), guiItem);
        }
    }
}
