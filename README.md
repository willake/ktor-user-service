# Ktor User Service
This project is a user service developed with Ktor

## How to run
Firstly, create a database `user-service` with database applications such as [PosgreSQL](https://www.postgresql.org/).
You can also use a [h2 database](https://www.h2database.com/html/main.html), so that you don't need to set up the database manually.

To use the other database applications like `MySQL` or `MSSQL`, you will need to add the driver dependency in `build.gradle.kts`.

Next, open project using Kotlin IDEs such as Jetbrains IntelliJ 

Create a `.env` file with following data:
```
DB_URL=
DB_DRIVER=
DB_USERNAME=
DB_PASSWORD=
JWT_SECRET_KEY=
JWT_EXPIRATION=
JWT_ISSUER=
JWT_AUDIENCE=
JWT_REALM=
```
An example environment variable data has been provided in `.env.example`

If you would like to use h2 database, which will not require you to create a database manually. You can input the 
DB-related variables as following example:
```
DB_URL=jdbc:h2:mem:test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1
DB_DRIVER=org.h2.Driver
DB_USERNAME=root
DB_PASSWORD=
```

This use an in-memory database, which will be wiped out after the application lifecycle.

After `.env` is set, run the project with `Application.kt` or with cli
```
./gradlew run
```

For running test, select test file and run in IDE or run with the following command
```
./gradlew test
```

By default, server is run on localhost:8080. The port can be adjusted in `application.yaml`

## APIs
### Create user
`POST /api/v1/user`
#### Request
```
{
  "username": string,
  "email": string,
  "password": string
}
```
#### Response
```
{
  "id": string,
  "username": string,
  "email": string
}
```
### Get user
`GET /api/v1/user/{id}` get user
#### Response
```
{
  "id": string,
  "username": string,
  "email": string
}
```
### Update user info except username and password
`PUT /api/v1/user`
#### Request
```
{
  "id": string,
  "email": string
}
```
#### Response
```
{
  "id": string,
  "username": string,
  "email": string
}
```
Update user password
`PUT /api/v1/user/{id}/password`
#### Request
```
{
  "oldPassword": string,
  "newPassword": string
}
```
### Delete user
`DELETE /api/v1/user/{id}`

### Login
`POST /api/v1/auth/login`
#### Request
```
{
  "username": string,
  "password": string
}
```
#### Response
```
{
  "userId": string,
  "accessToken": string
}
```
## TODO
- [ ] Error Handling (Exceptions)
- [ ] Integration tests
- [ ] Unit tests
- [ ] CI/CD

## Version Control
This project follows [GitFlow](http://datasift.github.io/gitflow/IntroducingGitFlow.html) version control rules, but not that strict.

Hereby a brief guide:

- For the `main` branch, you cannot push commits into it directly. It can only be merged from any `release` branch with a pull request. We need to make sure that every version of the `main` branch is the latest production-ready game.
- For the `develop` branch, you cannot push commits into it directly. It can only be merged from any `feature/xxx_xxx` branch with a pull request. We need to make sure that every version of the `develop` branch is a runnable game.
- If you want to add a new feature or maybe refactor any code structure, **you should create a branch named `feature/the_name_of_feature` from the develop branch.** When the branch is finished, you can create a pull request for updating the `develop` branch.
- When a `develop` branch is ready to be published, you can create a pull request to create a `release/vx.x.x` branch. The name of a branch could be `release/v0.0.1` or `release/alpha1`.
