package dev.lightdream.filemanager;

import dev.lightdream.lambda.LambdaExecutor;
import dev.lightdream.logger.LoggableMain;

import java.io.File;

public interface FileManagerMain {

    @SuppressWarnings("unused")
    static String getVersion() {
        return "FileManager " + FileManagerMain.class.getPackage().getImplementationVersion() +
                "    -> " + LoggableMain.getVersion() +
                "    -> " + LambdaExecutor.getVersion();
    }

    File getDataFolder();

}
