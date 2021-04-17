# Repository

## Maven

In order to be able to access the repository hosted by github packages, you need to setup authentication.  
You have to add the following repository and a server matching repository name in the `~/Users/${USER}/.m2/settings.xml`, following this [Guide](https://docs.github.com/en/packages/guides/configuring-apache-maven-for-use-with-github-packages) from GitHub.

```text
<repository>
  <id>github-anweisen</id>
  <url>https://maven.pkg.github.com/anweisen/*</url>
  <snapshots>
    <enabled>true</enabled>
  </snapshots>
</repository>
```

