package org.example.screenmetch2;

import org.example.screenmetch2.model.DadosSerie;
import org.example.screenmetch2.servicos.ConsumirApi;
import org.example.screenmetch2.servicos.ConverterDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class  Screenmetch2Application implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(Screenmetch2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var consumirApi = new ConsumirApi();
        var json = consumirApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=9dcffd08");
        System.out.println(json);
        ConverterDados converterDados = new ConverterDados();
        DadosSerie dadosSerie = converterDados.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);
    }
}
