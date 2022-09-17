package dev.lightdream.filemanager.example;

import dev.lightdream.filemanager.FileManager;
import dev.lightdream.filemanager.FileManagerMain;

import java.io.File;

public class Example implements FileManagerMain {
    private FileManager fileManager;
    private Object object;

    public Example() {
        enable();
    }

    public void enable() {
        this.fileManager = new FileManager(this);
        load();
    }

    public void load() {
        object = fileManager.load(Object.class);
    }

    @Override
    public File getDataFolder() {
        return new File(System.getProperty("user.dir"));
    }

    public class Object {
        public String name = "Example";
    }
}
