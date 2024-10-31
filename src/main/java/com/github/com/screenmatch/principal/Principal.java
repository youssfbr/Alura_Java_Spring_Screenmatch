package com.github.com.screenmatch.principal;

import com.github.com.screenmatch.models.Serie;
import com.github.com.screenmatch.models.Temporada;
import com.github.com.screenmatch.services.ConsumoApi;
import com.github.com.screenmatch.services.ConverteDados;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class Principal {

    private final ConsumoApi consumoApi;
    private final ConverteDados conversor;
    private final Scanner sc = new Scanner(System.in);
    private static final String URL = "https://www.omdbapi.com/?t=";
    private static final String API_KEY = System.getenv("YOUR_API_KEY");

    public Principal(ConsumoApi consumoApi , ConverteDados conversor) {
        this.consumoApi = consumoApi;
        this.conversor = conversor;
    }

    public void exibeMenu() {
        System.out.print("\nDigite o nome da série para busca: ");
        String nomeSerie = sc.nextLine();
        String uri = URL + nomeSerie.replace(" " , "+") + "&apikey=" + API_KEY;
        var json = consumoApi.obterDados(uri);
        final Serie serie = conversor.obterDados(json , Serie.class);
        System.out.println(serie);

        final List<Temporada> temporadass = new ArrayList<>();
        for (int i = 1; i <= serie.totalTemporadas(); i++) {
            final String uri2 = URL + nomeSerie.replace(" " , "+") + "&season=" + i + "&apikey=" + API_KEY;
            json = consumoApi.obterDados(uri2);
            final Temporada temporada = conversor.obterDados(json , Temporada.class);
            temporadass.add(temporada);
        }
        temporadass.forEach(System.out::println);
    }

}
