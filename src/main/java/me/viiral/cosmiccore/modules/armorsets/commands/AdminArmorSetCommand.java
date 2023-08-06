package me.viiral.cosmiccore.modules.armorsets.commands;

import me.mattstudios.mf.annotations.*;
import me.mattstudios.mf.base.CommandBase;
import me.viiral.cosmiccore.modules.armorsets.ArmorSetAPI;
import me.viiral.cosmiccore.modules.armorsets.struct.ArmorSet;
import me.viiral.cosmiccore.modules.armorsets.struct.cache.ArmorCrystalCache;
import me.viiral.cosmiccore.modules.armorsets.struct.cache.ArmorSetCache;
import me.viiral.cosmiccore.utils.CC;
import me.viiral.cosmiccore.utils.cache.CacheManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command("armorset")
public class AdminArmorSetCommand extends CommandBase {

    @Default
    @Permission("cosmic.admin")
    public void mainCommand(CommandSender sender)  {
        sender.sendMessage(CC.translate("&c&l(!)&c /armorset give <type> <player>"));
    }

    @SubCommand("give")
    @WrongUsage("&cUsage: /armorset give <armorset> [player]")
    @Permission("cosmic.admin")
    public void setCommand(CommandSender sender, @Completion("#sets") ArmorSet armorSet, @Completion("#players") @Optional Player player) {

        if (armorSet == null) {
            sender.sendMessage(CC.translate("&c&l(!)&c Invalid ArmorSet type!"));
            return;
        }

        if (player != null) {
            armorSet.givePlayerArmorSet(player);
            return;
        }

        if (sender instanceof Player) {
            armorSet.givePlayerArmorSet((Player) sender);
        }
    }

    @SubCommand("debug")
    @WrongUsage("&cUsage: /armorset debug")
    @Permission("cosmic.admin")
    public void debugCommand(Player sender) {
        ArmorSetCache armorSetCache = (ArmorSetCache) CacheManager.getInstance().getCachedPlayer(sender).getCache("armor_set");
        ArmorCrystalCache armorCrystalCache = (ArmorCrystalCache) CacheManager.getInstance().getCachedPlayer(sender).getCache("armor_crystal");
        armorSetCache.getArmorSets().forEach((armorset, amount) -> System.out.println(armorset.getID() + " -> " + amount));
        armorCrystalCache.getCrystals().forEach((crystal, amount) -> System.out.println(crystal.getID() + " -> " + amount));
        try {
            System.out.println(ArmorSetAPI.getCurrentArmorSet(sender).getID());
        } catch(Exception e) {
            System.out.println("No Armor Set");
        }
    }
}
