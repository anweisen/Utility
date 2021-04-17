---
description: All updates to all modules
---

# Changelog

## Development v1.2.1

### Added

* Added `valuesAsString()` and `mapValues(Function, Function)` on `Propertyable`
* Added `Document.empty()` for creating an instance of `EmptyDocument`
* Added `onEvent` methods on `BukkitModule`
* Started jda-commandmanager module

### Changed

* Moved `getSerializeable` from `Propertyable` to `Document`

## Release v1.2

### Added

* Added `getOptional(String, BiFunction)`, `isList(String)`, `isObject(String)`  to `Propertyable`
* Added `hasChildren(String)` to `Document`
* Added `DatabaseAccess` system
* Implemented equals & hashCode in database actions

### Fixed

* A `NullPointerException` was thrown in `getDocument(String)` on `BsonDocument` when there was no entry in the original bson `Document`



