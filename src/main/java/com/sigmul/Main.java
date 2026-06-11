package com.sigmul;

import com.sigmul.menu.MenuMulta;
import com.sigmul.menu.MenuPolicial;
import com.sigmul.LeitorEntrada.LeitorEntrada;

public class Main {
    public static void main(String[] args) {

        int opcao;

        do {
            System.out.println("\n========== SIGMUL ==========");
            System.out.println("1. Gerenciar Multas");
            System.out.println("2. Consultar Policiais");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");

            opcao = LeitorEntrada.lerInt();

            switch (opcao) {
                case 1 -> new MenuMulta().exibir();
                case 2 -> MenuPolicial.exibir();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }
}
