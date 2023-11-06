package dev.lightdream.filemanager;

@SuppressWarnings("unused")
public interface IConfig {

    default void save() {
        FileManager.save(this);
    }

}
