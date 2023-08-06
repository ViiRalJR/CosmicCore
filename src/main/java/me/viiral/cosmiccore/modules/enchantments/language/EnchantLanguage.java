package me.viiral.cosmiccore.modules.enchantments.language;

import com.google.common.collect.ImmutableList;
import com.massivecraft.factions.shade.apache.StringUtils;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public enum EnchantLanguage {

    SOUL_MODE_ENABLE,
    SOUL_MODE_DISABLE,
    OUT_OF_SOULS,
    CLICKED_WRONG_SOUL_GEM,
    SOUL_DRAIN_MESSAGE,

    INVALID_ENCHANTMENT_NAME,
    INVALID_ENCHANTMENT_LEVEL,
    EMPTY_HAND,
    ALREADY_HAS_ENCHANTMENT,
    DOESNT_HAVE_REQUIRED,
    DOESNT_HAVE_ANY_ENCHANTMENTS,

    NE_COMMAND_HELP,

    ADMIN_RELOADING,
    ADMIN_RELOADED,

    EQUIP_POTION_ENCHANT,
    UNEQUIP_POTION_ENCHANT,

    WEAK_SHOT,
    INVALID_ITEM_TYPE_FOR_ENCHANT,
    NOT_ENOUGH_SLOTS,
    ITEM_PROTECTED_BY_WHITE_SCROLL,

    SUCCESSFULLY_RENAMED,
    RENAME_TAG_CLICKED,

    BOOK_OPEN,
    BOOK_RIGHT_CLICK,

    FULL_INVENTORY,

    KIT_RECEIVED,
    KIT_COOLDOWN,
    KIT_INVALID_NAME,
    KIT_NO_ACCESS,

    SOUL_TRACKER_APPLIED,
    ALREADY_HAS_SOUL_TRACKER,
    SOUL_HARVEST_COOLDOWN,
    DOESNT_HAVE_SOUL_TRACKER,

    REMOVE_EXP,
    NOT_ENOUGH_EXP,

    ;

    private String value;
    private ImmutableList<String> valueList;

    public String getValue() {
        return this.value != null ? this.value : this.name();
    }

    public String getValue(UnaryOperator<String> function) {
        return this.value != null ? function.apply(this.value) : this.name();
    }

    public String getPath() {
        return this.name();
    }

    public List<String> getValuesAsList() {
        return this.valueList;
    }

    public List<String> getValuesAsList(String oldString, String newString) {
        List<String> list = new ArrayList<>(this.valueList);
        list.replaceAll(s -> s.replace(oldString, newString));
        return list;
    }

    void setValue(String value) {
        this.value = CC.translate(value);

        List<String> tempList = Arrays.stream(StringUtils.split(value, System.lineSeparator()))
                .map(CC::translate)
                .collect(Collectors.toList());
        this.valueList = ImmutableList.copyOf(tempList);
    }

    void setValue(List<String> valueList) {
        this.value = CC.translate(valueList.parallelStream().collect(Collectors.joining(System.lineSeparator())));

        List<String> tempList = valueList.stream()
                .map(CC::translate)
                .collect(Collectors.toList());
        this.valueList = ImmutableList.copyOf(tempList);
    }

    public void send(CommandSender sender, UnaryOperator<String> function) {
        sender.sendMessage(CC.translate(this.getValue(function)));
    }

    public void send(CommandSender sender) {
        sender.sendMessage(CC.translate(this.getValue()));
    }


    public void send(List<CommandSender> senders, UnaryOperator<String> function) {
        String msg = CC.translate(this.getValue(function));
        senders.forEach(sender -> sender.sendMessage(msg));
    }

    public void sendPlayers(List<Player> senders, UnaryOperator<String> function) {
        String msg = CC.translate(this.getValue(function));
        senders.forEach(sender -> sender.sendMessage(msg));
    }

    public void send(CommandSender ...senders) {
        String msg = CC.translate(this.getValue());
        Arrays.asList(senders).forEach(sender -> sender.sendMessage(msg));
    }

    public String getMessage() {
        return CC.translate(this.getValue());
    }

    public String getMessage(UnaryOperator<String> function) {
        return CC.translate(this.getValue(function));
    }

}
