# FileCompressFramework

| | branch | travis ci | JitPack |
|:---:|:---|:---|:---|
| release | gh-pages | [![Build Status](https://travis-ci.org/mickey305/FileCompressFramework.svg?branch=gh-pages)](https://travis-ci.org/mickey305/FileCompressFramework) | [![](https://jitpack.io/v/mickey305/FileCompressFramework.svg)](https://jitpack.io/#mickey305/FileCompressFramework) |
| - | develop | [![Build Status](https://travis-ci.org/mickey305/FileCompressFramework.svg?branch=develop)](https://travis-ci.org/mickey305/FileCompressFramework) |  |

# Installation(Pattern 1)
## 1 - Register repository in local library

```
repositories {
  maven { url 'http://mickey305.github.io/FileCompressFramework/repository/' }
  ...
}
```

## 2 - Compile library

```
dependencies {
  // newest version
  compile 'com.mickey305:framework:+@jar'
  ...
}
```

```
dependencies {
  // target version - e.g. version 0.0.1-SNAPSHOT
  compile 'com.mickey305:framework:0.0.1-SNAPSHOT'
  ...
}
```

# Installation(Pattern 2) - how to use the JitPack service
## 1 - Register repository in local library

```
repositories {
  maven { url 'https://jitpack.io' }
  ...
}
```

## 2 - Compile library

```
dependencies {
  // target version - e.g. version 0.0.1-SNAPSHOT
  compile 'com.github.mickey305:FileCompressFramework:0.0.1-SNAPSHOT'
  ...
}
```

# History
 * version 0.0.2-SNAPSHOT deploy - 2017-4-24
    * singleton object - multi-thread supported.
    * javadoc comment added.
 * version 0.0.1-SNAPSHOT deploy - 2017-4-16
