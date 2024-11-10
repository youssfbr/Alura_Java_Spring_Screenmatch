package com.github.com.screenmatch.models.enums;

public enum Categoria {

    ACAO ("Action" , "Acao") ,
    AVENTURA ("Adventure" , "Aventura") ,
    ANIMACAO ("Animation" , "Animacao") ,
    DOCUMENTARIO ("Documentary" , "Documentario") ,
    ROMANCE ("Romance" , "romance") ,
    COMEDIA ("Comedy" , "comedia") ,
    DRAMA ("Drama" , "drama") ,
    CRIME ("Crime" , "crime") ,
    CURTO ("Short" , "curto") ;

    private final String categoriaOmdb;
    private final String categoriaPortugues;

    Categoria(String categoriaOmdb , String categoriaPortugues) {
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaPortugues = categoriaPortugues;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }

    public static Categoria fromStringPortugues(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaPortugues.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
