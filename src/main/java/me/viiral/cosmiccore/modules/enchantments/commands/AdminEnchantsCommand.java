package me.viiral.cosmiccore.modules.enchantments.commands;

import com.massivecraft.factions.shade.particlelib.particle.ParticleBuilder;
import com.massivecraft.factions.shade.particlelib.particle.ParticleEffect;
import com.massivecraft.factions.shade.particlelib.particle.data.texture.BlockTexture;
import me.mattstudios.mf.annotations.*;
import me.mattstudios.mf.base.CommandBase;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import me.viiral.cosmiccore.modules.enchantments.gkits.Gkit;
import me.viiral.cosmiccore.modules.enchantments.gkits.GkitManager;
import me.viiral.cosmiccore.modules.enchantments.language.EnchantLanguage;
import me.viiral.cosmiccore.modules.enchantments.language.LanguageHandler;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.SoulTrackerCache;
import me.viiral.cosmiccore.modules.enchantments.struct.configuration.ConfigManager;
import me.viiral.cosmiccore.modules.enchantments.struct.configuration.EnchantConfigManager;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.items.*;
import me.viiral.cosmiccore.modules.enchantments.utils.ItemUtils;
import me.viiral.cosmiccore.utils.CacheUtils;
import me.viiral.cosmiccore.utils.TimeUtils;
import me.viiral.cosmiccore.utils.player.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Command("ce")
public class AdminEnchantsCommand extends CommandBase {

    private final GkitManager gkitManager;
    private final ConfigManager configManager;
    private final EnchantConfigManager enchantConfigManager;
    private final LanguageHandler languageHandler;

    public AdminEnchantsCommand(CosmicCore plugin) {
        this.enchantConfigManager = plugin.getEnchantConfigManager();
        this.languageHandler = plugin.getLanguageHandler();
        this.configManager = plugin.getConfigManager();
        this.gkitManager = plugin.getGkitManager();
    }

    @Default
    @Permission("cosmic.admin")
    public void mainCommand(CommandSender sender)  {
        EnchantLanguage.NE_COMMAND_HELP.send(sender);
    }

    @SubCommand("book")
    @WrongUsage("&cUsage: /ce book <enchant> <level> [player]")
    @Permission("cosmic.admin")
    public void bookCommand(CommandSender sender, @Completion("#enchants") Enchantment enchantment, Integer level, @Completion("#players") @Optional Player player) {

        if (enchantment == null) {
            EnchantLanguage.INVALID_ENCHANTMENT_NAME.send(sender);
            return;
        }

        if (level == null || level < 1 || level > enchantment.getMax()) {
            EnchantLanguage.INVALID_ENCHANTMENT_LEVEL.send(sender, s -> s.replace("{max}", String.valueOf(enchantment.getMax())));
            return;
        }

        ItemStack itemStack = new BookBuilder(enchantment, level)
                .setSuccessRate(100)
                .setDestroyRate(0)
                .build();

        if (player != null) {
            player.getInventory().addItem(itemStack);
            return;
        }

        if (sender instanceof Player) {
            InventoryUtils.giveItemToPlayer(((Player) sender), itemStack);
        }
    }

    @SubCommand("dust")
    @WrongUsage("&cUsage: /ce dust <tier> <percent> [player]")
    @Permission("cosmic.admin")
    public void dustCommand(CommandSender sender, @Completion("#enum") EnchantTier tier, @Completion("#range:1-15") Integer percent, @Completion("#players") @Optional Player player) {
        if (tier == null) {
            return;
        }

        ItemStack itemStack = new DustBuilder(tier, percent).build();

        if (player != null) {
            player.getInventory().addItem(itemStack);
            return;
        }

        if (sender instanceof Player) {
            InventoryUtils.giveItemToPlayer(((Player) sender), itemStack);
        }
    }

