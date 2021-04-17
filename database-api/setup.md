---
description: Setting up database connections and tables
---

# Setup

## Creating Connections

Databases are usually created using a `DatabaseConfig` object, which contains information like host, port, username, password, database, or filename. `DatabaseConfig` objects are immutable.

The database connection is created synchronously by calling the `connect()` method on the database. If the database is already connected a `DatabaseAlreadyConnectedException` or if the connection could not be established successfully a `DatabaseException` is thrown. If the connection should be created without throwing an exception `connectSafely()` can be used. This will return `true` if the connection was established successfully, `false` otherwise.

## Creating Tables / Collections

You can create database tables by calling the `createTablesIfNotExists(String, SQLColumn...)` on the database. This will throw a `DatabaseConnectionClosedException` if the database is not connected or a `DatabaseException` when something goes wrong. If the table should be created without throwing an exception `createTablesIfNotExistsSafely(String, SQLColumn...)` can be used.

If a no-sql database, like mongodb, is used, the given columns will be ignored, because they are only used in sql databases. A `SQLColumn` always contains the name, type and length and is immutable.

