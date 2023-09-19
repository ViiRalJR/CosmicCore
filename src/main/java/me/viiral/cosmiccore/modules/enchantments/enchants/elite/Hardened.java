package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Reloadable;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.utils.ItemUtils;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;


public class Hardened extends ArmorIncomingPVPDamageEventEnchant implements Reloadable {

    @ConfigValue
    private double procChance = 0.2;

    private ParticleBuilder particle;

    @ConfigValue
    private int particleAmount = 10;
    @ConfigValue
    private double particleYoffset = 0.3;
    @ConfigValue
    private double particleSpeed = 0.1;

    public Hardened() {
        super("Hardened", EnchantTier.ELITE, false, 3, EnchantType.ARMOR, "Armor takes less durability damage.");
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (Math.random() < procChance * enchantInfo.getLevel()) {
            this.particle.setLocation(victim.getLocation()).display(PVPUtils.getNearbyPlayersExceptPlayer(victim, 30));
            for (ItemStack itemStack : victim.getInventory().getArmorContents()) {
                if (!ItemUtils.isArmor(itemStack)) continue;
                itemStack.setDurability((short) (itemStack.getDurability() - enchantInfo.getLevel() + 2));
            }
        }
    }

    @Override
    public void reloadValues() {
        this.particle = new ParticleBuilder(ParticleEffect.SPELL_WITCH)
                .setAmount(particleAmount)
                .setOffsetY((float) particleYoffset)
                .setSpeed((float) particleSpeed);
    }
}