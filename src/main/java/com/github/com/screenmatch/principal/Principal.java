package com.github.com.screenmatch.principal;

import com.github.com.screenmatch.models.Serie;
import com.github.com.screenmatch.models.Temporada;
import com.github.com.screenmatch.services.ConsumoApi;
import com.github.com.screenmatch.services.ConverteDados;
import com.github.com.screenmatch.utils.Mensagens;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Principal {

    private final ConsumoApi consumoApi;
    private final ConverteDados conversor;
    private final Scanner sc = new Scanner(System.in);
    private static final String URL = "https://www.omdbapi.com/?t=";
    private static final String API_KEY = System.getenv("YOUR_API_KEY");
    private final List<Serie> dadosSeries = new ArrayList<>();

    public Principal(ConsumoApi consumoApi , ConverteDados conversor) {
        this.consumoApi = consumoApi;
        this.conversor = conversor;
    }

    public void exibeMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println(Mensagens.MENSAGENS_OPCOES);
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 0 -> System.out.println("Saindo...");
                case 1 -> buscarSerieWeb();
                case 2 -> buscarEpisodioPorSerie();
                case 3 -> listarSeriesBuscadas();
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void buscarSerieWeb() {
        Serie dados = getDadosSerie();
        dadosSeries.add(dados);
        System.out.println(dados);
    }

    private Serie getDadosSerie() {
        System.out.print("\nDigite o nome da série para busca: ");
        String nomeSerie = sc.nextLine();
        String uri = URL + nomeSerie.replace(" " , "+") + "&apikey=" + API_KEY;
        var json = consumoApi.obterDados(uri);
        return conversor.obterDados(json , Serie.class);
    }

    private void buscarEpisodioPorSerie() {
        final Serie dadosSerie = getDadosSerie();
        final List<Temporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            final String endereco = URL + dadosSerie.titulo().replace(" " , "+") + "&season=" + i + "&apikey=" + API_KEY;
            var json = consumoApi.obterDados(endereco);

            final Temporada dadosTemporada = conversor.obterDados(json , Temporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);
    }

    private void listarSeriesBuscadas() {
        if (!dadosSeries.isEmpty()) {
            dadosSeries.forEach(System.out::println);
        } else {
            System.out.println("Não há Dados na lista para exibir");
        }
    }




    // ---------------------------------------------------------------------------------------------------------------------
    private void comentariosParaEstudar() {
        /*
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
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.dadosEpisodios().stream()
                        .map(d -> new Episodio(t.numero(), d))
                ).collect(Collectors.toList());

        System.out.println();
        episodios.forEach(System.out::println);

//        System.out.println("Digite um trecho do título do episodio");
//        String trechoTitulo = sc.nextLine();
//        final Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toLowerCase().contains(trechoTitulo.toLowerCase()))
//                .findFirst();
//        System.out.println(episodioBuscado);
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
//        System.out.println("\nTop 10 episódios");
//        dadosDadosEpisodios.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Primeiro filtro (N/A) " + e))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .peek(e -> System.out.println("Ordenação " + e))
//                .limit(10)
//                .peek(e -> System.out.println("Limite " + e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e -> System.out.println("Mapeamento " + e))
//                .forEach(System.out::println);

//        System.out.println("\nNúmeros");
//        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5);
//
//        int soma = numeros.stream()
//                .peek(n -> System.out.println("Elemento: " + n))
//                .map(n -> n * 2)
//                .peek(n -> System.out.println("Conteúdo depois do map: " + n))
//                .reduce(0, (total, numero) -> total + numero);
//
//        System.out.println("A soma dos números é: " + soma);
        // Avaliaçoes por temporada
        System.out.println("\nAvaliaçoes por temporada");
//        final Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
//                .collect(Collectors.groupingBy(Episodio::getTemporada,
//                        Collectors.averagingDouble(Episodio::getAvaliacao)));
//        System.out.println(avaliacoesPorTemporada);

        episodios.forEach(x -> {
            System.out.print(x.getTemporada());
            System.out.print(" | ");
            System.out.print(x.getNumeroEpisodio());
            System.out.print(" | ");
            System.out.println(x.getAvaliacao());
        });

        System.out.println("\nAvaliaçoes por temporada");
        final Map<Integer, Double> collect = episodios.stream()
                .filter(e -> e.getAvaliacao() != null)
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(
                        Collectors.groupingBy(Episodio::getTemporada ,
                                Collectors.averagingDouble(Episodio::getAvaliacao))
                );
        System.out.println(collect);

        System.out.println("\nEstatistica");
        final DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() != null)
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println(est);
        System.out.println("Média: " + est.getAverage());
        System.out.println("Melhor episodio: " + est.getMax());
        System.out.println("Pior episodio: " + est.getMin());
        System.out.println("Quantidade: " + est.getCount());
         */
    }
}
