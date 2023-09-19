package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Relation;
import me.viiral.cosmiccore.modules.enchantments.events.EnchantProcEvent;
import me.viiral.cosmiccore.modules.enchantments.events.EnchantProcOnEntityEvent;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.modules.enchantments.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

public class Teleportation extends Enchantment {

    public Teleportation() {
        super("Teleportation", EnchantTier.ELITE, false, 5, EnchantType.BOW, "When an ally or faction member is hit", "with your arrow, you teleport to them.");
    }

    @EventHandler
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Arrow)) return;

        Player victim = (Player) event.getEntity();
        Arrow arrow = (Arrow) event.getDamager();


        if (!(arrow.getShooter() instanceof Player)) return;

        Player attacker = (Player) arrow.getShooter();

        ItemStack itemStack = attacker.getItemInHand();

        if (!ItemUtils.isEnchantable(itemStack)) return;

        EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(itemStack);

        if (!enchantedItemBuilder.hasEnchantment(this)) return;

        EnchantProcEvent procEvent = new EnchantProcOnEntityEvent(victim, attacker, this, enchantedItemBuilder.getEnchantmentLevel(this));
        Bukkit.getPluginManager().callEvent(procEvent);

        if (procEvent.isCancelled()) return;

        FPlayer fPlayerVictim = FPlayers.getInstance().getByPlayer(victim);
        FPlayer fPlayerAttacker = FPlayers.getInstance().getByPlayer(attacker);
        Relation relation = fPlayerVictim.getRelationTo(fPlayerAttacker);

        if (!relation.isAlly() && !relation.isTruce() && !relation.isMember()) return;

        attacker.teleport(victim.getLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
    }
}
