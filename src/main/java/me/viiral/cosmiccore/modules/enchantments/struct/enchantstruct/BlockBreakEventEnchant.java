package me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct;

import me.viiral.cosmiccore.modules.enchantments.events.EnchantProcEvent;

import me.viiral.cosmiccore.modules.enchantments.events.EnchantProcOnBlockEvent;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public abstract class BlockBreakEventEnchant extends Enchantment {

    public BlockBreakEventEnchant(String name, EnchantTier tier, int max, EnchantType type, String... description) {
        super(name, tier, false, max, type, description);
    }

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent event) {
        if (event.isCancelled()) return;

        ItemStack itemStack = event.getPlayer().getItemInHand();

        if (itemStack == null) return;
        if (!this.getType().getItems().contains(itemStack.getType())) return;

        EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(itemStack);

        if (!enchantedItemBuilder.hasEnchantment(this)) return;
        EnchantProcEvent procEvent = new EnchantProcOnBlockEvent(event.getBlock(), this,enchantedItemBuilder.getEnchantmentLevel(this));
        Bukkit.getPluginManager().callEvent(procEvent);

        if(procEvent.isCancelled()) return;
        this.runBlockBreakEvent(event, enchantedItemBuilder);
    }

    public abstract void runBlockBreakEvent(BlockBreakEvent event, EnchantedItemBuilder enchantedItemBuilder);
}
