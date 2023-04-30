### Create the main

```java
public class ExampleMain implements FileManagerMain {

    public ExampleMain(GsonBuilder builder) {
        FileManager fileManager = new FileManager(this);

        // If you have any custom serializers you can use the FileManager#setGsonBuilder method
        fileManager.setGsonBuilder(builder);

        // If you want the FileManager instance to be accessible from a static context you can sue
        fileManager.setStatic();
        FileManager fileManagerFromStatic = FileManager.get();
    }

    @Override
    public String getPath() {
        return "config/Example";
    }
}

```

### Working with the file manager

You can use any class that is serializable by JSON.

```java
public class Foo {
    private int property1;
    private String property2;
}
```

To save or load the class from or to disk you can use the `FileManager#save` and `FileManager#load` methods

```java
public class ExampleClass {
    public void save(FileManager fileManager) {
        Foo bar = new Foo();
        fileManager.save(bar);
    }

    public void load(FileManager fileManager) {
        Foo bar = fileManager.load(Foo.class);
    }
}

```