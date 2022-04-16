# Not-witter

Implemented registration and authorization. Implemented basic CRUD opperations.

```
localhost:8080/login
```

This application will use a PostgreSQL database. To enable it, run the following command:

```
docker run --name ContainerDatabase -p 5432:5432 -e POSTGRES_PASSWORD=p4ssw0rd -d postgres
```

To stop database container run:

```
docker stop ContainerDatabase
docker rm ContainerDatabase
```
___

Swagger UI URL:

```
http://localhost:8080/swagger-ui.html#/
```

Swagger OpenApi docs:

```
http://localhost:8080/v2/api-docs
```
