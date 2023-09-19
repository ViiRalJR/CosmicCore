package me.viiral.cosmiccore.modules.armorsets.struct.armorsets;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Relation;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.armorsets.struct.ArmorSet;
import me.viiral.cosmiccore.utils.CC;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.stream.Collectors;

public class TravelerArmorSet extends ArmorSet implements Listener {

    private final Map<UUID, TravelerFallingBlock> fallingBlocks;

    public TravelerArmorSet() {
        this.fallingBlocks = new HashMap<>();
    }

    @Override
    public String getColor() {
        return CC.DarkPurple;
    }

    @Override
    public String getName() {
        return "Traveler";
    }

    public ItemStack getHelmet() {
        return (new ItemBuilder(Material.DIAMOND_HELMET))
                .setName(CC.translate("&5&lTraveler Hood"))
                .addLore(CC.translate("&5A hood that fades in and out."))
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .build();
    }

    public ItemStack getChestplate() {
        return (new ItemBuilder(Material.DIAMOND_CHESTPLATE)).setName(CC.translate("&5&lTraveler Chestplate")).addLore(CC.translate("&5A chestplate that fades in and out.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getLeggings() {
        return (new ItemBuilder(Material.DIAMOND_LEGGINGS)).setName(CC.translate("&5&lTraveler Robes")).addLore(CC.translate("&5Robes that fades in and out.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getBoots() {
        return (new ItemBuilder(Material.DIAMOND_BOOTS)).setName(CC.translate("&5&lTraveler Sandals")).addLore(CC.translate("&5A sandal that fades in and out.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    @Override
    public List<String> getEquipMessage() {
        List<String> message = new ArrayList<>();
        message.add("");
        message.add(CC.translate("&5&lTRAVELER SET BONUS"));
        message.add(CC.translate("&8* &5Deal +30% damage to all enemies."));
        message.add(CC.translate("&8* &5Dimensional Shift Passive Ability."));
        message.add("");
        return message;
    }

    @Override
    public List<String> getCrystalLore() {
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&5&l Dimensional Traveler"));
        lore.add(CC.translate("&5&l  *&5 +7.5% Outgoing Damage"));
        return lore;
    }

    @Override
    public void onAttackCrystal(Player attacker, Entity attacked, int crystalAmount, EntityDamageByEntityEvent event) {
        super.getDamageHandler().increaseDamage(7.5 * crystalAmount, event, "TravelerCrystal");
    }

    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        super.getDamageHandler().increaseDamage(30, event, "TravelerSet");
    }

    @Override
    public void onAttacked(Player attacked, Entity attacker, EntityDamageByEntityEvent event) {
        if (Math.random() < 0.005D)
            this.onDimensionalRift(attacked);
    }

    public List<String> getArmorLore() {
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate(""));
        lore.add(CC.translate("&5&lTRAVELER SET BONUS"));
        lore.add(CC.translate("&8* &5Deal +30% damage to all enemies."));
        lore.add(CC.translate("&8* &5Dimensional Shift Passive Ability."));
        lore.add(CC.translate("&7(&oRequires all 4 traveler items.&7)"));
        return lore;
    }

    public void onDimensionalRift(Player victim) {
        FPlayer damagedFPlayer = FPlayers.getInstance().getByPlayer(victim);
        for (Entity nearbyEnt : victim.getNearbyEntities(25.0D, 32.0D, 25.0D)) {
            if (nearbyEnt instanceof Player) {
                Player nearbyPlayer = (Player)nearbyEnt;
                FPlayer fpNear = FPlayers.getInstance().getByPlayer(nearbyPlayer);
                if ((fpNear.hasFaction() && fpNear.getRelationTo(damagedFPlayer).isAtLeast(Relation.TRUCE)) || !CosmicCore.getInstance().getWorldGuardUtils().canPvPInRegion(damagedFPlayer.getPlayer().getLocation()))
                    continue;
                if (!CosmicCore.getInstance().getWorldGuardUtils().canPvPInRegion(nearbyPlayer.getLocation()))
                    continue;
                if (nearbyPlayer.hasMetadata("NPC"))
                    continue;
                nearbyPlayer.playSound(nearbyPlayer.getLocation().add(0.0D, 4.0D, 0.0D), Sound.ENDERMAN_TELEPORT, 1.0F, 1.1F);
                nearbyPlayer.playSound(nearbyPlayer.getLocation().add(0.0D, 4.0D, 0.0D), Sound.ANVIL_LAND, 1.0F, 1.1F);
                nearbyPlayer.sendMessage(CC.DarkPurpleB + "** DIMENSIONAL SHIFT (" + CC.Red + fpNear.getRelationTo(damagedFPlayer).getColor() + victim.getName() + " [4s]" + CC.DarkPurpleB + ") **");

                this.spawnDimensionalBlocks(nearbyPlayer, victim);
            }
        }
    }

    @EventHandler
    public void onBlockDamage(EntityChangeBlockEvent event) {
        if (event.getEntity().getType() != EntityType.FALLING_BLOCK) return;

        FallingBlock fallingBlock = (FallingBlock)event.getEntity();
        TravelerFallingBlock block = this.getTravelerBlock(fallingBlock.getUniqueId());

        if (block == null) return;

        event.setCancelled(true);
        this.fallingBlocks.remove(block.getUUID());
        FPlayer blockFPlayer = FPlayers.getInstance().getByPlayer(block.getPlayer());

        List<Player> nearbyEnemies = event.getBlock().getLocation().getWorld()
                .getNearbyEntities(event.getBlock().getLocation(), 2.0D, 2.0D, 2.0D)
                .stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player)entity).filter(player -> {
                    FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
                    Relation relationTo = fPlayer.getRelationTo(blockFPlayer);
                    return ((relationTo == Relation.ENEMY || relationTo == Relation.NEUTRAL) && CosmicCore.getInstance().getWorldGuardUtils().canPvPInRegion(player.getLocation()));
        }).collect(Collectors.toList());

        nearbyEnemies.remove(block.getPlayer());
        nearbyEnemies.forEach(player -> {
            player.damage(1.0D, block.getPlayer());
            player.damage(this.getRandomDamage());
        });
    }

    public TravelerFallingBlock getTravelerBlock(UUID fallingBlockUUID) {
        return this.fallingBlocks.get(fallingBlockUUID);
    }

    public double getRandomDamage() {
        return (12 + (new Random()).nextInt(16));
    }

    private void spawnDimensionalBlocks(Player player, Player damaged) {
        Location center = player.getLocation().add(0.0D, 10.0D, 0.0D);
        int playerX = center.getBlockX(), playerZ = center.getBlockZ(), playerY = center.getBlockY();
        int minX = playerX - 3, minZ = playerZ - 3;
        int maxX = playerX + 3, maxZ = playerZ + 3;
        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                double locX = x + this.getRandomOffset(5.8D);
                double locZ = z + this.getRandomOffset(5.8D);
                Location location = new Location(center.getWorld(), locX, playerY, locZ);
                Material material = (new Random()).nextBoolean() ? Material.NETHERRACK : Material.ENDER_STONE;
                FallingBlock fallingBlock = location.getWorld().spawnFallingBlock(location, material, (byte)0);
                fallingBlock.setVelocity(new Vector(0, -1, 0));
                fallingBlock.setHurtEntities(false);
                fallingBlock.setDropItem(false);
                fallingBlock.setMetadata("dimensionalShift", new FixedMetadataValue(CosmicCore.getInstance(), Boolean.TRUE));
                this.fallingBlocks.put(fallingBlock.getUniqueId(), new TravelerFallingBlock(damaged, fallingBlock.getUniqueId()));
            }
        }
    }

    public double getRandomOffset(double baseDouble) {
        Random r = new Random();
        return (r.nextFloat() * r.nextFloat()) * baseDouble;
    }

    public static class TravelerFallingBlock {
        private Player player;

        private UUID UUID;

        public TravelerFallingBlock(Player player, UUID blockUUID) {
            this.player = player;
            this.UUID = blockUUID;
        }

        public Player getPlayer() {
            return this.player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }

        public UUID getUUID() {
            return this.UUID;
        }

        public void setUUID(UUID uUID) {
            this.UUID = uUID;
        }
    }
}
