package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Heroicable;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Reloadable;import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Silenceable;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;
import me.viiral.cosmiccore.modules.user.User;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.texture.BlockTexture;

public class Armored extends ArmorIncomingPVPDamageEventEnchant implements Silenceable, Reloadable, Heroicable {

    @ConfigValue
    private double damageNegationMultiplier = 1.85;
    private ParticleBuilder particle;

    @ConfigValue
    private int particleAmount = 1000;
    @ConfigValue
    private double particleYoffset = 0.7;
    @ConfigValue
    private double particleSpeed = 0.05;

    public Armored() {
        super("Armored", EnchantTier.LEGENDARY, true, 4, EnchantType.ARMOR, "Decreases damage from enemy swords by", "x1.85 per level");
        this.reloadValues();
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (!(attacker instanceof Player)) return;
        if (!EnchantType.SWORD.getItems().contains(((Player) attacker).getItemInHand().getType())) return;

        User user = CosmicCore.getInstance().getUserManager().getUsers().get(attacker.getUniqueId());

        if (user != null && user.types.contains(EffectType.BLOCK_ARMORED)) return;


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
    public Enchantment getHeroicEnchant() {
        return CosmicCore.getInstance().getEnchantRegister().getEnchantmentFromName("Paladin Armored");
    }
}
