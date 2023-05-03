package dev.lightdream.filemanager;

import dev.lightdream.logger.Logger;

public interface IConfig {

    default void save() {
        if (FileManager.get() == null) {
            Logger.warn("IConfig#save was called but FileManager#setStatic was not.");
            return;
        }

        FileManager.get().save(this);
    }

}
