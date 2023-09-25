package me.viiral.cosmiccore.modules.armorsets.struct.armorsets;

import me.viiral.cosmiccore.modules.armorsets.struct.ArmorSet;
import me.viiral.cosmiccore.utils.CC;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SupremeArmorSet extends ArmorSet {

    // TODO: 24/09/2023 Fall damage and fly
    @Override
    public String getName() {
        return "Supreme";
    }

    @Override
    public String getColor() {
        return CC.DarkRed;
    }

    @Override
    protected ItemStack getHelmet() {
        return (new ItemBuilder(Material.DIAMOND_HELMET))
                .setName(CC.translate("&4&lSupreme Headgear"))
                .addLore(CC.translate("&4&oA lightweight headpiece"), CC.translate( "&4&ofit for take off."))
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .build();
    }

    @Override
    protected ItemStack getChestplate() {
        return (new ItemBuilder(Material.DIAMOND_CHESTPLATE))
                .setName(CC.translate("&4&lSupreme Vest"))
                .addLore(CC.translate("&4&oAn aerodynamic vest that"), CC.translate( "&4&ois capable of sustaining flight."))
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .build();
    }

    @Override
    protected ItemStack getLeggings() {
        return (new ItemBuilder(Material.DIAMOND_LEGGINGS))
                .setName(CC.translate("&4&lSupreme Chaps"))
                .addLore(CC.translate("&4&oLightweight and clout powered,"), CC.translate( "&4&oprovides enough thrust to boost"), CC.translate("&4&oeven the most feeble into the skies."))
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .build();
    }

    @Override
    protected ItemStack getBoots() {
        return (new ItemBuilder(Material.DIAMOND_BOOTS))
                .setName(CC.translate("&4&lSupreme Thruster Boots"))
                .addLore(CC.translate("&4&oSupreme boots capable of"), CC.translate( "&4&oconverting clout into flight."))
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .build();
    }

    @Override
    public List<String> getEquipMessage() {
        return Arrays.asList(
                "",
                CC.translate("&4&lSUPREME SET BONUS"),
                CC.translate("&8* &4+15% OUTGOING DAMAGE"),
                CC.translate("&8* &4+10% INCOMING ARROW DAMAGE"),
                CC.translate("&8* &4NO FALL DAMAGE"),
                CC.translate("&8* &4NO FOOD LOSS"),
                CC.translate("&8* &4GEARS IV"),
                CC.translate("&8* &4+200% CLOUT (Flight Enabled)"),
                ""
        );
    }

    @Override
    public List<String> getArmorLore() {
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate(""));
        lore.add(CC.translate("&4&lSUPREME SET BONUS"));
        lore.add(CC.translate("&4&l* &4Gears IV"));
        lore.add(CC.translate("&4&l* &4No Fall Damage / Food Loss"));
        lore.add(CC.translate("&4&l* &4Deal +15% damage to all enemies"));
        lore.add(CC.translate("&4&l* &4Enemy arrows deal 10% more damage to you."));
        lore.add(CC.translate("&4&l* &4+200% Clout"));
        lore.add(CC.translate("&7(&oRequires all 4 supreme items.&7)"));
        return lore;
    }

    @Override
    public List<String> getCrystalLore() {
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&4&l Supreme"));
        lore.add(CC.translate("&5&l  *&5 +3% Outgoing Damage"));
        return lore;
    }

    @Override
    public void onAttackCrystal(Player attacker, Entity attacked, int crystalAmount, EntityDamageByEntityEvent event) {
        this.getDamageHandler().increaseDamage(3, event, getName() + " Crystal");
    }

    @Override
    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        this.getDamageHandler().increaseDamage(15, event, getName() + " Set");
    }

    @Override
    public void onAttacked(Player attacked, Entity attacker, EntityDamageByEntityEvent event) {
        if (!(attacker instanceof Player)) return;
        if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            this.getDamageHandler().reduceDamage(-10, event, getName() + " Set");
        }
    }

    @Override
    public void onEquip(Player player) {
        addPotionEffect(player, PotionEffectType.SPEED, 0, 3);
    }

    @Override
    public void onUnequip(Player player) {
        removePotionEffect(player, PotionEffectType.SPEED, 3);
    }
}
