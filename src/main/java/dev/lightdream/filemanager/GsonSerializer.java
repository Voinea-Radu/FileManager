package dev.lightdream.filemanager;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

@SuppressWarnings("unused")
public interface GsonSerializer<T> extends JsonSerializer<T>, JsonDeserializer<T> {

    Class<T> getClazz();

    @SuppressWarnings("unused")
    default void register(GsonSettings settings) {
        settings.gsonBuilder()
                .registerTypeAdapter(getClazz(), this);

        settings.update();
    }

}
