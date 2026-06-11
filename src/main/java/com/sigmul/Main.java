package com.sigmul;

import com.sigmul.menu.MenuMulta;
import com.sigmul.menu.MenuPolicial;
import com.sigmul.LeitorEntrada.LeitorEntrada;

public class Main {
    public static void main(String[] args) {

        int opcao;

        do {
            System.out.println("\n========== SIGMUL ==========");
            System.out.println("1. Registrar Multa");
            System.out.println("2. Listar Multas");
            System.out.println("3. Consultar Policiais");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");

            opcao = LeitorEntrada.lerInt();

            switch (opcao) {
                case 1 -> MenuMulta.registrar();
                case 2 -> MenuMulta.listar();
                case 3 -> MenuPolicial.exibir();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }
}