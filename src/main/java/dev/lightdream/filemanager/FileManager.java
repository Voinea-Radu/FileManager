package dev.lightdream.filemanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.lightdream.lambda.LambdaExecutor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

@SuppressWarnings("unused")
public class FileManager {

    private final static String extension = ".json";
    private final FileManagerMain main;
    private final Gson gson;

    public FileManager(FileManagerMain main) {
        this.main = main;
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    public FileManager(FileManagerMain main, Gson gson) {
        this.main = main;
        this.gson = gson;
    }

    public void save(Object object) {
        LambdaExecutor.LambdaCatch.NoReturnLambdaCatch.executeCatch(() -> {
            String json = gson.toJson(object);
            String path = main.getDataFolder() + "/" + object.getClass().getSimpleName().toLowerCase() + extension;

            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(json);

            writer.close();
        });
    }

    public <T> T load(Class<T> clazz) {
        return LambdaExecutor.LambdaCatch.ReturnLambdaCatch.executeCatch(() -> {
            String json = "";
            String path = main.getDataFolder() + "/" + clazz.getSimpleName().toLowerCase() + extension;

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
