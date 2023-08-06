package me.viiral.cosmiccore.modules.enchantments.commands;

import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Completion;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.Optional;
import me.mattstudios.mf.base.CommandBase;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.gkits.Gkit;
import me.viiral.cosmiccore.modules.enchantments.gkits.users.GkitUser;
import me.viiral.cosmiccore.modules.enchantments.gkits.users.GkitUserManager;
import me.viiral.cosmiccore.modules.enchantments.inventories.GkitsUI;
import me.viiral.cosmiccore.modules.enchantments.language.EnchantLanguage;
import me.viiral.cosmiccore.utils.TimeUtils;
import org.bukkit.entity.Player;

@Command("gkit")
public class GkitCommand extends CommandBase {

    private final GkitUserManager userManager;

    public GkitCommand(CosmicCore plugin) {
        this.userManager = plugin.getGkitUserManager();
    }

    @Default
    public void mainCommand(Player sender, @Optional @Completion("#gkits") Gkit gkit)  {
        if (gkit == null) {
            GkitsUI gkitsUI = new GkitsUI();
            gkitsUI.getGkitsUI().open(sender);
            return;
        }

        if (!sender.hasPermission(gkit.getPermission())) {
            EnchantLanguage.KIT_NO_ACCESS.send(sender);
            return;
        }

        GkitUser user = this.userManager.getUserOrCreate(sender.getUniqueId());

        if (user.hasCooldownOnKit(gkit)) {
            EnchantLanguage.KIT_COOLDOWN.send(sender, string -> string.replace(
                    "{time}", TimeUtils.formatSeconds(user.getSecondsTillKitAvailable(gkit))));
            return;
        }

        gkit.supplyKit(sender);
        user.newCooldownOnKit(gkit);
        EnchantLanguage.KIT_RECEIVED.send(sender, str -> str.replace("{gkit}", gkit.getName()));
    }
}
