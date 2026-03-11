PASO 3
Construyendo el Cliente para Solicitudes (HttpClient)

En la tercera fase de nuestro desafío, empleamos la clase HttpClient para realizar solicitudes a la API de libros y obtener datos esenciales. El uso de HttpClient en Java facilita la conexión y la obtención de respuestas de manera eficiente. Además, proporciona una base sólida para realizar operaciones HTTP de manera más estructurada y versátil. Explorar y entender el manejo de esta clase en Java optimiza el proceso de solicitud.

Construyendo la Solicitud (HttpRequest)

Además, nos sumergimos en el uso de la clase HttpRequest para configurar y personalizar nuestras solicitudes a la API de libros. La clase HttpRequest en Java nos brinda un control detallado sobre los parámetros de nuestras solicitudes, lo que resulta esencial para adaptar la consulta a nuestras necesidades específicas. Aprender a utilizar la clase HttpRequest no solo es crucial para el éxito de nuestro proyecto, sino que también proporciona una comprensión más profunda de cómo interactuar eficientemente con APIs en Java.

Construyendo la Respuesta (HttpResponse)

Finalmente, nos enfocamos en el uso de la interfaz HttpResponse para gestionar las respuestas recibidas de la API. La interfaz HttpResponse en Java ofrece una estructura que permite analizar y acceder a los diferentes elementos de una respuesta HTTP. Al entender cómo trabajar con esta interfaz, podrás extraer información significativa de las respuestas, como códigos de estado, encabezados y el cuerpo de la respuesta, que normalmente se presenta en formato JSON.

4) Analizando la respuesta en formato JSON
   Etiquetas
   Java
   Jackson
   Descripción
   En la cuarta fase de nuestro desafío, nos sumergimos en el análisis de la respuesta JSON utilizando la biblioteca Jackson en Java. La manipulación de datos JSON es esencial, ya que la mayoría de las respuestas de las API se presentan en este formato.

→ Para facilitar el análisis de los datos que se obtendrán de la API, recomendamos el uso del sitio de API para realizar consulta de libros o autores.

Con la biblioteca Jackson, puedes realizar el mapeo eficiente de los datos JSON a objetos Java, facilitando así la extracción y manipulación de la información necesaria.

→ No olvides agregar la biblioteca Jackson al proyecto como dependencia del archivo POM.xml - sugerimos usar la versión 2.16.

Recuerda utilizar las clases proporcionadas por Jackson, como ObjectMapper, para acceder a las distintas propiedades de la respuesta JSON.

PASO 5
Descripción
En esta etapa, llevaremos a cabo las conversiones con los datos de libros y autores, ahora que contamos con la información en nuestro poder.raised_hands

Experimenta utilizando clases java para recibir los datos obtenidos mediante la API, transformar los atributos del cuerpo JSON a una clase Java y mostrar los resultados.

Es fundamental crear métodos específicos para manejar estos datos, lo que hará que el código sea más modular y fácil de entender, como getters, setters and toString().

→ No olvides utilizar las anotaciones @JsonIgnoreProperties y @JsonAlias para obtener los atributos deseados del cuerpo de respuesta json.

PASO 6
Descripción
En esta etapa del desafío, nos adentraremos en la interacción con el usuario. El método Main debe implementar la interfaz CommandLineRunner y su método run() donde deberás llamar un método para exhibir el menu. En este método, debes crear un bucle para presentar a tu usuario las opciones de insercion y consulta. El usuario deberá seleccionar un número que corresponderá a la opcion numérica y proporcionar los datos que la aplicación recibirá, utilizando la clase Scanner para capturar la entrada do usuário.

Recuerda exhibirle al usuario las informaciones que han sido demandadas y informarle cuando hay un error. Además, es fundamental realizar pruebas exhaustivas para garantizar el correcto funcionamiento del programa, simulando diversas situaciones y recorridos para identificar y corregir posibles errores.

paso 7
Descripción
Bien, ahora vamos a empezar a construir nuestro catálogo de libros y autores. En esta etapa realizamos consulta de libro en la API, con la ayuda de la URL base “https://gutendex.com/books/” (sugerimos ver la documentación oficial para diferentes ejemplos de consulta).

