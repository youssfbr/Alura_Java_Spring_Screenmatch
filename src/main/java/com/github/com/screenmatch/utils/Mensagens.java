package com.github.com.screenmatch.utils;

public class Mensagens {

    private Mensagens() {
        throw new IllegalStateException("Utility class");
    }

    public static final String MENSAGENS_OPCOES =
            """
                        
                        1  - Buscar séries
                        2  - Buscar episodios
                        3  - Listar séries buscadas
                        4  - Buscar série por título
                        5  - Buscar séries por ator
                        6  - Buscar séries por ator com avaliação
                        7  - Top 5 Séries
                        8  - Buscar séries por Categoria
                        9  - Filtrar sériesfiltro
                        10 - Series filtradas por SQL
                        11 - Series filtradas por JPQL
                        12 - Buscar Episodios por trecho
                        -
                        0 - Sair
                        """;
}
