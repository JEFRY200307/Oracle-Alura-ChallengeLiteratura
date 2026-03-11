# Literatura Challenge - Oracle ONE

Aplicación Spring Boot basada en arquitectura MVC que consume la API pública [Gutendex](https://gutendex.com/books/) para construir un catálogo de libros y autores. El objetivo es cubrir los pasos 3-12 del reto, desde el consumo de la API mediante `HttpClient`, el mapeo JSON con Jackson y la persistencia en PostgreSQL, hasta ofrecer un menú interactivo vía consola y documentar el resultado final.

## Stack y dependencias clave
- Java 17 + Spring Boot 4 (Web, Data JPA, Validation)
- `HttpClient` y Jackson 2.16 para integrar y mapear la API Gutendex
- PostgreSQL como base de datos principal (H2 en memoria para pruebas)
- Tests con Spring Boot Starter Test, Mockito y AssertJ

## Arquitectura MVC por capas
- **Client/DTOs** (`client`, `dto`): encapsulan las llamadas HTTP y la deserialización de Gutendex.
- **Service** (`service`): reglas de negocio, orquestación de repositorios y estadísticas con Streams.
- **Repository** (`repository`): interfaces `JpaRepository` para `Author` y `Book`, con derived queries.
- **Model** (`model`): entidades JPA y relación Autor ↔ Libro (1:N).
- **View** (`view.ConsoleMenu`): menú CLI ejecutado desde `CommandLineRunner`.

# Oracle-Alura-ChallengeLiteratura

## Arquitectura y buenas prácticas

Este proyecto sigue la arquitectura MVC y las mejores prácticas de Spring Boot:

- **Modelo:**
  - `Book` y `Author` en `oracle.alura.literaturachallenge.model`.
- **Vista:**
  - `ConsoleMenu` en `oracle.alura.literaturachallenge.view`.
- **Controlador/Servicio:**
  - `LibraryService` orquesta la lógica de negocio.
  - `GutendexService` y `GutendexClient` gestionan la integración con la API externa.
  - Los DTOs (`GutendexBookDto`, `GutendexAuthorDto`, `GutendexSearchResponse`) aíslan el modelo interno del externo.

## Flujo de búsqueda de libro por título

1. El usuario ingresa el título en el menú.
2. `ConsoleMenu` llama a `LibraryService.searchAndStoreBookByTitle`.
3. Si el libro no existe localmente, `LibraryService` usa `GutendexService.fetchFirstMatch` para consultar la API externa.
4. El resultado se transforma de DTO a entidad y se almacena en la base de datos.
5. Se maneja cualquier error de red, formato o negocio, mostrando mensajes claros al usuario.

## Buenas prácticas implementadas

- Código muerto eliminado (`LibroService.java`).
- Manejo robusto de errores en la integración externa (`GutendexClient`).
- Uso de DTOs para desacoplar el modelo interno del externo.
- Repositorios Spring Data JPA para persistencia.
- Separación clara de responsabilidades.

## Requisitos previos
 1. Java 17 y Maven (o utilizar `mvnw`).
 2. PostgreSQL con una base creada (por defecto `literatura`). Si no defines credenciales externas, la aplicación arranca automáticamente con una base H2 en memoria configurada en modo PostgreSQL para que puedas probar el menú sin un servidor externo.
 3. Variables opcionales para personalizar la conexión:
    - `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`
    - `APP_GUTENDEX_BASE_URL` (útil para pruebas con mocks locales)
    - `APP_STATS_LANGUAGES` (lista separada por comas para las estadísticas)
    - (Opcional) `SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.PostgreSQLDialect` cuando apuntes a PostgreSQL y quieras forzar el dialecto.

## Puesta en marcha
```powershell
cd C:\Users\Jefferson\Documents\Oracle-Alura-LATAM\Oracle-Alura-ChallengeLiteratura
.\mvnw spring-boot:run
```
Al iniciar, la aplicación mostrará el menú en consola y podrás interactuar usando números 0-6.

### Menú disponible
1. Buscar libro por título (consulta Gutendex, almacena primer autor e idioma).
2. Listar todos los libros guardados.
3. Listar todos los autores registrados.
4. Listar autores vivos en un año indicado (derived query).
5. Listar libros por idioma (se conserva un único idioma por libro).
6. Estadísticas de cantidad de libros por idioma (Streams + derived query).
0. Salir.

Los errores de negocio se informan de forma amigable en la consola.

## Esquema SQL de referencia
- `src/main/resources/schema.sql` contiene el script sugerido para crear las tablas `authors` y `books` en PostgreSQL si deseas administrarlas manualmente.

## Pruebas automatizadas
```powershell
cd C:\Users\Jefferson\Documents\Oracle-Alura-LATAM\Oracle-Alura-ChallengeLiteratura
.\mvnw test
```
- `AuthorRepositoryTest` valida la derived query de autores vivos.
- `LibraryServiceTest` cubre las rutas de negocio principales (reutilización de libros existentes, importación desde Gutendex y estadísticas).

## Cobertura de los pasos del desafío
| Paso | Descripción | Implementación |
|------|-------------|----------------|
| 3-4  | Cliente HTTP + análisis JSON | `GutendexClient`, DTOs en `dto`, Jackson 2.16 configurado en `pom.xml` |
| 5    | Conversión de datos y DTOs | Clases DTO con `@JsonIgnoreProperties` y `@JsonAlias` |
| 6    | Interacción CLI (CommandLineRunner) | `LiteraturaChallengeApplication` + `ConsoleMenu` con `Scanner` |
| 7    | Búsqueda por título y listado de libros | `LibraryService.searchAndStoreBookByTitle`, `listAllBooks` |
| 8    | Gestión de autores y consulta por año | Entidades `Author`/`Book`, método `listAuthorsAliveInYear` |
| 9    | Persistencia con PostgreSQL | Entidades JPA, repositorios y script `schema.sql` |
| 10   | Estadísticas por idioma | `LibraryService.countBooksByLanguages` y opción 6 del menú |
| 11   | Derived query para autores vivos | Método custom en `AuthorRepository` y pruebas asociadas |
| 12   | Documentación | Este README resume la solución e instrucciones |

## Próximos pasos sugeridos
1. Añadir más opciones de filtrado (por número de descargas, por década, etc.).
2. Integrar Spring Shell o una interfaz web para complementar el menú de consola.

## Notas
- Si la API externa cambia el formato, ajusta los DTOs.
- Si ocurre un error de JSON, revisa la traza en consola y asegúrate de que la API esté disponible.

---

Proyecto desarrollado siguiendo las mejores prácticas de Java y Spring Boot.
