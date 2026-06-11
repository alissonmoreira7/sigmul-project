package com.sigmul.LeitorEntrada;

import java.util.Scanner;

public class LeitorEntrada {

    private static final Scanner scanner = new Scanner(System.in);

    public static int lerInt() {
        while (true) {
            try {
                String linha = scanner.nextLine().trim();
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.print("Digite apenas números inteiros: ");
            }
        }
    }

    public static String lerTextoObrigatorio() {
        while (true) {
            String texto = scanner.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.print("Campo obrigatório, digite um valor: ");
        }
    }

    public static String lerDocumento11Digitos(String rotulo) {
        while (true) {
            String valor = lerTextoObrigatorio();
            if (valor.matches("\\d{11}")) {
                return valor;
            }
            System.out.print(rotulo + " inválido! Digite exatamente 11 números: ");
        }
    }

    public static int lerIntNaoNegativo() {
        while (true) {
            int valor = lerInt();
            if (valor >= 0) {
                return valor;
            }
            System.out.print("O valor não pode ser negativo. Digite novamente: ");
        }
    }
}