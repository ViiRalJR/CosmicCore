package me.viiral.cosmiccore.modules.skins.struct.item;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import me.viiral.cosmiccore.modules.enchantments.struct.items.CustomItem;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinRegister;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class SkinBuilder extends CustomItem {

    private static final String SKIN_FORMAT = "&f&lItem Skin (";
    private static final String NBT_SEPARATOR = "@;;";
    private static final Material SKIN_MATERIAL = Material.EYE_OF_ENDER;

    public SkinBuilder(ItemStack itemStack) {
        super("skinType");
        this.nbtItem = new NBTItem(itemStack);
    }

    public SkinBuilder(List<Skin> skins) {
        super("skinType");
        createNBTItem(skins);
    }

    private void createNBTItem(List<Skin> skins) {
        int skinsLength = skins.size();

        StringBuilder name = new StringBuilder();
        List<String> lore = new ArrayList<>();
        StringBuilder nbt = new StringBuilder();
        name.append(SKIN_FORMAT);

        for (int i = 0; i < skinsLength; i++) {
            Skin skin = skins.get(i);
            name.append(skin.getColor())
                    .append(ChatColor.BOLD)
                    .append(skin.getName());

            if (i != skinsLength - 1) name.append(", ");
            else name.append(ChatColor.WHITE).append(ChatColor.BOLD).append(")");

            if (skinsLength == 1) {
                lore.addAll(skin.getLore());
            } else {
                lore.add("&7");
                lore.add(skin.getColor() + CC.Bold + skin.getName());
                lore.addAll(skin.getLore());
            }
            nbt.append(skin.getID()).append(NBT_SEPARATOR);
        }

        ItemStack itemStack = new ItemBuilder(SKIN_MATERIAL)
                .setName(name.toString())
                .addLore(lore)
                .addLore("",
                        "&7Attach this skin to any " + skins.get(0).getApplicable().getLabel(),
                        "&7to override its visual appearance.",
                        "",
                        "&7Drag n' Drop onto item to attach.",
                        "&7Right-Click item to detach skin."
                        )
                .colorize()
                .build();

        NBTItem nbtItem = new NBTItem(itemStack);
        this.nbtItem = nbtItem;
        nbtItem.setString("randomUUID", UUID.randomUUID().toString());
        nbtItem.setString("applicable", skins.get(0).getApplicable().toString());

        super.applyData(nbt.toString());
        this.nbtItem = nbtItem;
    }

    public List<Skin> getSkins() {
        String skinString = Optional.ofNullable(this.nbtItem.getOrCreateCompound("cosmicData").getString("skinType")).orElse("");
        return Arrays.stream(skinString.split(NBT_SEPARATOR))
                .map(s -> SkinRegister.getInstance().getSkinFromID(s))
                .collect(Collectors.toList());
    }

    public SkinType getApplicable() {
        return SkinType.valueOf(this.nbtItem.getString("applicable"));
    }


    public ItemStack build() {
        return this.nbtItem.getItem();
    }
}
