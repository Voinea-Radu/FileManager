package dev.lightdream.filemanager;

import dev.lightdream.lambda.LambdaExecutor;
import dev.lightdream.logger.LoggableMain;

import java.io.File;

public interface FileManagerMain {

    @SuppressWarnings({"unused", "StringConcatenationInLoop"})
    static String getVersion(int tabs) {
        String output = "FileManager 2.0.7\n";
        String prepend = "";

        for (int i = 0; i < tabs; i++) {
            prepend += "    ";
        }

        output += prepend + "    -> " + LoggableMain.getVersion() + "\n";
        output += prepend + "    -> " + LambdaExecutor.getVersion() + "\n";

        return output;
    }

    File getDataFolder();

}
