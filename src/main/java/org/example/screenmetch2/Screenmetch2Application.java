package org.example.screenmetch2;

import org.example.screenmetch2.model.DadosEpisodio;
import org.example.screenmetch2.model.DadosSerie;
import org.example.screenmetch2.model.DadosTemporada;
import org.example.screenmetch2.principal.Menu;
import org.example.screenmetch2.servicos.ConsumirApi;
import org.example.screenmetch2.servicos.ConverterDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class  Screenmetch2Application implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(Screenmetch2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
         Menu menu = new Menu();
         menu.exibeMenu();




    }
}
