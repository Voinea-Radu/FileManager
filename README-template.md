# FileManager

![Build](../../actions/workflows/build.yml/badge.svg)
![Version](https://img.shields.io/badge/Version-${{ env.VERSION }}-red.svg)

A library that allows you to map java objects to json and read them back from disk back into a java object.

## Use

### Maven

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
        <artifactId>${{ env.ARTIFACT }}</artifactId>
        <version>${{ env.VERSION }}</version>
    </dependency>
    <!-- Other dependencies -->
</dependencies>
```

### Gradle

```groovy
repositories {
    maven { url "https://repo.lightdream.dev/repository/LightDream-API/" }

    // Other repositories
}

dependencies {
    implementation "dev.lightdream:${{ env.ARTIFACT }}:${{ env.VERSION }}"

    // Other dependencies
}
```

## Example

Can be found in the [source code](/src/main/java/dev/lightdream/${{ env.PACKAGE }}/example)
