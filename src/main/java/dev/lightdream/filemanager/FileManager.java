package dev.lightdream.filemanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.lightdream.lambda.LambdaExecutor;

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

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void save(Object object, String name) {
        LambdaExecutor.LambdaCatch.NoReturnLambdaCatch.executeCatch(() -> {
            String json = gson.toJson(object);
            String path = main.getDataFolder() + "/" + name + extension;

            //Create folders
            new File(path).getParentFile().mkdirs();

            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(json);

            writer.close();
        });
    }

    public <T> T load(Class<T> clazz) {
        return load(clazz, clazz.getSimpleName().toLowerCase());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public <T> T load(Class<T> clazz, String name) {
        return LambdaExecutor.LambdaCatch.ReturnLambdaCatch.executeCatch(() -> {
            String json = "";
            String path = main.getDataFolder() + "/" + name + extension;

            //Create folers
            new File(path).getParentFile().mkdirs();

            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

            String curLine;
            while ((curLine = bufferedReader.readLine()) != null) {
                json += curLine + "\n";
            }
            bufferedReader.close();

            return gson.fromJson(json, clazz);
        }, e -> {
            T obj = LambdaExecutor.LambdaCatch.ReturnLambdaCatch.executeCatch(clazz::newInstance);
            save(obj);
            return obj;
        });
    }


}
