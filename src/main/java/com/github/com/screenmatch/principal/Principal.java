package com.github.com.screenmatch.principal;

import com.github.com.screenmatch.models.DadosSerie;
import com.github.com.screenmatch.models.Episodio;
import com.github.com.screenmatch.models.Serie;
import com.github.com.screenmatch.models.Temporada;
import com.github.com.screenmatch.models.enums.Categoria;
import com.github.com.screenmatch.repositories.ISerieRepository;
import com.github.com.screenmatch.services.ConsumoApi;
import com.github.com.screenmatch.services.ConverteDados;
import com.github.com.screenmatch.services.ISerieService;
import com.github.com.screenmatch.utils.Mensagens;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class Principal {

    private final ConsumoApi consumoApi;
    private final ConverteDados conversor;
    private final Scanner sc = new Scanner(System.in);
    private static final String URL = "https://www.omdbapi.com/?t=";
    private static final String API_KEY = System.getenv("YOUR_API_KEY");
    private List<Serie> series = new ArrayList<>();
    private final ISerieService serieService;
    private final ISerieRepository serieRepository;

    public Principal(ConsumoApi consumoApi , ConverteDados conversor , ISerieService serieService , ISerieRepository serieRepository) {
        this.consumoApi = consumoApi;
        this.conversor = conversor;
        this.serieService = serieService;
        this.serieRepository = serieRepository;
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
                case 4 -> buscarSeriePorTitulo();
                case 5 -> buscarSeriesPorAtor();
                case 6 -> buscarSeriesPorAtorGreaterThanEquals();
                case 7 -> buscarTop5Series();
                case 8 -> buscarSeriesPorCategoria();
                case 9 -> filtrarSeriesPorTemporadaEAvaliacao();
                case 10 -> seriePorTemporadaEAvaliacaoSQL();
                case 11 -> seriePorTemporadaEAvaliacaoJPQL();
                case 12 -> buscarEpisodioPorTrecho();
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void buscarEpisodioPorTrecho() {
        System.out.print("\nQual o nome do episódio para busca? : ");
        String trechoEpisodio = sc.nextLine();
        List<Episodio> episodiosEncontrados = serieRepository.episodiosPorTrecho(trechoEpisodio);

        episodiosEncontrados.forEach(e ->
                System.out.printf("Série: %s - Temporada %s - Episódio %s - %s\n" ,
                        e.getSerie().getTitulo() ,
                        e.getTemporada() ,
                        e.getNumeroEpisodio() ,
                        e.getTitulo()
                )
        );
    }

    private void seriePorTemporadaEAvaliacaoJPQL() {
        System.out.println("Filtrar séries até quantas temporadas? ");
        var totalTemporadas = sc.nextInt();
        sc.nextLine();
        System.out.println("Com avaliação a partir de que valor? ");
        var avaliacao = sc.nextDouble();
        sc.nextLine();
        final List<Serie> filtroSeries = serieRepository.seriesPorTemporadaEAvaliacaoJPQL(totalTemporadas , avaliacao);
        System.out.println("*** Séries filtradas ***");
        filtroSeries.forEach(s ->
                System.out.println(s.getTitulo() + "  - avaliação: " + s.getAvaliacao()));
    }

    private void seriePorTemporadaEAvaliacaoSQL() {
        System.out.println("Filtrar séries até quantas temporadas? ");
        var totalTemporadas = sc.nextInt();
        sc.nextLine();
        System.out.println("Com avaliação a partir de que valor? ");
        var avaliacao = sc.nextDouble();
        sc.nextLine();
        final List<Serie> filtroSeries = serieRepository.seriesPorTemporadaEAvaliacaoSQL(totalTemporadas , avaliacao);
        System.out.println("*** Séries filtradas ***");
        filtroSeries.forEach(s ->
                System.out.println(s.getTitulo() + "  - avaliação: " + s.getAvaliacao()));
    }

    private void filtrarSeriesPorTemporadaEAvaliacao(){
        System.out.println("Filtrar séries até quantas temporadas? ");
        var totalTemporadas = sc.nextInt();
        sc.nextLine();
        System.out.println("Com avaliação a partir de que valor? ");
        var avaliacao = sc.nextDouble();
        sc.nextLine();
        final List<Serie> filtroSeries = serieRepository.findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(totalTemporadas, avaliacao);
        System.out.println("*** Séries filtradas ***");
        filtroSeries.forEach(s ->
                System.out.println(s.getTitulo() + "  - avaliação: " + s.getAvaliacao()));
    }


    private void buscarSeriesPorCategoria() {
        System.out.print("\nQual categoria/gênero deseja buscar?: ");
        String nomeGenero = sc.nextLine();
        Categoria categoria = Categoria.fromStringPortugues(nomeGenero);
        final List<Serie> seriesPorCategoria = serieRepository.findByGenero(categoria);
        System.out.println("\nSéries da categoria " + nomeGenero);
        seriesPorCategoria.forEach(System.out::println);
    }

    private void buscarTop5Series() {
        final List<Serie> seriesTop = serieRepository.findTop5ByOrderByAvaliacaoDesc();
        seriesTop.forEach(s ->
                System.out.println(s.getTitulo() + " - avaliação: " + s.getAvaliacao()));
    }

    private void buscarSeriesPorAtorGreaterThanEquals() {
        System.out.print("\nQual o nome do ator para busca: ");
        String nomeAtor = sc.nextLine();
        System.out.print("\nAvaliacoes a partir de que valor: ");
        Double avaliacao = sc.nextDouble();
        final List<Serie> seriesEncontradas = serieRepository.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual (nomeAtor , avaliacao);
        System.out.println("\nSéries em que " + nomeAtor + " trabalhou: ");
        seriesEncontradas.forEach(s ->
                System.out.println(s.getTitulo() + " - avaliação: " + s.getAvaliacao()));
    }

    private void buscarSeriesPorAtor() {
        System.out.print("\nQual o nome do ator para busca: ");
        String nomeAtor = sc.nextLine();
        final List<Serie> seriesEncontradas = serieRepository.findByAtoresContainingIgnoreCase(nomeAtor);
        System.out.println("\nSéries em que " + nomeAtor + " trabalhou: ");
        seriesEncontradas.forEach(s ->
                System.out.println(s.getTitulo() + " - avaliação: " + s.getAvaliacao()));
    }

    private void buscarSeriePorTitulo() {
        System.out.print("\nEscolha uma série pelo nome: ");
        String nomeSerie = sc.nextLine();
        final Optional<Serie> serieBuscada = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBuscada.isPresent()) {
            System.out.println("Dados da série: " + serieBuscada.get());
        } else {
            System.out.println("Série nao encontrada!");
        }

    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        serieService.criarSerie(dados);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.print("\nDigite o nome da série para busca: ");
        String nomeSerie = sc.nextLine();
        String uri = URL + nomeSerie.replace(" " , "+") + "&apikey=" + API_KEY;
        var json = consumoApi.obterDados(uri);
        return conversor.obterDados(json , DadosSerie.class);
    }

    private void buscarEpisodioPorSerie() {
        listarSeriesBuscadas();
        System.out.println("Escolha uma série pelo nome: ");
        final String nomeSerie = sc.nextLine();

        final Optional<Serie> serie = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);

        if (serie.isPresent()) {
            final Serie serieEncontrada = serie.get();
            final List<Temporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                final String endereco = URL + serieEncontrada.getTitulo().replace(" " , "+") + "&season=" + i + "&apikey=" + API_KEY;
                var json = consumoApi.obterDados(endereco);

                final Temporada dadosTemporada = conversor.obterDados(json , Temporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            final List<Episodio> episodios = temporadas.stream()
                    .flatMap(dadosTemporada -> dadosTemporada.dadosEpisodios().stream()
                            .map(episodio -> new Episodio(dadosTemporada.numero() , episodio))).collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            System.out.println(serieEncontrada);
            serieRepository.save(serieEncontrada);
        } else {
            System.out.println("Série não encontrada.");
        }
    }

    private void listarSeriesBuscadas() {
        series = serieService.buscarSeries();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
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
