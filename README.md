# Utilities
Some advanced java utils: database api, jda commandmanager, common utils

## Import

#### Maven

````xml
<repositories>
  <!-- Jitpack Repository for Utility Dependencies -->
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependencies>
  <!-- Common Utils -->
  <dependency>
    <groupId>com.github.anweisen.Utility</groupId>
    <artifactId>common-utils</artifactId>
    <version>${utilities.version}</version>
  </dependency>
  
  <!-- Bukkit Utils -->
  <dependency>
    <groupId>com.github.anweisen.Utility</groupId>
    <artifactId>bukkit-utils</artifactId>
    <version>${utilities.version}</version>
  </dependency>
  
  <!-- Database API -->
  <dependency>
    <groupId>com.github.anweisen.Utility</groupId>
    <artifactId>database-api</artifactId>
    <version>${utilities.version}</version>
  </dependency>
  <!-- Database SQL Implementation -->
  <dependency>
    <groupId>com.github.anweisen.Utility</groupId>
    <artifactId>database-sql</artifactId>
    <version>${utilities.version}</version>
  </dependency>
  <!-- Database MongoDB Implementation -->
  <dependency>
    <groupId>com.github.anweisen.Utility</groupId>
    <artifactId>database-mongodb</artifactId>
    <version>${utilities.version}</version>
  </dependency>

  <!-- JDA Manager -->
  <dependency>
    <groupId>com.github.anweisen.Utility</groupId>
    <artifactId>jda-manager</artifactId>
    <version>${utilities.version}</version>
  </dependency>
</dependencies>
````

#### Gradle

````groovy

// Jitpack Repository for Utility Dependencies
repositories {
  maven { url 'https://jitpack.io' }
}

dependencies {
  implementation 'com.github.anweisen.Utility:common-utils:${utilities.version}'
  
  implementation 'com.github.anweisen.Utility:bukkit-utils:${utilities.version}'
   
  implementation 'com.github.anweisen.Utility:database-api:${utilities.version}'
  implementation 'com.github.anweisen.Utility:database-sql:${utilities.version}'
  implementation 'com.github.anweisen.Utility:database-mongodb:${utilities.version}'
  
  implementation 'com.github.anweisen.Utility:database-jda-manager:${utilities.version}'
}

````