    @SubCommand("blackscroll")
    @Alias("bscroll")
    @WrongUsage("&cUsage: /ce blackscroll <percent> [player]")
    @Permission("cosmic.admin")
    public void blackScrollCommand(CommandSender sender, @Completion("#range:1-100") Integer percent, @Completion("#players") @Optional Player player) {
        ItemStack itemStack = new BlackScrollBuilder(percent).build();

        if (player != null) {
            player.getInventory().addItem(itemStack);
            return;
        }

        if (sender instanceof Player) {
            InventoryUtils.giveItemToPlayer(((Player) sender), itemStack);
        }
    }

    @SubCommand("soulgem")
    @Alias("sgem")
    @WrongUsage("&cUsage: /ce soulgem <souls> [player]")
    @Permission("cosmic.admin")
    public void soulGemCommand(CommandSender sender, Integer souls, @Completion("#players") @Optional Player player) {
        ItemStack itemStack = new SoulGemBuilder(souls).build();

        if (player != null) {
            player.getInventory().addItem(itemStack);
            return;
        }

        if (sender instanceof Player) {
            InventoryUtils.giveItemToPlayer(((Player) sender), itemStack);
        }
    }

    @SubCommand("orb")
    @Alias("eorb")
    @WrongUsage("&cUsage: /ce orb <slots> <success> <type> [player]")
    @Permission("cosmic.admin")
    public void orbCommand(CommandSender sender, @Completion("#range:10-16") Integer slots, @Completion("#range:1-100") Integer success, @Completion("#enum") OrbBuilder.OrbType type, @Completion("#players") @Optional Player player) {
        ItemStack itemStack = new OrbBuilder(type, slots, success).build();

        if (player != null) {
            player.getInventory().addItem(itemStack);
            return;
        }

        if (sender instanceof Player) {
            InventoryUtils.giveItemToPlayer(((Player) sender), itemStack);
        }
    }

    @SubCommand("whitescroll")
    @Alias("wscroll")
    @WrongUsage("&cUsage: /ce whitescroll <amount> [player]")
    @Permission("cosmic.admin")
    public void whiteScrollCommand(CommandSender sender, @Completion("#range:1-64") Integer amount, @Completion("#players") @Optional Player player) {
        ItemStack itemStack = new WhiteScrollBuilder().build();

        itemStack.setAmount(amount);

        if (player != null) {
            player.getInventory().addItem(itemStack);
            return;
        }

        if (sender instanceof Player) {
            InventoryUtils.giveItemToPlayer(((Player) sender), itemStack);
        }
    }

    @SubCommand("transmogscroll")
    @Alias({"tscroll", "transmog"})
    @WrongUsage("&cUsage: /ce transmogscroll <amount> [player]")
    @Permission("cosmic.admin")
    public void transmogScrollCommand(CommandSender sender, @Completion("#range:1-64") Integer amount, @Completion("#players") @Optional Player player) {
        ItemStack itemStack = new TransmogScrollBuilder().build();

        itemStack.setAmount(amount);

        if (player != null) {
            player.getInventory().addItem(itemStack);
            return;
        }

        if (sender instanceof Player) {
            InventoryUtils.giveItemToPlayer(((Player) sender), itemStack);
        }
    }

    @SubCommand("randomizationscroll")
    @Alias({"rscroll"})
    @WrongUsage("&cUsage: /ce randomizationscroll <amount> <tier> [player]")
    @Permission("cosmic.admin")
    public void randomizationScrollCommand(CommandSender sender, @Completion("#range:1-64") Integer amount, @Completion("#enum") EnchantTier tier, @Completion("#players") @Optional Player player) {
        if (tier == null) {
            return;
        }

        ItemStack itemStack = new RandomizationScrollBuilder(tier).build();

        itemStack.setAmount(amount);

        if (player != null) {
            player.getInventory().addItem(itemStack);
            return;
        }

        if (sender instanceof Player) {
            InventoryUtils.giveItemToPlayer(((Player) sender), itemStack);
        }
    }

