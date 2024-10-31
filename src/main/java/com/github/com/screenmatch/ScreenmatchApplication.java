package com.github.com.screenmatch;

import com.github.com.screenmatch.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	private final Principal principal;

    public ScreenmatchApplication(Principal principal) {
        this.principal = principal;
    }

    public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("\n" + principal.exibeMenu());

//		final List<Temporada> temporadass = new ArrayList<>();
//		for (int i = 1; i <= serie.totalTemporadas(); i++) {
//			json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&Season=" + i + "&apikey=6585022c");
//			final Temporada temporada = conversor.obterDados(json , Temporada.class);
//			temporadass.add(temporada);
//		}
//		temporadass.forEach(System.out::println);

	}
}
