package com.aluracursos.literalura.model;

public enum Idioma {

    ESPAÑOL("es", "Español"),
    INGLES("en", "Ingles"),
    FRANCES("fr", "Frances"),
    PORTUGUES("pt", "Portugues");

    private String idiomaGuntendex;
    private String idiomaEspanol;

    Idioma(String idiomaGuntendex, String idiomaEspanol){
        this.idiomaGuntendex = idiomaGuntendex;
        this.idiomaEspanol = idiomaEspanol;
    }

    public static Idioma fromString (String text){
        for(Idioma idioma: Idioma.values()){
            if(idioma.idiomaGuntendex.equalsIgnoreCase(text)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ningun Idioma encontrado! "+text);
    }

    public static Idioma fromEspanol (String text){
        for(Idioma idioma: Idioma.values()){
            if(idioma.idiomaEspanol.equalsIgnoreCase(text)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ningun Idioma encontrado! "+text);
    }



}
