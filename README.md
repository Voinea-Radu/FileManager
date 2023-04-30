# FileManager

![Build](../../actions/workflows/build.yml/badge.svg)
![Version](https://img.shields.io/badge/Version-2.6.3-red.svg)

# Table Of Contents

1. [Description](#description)
2. [How to add to your project](#how-to-add-to-your-project)
3. [How to use](#how-to-use)

## Description

A simple lambda library used by many others proprietary libs and projects. Also implements a scheduler util class.
Allows to pass functions or methods as arguments to other methods

## How to add to your project

The artifact can be found at the repository https://repo.lightdream.dev or https://jitpack.io (under
com.github.L1ghtDream instead of dev.lightdream)

### Maven

```xml

<repositories>
    <repository>
        <id>lightdream-repo</id>
        <url>https://repo.lightdream.dev/</url>
    </repository>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

```xml

<dependencies>
    <dependency>
        <groupId>dev.lightdream</groupId>
        <artifactId>file-manager</artifactId>
        <version>2.6.3</version>
    </dependency>
    <dependency>
        <groupId>com.github.L1ghtDream</groupId>
        <artifactId>file-manager</artifactId>
        <version>2.6.3</version>
    </dependency>
</dependencies>
```

### Gradle - Groovy DSL

```groovy
repositories {
    maven { url "https://repo.lightdream.dev/" }
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation "dev.lightdream:file-manager:2.6.3"
    implementation "com.github.L1ghtDream:file-manager:2.6.3"
}
```

### Gradle - Kotlin DSL

```kotlin
repositories {
    maven("https://repo.lightdream.dev/")
    maven("https://jitpack.io")
}

dependencies {
    implementation("dev.lightdream:file-manager:2.6.3")
    implementation("com.github.L1ghtDream:file-manager:2.6.3")
}
```

If you want to use an older version that is not available in https://repo.lightdream.dev you can try
using https://archive-repo.lightdream.dev

## How to use

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
