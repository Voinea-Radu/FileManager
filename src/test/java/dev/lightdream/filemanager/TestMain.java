package dev.lightdream.filemanager;

public class TestMain implements FileManagerMain {

    public TestMain() {
        FileManager fileManager = new FileManager(this);
        fileManager.setStatic();
    }

    @Override
    public String getPath() {
        return "tmp-config";
    }
}
