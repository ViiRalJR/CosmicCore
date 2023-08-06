package me.viiral.cosmiccore.utils.items;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

public class SkullUtils {

    private final static ItemStack DEFAULT_SKULL = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);

    public static ItemStack getSkullFromSkin(String skinURL) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short)3);
        if (skinURL == null || skinURL.isEmpty())
            return skull;
        SkullMeta skullMeta = (SkullMeta)skull.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", Base64Coder.encodeString("{textures:{SKIN:{\"url\":\"http://textures.minecraft.net/texture/" + skinURL + "\"}}}")));
        Field profileField;
        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException|SecurityException e) {
            e.printStackTrace();
            return null;
        }
        profileField.setAccessible(true);
        try {
            profileField.set(skullMeta, profile);
        } catch (IllegalArgumentException|IllegalAccessException e) {
            e.printStackTrace();
        }
        skull.setItemMeta(skullMeta);
        return skull;
    }

    public static ItemStack getSkull(ItemStack item, String skullTexture) {
        if (skullTexture == null) throw new IllegalStateException("Skull Texture cannot be null!");

        NBTItem nbtItem = new NBTItem(item);

        NBTCompound skullOwnerCompound = nbtItem.addCompound("SkullOwner");

        String uuid = UUID.randomUUID().toString();
        skullOwnerCompound.setString("Id", uuid);

        skullOwnerCompound.addCompound("Properties").getCompoundList("textures").addCompound().setString("Value", skullTexture);

        return nbtItem.getItem();
    }

    public static ItemStack getSkull(String skullTexture) {
        if (skullTexture == null) throw new IllegalStateException("Skull Texture cannot be null!");

        return getSkull(DEFAULT_SKULL, skullTexture);
    }

    public static ItemStack getSkullFromPlayer(Player player) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName(player.getName());
        skull.setOwner(player.getName());
        item.setItemMeta(skull);
        return item;
    }

}
