package com.github.com.screenmatch;

import com.github.com.screenmatch.models.Serie;
import com.github.com.screenmatch.services.ConsumoApi;
import com.github.com.screenmatch.services.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
	//	final ConsumoApi consumoApi = new ConsumoApi();
		String json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=6585022c"); // &Season=1
//		System.out.println(json);
//		json = consumoApi.obterDados("https://coffee.alexflipnote.dev/random.json");
		// System.out.println(json);
		final ConverteDados conversor = new ConverteDados();
		final Serie serie = conversor.obterDados(json , Serie.class);
		System.out.println(serie);
	}
}
