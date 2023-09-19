package me.viiral.cosmiccore.modules.mask.struct.item;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import me.viiral.cosmiccore.modules.enchantments.struct.items.CustomItem;
import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.modules.mask.struct.MaskRegister;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import me.viiral.cosmiccore.utils.items.SkullUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class MaskBuilder extends CustomItem {

    private static final String MASK_FORMAT = "&f&lMulti-Mask (";
    private static final String NBT_SEPARATOR = "@;;";
    private static final Material MASK_MATERIAL = Material.SKULL_ITEM;

    public MaskBuilder(ItemStack itemStack) {
        super("maskType");
        this.nbtItem = new NBTItem(itemStack);
    }

    public MaskBuilder(Mask mask) {
        super("maskType");
        createNBTItem(Collections.singletonList(mask));
    }

    public MaskBuilder(List<Mask> masks) {
        super("maskType");
        createNBTItem(masks);
    }

    private void createNBTItem(List<Mask> masks) {
        int masksLength = masks.size();

        StringBuilder name = new StringBuilder();
        List<String> lore = new ArrayList<>();
        StringBuilder nbt = new StringBuilder();
        name.append(MASK_FORMAT);

        for (int i = 0; i < masksLength; i++) {
            Mask mask = masks.get(i);
            name.append(mask.getColor())
                    .append(ChatColor.BOLD)
                    .append(mask.getName());

            if (i != masksLength - 1) name.append(", ");
            else name.append(ChatColor.WHITE).append(ChatColor.BOLD).append(")");

            if (masksLength > 1) lore.add(mask.getColor() + ChatColor.BOLD + mask.getName().toUpperCase() + " MASK");
            lore.addAll(mask.getLore());
            lore.add("&7");
            nbt.append(mask.getID()).append(NBT_SEPARATOR);
        }

        ItemStack itemStack = new ItemBuilder(Objects.requireNonNull(SkullUtils.getSkullFromSkin("eyJ0aW1lc3RhbXAiOjE1NjIwODg5MjU2NDUsInByb2ZpbGVJZCI6IjdjODk1YWQwMTFkMDQzNTA5YWU1ZjJiYjFjZjZjOGVhIiwicHJvZmlsZU5hbWUiOiJCYXNpY0JBRSIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83M2UxYWQ0ZmY0ZDhlMjM5YmJlYTUyNGIyNGM0ZDRkNWQ4NjIzZTUyZDdkOGVlNzllNmE1ZjhjN2FkZWRiOWEzIn19fQ==")))
                .setName(name.toString())
                .addLore("&7This mask contains the powers of:", "")
                .addLore(lore)
                .addLore("&7To equip, place this mask on a helmet.", "&7To remove, right-click helmet while attached.")
                .colorize()
                .build();


        NBTItem nbtItem = new NBTItem(itemStack);
        this.nbtItem = nbtItem; // needed to avoid null pointer
        nbtItem.setString("randomUUID", UUID.randomUUID().toString());

        super.applyData(nbt.toString());

        this.nbtItem = nbtItem;
    }

    public List<Mask> getMasks() {
        String maskString = Optional.ofNullable(this.nbtItem.getOrCreateCompound("cosmicData").getString("maskType")).orElse("");
        return Arrays.stream(maskString.split(NBT_SEPARATOR))
                .map(s -> MaskRegister.getInstance().getMaskFromID(s))
                .collect(Collectors.toList());
    }

    public ItemStack build() {
        return this.nbtItem.getItem();
    }
}





