package me.viiral.cosmiccore.modules.armorsets.struct.armorsets;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.viiral.cosmiccore.modules.armorsets.struct.ArmorSet;
import me.viiral.cosmiccore.modules.armorsets.struct.WeaponArmorSet;
import me.viiral.cosmiccore.utils.CC;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class YetiArmorSet extends ArmorSet implements WeaponArmorSet {
    
    @Override
    public String getName() {
        return "Yeti";
    }

    @Override
    public String getColor() {
        return CC.Aqua;
    }

    public ItemStack getHelmet() {
        return (new ItemBuilder(Material.DIAMOND_HELMET)).setName(CC.translate("&b&lYeti Facemask")).addLore(CC.translate("&bThe savage mask of the Yeti.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getChestplate() {
        return (new ItemBuilder(Material.DIAMOND_CHESTPLATE)).setName(CC.translate("&b&lBloody Yeti Torso")).addLore(CC.translate("&bThe impermable chestplate of the Yeti.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getLeggings() {
        return (new ItemBuilder(Material.DIAMOND_LEGGINGS)).setName(CC.translate("&b&lFuzzy Yeti Leggings")).addLore(CC.translate("&bThe cozy yet monstrous legs of the Yeti.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getBoots() {
        return (new ItemBuilder(Material.DIAMOND_BOOTS)).setName(CC.translate("&b&lBig-Yeti Boots")).addLore(
                CC.translate("&bBigfoot has nothing on these boots."))
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    @Override
    public List<String> getEquipMessage() {
        List<String> message = new ArrayList<>();
        message.add("");
        message.add(CC.translate("&b&lYETI SET BONUS"));
        message.add(CC.translate("&8* &b+10% Outgoing DMG"));
        message.add(CC.translate("&8* &b-10% Incoming DMG"));
        message.add("");
        return message;
    }

    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        super.getDamageHandler().increaseDamage(10, event, "YetiSet");
    }

    @Override
    public void onAttacked(Player attacked, Entity attacker, EntityDamageByEntityEvent event) {
        super.getDamageHandler().reduceDamage(10, event, "YetiSet");

    }

    public List<String> getArmorLore() {
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate(""));
        lore.add(CC.translate("&b&lYETI SET BONUS"));
        lore.add(CC.translate("&8* &bDeal +10% damage to all enemies."));
        lore.add(CC.translate("&8* &bEnemies deal 10% less damage to you."));
        lore.add(CC.translate("&7(&oRequires all 4 yeti items.&7)"));
        return lore;
    }

    @Override
    public List<String> getCrystalLore() {
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&b&l Yeti"));
        lore.add(CC.translate("&b&l  *&b +2.5% Outgoing Damage"));
        lore.add(CC.translate("&b&l  *&b -2.5% Incoming Damage"));
        return lore;
    }

    @Override
    public void onAttackCrystal(Player attacker, Entity attacked, int crystalAmount, EntityDamageByEntityEvent event) {
        super.getDamageHandler().increaseDamage(2.5 * crystalAmount, event, "YetiCrystal");
    }

    @Override
    public void onAttackedCrystal(Player attacked, Entity attacker, int crystalAmount, EntityDamageByEntityEvent event) {
        super.getDamageHandler().reduceDamage(2.5 * crystalAmount, event, "YetiCrystal");
    }

    public ItemStack[] getWeapons() {
        List<ItemStack> weapons = new ArrayList<>();
        NBTItem sword = new NBTItem(new ItemBuilder(Material.DIAMOND_AXE)
                .setName(CC.RedB + "Yeti Maul")
                .addLore(CC.Gray + "A penetrating axe used by a Yeti."
                        , ""
                        , CC.AquaB + "YETI WEAPON BONUS"
                        , CC.translate("&8* &bDeal +7.5% damage to all enemies")
                        , CC.translate("&8* &bBypass 50% of Heroic Armor")
                        , CC.Gray + "(" + CC.GrayI + "Requires all 4 yeti items." + CC.Gray + ")")
                .addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.DURABILITY, 3).build());

        sword.setString("cosmicType", "YETI_AXE");

        weapons.add(sword.getItem());
        return weapons.toArray(new ItemStack[]{});
    }


}
