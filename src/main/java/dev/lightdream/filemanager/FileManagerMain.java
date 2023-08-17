package dev.lightdream.filemanager;

import java.io.File;

public interface FileManagerMain {

    /**
     * @return The exact file location of where to save the json files
     */
    default File getDataFolder() {
        String path = getPath();
        if (!path.startsWith("/")) {
            path = "/" + path;
        }

        return new File(System.getProperty("user.dir") + path);
    }

    /**
     * @return The relative path from the jar working directory in which to save the json files.
     */
    String getPath();

    /**
     * @return Whether to complete missing after loading a file
     */
    default  boolean autoCompleteConfig(){
        return false;
    }

}
