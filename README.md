# LightDream FileManager

```xml
<repositories>
    <repository>
        <id>lightdream-repo</id>
        <url>https://repo.lightdream.dev/repository/LightDream-API/</url>
    </repository>
    <!-- Other repositories -->
</repositories>
```

```xml
<dependencies>
    <dependency>
        <groupId>dev.lightdream</groupId>
        <artifactId>FileManager</artifactId>
        <version>VERSION</version>
    </dependency>
    <!-- Other dependencies -->
</dependencies>
```

## Creating a DatabaseManager Implementation


## Example Main
```java
public class Example implements FileManagerMain {

    private FileManager fileManager;

    public Example() {
        enable();
    }

    public void enable() {
        this.fileManager = new FileManager(this, FileManager.PersistType.YAML);
    }

    @Override
    File getDataFolder(){
        return new File("path/to/your/working/directory");
    }
}
```


