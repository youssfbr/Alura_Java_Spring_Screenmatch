package com.github.com.screenmatch.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.com.screenmatch.models.DadosTraducao;
import com.github.com.screenmatch.services.ConsumoApi;

import java.net.URLEncoder;

public class ConsultaMyMemory {

    private ConsultaMyMemory() {
        throw new IllegalStateException("Utility class");
    }

    public static String obterTraducao(String text) {
        ObjectMapper mapper = new ObjectMapper();

        ConsumoApi consumo = new ConsumoApi();

        String texto = URLEncoder.encode(text);
        String langpair = URLEncoder.encode("en|pt-br");

        String url = "https://api.mymemory.translated.net/get?q=" + texto + "&langpair=" + langpair;

        String json = consumo.obterDados(url);

        DadosTraducao traducao;
        try {
            traducao = mapper.readValue(json, DadosTraducao.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return traducao.dadosResposta().textoTraduzido();
    }
}