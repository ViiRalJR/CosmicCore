package me.viiral.cosmiccore.modules.enchantments.gkits;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.utils.Pair;
import me.viiral.cosmiccore.utils.player.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;


@RequiredArgsConstructor
public class Gkit {

    @Getter
    private final String name;
    @Getter
    private final long cooldown;
    private final Map<Pair<ItemStack, Double>, ConfigEnchants> lootList;
    @Getter
    private final List<Pair<String, Double>> commandsList;
    @Getter
    private final ItemStack guiItem;
    @Getter
    private final boolean showInUI;

    public String getID() {
        return this.getName().replace(" ", "").toLowerCase(Locale.ROOT);
    }

    public String getPermission() {
        return "gkit." + this.getID();
    }

    public void supplyKit(Player player) {
        List<ItemStack> items = new ArrayList<>();

        this.lootList.forEach((itemStackChancePair, configEnchants) -> {
            if (Math.random() < itemStackChancePair.getRight()) {
                if (configEnchants == null) {
                    items.add(itemStackChancePair.getLeft());
                    return;
                }

                EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(itemStackChancePair.getLeft());

                configEnchants.getEnchants().forEach((enchantment, minMaxValue) -> enchantedItemBuilder.addEnchantment(enchantment, minMaxValue.getRandomLevel()));

                items.add(enchantedItemBuilder.build());
            }
        });

        for (Pair<String, Double> command : this.commandsList) {
            if (Math.random() < command.getRight())
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.getLeft().replace("{player}", player.getName()));
        }

        InventoryUtils.giveItemToPlayer(player, items);
    }
}
