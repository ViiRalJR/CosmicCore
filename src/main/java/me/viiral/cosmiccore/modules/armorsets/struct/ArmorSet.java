package me.viiral.cosmiccore.modules.armorsets.struct;

import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTCompound;

import lombok.Getter;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.user.UserManager;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.DamageHandler;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public abstract class ArmorSet implements Listener {

    @Getter
    private final DamageHandler damageHandler;
    private final UserManager userManager;

    public ArmorSet() {
        this.damageHandler = CosmicCore.getInstance().getDamageHandler();
        this.userManager = CosmicCore.getInstance().getUserManager();
    }

    public abstract String getName();

    public abstract String getColor();

    protected abstract ItemStack getHelmet();

    protected abstract ItemStack getChestplate();

    protected abstract ItemStack getLeggings();

    protected abstract ItemStack getBoots();

    public abstract List<String> getEquipMessage();

    public abstract List<String> getArmorLore();

    public abstract List<String> getCrystalLore();
    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {}

    public void onAttacked(Player attacked, Entity attacker, EntityDamageByEntityEvent event) {}

    public void onAttackCrystal(Player attacker, Entity attacked, int crystalAmount, EntityDamageByEntityEvent event) {}

    public void onAttackedCrystal(Player attacked, Entity attacker, int crystalAmount, EntityDamageByEntityEvent event) {}

    public void onEquip(Player player) {}

    public void onUnequip(Player player) {}

    protected List<ItemStack> getArmorSet() {
        return Arrays.asList(this.getHelmet(), this.getChestplate(), this.getLeggings(), this.getBoots());
    }

    public String getID() {
        return this.getName().toLowerCase(Locale.ROOT).replace(" ", "");
    }

    public void givePlayerArmorSet(Player player) {
        for (ItemStack itemStack : this.getArmorSet()) {
            player.getInventory().addItem(this.applyData(itemStack));
        }
    }

    protected ItemStack applyData(ItemStack itemStack) {
        ItemBuilder itemBuilder = new ItemBuilder(itemStack).addLore(this.getArmorLore());
        NBTItem wrapper = new NBTItem(itemBuilder.build());
        NBTCompound cosmicData = wrapper.getOrCreateCompound("cosmicData");
        cosmicData.setString("armorSetType", this.getName());
        return wrapper.getItem();
    }

    protected void addPotionEffect(Player player, PotionEffectType type, int duration, int amplifier) {
        userManager.addPotionEffect(player.getUniqueId(), type, amplifier, duration);
    }

    protected void removePotionEffect(Player player, PotionEffectType effect, int amplifier) {
        userManager.removePotionEffect(player.getUniqueId(), effect, amplifier);
    }

    protected void addEffect(Player player, EffectType type) {
        userManager.addEffect(player.getUniqueId(), type);
    }

    protected void removeEffect(Player player, EffectType type) {
        userManager.removeEffect(player.getUniqueId(), type);
    }

}

