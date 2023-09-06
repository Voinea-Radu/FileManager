package dev.lightdream.filemanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.lightdream.logger.Logger;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings("unused")
public class FileManager {

    private static FileManager staticInstance;

    private final FileManagerMain main;

    private @Setter String extension = ".json";
    private Gson gson;
    @Getter
    private GsonBuilder gsonBuilder;
    private boolean debug = false;

    public FileManager(FileManagerMain main) {
        this(main, new GsonBuilder().setPrettyPrinting());
    }

    public FileManager(FileManagerMain main, GsonBuilder gsonBuilder) {
        this.main = main;
        this.gsonBuilder = gsonBuilder;
        reload();
    }

    public static @Nullable FileManager get() {
        return FileManager.staticInstance;
    }

    public void setStatic() {
        FileManager.staticInstance = this;
    }

    public void registerSerializer(GsonSerializer<?> serializer){
        serializer.register(this);
    }

    public void enableDebugging() {
        debug = true;
    }

    private void reload() {
        this.gson = gsonBuilder.create();
    }

    public void setGsonBuilder(GsonBuilder gsonBuilder) {
        this.gsonBuilder = gsonBuilder;
        reload();
    }

    public void save(Object object) {
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

    private @NotNull String toSnakeCase(@NotNull String string) {
        String output = string.replaceAll("([A-Z])", "_$1").toLowerCase();

        if (output.startsWith("_")) {
            output = output.substring(1);
        }

        return output;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SneakyThrows
    public void save(@NotNull Object object, @NotNull String directory, @NotNull String fileName) {
        String json = gson.toJson(object);

        if (!fileName.endsWith(extension)) {
            fileName += extension;
        }

        Path path = Paths.get(main.getDataFolder().getPath(), directory, fileName);

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
    public <T> @NotNull T load(@NotNull Class<T> clazz) {
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
    public <T> @NotNull T load(@NotNull Class<T> clazz, @NotNull String directory,
                               @NotNull String fileName) {
        try {
            if (!fileName.endsWith(extension)) {
                fileName += extension;
            }

            Path path = Paths.get(main.getDataFolder().getPath(), directory, fileName);

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

            T output = gson.fromJson(json.toString(), clazz);

            if (main.autoCompleteConfig()) {
                save(output);
            }

            return output;
        } catch (Exception e) {
            Logger.warn("Could not load " + clazz.getSimpleName() + ". Creating and saving new instance.");
            if (debug) {
                //noinspection CallToPrintStackTrace
                e.printStackTrace();
            }
            T obj = clazz.getDeclaredConstructor().newInstance();
            save(obj, directory, fileName);
            return obj;
        }
    }


}
