package me.viiral.cosmiccore.modules.enchantments.enchants.soul;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.SoulModeCache;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.enchantscache.TeleblockCache;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.BowEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Reloadable;import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.SoulEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.modules.enchantments.struct.souls.SoulManager;
import me.viiral.cosmiccore.utils.CacheUtils;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Teleblock extends BowEventEnchant implements Reloadable, SoulEnchant {

    @ConfigValue
    private String soulCostFormula = "level * 6";
    private Expression soulCostExpression;
    private final SoulManager soulManager;

    public Teleblock() {
        super("Teleblock", EnchantTier.SOUL, 5, EnchantType.BOW, "Active soul enchant. Your bow is enchanted with enderpearl blocking magic,", "damaged players will be unable to use enderpearls for up to 20 seconds,", "and will lose up to 15 enderpearls from their inventory.");
        this.reloadValues();
        this.soulManager = CosmicCore.getInstance().getSoulManager();
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, Player victim, Player attacker, Arrow arrow, EnchantedItemBuilder enchantedItemBuilder) {
        if (!this.soulManager.isInSoulMode(attacker)) return;

//        if (EliteAPI.hasMask(victim.getInventory().getHelmet()) && victim.getInventory().getHelmet() != null && EliteAPI.hasMaskEffect(victim.getInventory().getHelmet(), "Glitch")) return;

        int level = enchantedItemBuilder.getEnchantmentLevel(this);
        int soulCost = (int) this.soulCostExpression.setVariable("level", level).evaluate();
        SoulModeCache soulModeCache = this.soulManager.getSoulModeCache(attacker);

        if (!soulModeCache.hasEnoughSouls(soulCost)) return;

        TeleblockCache teleblockCache = CacheUtils.getTeleblockCache(victim);

        if (teleblockCache.isTeleblockActive()) return;

        soulModeCache.getSoulGemBuilder().removeSouls(soulCost);
        soulModeCache.updateSoulGem();
        teleblockCache.procTeleblock(level);
        PlayerInventory victimInventory = victim.getInventory();

        int pearlsToDestroy = level * 3;
        for (int i = 0; i < victimInventory.getSize(); ++i) {
            if (!(victimInventory.getItem(i) == null || victimInventory.getItem(i).getType() != Material.ENDER_PEARL || victimInventory.getItem(i).hasItemMeta() && victimInventory.getItem(i).getItemMeta().hasDisplayName())) {
                int amount = victimInventory.getItem(i).getAmount();
                if (amount <= pearlsToDestroy) {
                    pearlsToDestroy -= amount;
                    victimInventory.setItem(i, new ItemStack(Material.AIR));
                } else {
                    ItemStack invPearl = victimInventory.getItem(i);
                    invPearl.setAmount(invPearl.getAmount() - pearlsToDestroy);
                    victimInventory.setItem(i, invPearl);
                    pearlsToDestroy = 0;
                }
            }
            if (pearlsToDestroy <= 0) break;
        }
    }

    @EventHandler
    public void onEnderpearlThrow(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (!event.hasItem()) return;
        if (event.getItem().getType() != Material.ENDER_PEARL) return;

        TeleblockCache teleblockCache = CacheUtils.getTeleblockCache(event.getPlayer());

        if (!teleblockCache.isTeleblockActive()) return;

        event.setCancelled(true);
        event.setUseItemInHand(Event.Result.DENY);
        event.getPlayer().setItemInHand(event.getItem());
    }

    @Override
    public void reloadValues() {
        this.soulCostExpression = new ExpressionBuilder(this.soulCostFormula).variable("level").build();
    }

    @Override
    public int getSoulCost() {
        return 6;
    }
}
