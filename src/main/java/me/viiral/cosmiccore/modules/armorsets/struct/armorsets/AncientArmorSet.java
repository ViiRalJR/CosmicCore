package me.viiral.cosmiccore.modules.armorsets.struct.armorsets;

import me.viiral.cosmiccore.modules.armorsets.struct.ArmorSet;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
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

public class AncientArmorSet extends ArmorSet {
    @Override
    public String getName() {
        return "Ancient";
    }

    @Override
    public String getColor() {
        return CC.DarkAqua;
    }

    @Override
    protected ItemStack getHelmet() {
        return (new ItemBuilder(Material.DIAMOND_HELMET))
                .setName(CC.translate("&3&lAncient Cap"))
                .addLore(CC.translate("&3&oPowerful, martime helmet of"), CC.translate( "&3&othe great Leviathan legend."))
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .build();
    }

    @Override
    protected ItemStack getChestplate() {
        return (new ItemBuilder(Material.DIAMOND_CHESTPLATE))
                .setName(CC.translate("&3&lAncient Body"))
                .addLore(CC.translate("&3&oPowerful, martime chestplate of"), CC.translate( "&3&othe great Leviathan legend."))
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .build();
    }

    @Override
    protected ItemStack getLeggings() {
        return (new ItemBuilder(Material.DIAMOND_LEGGINGS))
                .setName(CC.translate("&3&lAncient Legs"))
                .addLore(CC.translate("&3&oPowerful, martime leggings of"), CC.translate( "&3&othe great Leviathan legend."))
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .build();
    }

    @Override
    protected ItemStack getBoots() {
        return (new ItemBuilder(Material.DIAMOND_BOOTS))
                .setName(CC.translate("&4&lAncient Boots"))
                .addLore(CC.translate("&3&oPowerful, martime boots of"), CC.translate( "&3&othe great Leviathan legend."))
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .build();
    }

    @Override
    public List<String> getEquipMessage() {
        return Arrays.asList(
                "",
                "&4&lANCIENT SET BONUS",
                "&8* &3+35% Outgoing Damage.",
                "&8* &3+10% Outgoing Damage from Soul Enchants.",
                "&8* &3-15% Incoming Damage.",
                "&8* &3Immune to Abyssal Stronghold perks.",
                "&8* &3-50% Knockback.",
                "&8* &3Auto Bless.",
                "&8* &3Leviathan's Curse Ability.",
                ""
        );
    }

    @Override
    public List<String> getArmorLore() {
        List<String> lore = new ArrayList<>();

        lore.add(CC.translate("&3&lANCIENT SET BONUS"));
        lore.add(CC.translate("&8* &3-15% Incoming Damage"));
        lore.add(CC.translate("&8* &3+35% Outgoing Damage"));
        lore.add(CC.translate("&8* &3+10% Damage from Outgoing Soul Enchants"));
        lore.add(CC.translate("&8* &3Auto Bless"));
        lore.add(CC.translate("&8* &3Leviathan's Curse Ability"));
        lore.add(CC.translate("&7&o(Requires all 4 ancient items.)"));

        return lore;
    }

    @Override
    public void onEquip(Player player) {
        this.addEffect(player, EffectType.AUTO_BLESS);
    }

    @Override
    public void onUnequip(Player player) {
        this.removeEffect(player, EffectType.AUTO_BLESS);
    }

    @Override
    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        this.getDamageHandler().increaseDamage(35, event, getName() + " Set");
    }

    @Override
    public void onAttacked(Player attacked, Entity attacker, EntityDamageByEntityEvent event) {
        this.getDamageHandler().reduceDamage(15, event, getName() + " Crystal");
    }

    @Override
    public List<String> getCrystalLore() {
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&3&l Ancient"));
        lore.add(CC.translate("&3&l  *&3 +7% Outgoing Damage"));
        lore.add(CC.translate("&3&l  *&3 -3% Incoming Damage"));
        lore.add(CC.translate("&3&l  *&3 10% Leviathan's Curse Ability"));
        return lore;
    }

    @Override
    public void onAttackCrystal(Player attacker, Entity attacked, int crystalAmount, EntityDamageByEntityEvent event) {
        this.getDamageHandler().increaseDamage(7, event, getName() + " Crystal");
    }

    @Override
    public void onAttackedCrystal(Player attacked, Entity attacker, int crystalAmount, EntityDamageByEntityEvent event) {
        this.getDamageHandler().reduceDamage(3, event, getName() + " Crystal");
    }
}
