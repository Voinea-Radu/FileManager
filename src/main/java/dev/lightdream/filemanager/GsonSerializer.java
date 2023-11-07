package dev.lightdream.filemanager;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

@SuppressWarnings("unused")
public interface GsonSerializer<T> extends JsonSerializer<T>, JsonDeserializer<T> {

    Class<T> getClazz();

    @SuppressWarnings("unused")
    default void register() {
        FileManager.instance()
                .gsonSettings()
                .gsonBuilder()
                .registerTypeAdapter(getClazz(), this);

        FileManager.instance()
                .gsonSettings()
                .update();
    }

}
