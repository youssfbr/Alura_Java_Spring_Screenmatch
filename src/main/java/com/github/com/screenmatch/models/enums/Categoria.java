package com.github.com.screenmatch.models.enums;

public enum Categoria {

    ACAO ("Action") ,
    AVENTURA ("Adventure") ,
    ANIMACAO ("Animation") ,
    DOCUMENTARIO ("Documentary") ,
    ROMANCE ("Romance") ,
    COMEDIA ("Comedy") ,
    DRAMA ("Drama") ,
    CRIME ("Crime") ,
    CURTO ("Short") ;

    private final String categoriaOmdb;

    Categoria(String categoriaOmdb) {
        this.categoriaOmdb = categoriaOmdb;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
