# balldontlie
GraphQL-java demo project with Spring Boot and balldontlie API. 

## Basic information
To run this project, you have to set up a PostgreSQL database with the following parameters:
* name: **balldontlie**
* username: **postgres**
* password: **admin**

This information can be found in the **application.yml** file as well.

### Database modification
#### Enums
Two enums should be created manually, because it cannot be done automatically:
* `CREATE TYPE conference AS ENUM ('East', 'West');`
* `CREATE TYPE division AS ENUM ('Pacific', 'Southeast', 'Southwest', 'Atlantic', 'Northwest', 'Central');`

#### Columns
Run these two commands to change the type of the columns from TEXT to the corresponding enum type:
* `ALTER TABLE team ALTER COLUMN conference TYPE conference USING conference::conference;`
* `ALTER TABLE team ALTER COLUMN division TYPE division USING division::division;`

The project doesn't have any profiling yet, so it basically runs in **dev** mode.

## Startup
During startup, the application will try to get some data from the [balldontlie API](https://www.balldontlie.io/#introduction),
and will persist some of it, if it doesn't exist in the database.

This data fetching can be seen in the console logs.

This data import can be disabled by adding `-DappConfig.skipImport=true` as a VM option.

## Usage
After the successful startup, 
the stored data can be reached through **GraphQL** queries defined in resources/graphql/**balldontlie.graphqls** using **GraphiQL**.

GraphiQL can be found at http://localhost:8080/graphiql.
