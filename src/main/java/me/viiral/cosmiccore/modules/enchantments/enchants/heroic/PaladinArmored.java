package me.viiral.cosmiccore.modules.enchantments.enchants.heroic;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.enchants.legendary.Armored;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.HeroicEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Reloadable;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Silenceable;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.texture.BlockTexture;

public class PaladinArmored extends ArmorIncomingPVPDamageEventEnchant implements Silenceable, Reloadable, HeroicEnchant {

    @ConfigValue
    private double damageNegationMultiplier = 2.50;
    private ParticleBuilder particle;

    @ConfigValue
    private int particleAmount = 1000;
    @ConfigValue
    private double particleYoffset = 0.7;
    @ConfigValue
    private double particleSpeed = 0.05;

    public PaladinArmored() {
        super("Paladin Armored", EnchantTier.HEROIC, true, 4, EnchantType.ARMOR, "Decreases damage from enemy swords by", "x2.5 per level");
        this.reloadValues();
        setHeroic();
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (!(attacker instanceof Player)) return;
        if (((Player) attacker).getItemInHand() == null) return;
        if (!EnchantType.SWORD.getItems().contains(((Player) attacker).getItemInHand().getType())) return;

        double damageNegation = enchantInfo.getLevel() * damageNegationMultiplier;
        super.getDamageHandler().reduceDamage(damageNegation, event, this.getName());
        this.particle.setLocation(victim.getLocation()).display(PVPUtils.getNearbyPlayersExceptPlayer(victim, 20));
        victim.playEffect(victim.getLocation(), Effect.STEP_SOUND, Material.DIAMOND_BLOCK);
    }

    @Override
    public void reloadValues() {
        this.particle = new ParticleBuilder(ParticleEffect.BLOCK_DUST)
                .setParticleData(new BlockTexture(Material.DIAMOND_BLOCK))
                .setAmount(particleAmount)
                .setOffsetY((float) particleYoffset)
                .setSpeed((float) particleSpeed);
    }

    @Override
    public Enchantment getNonHeroicEnchant() {
        return CosmicCore.getInstance().getEnchantRegister().getEnchantmentFromName("Armored");
    }
}