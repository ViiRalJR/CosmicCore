package me.viiral.cosmiccore.modules.enchantments.inventories;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import lombok.Getter;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.gkits.Gkit;
import me.viiral.cosmiccore.modules.enchantments.gkits.users.GkitUser;
import me.viiral.cosmiccore.modules.enchantments.language.EnchantLanguage;
import me.viiral.cosmiccore.utils.TimeUtils;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class GkitsUI {

    @Getter
    private final PaginatedGui gkitsUI;

    public GkitsUI() {
        this.gkitsUI = Gui.paginated().
                title(Component.text("Gkits Menu"))
                .rows(4)
                .disableAllInteractions()
                .create();

        this.gkitsUI.setItem(4, 3, new GuiItem(new ItemBuilder(Material.ARROW).setName("&cPrevious").colorize().build(), event -> this.gkitsUI.previous()));
        this.gkitsUI.setItem(4, 7, new GuiItem(new ItemBuilder(Material.ARROW).setName("&cNext").colorize().build(), event -> this.gkitsUI.next()));

        this.gkitsUI.getFiller().fillBottom(new GuiItem(new ItemBuilder(Material.STAINED_GLASS_PANE).setName("").setDurability((short) 7).build()));

        for (Gkit gkit : CosmicCore.getInstance().getGkitManager().getGkits()) {
            if (!gkit.isShowInUI()) continue;
            this.gkitsUI.addItem(new GuiItem(gkit.getGuiItem(), event -> {
                Player player = (Player) event.getWhoClicked();
                if (!player.hasPermission(gkit.getPermission())) {
                    EnchantLanguage.KIT_NO_ACCESS.send(player);
                    return;
                }

                GkitUser user = CosmicCore.getInstance().getGkitUserManager().getUserOrCreate(player.getUniqueId());

                if (user.hasCooldownOnKit(gkit)) {
                    EnchantLanguage.KIT_COOLDOWN.send(player, string -> string.replace(
                            "{time}", TimeUtils.formatSeconds(user.getSecondsTillKitAvailable(gkit))));
                    return;
                }

                gkit.supplyKit(player);
                user.newCooldownOnKit(gkit);
                EnchantLanguage.KIT_RECEIVED.send(player, str -> str.replace("{gkit}", gkit.getName()));
            }));
        }
    }
}
