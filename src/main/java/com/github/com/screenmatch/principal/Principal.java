package com.github.com.screenmatch.principal;

import com.github.com.screenmatch.models.Serie;
import com.github.com.screenmatch.services.ConsumoApi;
import com.github.com.screenmatch.services.ConverteDados;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class Principal {

    private final ConsumoApi consumoApi;
    private final ConverteDados conversor;
    private final Scanner sc = new Scanner(System.in);
    private static final String API_KEY = System.getenv("YOUR_API_KEY");
    private static final String URL = "https://www.omdbapi.com/?t=";

    public Principal(ConsumoApi consumoApi , ConverteDados conversor) {
        this.consumoApi = consumoApi;
        this.conversor = conversor;
    }

    public Serie exibeMenu() {
        System.out.print("\nDigite o nome da série para busca: ");
        String nomeSerie = sc.nextLine();
        String uri = URL + nomeSerie.replace(" " , "+") + "&apikey=" + API_KEY;
        var json = consumoApi.obterDados(uri);
        return conversor.obterDados(json , Serie.class);
    }

}
