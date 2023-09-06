package dev.lightdream.filemanager;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import org.jetbrains.annotations.NotNull;

public interface GsonSerializer<T> extends JsonSerializer<T>, JsonDeserializer<T> {

    Class<T> getClazz();

    default void register(@NotNull FileManager fileManager) {
        fileManager.setGsonBuilder(
                fileManager
                        .getGsonBuilder()
                        .registerTypeAdapter(getClazz(), this)
        );
    }

}
