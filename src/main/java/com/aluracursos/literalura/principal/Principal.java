package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private LibroRepository repositorioLibro;
    private AutorRepository repositorioAutor;
    private List<Libro> libros;
    private List<Autor> autores;

    public Principal(LibroRepository repositoryLibro, AutorRepository repositoryAutor){
        this.repositorioLibro = repositoryLibro;
        this.repositorioAutor = repositoryAutor;
    }

    public void muestraMenu(){
        var opcion = -1;
        while(opcion != 0 ){
            var menu = """
                    Elija la opción a través de un número:
                    1. Buscar libro por título
                    2. Listar libros registrados
                    3. Listar autores registrados
                    4. Listar autores vivos en un determinado año
                    5. Listar libros por idioma
                    6. Top 5 Libros más descargados en Base de Datos
                    7. Estadisticas en API Gutendex
                    0. Salir
                    """;

            System.out.println(menu);
            System.out.println("Ingrese una opcion de la lista: ");
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion){
                case 0:
                    System.out.println("Cerrando aplicación");
                    break;
                case 1:
                    buscarLibrosPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresPorAno();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 6:
                    top5LibrosMasDescargados();
                    break;
                case 7:
                    generarEstadisticas();
                    break;
                default:
                    System.out.println("La opción ingresada es inválida!");
            }

        }

    }

    private void buscarLibrosPorTitulo() {

        System.out.println("Ingrese el nombre del libro que deseas buscar: ");
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE+nombreLibro.replace(" ","+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        Optional<DatosLibros> libroBuscado = datosBusqueda.datosLibros().stream()
                .filter(l-> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();

        if(libroBuscado.isPresent()){
            System.out.println("Libro encontrado");
            var menuLibroEncontrado = """
                    ------------- LIBRO -----------------
                    título : %s 
                    Autor  : %s
                    Idioma : %s
                    Numero de descargas : %s
                    -------------------------------------
                    """;
            System.out.println(menuLibroEncontrado.formatted(libroBuscado.get().titulo(),
                    libroBuscado.get().autores().get(0).nombre(),
                    libroBuscado.get().idiomas().get(0),
                    libroBuscado.get().numeroDescargas()));

            Optional<Libro> libroRepositorio = repositorioLibro.findByTitulo(libroBuscado.get().titulo());

            if (libroRepositorio.isPresent()){
                System.out.println("Libro ya se encuentra en base de datos!");
            } else{

                System.out.println("Libro guardado en Base de Datos!");

                DatosAutor datosAutor = libroBuscado.get().autores().get(0);

                Optional<Autor> autorRepositorio = repositorioAutor.findByNombre(datosAutor.nombre());

                if (autorRepositorio.isPresent()){

                    Libro libro = new Libro(libroBuscado.get());
                    libro.setAutor(autorRepositorio.get());
                    repositorioLibro.save(libro);

                }else{
                    libros = libroBuscado.stream()
                            .map(l -> new Libro(l))
                            .collect(Collectors.toList());

                    Autor autor = new Autor(datosAutor);
                    autor.setLibro(libros);
                    repositorioAutor.save(autor);
                }
            }

        }else{
            System.out.println("Libro no encontrado!");
        }

    }

    private void listarLibrosRegistrados() {
        libros = repositorioLibro.findAll();
        var menuLibroRegistrado = """
                --------- LIBRO -----------
                Título : %s 
                Autor  : %s
                Idioma : %s
                Numero de descargas : %s
                ---------------------------
                """;
        libros.forEach(l-> System.out.println(menuLibroRegistrado.formatted(
                l.getTitulo(),l.getAutor().getNombre(),l.getIdioma(),l.getNumeroDescargas())));
    }

    private void listarAutoresRegistrados(){
        autores = repositorioAutor.findAll();
        var menuAutorRegistrado = """
                ----------- AUTOR -------------------
                Nombre : %s
                Fecha de nacimiento   : %s
                Fecha de fallecimiento: %s
                Libros : %s
                -------------------------------------
                """;

        autores.forEach(a -> System.out.println(menuAutorRegistrado.formatted(
                a.getNombre(),
                a.getFechaDeNacimiento(),
                a.getFechaDeMuerte(),
                a.getLibro().stream()
                        .map(l->l.getTitulo())
                        .collect(Collectors.joining(" , ","[","]")))));

    }

    private void listarAutoresPorAno(){

        try{
            System.out.println("Ingrese el año para ver la lista de autores vivos: ");
            int anioNumero = teclado.nextInt();
            teclado.nextLine();
            //var anio = teclado.nextLine();
            var anio = String.valueOf(anioNumero);

            List<Autor> autoresVivos = repositorioAutor.findByFechaDeMuerteGreaterThanEqual(anio);

            var menuAutorRegistrado = """
                ----------- AUTOR -------------------
                Nombre : %s
                Fecha de nacimiento   : %s
                Fecha de fallecimiento: %s
                Libros : %s
                -------------------------------------
                """;

            autoresVivos.forEach(a -> System.out.println(menuAutorRegistrado.formatted(
                    a.getNombre(),
                    a.getFechaDeNacimiento(),
                    a.getFechaDeMuerte(),
                    a.getLibro().stream()
                            .map(l->l.getTitulo())
                            .collect(Collectors.joining(" , ","[","]")))));
        }catch (InputMismatchException e){
            System.out.println("Ingrese un año valido! " +e.getMessage());
            teclado.next();
        }


    }

    private void listarLibrosPorIdioma(){
        var menuIdiomas = """
                
                Opciones válidas: 
                1. Español
                2. Ingles
                3. Frances
                4. Portugues
                """;
        System.out.println("Ingrese el idioma del cual desea ver los libros: "+menuIdiomas);
        var idiomaTeclado = teclado.nextLine();
        List<Libro> libroByIdioma;
        try {
            var idioma = Idioma.fromEspanol(idiomaTeclado);
            libroByIdioma = repositorioLibro.findByIdioma(idioma);

            System.out.println("Los Libros en el idioma "+idiomaTeclado+ " son los siguientes:");
            libroByIdioma.forEach(l-> System.out.println(l.getTitulo()));

            var totalLibrosPorIdioma = libroByIdioma.size();
            System.out.println("Hay un total de "+totalLibrosPorIdioma+ " Libros, en el idioma "+idiomaTeclado);
            System.out.println();

        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
            System.out.println("La Opcion ingresada no es válida!");
            System.out.println();
        }

    }

    private void top5LibrosMasDescargados() {

        libros = repositorioLibro.top5LibrosMasDescargados();
        libros.forEach(l -> System.out.println(l.getTitulo()+" - "+ l.getNumeroDescargas()));

    }

    private void generarEstadisticas(){
        System.out.println("TOP 10 libros más descargados Gutendex : ");
        var json = consumoAPI.obtenerDatos("https://gutendex.com/books/");
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        datosBusqueda.datosLibros().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDescargas).reversed())
                .limit(10)
                .map(l->l.titulo().toUpperCase())
                .forEach(System.out::println);

        DoubleSummaryStatistics est = datosBusqueda.datosLibros().stream()
                .filter(l->l.numeroDescargas()>0)
                .collect(Collectors.summarizingDouble(DatosLibros::numeroDescargas));

        System.out.println("Cantidad media de descargas: "+est.getAverage());
        System.out.println("Cantidad Maxima de descargas: "+est.getMax());
        System.out.println("Cantidad Minima de descargas: "+est.getMin());
        System.out.println("Cantidad de registros evaluados para estadisticas: "+est.getCount());

    }



}
