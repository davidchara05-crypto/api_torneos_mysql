# API de Gestión de Torneos

Proyecto desarrollado para la asignatura Lenguaje de Programación III de la Corporación Universitaria Remington.

La aplicación consiste en una API REST desarrollada con Spring Boot para gestionar torneos y equipos, utilizando persistencia de datos mediante JPA/Hibernate y MySQL.

## Tecnologías utilizadas

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- REST API
- Postman
- Git y GitHub
- RestClient para comunicación con servicios externos

## Funcionalidades

La API permite:

- Crear torneos.
- Consultar todos los torneos.
- Consultar un torneo por ID.
- Actualizar torneos.
- Eliminar torneos.
- Buscar torneos por ciudad.
- Buscar torneos por categoría.
- Crear equipos.
- Consultar equipos.
- Consultar equipos por ID.
- Consultar equipos pertenecientes a un torneo.
- Actualizar equipos.
- Eliminar equipos.
- Consultar información de clima mediante una API externa.

## Persistencia de datos

La aplicación utiliza Spring Data JPA e Hibernate para la persistencia de información en MySQL.

Las entidades principales son:

- `Torneo`
- `Equipo`

La relación entre ambas entidades se implementa mediante:

```java
@OneToMany