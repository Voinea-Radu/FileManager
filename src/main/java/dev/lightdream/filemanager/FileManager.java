package dev.lightdream.filemanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.lightdream.logger.Logger;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.*;

@SuppressWarnings("unused")
public class FileManager {

    private static FileManager staticInstance;

    private final File dataFolder;

    private @Setter String extension = ".json";
    private Gson gson;
    private GsonBuilder gsonBuilder;
    private boolean debug = false;

    public FileManager(File dataFolder) {
        this.dataFolder = dataFolder;
        this.gsonBuilder = new GsonBuilder()
                .setPrettyPrinting();
        reload();
    }

    public FileManager(FileManagerMain main) {
        this(main.getDataFolder());
    }

    public FileManager(File dataFolder, GsonBuilder gsonBuilder) {
        this.dataFolder = dataFolder;
        this.gsonBuilder = gsonBuilder;
    }

    public FileManager(FileManagerMain main, GsonBuilder gsonBuilder) {
        this(main.getDataFolder(), gsonBuilder);
    }

    /**
     * @return The static instance of the FileManager or null if {@link #setStatic()} was not called
     */
    public static FileManager get() {
        return FileManager.staticInstance;
    }

    /**
     * Sets the static instance of the FileManager
     */
    public void setStatic() {
        FileManager.staticInstance = this;
    }

    public void enableDebugging() {
        debug = true;
    }

    private void reload() {
        this.gson = gsonBuilder.create();
    }

    public GsonBuilder getGsonBuilder() {
        return gsonBuilder;
    }

    public void setGsonBuilder(GsonBuilder gsonBuilder) {
        this.gsonBuilder = gsonBuilder;
        reload();
    }

    public void save(Object object) {
        save(object, object.getClass().getSimpleName().toLowerCase());
    }

    @SneakyThrows
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void save(Object object, String name) {
        String json = gson.toJson(object);
        String path = dataFolder + "/" + name + extension;

        //Create folders
        new File(path).getParentFile().mkdirs();

        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(json);

        writer.close();
    }

    public <T> T load(Class<T> clazz) {
        return load(clazz, clazz.getSimpleName().toLowerCase());
    }

    @SneakyThrows
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public <T> T load(Class<T> clazz, String name) {
        try {
            StringBuilder json = new StringBuilder();
            String path = dataFolder + "/" + name + extension;

            //Create folders
            new File(path).getParentFile().mkdirs();

            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

            String curLine;
            while ((curLine = bufferedReader.readLine()) != null) {
                json.append(curLine).append("\n");
            }
            bufferedReader.close();

            return gson.fromJson(json.toString(), clazz);
        } catch (Exception e) {
            Logger.warn("Could not load " + clazz.getSimpleName() + ". Creating and saving new instance.");
            if (debug) {
                e.printStackTrace();
            }
            T obj = clazz.getDeclaredConstructor().newInstance();
            save(obj, name);
            return obj;
        }
    }

}
