package com.github.com.screenmatch;

import com.github.com.screenmatch.models.Episodio;
import com.github.com.screenmatch.models.Serie;
import com.github.com.screenmatch.models.Temporada;
import com.github.com.screenmatch.services.ConsumoApi;
import com.github.com.screenmatch.services.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	private final ConsumoApi consumoApi;

    public ScreenmatchApplication(ConsumoApi consumoApi) {
        this.consumoApi = consumoApi;
    }

    public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		String json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=6585022c");
//		System.out.println(json);
//		json = consumoApi.obterDados("https://coffee.alexflipnote.dev/random.json");

		final ConverteDados conversor = new ConverteDados();
		final Serie serie = conversor.obterDados(json , Serie.class);
		System.out.println(serie);

		json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&Season=1&episode=2&apikey=6585022c");
		final Episodio episodio = conversor.obterDados(json , Episodio.class);
		System.out.println(episodio);

		json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&Season=1&apikey=6585022c");
		final Temporada temporadas = conversor.obterDados(json , Temporada.class);
		System.out.println(temporadas);

		final List<Temporada> temporadass = new ArrayList<>();
		for (int i = 1; i <= serie.totalTemporadas(); i++) {
			json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&Season=" + i + "&apikey=6585022c");
			final Temporada temporada = conversor.obterDados(json , Temporada.class);
			temporadass.add(temporada);
		}
		temporadass.forEach(System.out::println);

	}
}
