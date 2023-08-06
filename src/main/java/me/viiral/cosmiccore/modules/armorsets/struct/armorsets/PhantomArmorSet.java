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
import java.util.Arrays;
import java.util.List;

public class PhantomArmorSet extends ArmorSet implements WeaponArmorSet {

    @Override
    public String getName() {
        return "Phantom";
    }

    @Override
    public String getColor() {
        return CC.Red;
    }

    @Override
    public ItemStack getHelmet() {
        return (new ItemBuilder(Material.DIAMOND_HELMET))
                .setName(CC.translate("&c&lPhantom Hood"))
                .addLore(CC.translate("&cThe fabled hood of the Phantom."))
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .build();
    }

    public ItemStack getChestplate() {
        return (new ItemBuilder(Material.DIAMOND_CHESTPLATE))
                .setName(CC.translate("&c&lPhantom Shroud"))
                .addLore(CC.translate("&cThe legendary shroud of the Phantom."))
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .build();
    }

    public ItemStack getLeggings() {
        return (new ItemBuilder(Material.DIAMOND_LEGGINGS))
                .setName(CC.translate("&c&lPhantom Robe"))
                .addLore(CC.translate("&cThe demonic robe of the Phantom."))
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .build();
    }

    public ItemStack getBoots() {
        return (new ItemBuilder(Material.DIAMOND_BOOTS))
                .setName(CC.translate("&c&lPhantom Sandals"))
                .addLore(CC.translate("&cThe silent sandals of the Phantom."))
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .build();
    }

    @Override
    public List<String> getEquipMessage() {
        return Arrays.asList(
        "",
        CC.translate("&c&lPHANTOM SET BONUS"),
        CC.translate("&8* &c+25% DMG"),
        ""
        );
    }

    @Override
    public List<String> getCrystalLore() {
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&c&l Phantom"));
        lore.add(CC.translate("&c&l  *&c +5% Outgoing Damage"));
        return lore;
    }

    @Override
    public void onAttackCrystal(Player attacker, Entity attacked, int crystalAmount, EntityDamageByEntityEvent event) {
        super.getDamageHandler().increaseDamage(5 * crystalAmount, event, "PhantomCrystal");
    }


    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        super.getDamageHandler().increaseDamage(25, event, "PhantomSet");
    }

    public List<String> getArmorLore() {
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate(""));
        lore.add(CC.translate("&c&lPHANTOM SET BONUS"));
        lore.add(CC.translate("&8* &cDeal 25% damage to all enemies."));
        lore.add(CC.translate("&7(&oRequires all 4 phantom items.&7)"));
        return lore;
    }

    public ItemStack[] getWeapons() {
        List<ItemStack> weapons = new ArrayList<>();
        NBTItem sword = new NBTItem(new ItemBuilder(Material.DIAMOND_SWORD)
                .setName(CC.RedB + "Phantom Scythe")
                .addLore(CC.Gray + "An eerie blade designed to"
                        , CC.Gray + "cut through the enemies."
                        , ""
                        , CC.RedB + "PHANTOM WEAPON BONUS"
                        , CC.translate("&8* &cDeal +10% damage to all enemies.")
                        , CC.Gray + "(" + CC.GrayI + "Requires all 4 phantom items." + CC.Gray + ")")
                .addEnchantment(Enchantment.DAMAGE_ALL, 5)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .build());

        sword.setString("cosmicType", "PHANTOM_SWORD");

        weapons.add(sword.getItem());
        return weapons.toArray(new ItemStack[]{});
    }


}
