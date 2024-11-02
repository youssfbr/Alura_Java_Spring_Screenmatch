package com.github.com.screenmatch.principal;

import com.github.com.screenmatch.models.DadosEpisodio;
import com.github.com.screenmatch.models.Episodio;
import com.github.com.screenmatch.models.Serie;
import com.github.com.screenmatch.models.Temporada;
import com.github.com.screenmatch.services.ConsumoApi;
import com.github.com.screenmatch.services.ConverteDados;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

            final List<DadosEpisodio> dadosEpisodios = temporadas.get(i).dadosEpisodios();

            for (int j = 0; j < dadosEpisodios.size() ; j++) {
                System.out.println(dadosEpisodios.get(j).titulo());
            }
            System.out.println();
        }

        System.out.println();
        temporadas.forEach(t -> t.dadosEpisodios().forEach(e -> System.out.println(e.titulo()))) ;

        System.out.println();
        System.out.println("flatMap");
        final List<DadosEpisodio> dadosDadosEpisodios = temporadas.stream()
                .flatMap(t -> t.dadosEpisodios().stream())
                        .collect(Collectors.toList());
                //.toList(); // Fica imutável
        dadosDadosEpisodios.forEach(System.out::println);

//        System.out.println("\nTop 5 episódios");
//        dadosDadosEpisodios.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .limit(5)
//                .forEach(System.out::println);
//
//        List<Episodio> episodios = temporadas.stream()
//                .flatMap(t -> t.dadosEpisodios().stream()
//                        .map(d -> new Episodio(t.numero(), d))
//                ).collect(Collectors.toList());
//
//        System.out.println();
//        episodios.forEach(System.out::println);
//
//        // Buscando pela data a partir de...
//        System.out.println("\nA partir de que ano você deseja ver os episódios?");
//        final int ano = sc.nextInt();
//        sc.nextLine();
//
//        final LocalDate dataBusca = LocalDate.of(ano , 1 , 1);
//        System.out.println(dataBusca);
//
//        final DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                                "Episódio: " + e.getTitulo() +
//                                "Data lançamento: " + e.getDataLancamento().format(formatador)
//                ));
//
        System.out.println("\nTop 10 episódios");
        dadosDadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println("Primeiro filtro (N/A) " + e))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .peek(e -> System.out.println("Ordenação " + e))
                .limit(10)
                .peek(e -> System.out.println("Limite " + e))
                .map(e -> e.titulo().toUpperCase())
                .peek(e -> System.out.println("Mapeamento " + e))
                .forEach(System.out::println);

        System.out.println("\nNúmeros");
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5);

        int soma = numeros.stream()
                .peek(n -> System.out.println("Elemento: " + n))
                .map(n -> n * 2)
                .peek(n -> System.out.println("Conteúdo depois do map: " + n))
                .reduce(0, (total, numero) -> total + numero);

        System.out.println("A soma dos números é: " + soma);
    }


}
