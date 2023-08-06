package me.viiral.cosmiccore.modules.enchantments.gkits.users;

import me.viiral.cosmiccore.CosmicCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

public class GkitUserManager {

    private final Map<UUID, GkitUser> userMap = new ConcurrentHashMap<>();
    private final CosmicCore plugin;
    private final File dataDir;

    public GkitUserManager(CosmicCore plugin) {
        this.plugin = plugin;
        dataDir = new File(plugin.getDataFolder(),"users/");
    }

    public void loadAll() {
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            getUserOrCreate(player.getUniqueId());
        }
    }

    public void saveAll() {
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            save(player.getUniqueId()).join();
        }
    }

    public CompletableFuture<GkitUser> loadUser(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            File file = new File(dataDir,uuid+".json");
            try (FileReader reader = new FileReader(file)) {
                GkitUser user = plugin.getGson().fromJson(reader, GkitUser.class);
                if (user == null) {
                    return null;
                }
                user.setUuid(uuid);
                return user;
            } catch (IOException e) {
                throw new CompletionException(e);
            }
        });
    }

    public CompletableFuture<GkitUser> loadCacheUser(UUID uuid) {
        return this.loadUser(uuid)
                .thenApply(user -> {
                    if (user != null) {
                        userMap.put(uuid, user);
                    }
                    return user;
                });
    }

    public GkitUser getUser(UUID uuid) {
        GkitUser user = userMap.get(uuid);
        if (user == null) {
            try {
                return this.loadCacheUser(uuid).get();
            } catch (InterruptedException | ExecutionException e) {
                return null;
            }
        }
        return user;
    }

    public GkitUser getUserOrCreate(UUID uuid) {
        GkitUser user = this.getUser(uuid);
        if (user == null) {
            user = new GkitUser(new HashMap<>());
            userMap.put(uuid,user);
        }
        return user;
    }

    public CompletableFuture<Void> save(UUID uuid) {
        return CompletableFuture.runAsync(() -> {
            File file = new File(dataDir,uuid + ".json");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            GkitUser user = getUserOrCreate(uuid);
            String jsonString = plugin.getGson().toJson(user);
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(jsonString);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
