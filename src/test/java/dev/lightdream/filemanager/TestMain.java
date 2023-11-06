package dev.lightdream.filemanager;

import lombok.Getter;

@Getter
public class TestMain {

    public TestMain() {
        new FileManager.Settings()
                .path("tmp-config")
                .build();
    }
}

