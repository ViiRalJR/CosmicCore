package me.viiral.cosmiccore.modules.armorsets.struct.armorsets;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Relation;
import de.tr7zw.changeme.nbtapi.NBTItem;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.armorsets.struct.ArmorSet;
import me.viiral.cosmiccore.modules.armorsets.struct.WeaponArmorSet;
import me.viiral.cosmiccore.utils.CC;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import me.viiral.cosmiccore.utils.world.LocationUtils;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class YijkiArmorSet extends ArmorSet implements WeaponArmorSet {

    @Override
    public String getName() {
        return "Yijki";
    }

    @Override
    public String getColor() {
        return CC.White;
    }

    public ItemStack getHelmet() {
        return (new ItemBuilder(Material.DIAMOND_HELMET)).setName(CC.translate("&f&lYijki Mask")).addLore(CC.translate("&fYijki face mask.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getChestplate() {
        return (new ItemBuilder(Material.DIAMOND_CHESTPLATE)).setName(CC.translate("&f&lYijki Torso")).addLore(CC.translate("&fThe forbidden mantel of yijki.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getLeggings() {
        return (new ItemBuilder(Material.DIAMOND_LEGGINGS)).setName(CC.translate("&f&lYijki Robeset")).addLore(CC.translate("&fThe fabled robes of yijki.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getBoots() {
        return (new ItemBuilder(Material.DIAMOND_BOOTS)).setName(CC.translate("&f&lYijki Footwraps")).addLore(CC.translate("&fLight as feather, heavy as stone.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    @Override
    public List<String> getEquipMessage() {
        List<String> message = new ArrayList<>();
        message.add("");
        message.add(CC.translate("&f&lYIJKI SET BONUS"));
        message.add(CC.translate("&8* &fEnemies deal 30% less damage to you."));
        message.add(CC.translate("&8* &fRevenge of Yijki Passive Ability."));
        message.add("");
        return message;
    }

    @Override
    public void onAttacked(Player attacked, Entity attacker, EntityDamageByEntityEvent event) {
        super.getDamageHandler().reduceDamage(30, event, "YijkiSet");
        if (Math.random() < 0.05 && attacker instanceof Player) {
        if (!CosmicCore.getInstance().getWorldGuardUtils().canPvPInRegion(attacker.getLocation()) || !CosmicCore.getInstance().getWorldGuardUtils().canPvPInRegion(attacked.getLocation())) return;
            this.activateYijki(attacker.getLocation(), attacked);
        }
    }

    public List<String> getArmorLore() {
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate(""));
        lore.add(CC.translate("&f&lYIJKI SET BONUS"));
        lore.add(CC.translate("&8* &fEnemies deal 30% less damage to you."));
        lore.add(CC.translate("&8* &fRevenge of Yijki Passive Ability."));
        lore.add(CC.translate("&7(&oRequires all 4 yijki items.&7)"));
        return lore;
    }

    public ItemStack[] getWeapons() {
        List<ItemStack> weapons = new ArrayList<>();
        NBTItem sword = new NBTItem(new ItemBuilder(Material.DIAMOND_SWORD)
                .setName(CC.translate("&f&lYijki World Ender"))
                .addLore(CC.Gray + "They laughed at you, they"
                        , CC.Gray + "An Absolute massive, corruped"
                        , CC.Gray + "executioner blade imbued with"
                        , CC.Gray + "highly destructive dark magic."
                        , ""
                        , CC.WhiteB + "YIJKI WEAPON BONUS"
                        , CC.translate("&8* &fDeal +20% damage to all enemies.")
                        , CC.translate("&8* &f+25% Revenge of Yijki Ability.")
                        , CC.Gray + "(" + CC.GrayI + "Requires all 4 yijki items." + CC.Gray + ")")
                .addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.DURABILITY, 3).build());

        sword.setString("cosmicType", "YIJKI_SWORD");

        weapons.add(sword.getItem());
        return weapons.toArray(new ItemStack[]{});
    }

    @Override
    public List<String> getCrystalLore() {
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&f&l Mother of Yijki"));
        lore.add(CC.translate("&f&l  *&f -5% Incoming Damage"));
        return lore;
    }

    @Override
    public void onAttackedCrystal(Player attacked, Entity attacker, int crystalAmount, EntityDamageByEntityEvent event) {
        super.getDamageHandler().reduceDamage(5 * crystalAmount, event, "YijkiCrystal");
    }

    public void activateYijki(Location center, Player player) {
        ArrayList<Location> procLocations = new ArrayList<>();
        ArrayList<Player> nearbyPlayers = new ArrayList<>();
        FPlayer fp = FPlayers.getInstance().getByPlayer(player);
        for (int i = 0; i < 16; ++i) {
            Location l = LocationUtils.getNearbyLocation(center, 8, 2, 0);
            l.getWorld().playSound(l, Sound.ENTITY_WITHER_SPAWN, 1.0f, 0.4f);
            //de.inventivegames.particle.ParticleEffect.WITCH_MAGIC.sendToPlayers(Bukkit.getOnlinePlayers(), l.clone(), 0f, 1f, 0f, 0.7f, 32);
            //de.inventivegames.particle.ParticleEffect.WITCH_MAGIC.sendToPlayers(Bukkit.getOnlinePlayers(), l.clone(), 0f, 0f, 0f, 0.7f, 32);
            procLocations.add(l);
        }
        for (Entity nearbyEnt : player.getNearbyEntities(32.0, 64.0, 32.0)) {
            if (!(nearbyEnt instanceof Player)) continue;
            Player pNear = (Player) nearbyEnt;
            FPlayer fpNear = FPlayers.getInstance().getByPlayer(pNear);
            if (fpNear.getRelationTo(fp).isAtLeast(Relation.TRUCE) || !CosmicCore.getInstance().getWorldGuardUtils().canPvPInRegion(pNear.getLocation()))
                continue;
            pNear.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "** REVENGE OF YIJKI (" + ChatColor.RED + fp.getName() + " [1.5s]" + ChatColor.DARK_PURPLE + ChatColor.BOLD + ") **");
            nearbyPlayers.add(pNear);
        }
        Bukkit.getScheduler().runTaskLater(CosmicCore.getInstance(), () -> {
            for (Location l : procLocations) {
                l.getWorld().strikeLightningEffect(l);
                l.getWorld().playSound(l, Sound.ENTITY_WITHER_SPAWN, 1.0f, 0.4f);
                for (Player pNear : nearbyPlayers) {
                    if (!(pNear.getLocation().distanceSquared(l) <= 2.0) || FPlayers.getInstance().getByPlayer(pNear).getRelationTo(fp).isAtLeast(Relation.TRUCE) || !CosmicCore.getInstance().getWorldGuardUtils().canPvPInRegion(pNear.getLocation()))
                        continue;
                    int damage = 16;

                    pNear.setHealth(Math.max(1.0, pNear.getHealth() - (double) damage));
                }
            }
        }, 20L);
    }


}