En este caso, realizamos la consulta por título del libro en la API para retener el primer resultado obtenido. Un libro debe tener los siguientes atributos:

Título;

Autor;

Idiomas;

Número de Descargas.

Con esta funcionalidad lista, será posible presentar en la consola un listado de todos los libros que ya fueron buscados.

Además, también debes posibilitar al usuario ver un listado con base en el idioma que uno o más libros fueron escritos, con la ayuda de las derived queries.

→ En este desafío vamos a considerar que un libro posee solo un idioma, para que las consultas hechas con tal objeto sean más comprensibles y sencillas. En otras palabras, nos quedaremos solo con el primer resultado de idioma de la lista de idiomas recibida.

En resumen tenemos estas dos funcionalidades obligatorias en el proyecto:

Búsqueda de libro por título

Lista de todos los libros


PASO 8
Descripción
Como podemos ver en el sitio web de la API, cada libro tiene datos relacionados con sus autores, en este caso el cuerpo de json recibe una lista de autores por libro, donde cada autor tiene tres características:

Nombre;

Año de nacimiento;

Año de fallecimiento.

→ En este desafío vamos a considerar que un libro posee solo un autor, para que las consultas hechas con tal objeto sean más comprensibles y sencillas. En otras palabras, nos quedaremos solo con el primer resultado de autor de la lista de autores recibida.

Al guardar los datos de los autores, tendremos la opción de listado de los autores de los libros buscados.

Además, pensando en los años de nacimiento y fallecimiento, es posible incluso realizar una consulta de autores vivos en un determinado año. ¿Qué te parece eso?

En resumen tenemos estas dos funcionalidades obligatorias relacionadas a los autores:

Lista de autores

Listar autores vivos en determinado año


PASO 9
primero configuración en el pmo.xml
segundo crear el comando sql para la creación de las tablas
y tercer lo que dice aqui
Descripción
Ha llegado el momento de enfocamos en construir una base de datos, con tablas y atributos relacionados a nuestros objetos de interés: Libro y Autor.

En este desafío vamos a utilizar la base de datos llamada PostgreSQL, una de las bases de datos open source más utilizadas en el mercado.

→ Si tienes más experiencia con otro banco relacional, como MySQL, puedes usarlo sin problemas.

Sugerimos la creación de clases de entidad/modelo para Libro y Autor, así como también sus respectivas interfaces de repositorio para manejar inserción y consultas en la base de datos.

→ No olvides usar las anotaciones correctas y de importar JpaRepository, porque estamos trabajando con un proyecto Spring con Spring Data JPA, así que puede manejar las funciones necesarias para nuestro desafío java con persistencia de datos.

Al crear los repositorios de libros y autores, recuerda realizar la conversión de los atributos del libro presentes en el resultado json para un objeto java correspondiente al libro, así quedará más fácil manejar los datos obtenidos en tu proyecto.

[IMPORTANTE] Al insertar un libro en la base también deberás insertar su autor y así mantener una relación entre los dos objetos vía atributo de identificación (o como lo llamamos, el famoso ID).

PASO 10
Descripción
Una vez que ya tienes libros y autores guardados en tu base de datos, ¿qué tal exhibir estadísticas sobre ellos a tu usuario? Aprovecha los recursos de Streams de Java y derived queries para brindar tu usuario con estadísticas sobre la cantidad de libros en un determinado idioma en la base de datos.

No es necesario crear opciones para todos los idiomas. Elija como mínimo dos idiomas.

En resumen tenemos esta funcionalidad obligatoria en el proyecto:

Exhibir cantidad de libros en un determinado idioma

PASO 11
Descripción
Ahora que ya has avanzado en el uso de la base de datos, te invitamos a cambiar el método para listar los autores vivos en determinado año. Para eso, debes utilizar las derived queries para recuperar todos los autores que estaban vivos en el año que el usuario te informará.

→ Recuerda hacer pruebas para recibir adecuadamente los datos, porque esto te ayudará a aprender a lidiar con los valores inválidos que el usuário pueda ingresar en el sistema.

PASO 12
Haz el README.md