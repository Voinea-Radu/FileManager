package dev.lightdream.filemanager;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

public interface GsonSerializer<T> extends JsonSerializer<T>, JsonDeserializer<T> {

    Class<T> getClazz();

    default void register(FileManager fileManager) {
        fileManager.setGsonBuilder(
                fileManager
                        .getGsonBuilder()
                        .registerTypeAdapter(getClazz(), this)
        );
    }

}
