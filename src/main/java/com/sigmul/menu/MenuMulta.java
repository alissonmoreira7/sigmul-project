package com.sigmul.menu;

import com.sigmul.DAO.*;
import com.sigmul.model.*;
import com.sigmul.LeitorEntrada.LeitorEntrada;

import java.util.List;

public class MenuMulta {

    private static final MotoristaDAO motoristaDAO = new MotoristaDAO();
    private static final PolicialDAO policialDAO = new PolicialDAO();
    private static final VeiculoDAO veiculoDAO = new VeiculoDAO();
    private static final RodoviaDAO rodoviaDAO = new RodoviaDAO();
    private static final InfracaoDAO infracaoDAO = new InfracaoDAO();
    private static final MultaAplicadaDAO multaDAO = new MultaAplicadaDAO();
    private static final ItemMultaDAO itemDAO = new ItemMultaDAO();

    public static void registrar() {

        System.out.println("\n--- Registrar Nova Multa ---");

        List<Motorista> motoristas = motoristaDAO.listarTodos();

        if (motoristas.isEmpty()) {
            System.out.println("Nenhum motorista cadastrado!");
            return;
        }

        System.out.println("\nMotoristas:");
        for (int i = 0; i < motoristas.size(); i++) {
            Motorista m = motoristas.get(i);
            System.out.println((i + 1) + " - " + m.getNome() + " (CNH: " + m.getCnh() + ")");
        }

        int escolhaMotorista = escolherIndice(motoristas.size());
        Motorista motorista = motoristas.get(escolhaMotorista);

        List<Policial> policiais = policialDAO.listarTodos();

        if (policiais.isEmpty()) {
            System.out.println("Nenhum policial cadastrado!");
            return;
        }

        System.out.println("\nPoliciais:");
        for (int i = 0; i < policiais.size(); i++) {
            Policial p = policiais.get(i);
            System.out.println((i + 1) + " - " + p.getNome());
        }

        int escolhaPolicial = escolherIndice(policiais.size());
        Policial policial = policiais.get(escolhaPolicial);

        Veiculo veiculo;

        while (true) {
            System.out.print("\nDigite a placa do veículo: ");
            String placa = LeitorEntrada.lerTextoObrigatorio();

            veiculo = veiculoDAO.buscarPorPlaca(placa);

            if (veiculo != null) {
                break;
            }

            System.out.println("Veículo não encontrado! Tente novamente.");
        }

        // ===============================
        // RODOVIA (escolha por lista)
        // ===============================
        List<Rodovia> rodovias = rodoviaDAO.listarTodos();

        if (rodovias.isEmpty()) {
            System.out.println("Nenhuma rodovia cadastrada!");
            return;
        }

        System.out.println("\nRodovias:");
        for (int i = 0; i < rodovias.size(); i++) {
            Rodovia r = rodovias.get(i);
            System.out.println((i + 1) + " - " + r.getCodigoBR());
        }

        int escolhaRodovia = escolherIndice(rodovias.size());
        Rodovia rodovia = rodovias.get(escolhaRodovia);

        // ===============================
        // KM
        // ===============================
        System.out.print("KM da ocorrência: ");
        int km = LeitorEntrada.lerIntNaoNegativo();

        // ===============================
        // CRIAR MULTA
        // ===============================
        MultaAplicada multa = new MultaAplicada(
                0,
                policial,
                veiculo,
                motorista,
                rodovia,
                km,
                null
        );

        int idMulta = multaDAO.salvar(multa);

        // ===============================
        // INFRAÇÕES
        // ===============================
        List<Infracao> infracoes = infracaoDAO.listarTodos();

        if (infracoes.isEmpty()) {
            System.out.println("Nenhuma infração cadastrada!");
            return;
        }

        System.out.print("\nQuantas infrações nessa multa? ");
        int quantidade = LeitorEntrada.lerIntNaoNegativo();

        for (int i = 0; i < quantidade; i++) {

            System.out.println("\nInfrações:");
            for (int j = 0; j < infracoes.size(); j++) {
                Infracao inf = infracoes.get(j);
                System.out.println((j + 1) + " - " + inf.getNome());
            }

            int escolhaInfracao = escolherIndice(infracoes.size());
            Infracao infracaoEscolhida = infracoes.get(escolhaInfracao);

            MultaAplicada multaRef = new MultaAplicada();
            multaRef.setId(idMulta);

            ItemMulta item = new ItemMulta(infracaoEscolhida, multaRef);

            itemDAO.salvar(item);
        }

        System.out.println("\n✅ Multa registrada com sucesso!");
    }

    // ===============================
    // ✅ LISTAR MULTAS
    // ===============================
    public static void listar() {

        System.out.println("\n--- Lista de Multas ---");

        List<MultaAplicada> multas = multaDAO.listarTodos();

        if (multas.isEmpty()) {
            System.out.println("Nenhuma multa cadastrada.");
            return;
        }

        for (MultaAplicada m : multas) {
            System.out.println(
                    "ID: " + m.getId() +
                            " | Motorista: " + m.getMotorista().getNome() +
                            " | Placa: " + m.getVeiculo().getPlaca() +
                            " | KM: " + m.getKm()
            );

            List<ItemMulta> itens = itemDAO.listarPorMulta(m.getId());

            for (ItemMulta item : itens) {
                System.out.println("   - Infração: " + item.getInfracao().getNome());
            }
        }
    }

    // ===============================
    // ✅ MÉTODO AUXILIAR
    // ===============================
    private static int escolherIndice(int tamanho) {
        int opcao;
        while (true) {
            System.out.print("Escolha: ");
            opcao = LeitorEntrada.lerInt();

            if (opcao >= 1 && opcao <= tamanho) {
                return opcao - 1;
            }

            System.out.println("Opção inválida!");
        }
    }
}