# Client Management Application

Esta aplicación está diseñada para gestionar datos de clientes y está construida con Spring Boot. Se encuentra actualmente desplegada en Heroku, y puedes acceder a la documentación Swagger de la API en el siguiente enlace:

[Swagger UI](https://appclientmanagement-3f623fe32462.herokuapp.com/swagger-ui/index.html#/client-controller/kpi)

## Características Principales

- **Validaciones de Payload**: Implementa validaciones básicas para asegurar que la edad y la fecha de nacimiento sean correctas.
- **Base de Datos**: Utiliza PostgreSQL como sistema de gestión de bases de datos.
- **Migraciones**: Se hace uso de Flyway para la creación de la tabla necesaria, gestionando las migraciones de forma automática.
- **Pruebas Locales**: Se emplea Testcontainers para la ejecución de pruebas locales, permitiendo un entorno aislado para cada prueba.

## Tecnologías Usadas

- **Spring Boot**
- **PostgreSQL**
- **Flyway**: Para la migración de la base de datos.
- **Testcontainers**: Para pruebas locales.
- **Swagger UI**: Para la documentación de la API.

## Despliegue

Esta aplicación está desplegada en Heroku. Para explorar las funcionalidades y ver la documentación Swagger, puedes acceder directamente a [este enlace](https://appclientmanagement-3f623fe32462.herokuapp.com/swagger-ui/index.html#/client-controller/kpi).

## Configuración de Pruebas Locales

Para ejecutar pruebas locales, asegúrate de que Docker esté instalado y en ejecución, ya que Testcontainers requiere Docker para crear y gestionar contenedores de prueba.
