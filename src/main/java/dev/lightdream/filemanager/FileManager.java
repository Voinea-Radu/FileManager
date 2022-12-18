package dev.lightdream.filemanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.lightdream.logger.Debugger;
import dev.lightdream.logger.Logger;
import lombok.SneakyThrows;

import java.io.*;

@SuppressWarnings("unused")
public class FileManager {

    private final static String extension = ".json";
    private final FileManagerMain main;
    private Gson gson;
    private GsonBuilder gsonBuilder;

    public FileManager(FileManagerMain main) {
        this.main = main;
        this.gsonBuilder = new GsonBuilder()
                .setPrettyPrinting();
        reload();
    }

    public FileManager(FileManagerMain main, GsonBuilder gsonBuilder) {
        this.main = main;
        this.gsonBuilder = gsonBuilder;
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
        String path = main.getDataFolder() + "/" + name + extension;

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
            String path = main.getDataFolder() + "/" + name + extension;

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
            if (Debugger.isLowLevelDebuggingEnabled()) {
                e.printStackTrace();
            }
            T obj = clazz.newInstance();
            save(obj);
            return obj;
        }
    }


}
