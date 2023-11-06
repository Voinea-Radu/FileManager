package dev.lightdream.filemanager;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

public interface GsonSerializer<T> extends JsonSerializer<T>, JsonDeserializer<T> {

    Class<T> getClazz();

    @SuppressWarnings("unused")
    default void register() {
        if (FileManager.get() == null) {
            return;
        }

        FileManager.get().registerSerializer(this);
    }

}
