package me.viiral.cosmiccore.modules.enchantments.inventories;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import lombok.Getter;
import me.viiral.cosmiccore.modules.enchantments.struct.EnchantRegister;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.items.BookBuilder;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

public class EnchantsUI {


    @Getter
    private final PaginatedGui enchantsUI;

    public EnchantsUI(EnchantTier enchantTier) {
        this.enchantsUI = Gui.paginated().
                title(Component.text(enchantTier.getFormatedName() + " Enchants"))
                .rows(4)
                .disableAllInteractions()
                .create();

        this.enchantsUI.setItem(4, 3, new GuiItem(new ItemBuilder(Material.ARROW).setName("&cPrevious").colorize().build(), event -> enchantsUI.previous()));
        this.enchantsUI.setItem(4, 7, new GuiItem(new ItemBuilder(Material.ARROW).setName("&cNext").colorize().build(), event -> enchantsUI.next()));
        this.enchantsUI.setItem(4, 5, new GuiItem(new ItemBuilder(Material.BARRIER).setName("&cBack").colorize().build(), event -> {
            event.getWhoClicked().closeInventory();
            Gui gui = new EnchanterUI().getEnchantersUI();
            gui.open(event.getWhoClicked());
        }));
        this.enchantsUI.getFiller().fillBottom(new GuiItem(new ItemBuilder(Material.STAINED_GLASS_PANE).setName("").setDurability((short) 7).build()));

        for (Enchantment enchantment : EnchantRegister.getInstance().getOrderedEnchantments(enchantTier)) {
            this.enchantsUI.addItem(new GuiItem(new BookBuilder(enchantment, enchantment.getMax()).setDestroyRate(100).setSuccessRate(100).build()));
        }
    }
}
