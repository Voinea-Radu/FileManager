package dev.lightdream.filemanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
@Getter
@Setter
@Accessors(fluent = true)
public class GsonSettings {

    private @NotNull GsonBuilder gsonBuilder = new GsonBuilder();
    private Gson gson = gsonBuilder.create();

    public void update() {
        this.gson = gsonBuilder.create();
    }

}
