package org.example.screenmetch2.principal;

import org.example.screenmetch2.model.DadosEpisodio;
import org.example.screenmetch2.model.DadosSerie;
import org.example.screenmetch2.model.DadosTemporada;
import org.example.screenmetch2.model.Episodio;
import org.example.screenmetch2.servicos.ConsumirApi;
import org.example.screenmetch2.servicos.ConverterDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Menu {

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String APIKEY =  "&apikey=9dcffd08";
    private ConverterDados converterDados = new ConverterDados();
    private Scanner sc = new Scanner(System.in);
    private ConsumirApi consumirApi = new ConsumirApi();



    public void exibeMenu(){

        System.out.print("Digite um nome da série para busca: ");
        String nome = sc.nextLine();
        var json = consumirApi.obterDados(ENDERECO + nome.replace(" ", "+") +APIKEY);
        DadosSerie dadosSerie = converterDados.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        List<DadosTemporada> Temporadas =  new ArrayList<>();
        for (int i = 1; i<= dadosSerie.totalTemporadas(); i++){
            json = consumirApi.obterDados(ENDERECO + nome.replace(" ", "+") + "&season=" + i + APIKEY);
            DadosTemporada dadosTemporada= converterDados.obterDados(json, DadosTemporada.class);
            Temporadas.add(dadosTemporada);

        }
        Temporadas.forEach(System.out::println);

//        List<DadosEpisodio> Dadosepisodios =  Temporadas.stream().flatMap(t -> t.episodios().stream()).collect(Collectors.toList());
//        System.out.println("\nTop 10 episodios");
//        Dadosepisodios.stream().filter(e -> !e.avaliacao().equalsIgnoreCase("N/A")).peek(e -> System.out.println("Primeiro filtro(N/A) " + e)).sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed()).peek(e -> System.out.println("Ordenação " + e)).limit(10).peek(e -> System.out.println("Limite " + e)).map(e -> e.title().toUpperCase()).peek(e -> System.out.println("Mapeamento " + e)).forEach(System.out::println);

        List<Episodio> episodios = Temporadas.stream()
                .flatMap(t -> t.episodios().stream().map(d -> new Episodio(t.numero(), d)))
                .collect(Collectors.toList());
        episodios.forEach(System.out::println);

        System.out.println("Digite um trecho do título do episódio");
        var trechoTitulo = sc.nextLine();
        Optional<Episodio> episodoBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();
        if(episodoBuscado.isPresent()){
            System.out.println("Episódio encontrado!");
            System.out.println("Temporada: " + episodoBuscado.get().getTemporada());
        }
        else {
            System.out.println("Episódio não encontrado!");
        }
//
//        System.out.println("A partir de que ano você quer ver os episodios? ");
//        int ano = sc.nextInt();
//        sc.nextLine();
//
//        LocalDate dataBuscada = LocalDate.of(ano, 1, 1);
//
//        DateTimeFormatter formatador  = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream().filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBuscada))
//                .forEach(e -> System.out.println(
//                        "Temporada" + e.getTemporada() + "Episodio: " + e.getTitulo() + " Data lançameto: " + e.getDataLancamento().format(formatador)
//                ));
//
        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream().filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)));

        System.out.println(avaliacoesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println("Média: " + est.getAverage());
        System.out.println("Melhor episódio: " + est.getMax());
        System.out.println("Pior episódio: " +  est.getMin());
        System.out.println("Quantidade:  " + est.getCount());








    }
}
