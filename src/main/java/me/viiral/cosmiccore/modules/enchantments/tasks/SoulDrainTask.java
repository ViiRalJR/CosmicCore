package me.viiral.cosmiccore.modules.enchantments.tasks;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import me.viiral.cosmiccore.modules.enchantments.language.EnchantLanguage;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.SoulModeCache;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.DrainSoulEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.SoulEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.modules.enchantments.struct.souls.SoulManager;
import me.viiral.cosmiccore.modules.enchantments.utils.ItemUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

public class SoulDrainTask extends BukkitRunnable {

    private final SoulManager soulManager;
    private final ParticleBuilder spellParticle;

    public SoulDrainTask(CosmicCore plugin) {
        this.soulManager = plugin.getSoulManager();
        this.spellParticle = new ParticleBuilder(ParticleEffect.SPELL)
                .setAmount(15)
                .setOffsetY(0.5f)
                .setSpeed(0.2f);
    }

    @Override
    public void run() {
        for (SoulModeCache soulModeCache : this.soulManager.getPlayersInSoulMode()) {
            Player player = soulModeCache.getPlayer();

            ItemStack itemStack = player.getInventory().getItem(soulModeCache.getSlot());

            if (!EnchantsAPI.isSoulGem(itemStack)) {
                this.soulManager.disableSoulMode(player);
                return;
            }

            if (player.getItemInHand() == null) return;
            if (!ItemUtils.isWeapon(player.getItemInHand())) return;

            EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(player.getItemInHand());
            int soulCost = 0;

            for (Enchantment enchantment : enchantedItemBuilder.getEnchantments().keySet()) {
                if (enchantment instanceof DrainSoulEnchant) {
                    SoulEnchant soulEnchant = ((DrainSoulEnchant) enchantment);
                    soulCost += soulEnchant.getSoulCost();
                }
            }

            if (soulCost == 0) return;

            soulModeCache.getSoulGemBuilder().removeSouls(soulCost);
            player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EAT, 0.4F, 0.2F);
            this.spellParticle.setLocation(player.getLocation()).display(player);
            soulModeCache.updateSoulGem();

            if (soulModeCache.getSouls() % 100 == 0) {
                EnchantLanguage.SOUL_DRAIN_MESSAGE.send(player, s -> s.replace("{souls}", String.valueOf(soulModeCache.getSouls())));
            }

            if (soulModeCache.getSoulGemBuilder().getSouls() <= 0) {
                player.playSound(player.getLocation(), Sound.ITEM_FIRECHARGE_USE, 1.0F, 1.2F);
                soulModeCache.updateSoulGem();
                this.soulManager.disableSoulMode(player);
            }
        }
    }
}
