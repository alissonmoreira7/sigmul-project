package com.sigmul.menu;

import com.sigmul.DAO.MotoristaDAO;
import com.sigmul.DAO.MultaAplicadaDAO;
import com.sigmul.LeitorEntrada.LeitorEntrada;
import com.sigmul.model.Motorista;
import com.sigmul.model.ResumoMotorista;

import java.util.List;

public class MenuMotorista {

    private static final MotoristaDAO motoristaDAO = new MotoristaDAO();
    private static final MultaAplicadaDAO multaDAO = new MultaAplicadaDAO();

    public static void exibir() {
        int opcao;

        do {
            System.out.println("\n--- Gerenciar Motoristas ---");
            System.out.println("1. Listar motoristas");
            System.out.println("2. Atualizar pontos");
            System.out.println("3. Ver total de multas de um motorista");
            System.out.println("4. Resumo completo do motorista");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");

            opcao = LeitorEntrada.lerInt();

            switch (opcao) {
                case 1 -> listar();
                case 2 -> atualizarPontos();
                case 3 -> totalMultas();
                case 4 -> resumoMotorista();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    private static void listar() {
        System.out.println("\n--- Lista de Motoristas ---");

        List<Motorista> motoristas = motoristaDAO.listarTodos();

        if (motoristas.isEmpty()) {
            System.out.println("Nenhum motorista cadastrado.");
            return;
        }

        for (Motorista m : motoristas) {
            System.out.println(
                    "CNH: " + m.getCnh() +
                            " | Nome: " + m.getNome() +
                            " | Pontos: " + m.getPontoAcumulado()
            );
        }
    }

    private static void atualizarPontos() {
        System.out.println("\n--- Atualizar Pontos via Procedure ---");

        System.out.print("CNH do motorista: ");
        String cnh = LeitorEntrada.lerDocumento11Digitos("CNH");

        System.out.print("Pontos a adicionar: ");
        int pontos = LeitorEntrada.lerIntNaoNegativo();

        multaDAO.atualizarPontosMotorista(cnh, pontos);
    }

    private static void totalMultas() {
        System.out.println("\n--- Total de Multas por Motorista ---");

        System.out.print("CNH do motorista: ");
        String cnh = LeitorEntrada.lerDocumento11Digitos("CNH");

        double total = multaDAO.calcularTotalMultas(cnh);
        System.out.printf("Total acumulado em multas: R$ %.2f%n", total);
    }

    private static void resumoMotorista() {
        System.out.println("\n--- Resumo Completo do Motorista ---");

        System.out.print("CNH do motorista: ");
        String cnh = LeitorEntrada.lerDocumento11Digitos("CNH");

        ResumoMotorista r = multaDAO.resumoMotorista(cnh);

        if (r == null) {
            System.out.println("Motorista sem multas registradas ou CNH não encontrada.");
            return;
        }

        System.out.println("====================================");
        System.out.println("Motorista            : " + r.getNome());
        System.out.println("Total de multas      : " + r.getTotalMultas());
        System.out.printf ("Valor total          : R$ %.2f%n", r.getValorTotal());
        System.out.println("Pontos acumulados    : " + r.getPontosTotais());
        System.out.println("Infração mais cometida: " + r.getInfracaoMaisComum());
        System.out.println("====================================");
    }
}