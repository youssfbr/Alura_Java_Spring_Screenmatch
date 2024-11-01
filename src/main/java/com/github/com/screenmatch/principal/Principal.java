package com.github.com.screenmatch.principal;

import com.github.com.screenmatch.models.Episodio;
import com.github.com.screenmatch.models.Serie;
import com.github.com.screenmatch.models.Temporada;
import com.github.com.screenmatch.services.ConsumoApi;
import com.github.com.screenmatch.services.ConverteDados;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

        System.out.println();

        final List<Temporada> temporadas = new ArrayList<>();
        for (int i = 1; i <= serie.totalTemporadas(); i++) {
            final String uri2 = URL + nomeSerie.replace(" " , "+") + "&season=" + i + "&apikey=" + API_KEY;
            json = consumoApi.obterDados(uri2);
            final Temporada temporada = conversor.obterDados(json , Temporada.class);
            temporadas.add(temporada);
        }
        temporadas.forEach(System.out::println);
        System.out.println();

        for (int i = 0 ; i < serie.totalTemporadas() ; i++) {
            System.out.println(temporadas.get(i).numero());

            final List<Episodio> episodios = temporadas.get(i).episodios();

            for (int j = 0 ; j < episodios.size() ; j++) {
                System.out.println(episodios.get(j).titulo());
            }
            System.out.println();
        }

        System.out.println();
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo()))) ;

        System.out.println();
        System.out.println("flatMap");
        final List<Episodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                        .collect(Collectors.toList());
                //.toList();

        //System.out.println(dadosEpisodios);

        // dadosEpisodios.add(new Episodio("teste", 3, "10", "2020-01-01"));
        dadosEpisodios.forEach(System.out::println);

        System.out.println("\nTop 5 episódios");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(Episodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);
    }

}
