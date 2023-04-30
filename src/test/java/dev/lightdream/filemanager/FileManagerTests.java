package dev.lightdream.filemanager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileManagerTests {

    @BeforeAll
    public static void init() {
        new TestMain();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @AfterAll
    public static void cleanup() {
        File file1 = new File(System.getProperty("user.dir") + "/tmp-config/testobject.json");
        File file2 = new File(System.getProperty("user.dir") + "/tmp-config");

        if (file1.exists()) {
            file1.delete();
        }

        if (file2.exists()) {
            file2.delete();
        }
    }

    @Test
    public void testObjectSaveLoad() {
        TestObject object = new TestObject(100, "test");

        FileManager.get().save(object);

        TestObject loadedObject = FileManager.get().load(TestObject.class);

        assertEquals(object.data1, loadedObject.data1);
        assertEquals(object.data2, loadedObject.data2);
    }


}
