# Inaugural Engine, Common Module

This is the module for the Soliloquy Inaugural Engine which supports functionality common across nearly all other modules, such as Collections, Maps, and the handling of persistent data.

## Getting Started

To use the Inaugural Common Module in your Soliloquy game, ensure that you have set up your game as a Maven project, and that the following sections are present in your POM:

```
  <repositories>
    <repository>
        <id>github-mvn-repo</id>
        <url>https://raw.githubusercontent.com/felix-t-morgenstern/soliloquy.artifacts/master/</url>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
    </repository>
  </repositories>
```

```
    <dependency>
        <groupId>disorg.soliloquy</groupId>
        <artifactId>inaugural.soliloquy.common</artifactId>
        <version>[DESIRED VERSION NUMBER]</version>
    </dependency>
```

### Prerequisites

* [Gradle](https://gradle.org/) - Dependency Management

### Installing

Using this project as a dependency is as simple as including it as a Maven dependency.

Making changes to this project only requires setting it up in a Java IDE with a working Maven plugin; that plugin should handle the installation of all dependencies.

(More detailed instructions, perhaps with pictures, may be included here at a later date.)

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the tags on this repository.

## Contributing

A section on contributing to this project will be added at a later date (when a full release version is finalized).

## Authors

All authors are welcome. Authors will be identified by contributions to the repository. Authors are welcome to identify themselves in the documentation of classes to which they have substantially contributed. At present, no formal or typical procedure exists by which contributions are verified or permitted.

## License

A license for open and free use is under consideration; at present, it has not been finalized, and therefore this code remains private property. This will be hashed out prior to the release of the first full version.