    @SubCommand("nametag")
    @Alias("renametag")
    @WrongUsage("&cUsage: /ce nametag <amount> [player]")
    @Permission("cosmic.admin")
    public void renameTagCommand(CommandSender sender, @Completion("#range:1-64") Integer amount, @Completion("#players") @Optional Player player) {
        ItemStack itemStack = new ItemRenameTagBuilder().build();

        itemStack.setAmount(amount);

        if (player != null) {
            player.getInventory().addItem(itemStack);
            return;
        }

        if (sender instanceof Player) {
            InventoryUtils.giveItemToPlayer(((Player) sender), itemStack);
        }
    }

    @SubCommand("soulpearl")
    @WrongUsage("&cUsage: /ce soulpearl <amount> [player]")
    @Permission("cosmic.admin")
    public void soulPearlCommand(CommandSender sender, @Completion("#range:1-64") Integer amount, @Completion("#players") @Optional Player player) {
        ItemStack itemStack = new SoulPearlBuilder().build();

        itemStack.setAmount(amount);

        if (player != null) {
            player.getInventory().addItem(itemStack);
            return;
        }

        if (sender instanceof Player) {
            InventoryUtils.giveItemToPlayer(((Player) sender), itemStack);
        }
    }

    @SubCommand("soultracker")
    @WrongUsage("&cUsage: /ce soultracker <amount> <tier> [player]")
    @Permission("cosmic.admin")
    public void soulTrackerCommand(CommandSender sender, @Completion("#range:1-64") Integer amount, @Completion("#enum") EnchantTier tier, @Completion("#players") @Optional Player player) {
        if (tier == null) {
            return;
        }

        ItemStack itemStack = new SoulTrackerBuilder(tier).build();

        itemStack.setAmount(amount);

        if (player != null) {
            player.getInventory().addItem(itemStack);
            return;
        }

        if (sender instanceof Player) {
            InventoryUtils.giveItemToPlayer(((Player) sender), itemStack);
        }
    }

    @SubCommand("rbook")
    @Alias({"randombook", "randbook"})
    @WrongUsage("&cUsage: /ce rbook <tier> <amount> [player]")
    @Permission("cosmic.admin")
    public void randomBookCommand(CommandSender sender, @Completion("#enum") EnchantTier tier, Integer amount, @Optional Player player) {

        if (amount == null) {
            amount = 1;
        }

        if (tier == null) {
            return;
        }

        ItemStack itemStack = EnchantTier.createMysteryBook(tier, amount);

        if (player != null) {
            player.getInventory().addItem(itemStack);
            return;
        }

        if (sender instanceof Player) {
            InventoryUtils.giveItemToPlayer(((Player) sender), itemStack);
        }
    }

    @SubCommand("enchant")
    @Alias({"addenchant", "addenchantment"})
    @WrongUsage("&cUsage: /ce enchant <enchant> [level]")
    @Permission("cosmic.admin")
    public void enchantCommand(Player sender, @Completion("#enchants") Enchantment enchantment, @Optional Integer level) {

        if (enchantment == null) {
            EnchantLanguage.INVALID_ENCHANTMENT_NAME.send(sender);
            return;
        }

        if (level == null) {
            level = enchantment.getMax();
        }

        if (level < 1 || level > enchantment.getMax()) {
            EnchantLanguage.INVALID_ENCHANTMENT_LEVEL.send(sender, s -> s.replace("{max}", String.valueOf(enchantment.getMax())));
            return;
        }

        if (sender.getItemInHand() == null || sender.getItemInHand().getType() == Material.AIR) {
            EnchantLanguage.EMPTY_HAND.send(sender);
            return;
        }

        EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(sender.getItemInHand());

        if (enchantedItemBuilder.hasEnchantment(enchantment)) {
            EnchantLanguage.ALREADY_HAS_ENCHANTMENT.send(sender);
            return;
        }

        enchantedItemBuilder.addEnchantment(enchantment, level);

        sender.setItemInHand(enchantedItemBuilder.build());
        sender.updateInventory();
    }

