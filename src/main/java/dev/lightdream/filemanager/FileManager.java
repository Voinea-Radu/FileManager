package dev.lightdream.filemanager;

import com.google.gson.GsonBuilder;
import dev.lightdream.logger.Logger;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class FileManager {

    private static @Getter FileManager instance;

    static {
        builder().build();
    }

    private GsonSettings gsonSettings;
    private boolean autoCompleteConfig;
    private @NotNull String path;
    private @NotNull String extension;

    @lombok.Builder(builderClassName = "Builder")
    public FileManager(GsonSettings gsonSettings, boolean autoCompleteConfig, @NotNull String path, @NotNull String extension) {
        instance = this;

        this.gsonSettings = gsonSettings;
        this.autoCompleteConfig = autoCompleteConfig;
        this.path = path;
        this.extension = extension;
    }

    public static void save(Object object) {
        Class<?> clazz = object.getClass();
        Saveable saveable = null;

        if (clazz.isAnnotationPresent(Saveable.class)) {
            saveable = clazz.getAnnotation(Saveable.class);
        }

        String directory = saveable == null ? "" : saveable.directory();
        String fileName = saveable == null ? toSnakeCase(clazz.getSimpleName()) :
                saveable.fileName().isEmpty() ?
                        toSnakeCase(clazz.getSimpleName()) : saveable.fileName();

        save(object, directory, fileName);
    }

    private static @NotNull String toSnakeCase(@NotNull String string) {
        String output = string.replaceAll("([A-Z])", "_$1").toLowerCase();

        if (output.startsWith("_")) {
            output = output.substring(1);
        }

        return output;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SneakyThrows
    public static void save(@NotNull Object object, @NotNull String directory, @NotNull String fileName) {
        String json = instance.gsonSettings().gson().toJson(object);

        if (!fileName.endsWith(instance.extension)) {
            fileName += instance.extension;
        }

        Path path = Paths.get(instance.getDataFolder().getPath(), directory, fileName);

        //Create folders
        File file = path.toFile();
        file.getParentFile().mkdirs();

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(json);

        writer.close();
    }

    /**
     * Loads an object form a json file on the disk.
     *
     * @param clazz The class of the object
     * @param <T>   The object type
     * @return The loaded object
     */
    public static <T> @NotNull T load(@NotNull Class<T> clazz) {
        Saveable saveable = null;

        if (clazz.isAnnotationPresent(Saveable.class)) {
            saveable = clazz.getAnnotation(Saveable.class);
        }

        String directory = saveable == null ? "" : saveable.directory();
        String fileName = saveable == null ? toSnakeCase(clazz.getSimpleName()) :
                saveable.fileName().isEmpty() ?
                        toSnakeCase(clazz.getSimpleName()) : saveable.fileName();

        return load(clazz, directory, fileName);
    }

    /**
     * Loads an object form a json file on the disk.
     *
     * @param clazz     The class of the object
     * @param directory Target directory
     * @param fileName  File name
     * @param <T>       The object type
     * @return The loaded object
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SneakyThrows
    public static <T> @NotNull T load(@NotNull Class<T> clazz, @NotNull String directory,
                                      @NotNull String fileName) {
        try {
            if (!fileName.endsWith(instance.extension)) {
                fileName += instance.extension;
            }

            Path path = Paths.get(instance.getDataFolder().getPath(), directory, fileName);

            //Create folders
            File file = path.toFile();
            file.getParentFile().mkdirs();

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            StringBuilder json = new StringBuilder();

            String curLine;
            while ((curLine = bufferedReader.readLine()) != null) {
                json.append(curLine).append("\n");
            }
            bufferedReader.close();

            T output = instance.gsonSettings().gson().fromJson(json.toString(), clazz);

            if (instance.autoCompleteConfig()) {
                save(output);
            }

            return output;
        } catch (Exception e) {
            Logger.warn("Could not load " + clazz.getSimpleName() + ". Creating and saving new instance.");
            T obj = clazz.getDeclaredConstructor().newInstance();
            save(obj, directory, fileName);
            return obj;
        }
    }

    public static Builder builder() {
        return new Builder()
                .gsonSettings(new GsonSettings(
                        new GsonBuilder()
                                .setPrettyPrinting()
                ))
                .autoCompleteConfig(false)
                .path("")
                .extension(".json");
    }

    public @NotNull File getDataFolder() {
        String path = path();
        if (!path.startsWith("/")) {
            path = "/" + path;
        }

        return new File(System.getProperty("user.dir") + path);
    }

}
