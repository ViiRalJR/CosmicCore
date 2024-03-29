package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.enchantscache.RageStacksCache;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Heroicable;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Reloadable;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Silenceable;


import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.utils.CacheUtils;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.texture.BlockTexture;

public class Cactus extends ArmorIncomingPVPDamageEventEnchant implements Silenceable, Reloadable, Heroicable {

    @ConfigValue
    private double damage = 0.5;
    private ParticleBuilder particle;

    @ConfigValue
    private int particleAmount = 1000;
    @ConfigValue
    private double particleYoffset = 0.7;
    @ConfigValue
    private double particleSpeed = 0.05;

    public Cactus() {
        super("Cactus", EnchantTier.ELITE, false, 2, EnchantType.ARMOR, "Injures your attacker but does not", "affect your durability.");
        this.reloadValues();
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (!(attacker instanceof Player attackerPlayer)) return;
        super.getDamageHandler().damage(attackerPlayer, (int) (damage * enchantInfo.getLevel()), this.getName());
        this.particle.setLocation(victim.getLocation()).display(PVPUtils.getNearbyPlayersExceptPlayer(victim, 30));
        attackerPlayer.playEffect(attackerPlayer.getLocation(), Effect.STEP_SOUND, Material.CACTUS);

        RageStacksCache attackerRageStack = CacheUtils.getRageStackCache(attackerPlayer);
        if (attackerRageStack.getRageStack() > 0) {
            attackerRageStack.resetRageStack();
        }
    }

    @Override
    public void reloadValues() {
        this.particle = new ParticleBuilder(ParticleEffect.BLOCK_DUST)
                .setParticleData(new BlockTexture(Material.CACTUS))
                .setAmount(particleAmount)
                .setOffsetY((float) particleYoffset)
                .setSpeed((float) particleSpeed);
    }


    @Override
    public Enchantment getHeroicEnchant() {
        return CosmicCore.getInstance().getEnchantRegister().getEnchantmentFromName("Mighty Cactus");
    }
}
