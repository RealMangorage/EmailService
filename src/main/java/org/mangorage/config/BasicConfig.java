package org.mangorage.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.mangorage.general.Utils;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class BasicConfig<T extends IConfig> {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .setLenient()
            .create();

    public static <T extends IConfig> BasicConfig<T> of(String configName, Class<T> configClass, T defaultValue) {
        return new BasicConfig<>(configName, configClass, defaultValue);
    }


    private final Class<T> tClass;
    private final Path path;

    private BasicConfig(String configName, Class<T> tClass, T defaulValue) {
        this.tClass = tClass;
        this.path = Path.of("configs/" + configName + ".json").toAbsolutePath();

        if (!Files.exists(path)) {
            Utils.writeToFile(GSON.toJson(defaulValue), path);
        }
    }

    public T get() {
        try (var fileReader = new FileReader(path.toFile()); var reader = new JsonReader(fileReader)) {
            return GSON.fromJson(reader, tClass);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
