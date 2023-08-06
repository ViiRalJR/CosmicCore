package me.viiral.cosmiccore.modules.enchantments.gkits.users;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import me.viiral.cosmiccore.modules.enchantments.gkits.Gkit;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class GkitUser {

    @Expose
    @SerializedName("CooldownMap")
    private final JsonObject cooldownMap = new JsonObject();
    private UUID uuid;

    public GkitUser(Map<String,Long> map) {
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            cooldownMap.addProperty(entry.getKey(),entry.getValue());
        }
    }

    void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public long getLastKitUsage(Gkit kit) {
        JsonElement value = cooldownMap.get(kit.getID());
        return value == null ? 0L : value.getAsLong();
    }

    public boolean hasCooldownOnKit(Gkit kit) {
        long claimedTime = getLastKitUsage(kit);
        long currentTime = System.currentTimeMillis();
        long difference = currentTime - claimedTime;
        long cooldown = kit.getCooldown();
        return difference < cooldown;
    }

    public void newCooldownOnKit(Gkit kit) {
        cooldownMap.addProperty(kit.getID(),System.currentTimeMillis());
    }

    public long getSecondsTillKitAvailable(Gkit kit) {
        long claimedTimeStamp = getLastKitUsage(kit);
        long currentTimeStamp = System.currentTimeMillis();
        long difference = currentTimeStamp - claimedTimeStamp;
        long cooldown = kit.getCooldown();
        return difference >= cooldown ? 0L :
                TimeUnit.MILLISECONDS.toSeconds(cooldown-difference);
    }

    public JsonObject getCooldownMap() {
        return cooldownMap;
    }
}
