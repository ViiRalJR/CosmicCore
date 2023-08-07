package me.viiral.cosmiccore.conf;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.SneakyThrows;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.conf.struct.Conf;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * @Author: Driftay
 * @Date: 8/7/2023 12:01 AM
 */
@Getter
public class ConfigurationService {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .serializeNulls()
            .enableComplexMapKeySerialization()
            .setLenient()
            .create();

    private final Path dataDirectory;

    public ConfigurationService() {
        dataDirectory = CosmicCore.getInstance().getDataFolder().toPath();
        init();
    }

    private final Map<Class<?>, Object> configurations = new IdentityHashMap<>(1);

    @SneakyThrows
    private Map<Class<?>, Path> getConfigFiles() {
        Path parent = dataDirectory.resolve("data");
        Map<Class<?>, Path> paths = new IdentityHashMap<>(1);
        paths.put(Conf.class, parent.resolve("conf.json"));
        return paths;
    }


    @SneakyThrows
    public void init() {
        Map<Class<?>, Path> files = getConfigFiles();
        for (Map.Entry<Class<?>, Path> entry : files.entrySet()) {
            Class<?> clazz = entry.getKey();
            Path path = entry.getValue();
            if (!path.toFile().exists()) {
                path.toFile().createNewFile();
                FileUtils.write(path.toFile(), GSON.toJson(clazz.newInstance()), StandardCharsets.UTF_8);
            }
            try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                this.configurations.put(clazz, GSON.fromJson(reader, clazz));
            }
        }
    }

    public void saveAllFiles() throws IOException {
        Map<Class<?>, Path> files = getConfigFiles();
        for (Map.Entry<Class<?>, Path> entry : files.entrySet()) {
            save(entry.getKey());
        }
    }

    public void saveSpecificFile(Class<?> clazz) throws IOException {
        Object configuration = this.configurations.get(clazz);
        if (configuration == null) {
            throw new IllegalArgumentException("Missing JSON mapping: " + clazz.getName());
        }

        Path path = getConfigFiles().get(clazz);
        if (path == null) {
            throw new IllegalArgumentException("Missing Path for JSON mapping: " + clazz.getName());
        }

        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            GSON.toJson(configuration, writer);
        }
    }

    public void save(Class<?> clazz) throws IOException {
        Object configuration = this.configurations.get(clazz);
        if (configuration == null) {
            throw new IllegalArgumentException("Missing JSON mapping: " + clazz.getName());
        }

        Path path = getConfigFiles().get(clazz);
        if (path == null) {
            throw new IllegalArgumentException("Missing Path for JSON mapping: " + clazz.getName());
        }

        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            GSON.toJson(configuration, writer);
        }
    }

    public <T> T getConfiguration(Class<T> clazz) {
        return clazz.cast(this.configurations.get(clazz));
    }

}
