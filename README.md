# Challenge de LiterAlura con Spring

Este proyecto es una aplicación de consola desarrollada en Java con el framework Spring. La aplicación consume la API de Gutendex para buscar libros por su título, y permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los libros y autores en una base de datos utilizando JPA.

## Funcionalidades

1. **Buscar libro por título**: Busca libros en la API de Gutendex y guarda los datos en la base de datos si no existen.
2. **Listar libros registrados**: Muestra una lista de todos los libros almacenados en la base de datos.
3. **Listar autores registrados**: Muestra una lista de todos los autores almacenados en la base de datos.
4. **Listar autores vivos en un determinado año**: Filtra y muestra los autores que estaban vivos en un año específico.
5. **Listar libros por idioma**: Filtra y muestra los libros según su idioma.
6. **Top 5 Libros más descargados en Base de Datos**: Muestra los 5 libros más descargados almacenados en la base de datos.
7. **Estadísticas en API Gutendex**: Genera estadísticas basadas en los datos obtenidos de la API de Gutendex.

## Tecnologías Utilizadas

- **Java**: Lenguaje de programación.
- **PgAdmin y PostgreSQL**: Gestor y motor de base de datos.
- **Spring Framework**: Framework principal para el desarrollo de la aplicación.
- **Spring Boot**: Facilita la configuración y el desarrollo del proyecto Spring.
- **Spring Data JPA**: Para las operaciones de persistencia con la base de datos.
- **Gutendex API**: API pública para obtener datos de libros.

## Instalación y Ejecución

1. **Clonar el repositorio**:
    ```sh
    git clone https://github.com/AndreGarT/LiterAlura.git
    ```

2. **Navegar al directorio del proyecto**:
    ```sh
    cd LiterAlura
    ```
3. **Creacion de la base de datos**: Crear base de datos llamada literalura (para el challengue se utilizó PostgreSQL)
   
5. **Configurar la base de datos**: Asegúrate de configurar tu archivo `application.properties` con las credenciales correctas para la conexión a la base de datos.

4. **Compilar y ejecutar la aplicación**:
    ```sh
    ./mvnw spring-boot:run
    ```

## Uso

Al iniciar la aplicación, se mostrará un menú en la consola con las opciones disponibles:
![image](https://github.com/AndreGarT/LiterAlura/assets/117962279/e8b709db-d0b7-463d-bb09-edebbb94ce80)


## EJemplos de Uso

Podrá ingresar cualquiera de las opciones disponibles en el menú, según lo que quiera realizar:
![image](https://github.com/AndreGarT/LiterAlura/assets/117962279/8044cfda-dedc-4019-acd2-2fb944136b74)
![image](https://github.com/AndreGarT/LiterAlura/assets/117962279/e8718a33-a625-4ac9-b9d8-fd271c3242a3)
![image](https://github.com/AndreGarT/LiterAlura/assets/117962279/ed05122a-7804-45dc-9589-dab5996adf4a)
![image](https://github.com/AndreGarT/LiterAlura/assets/117962279/381c41ed-a9fa-496f-bbf5-3e7822e22eb5)
![image](https://github.com/AndreGarT/LiterAlura/assets/117962279/ba749a9b-9310-445e-a01c-03aca92478ed)

## Requisitos

- Java JDK instalado en su sistema.
- Acceso a Internet para consultar la API Gutendex.

## Créditos

Este programa utiliza la API de https://gutendex.com/ para obtener datos de los libros. Agradecimientos al proveedor de la API por su servicio.

---

¡Disfrute haciendo busqueda de libros y autores con este sencillo programa en Java!


