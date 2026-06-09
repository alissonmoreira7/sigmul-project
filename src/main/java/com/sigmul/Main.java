package com.sigmul;

import java.util.Scanner;
import java.util.List;
import com.sigmul.DAO.MotoristaDAO;
import com.sigmul.model.Motorista;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        MotoristaDAO motoristaDAO = new MotoristaDAO();

        int opcao;

        do {
            System.out.println("\n========== SIGMUL ==========");
            System.out.println("1. Cadastrar motorista");
            System.out.println("2. Listar motoristas");
            System.out.println("3. Atualizar pontos de motorista");
            System.out.println("0. Sair");
            System.out.println("============================");
            System.out.print("Escolha uma opção: ");

            opcao = lerInt();

            switch (opcao) {

                case 1 -> cadastrarMotorista(motoristaDAO);
                case 2 -> listarMotoristas(motoristaDAO);
                case 3 -> atualizarPontos(motoristaDAO);
                case 0 -> System.out.println("Encerrando o sistema. Até logo!");
                default -> System.out.println("Opção inválida! Digite um número do menu.");
            }

        } while (opcao != 0);

        scanner.close();
    }

    static void cadastrarMotorista(MotoristaDAO dao) {
        System.out.println("\n--- Cadastrar Motorista ---");

        String cnh;
        while (true) {
            System.out.print("CNH (11 dígitos numéricos): ");
            cnh = lerTextoObrigatorio();
            if (cnh.matches("\\d{11}")) break; // se válido, sai do loop
            System.out.println("CNH inválida! Digite exatamente 11 números.");
        }

        String cpf;
        while (true) {
            System.out.print("CPF (11 dígitos numéricos): ");
            cpf = lerTextoObrigatorio();
            if (cpf.matches("\\d{11}")) break;
            System.out.println("CPF inválido! Digite exatamente 11 números.");
        }

        System.out.print("Nome completo: ");
        String nome = lerTextoObrigatorio();


        int pontos;
        while (true) {
            System.out.print("Pontos acumulados: ");
            pontos = lerInt();
            if (pontos >= 0) break;
            System.out.println("Pontos não podem ser negativos! Digite 0 ou mais.");
        }

        Motorista novoMotorista = new Motorista(cnh, cpf, nome, pontos);
        dao.salvar(novoMotorista);
    }

    static void listarMotoristas(MotoristaDAO dao) {
        System.out.println("\n--- Lista de Motoristas ---");

        List<Motorista> motoristas = dao.listarTodos();

        if (motoristas.isEmpty()) {
            System.out.println("Nenhum motorista cadastrado.");
        } else {
            for (Motorista m : motoristas) {
                System.out.println(
                        "CNH: " + m.getCnh() +
                                " | CPF: " + m.getCpf() +
                                " | Nome: " + m.getNome() +
                                " | Pontos: " + m.getPontoAcumulado()
                );
            }
        }
    }

    static void atualizarPontos(MotoristaDAO dao) {
        System.out.println("\n--- Atualizar Pontos ---");

        String cnh;
        while (true) {
            System.out.print("CNH do motorista: ");
            cnh = lerTextoObrigatorio();
            if (cnh.matches("\\d{11}")) break;
            System.out.println("CNH inválida! Digite exatamente 11 números.");
        }

        int pontos;
        while (true) {
            System.out.print("Novo valor de pontos: ");
            pontos = lerInt();
            if (pontos >= 0) break;
            System.out.println("Pontos não podem ser negativos! Digite 0 ou mais.");
        }

        dao.atualizarPontos(cnh, pontos);
    }

    static int lerInt() {
        while (true) {
            try {
                String linha = scanner.nextLine().trim();

                return Integer.parseInt(linha);

            } catch (NumberFormatException e) {

                System.out.print("Digite apenas números inteiros: ");
            }
        }
    }

    static String lerTextoObrigatorio() {
        while (true) {
            String texto = scanner.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.print("Campo obrigatório, digite um valor: ");
        }
    }
}
