package com.github.com.screenmatch.principal;

import java.util.Arrays;
import java.util.List;

public class EstudoLambda {
    public static void main(String[] args) {

        List<String> nomes = Arrays.asList("Jacque", "Iasmin", "Paulo", "Rodrigo", "Nico");

        nomes.stream()
                .sorted()
                .limit(3)
                .forEach(System.out::println);

        System.out.println();

        nomes.stream()
                .sorted()
                .limit(3)
                .filter(n -> n.startsWith("N"))
                .map(n -> n.toUpperCase())
                .forEach(System.out::println);
    }
}


