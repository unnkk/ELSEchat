# ELSEchat
That's just a simple messenger, my little bro.

# Installation
You need installed Docker to run test DB.

Clone this repo using
```
git clone https://github.com/unnkk/ELSEchat.git
```
or download ZIP with code using green "Code" button on top.

# Local running

Run database using
```shell
docker compose up    
```

After, build server using
```shell
.\gradlew build
```
and run using
```shell
java -jar build/libs/elsechat-0.0.1-SHAPSHOT.jar
```

Default port is 9090, but you can change it in ./src/main/resources/application.yml
JWT generation secret can be set by creating environment variable JWT_SECRET(32 symbols min.) 

# Endpoints

* GET "/api/health" - returns the "up" status if the program is successfully booted.
* GET "/api/users" - returns all users in the "users" table
* GET "/api/users/{username}" - returns user with specified username
* DELETE "/api/users/{id}" - deletes the user with specified id
* POST "/api/auth/register" - creates