    @SubCommand("unenchant")
    @Alias({"removeenchant", "removeenchantment", "deenchant"})
    @WrongUsage("&cUsage: /ce unenchant <enchant>")
    @Permission("cosmic.admin")
    public void unenchantCommand(Player sender, @Completion("#enchants") Enchantment enchantment) {

        if (sender.getItemInHand() == null || sender.getItemInHand().getType() == Material.AIR) {
            EnchantLanguage.EMPTY_HAND.send(sender);
            return;
        }

        EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(sender.getItemInHand());

        if (!enchantedItemBuilder.hasEnchantment(enchantment)) {
            return;
        }

        enchantedItemBuilder.removeEnchantment(enchantment);

        sender.setItemInHand(enchantedItemBuilder.build());
        sender.updateInventory();
    }

    @SubCommand("gkit")
    @WrongUsage("&cUsage: /ce gkit <gkit> [player]")
    @Permission("cosmic.admin")
    public void gkitCommand(CommandSender sender, @Completion("#gkits") Gkit gkit, @Optional Player player) {
        if (gkit == null) {
            return;
        }

        if (player != null) {
            gkit.supplyKit(player);
            return;
        }

        if (sender instanceof Player) {
            gkit.supplyKit(((Player) sender));
        }
    }

    @SubCommand("reload")
    @Permission("cosmic.admin")
    public void reloadCommand(Player sender) {
        EnchantLanguage.ADMIN_RELOADING.send(sender);
        long then = System.currentTimeMillis();

        this.configManager.reloadFiles();
        this.languageHandler.load();
        this.enchantConfigManager.reloadFiles();
        this.gkitManager.reloadGkits();

        long diff = System.currentTimeMillis() - then;
        EnchantLanguage.ADMIN_RELOADED.send(sender, str -> str.replace("%took%", String.valueOf(diff)));
    }

    @SubCommand("debug")
    @Permission("cosmic.admin")
    public void debugCommand(Player sender) {
        for (Enchantment enchantment : EnchantsAPI.getEnchantments(sender)) {
            System.out.println(enchantment.getName());
        }
    }

    @SubCommand("particle")
    @Permission("cosmic.admin")
    public void particleCommand(Player sender) {
        ParticleBuilder particle1 = new ParticleBuilder(ParticleEffect.BLOCK_DUST)
                .setParticleData(new BlockTexture(Material.DIAMOND_BLOCK))
                .setAmount(100)
                .setOffsetY(1)
                .setSpeed(0.15f);

        particle1.setLocation(sender.getLocation()).display(sender);

        SoulTrackerCache soulTrackerCache = CacheUtils.getSoulTrackerCache(sender);

        if (soulTrackerCache.isOnHarvestCooldown(sender)) {
            EnchantLanguage.SOUL_HARVEST_COOLDOWN.send(sender, str -> str
                    .replace("{player}", sender.getName())
                    .replace("{time}", TimeUtils.formatSeconds(soulTrackerCache.getSecondsLeftOnHarvestCooldown(sender)))
            );
            return;
        }

        soulTrackerCache.addHarvestCooldown(sender);
    }

    @SubCommand("test")
    @Permission("cosmic.admin")
    public void testCommand(Player sender) {
        ItemStack itemStack = sender.getItemInHand();

        if (!ItemUtils.isWeapon(itemStack)) return;


        EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(itemStack);

        if (!enchantedItemBuilder.hasSoulTrackerAlreadyApplied()) return;

        System.out.println(itemStack.getItemMeta().getLore());
        enchantedItemBuilder.incrementHarvestedSouls();
        enchantedItemBuilder.updateSoulsHarvested();

        sender.setItemInHand(enchantedItemBuilder.build());
        sender.updateInventory();
    }
}
