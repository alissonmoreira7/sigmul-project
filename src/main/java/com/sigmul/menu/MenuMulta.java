package com.sigmul.menu;

import com.sigmul.DAO.*;
<<<<<<< HEAD
import com.sigmul.model.*;
import com.sigmul.LeitorEntrada.LeitorEntrada;

=======
import com.sigmul.LeitorEntrada.LeitorEntrada;
import com.sigmul.model.*;

import java.time.LocalDateTime;
>>>>>>> 6671099aa10199b404a8843ce6fa5e55c2f3e610
import java.util.List;

public class MenuMulta {

<<<<<<< HEAD
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
=======
    private final MultaAplicadaDAO multaDAO = new MultaAplicadaDAO();
    private final VwMultaAplicadaDAO vwMultaDAO = new VwMultaAplicadaDAO();
    private final PolicialDAO policialDAO = new PolicialDAO();
    private final VeiculoDAO veiculoDAO = new VeiculoDAO();
    private final RodoviaDAO rodoviaDAO = new RodoviaDAO();
    private final InfracaoDAO infracaoDAO = new InfracaoDAO();

    public void exibir() {
        int opcao;

        do {
            System.out.println("\n======= MENU DE MULTAS =======");
            System.out.println("1. Registrar nova multa");
            System.out.println("2. Listar todas as multas");
            System.out.println("3. Buscar multa por ID");
            System.out.println("4. Buscar multas por motorista (CPF)");
            System.out.println("5. Buscar multas por placa");
            System.out.println("0. Voltar");
            System.out.println("==============================");
            System.out.print("Escolha uma opção: ");

            opcao = LeitorEntrada.lerInt();

            switch (opcao) {
                case 1 -> registrarMulta();
                case 2 -> listarTodas();
                case 3 -> buscarPorId();
                case 4 -> buscarPorMotorista();
                case 5 -> buscarPorPlaca();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida! Digite um número do menu.");
            }

        } while (opcao != 0);
    }

    private void registrarMulta() {
        System.out.println("\n--- Registrar Nova Multa ---");

        Policial policial = selecionarPolicial();
        if (policial == null) return;

        Veiculo veiculo = selecionarVeiculo();
        if (veiculo == null) return;

        Motorista motorista = veiculo.getMotorista();
        System.out.println("Motorista vinculado ao veículo: " + motorista.getNome() + " | CNH: " + motorista.getCnh());

        Rodovia rodovia = selecionarRodovia();
        if (rodovia == null) return;

        System.out.print("KM da ocorrência: ");
        int km = LeitorEntrada.lerIntNaoNegativo();

        Infracao infracao = selecionarInfracao();
        if (infracao == null) return;

        System.out.println("\n--- Confirmação ---");
        System.out.println("Policial  : " + policial.getNome() + " (matrícula " + policial.getMatricula() + ")");
        System.out.println("Veículo   : " + veiculo.getPlaca() + " - " + veiculo.getModelo());
        System.out.println("Motorista : " + motorista.getNome() + " | CNH: " + motorista.getCnh());
        System.out.println("Rodovia   : " + rodovia.getCodigoBR() + " - " + rodovia.getEstado() + " | KM: " + km);
        System.out.println("Infração  : " + infracao.getNome() + " | Valor: R$ " + infracao.getValorInfracao() + " | Pontos: " + infracao.getPontosInfracao());
        System.out.print("Confirmar registro? (1 = Sim / 0 = Cancelar): ");

        int confirma = LeitorEntrada.lerInt();
        if (confirma != 1) {
            System.out.println("Registro cancelado.");
            return;
        }

        MultaAplicada multa = new MultaAplicada();
        multa.setPolicial(policial);
        multa.setVeiculo(veiculo);
        multa.setMotorista(motorista);
        multa.setRodovia(rodovia);
        multa.setKmMulta(km);
        multa.setDataHoraMulta(LocalDateTime.now());

        multaDAO.salvar(multa, infracao.getId());
    }

    private void listarTodas() {
        System.out.println("\n--- Lista de Multas ---");

        List<VwMultaAplicada> multas = vwMultaDAO.listarTodos();

        if (multas.isEmpty()) {
            System.out.println("Nenhuma multa registrada.");
            return;
        }

        for (VwMultaAplicada m : multas) {
            exibirLinhaMulta(m);
        }

        System.out.println("Total: " + multas.size() + " multa(s).");
    }

    private void buscarPorId() {
        System.out.println("\n--- Buscar Multa por ID ---");
        System.out.print("ID da multa: ");
        int id = LeitorEntrada.lerInt();

        VwMultaAplicada multa = vwMultaDAO.buscarPorId(id);

        if (multa == null) {
            System.out.println("Nenhuma multa encontrada com o ID " + id + ".");
        } else {
            exibirLinhaMulta(multa);
        }
    }

    private void buscarPorMotorista() {
        System.out.println("\n--- Buscar Multas por Motorista ---");
        System.out.print("CPF do motorista (11 dígitos): ");
        String cpf = LeitorEntrada.lerDocumento11Digitos("CPF");

        List<VwMultaAplicada> multas = vwMultaDAO.listarPorMotorista(cpf);

        if (multas.isEmpty()) {
            System.out.println("Nenhuma multa encontrada para o CPF " + cpf + ".");
        } else {
            for (VwMultaAplicada m : multas) {
                exibirLinhaMulta(m);
            }
            System.out.println("Total: " + multas.size() + " multa(s).");
        }
    }

    private void buscarPorPlaca() {
        System.out.println("\n--- Buscar Multas por Placa ---");
        System.out.print("Placa do veículo: ");
        String placa = LeitorEntrada.lerTextoObrigatorio().toUpperCase();

        List<VwMultaAplicada> multas = vwMultaDAO.listarPorPlaca(placa);

        if (multas.isEmpty()) {
            System.out.println("Nenhuma multa encontrada para a placa " + placa + ".");
        } else {
            for (VwMultaAplicada m : multas) {
                exibirLinhaMulta(m);
            }
            System.out.println("Total: " + multas.size() + " multa(s).");
        }
    }

    private Policial selecionarPolicial() {
        System.out.print("Matrícula do policial: ");
        int matricula = LeitorEntrada.lerInt();

        Policial policial = new Policial();
        policial.setMatricula(matricula);
        return policial;
    }

    private Veiculo selecionarVeiculo() {
        System.out.println("\nVeículos cadastrados:");

        List<Veiculo> veiculos = veiculoDAO.listarTodos();

        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo cadastrado. Cadastre um veículo primeiro.");
            return null;
        }

        for (int i = 0; i < veiculos.size(); i++) {
            Veiculo v = veiculos.get(i);
            System.out.println((i + 1) + ". " + v.getPlaca() + " - " + v.getModelo()
                    + " | Motorista: " + v.getMotorista().getNome());
        }

        System.out.print("Escolha o número do veículo: ");
        int escolha = LeitorEntrada.lerInt();

        if (escolha < 1 || escolha > veiculos.size()) {
            System.out.println("Opção inválida.");
            return null;
        }

        return veiculos.get(escolha - 1);
    }

    private Rodovia selecionarRodovia() {
        System.out.println("\nRodovias cadastradas:");

        List<Rodovia> rodovias = rodoviaDAO.listarTodos();

        if (rodovias.isEmpty()) {
            System.out.println("Nenhuma rodovia cadastrada. Cadastre uma rodovia primeiro.");
            return null;
        }

        for (int i = 0; i < rodovias.size(); i++) {
            Rodovia r = rodovias.get(i);
            System.out.println((i + 1) + ". " + r.getCodigoBR() + " - " + r.getEstado()
                    + " | " + r.getKilometros() + " km");
        }

        System.out.print("Escolha o número da rodovia: ");
        int escolha = LeitorEntrada.lerInt();

        if (escolha < 1 || escolha > rodovias.size()) {
            System.out.println("Opção inválida.");
            return null;
        }

        return rodovias.get(escolha - 1);
    }

    private Infracao selecionarInfracao() {
        System.out.println("\nInfrações disponíveis:");

        List<Infracao> infracoes = infracaoDAO.listarTodos();

        if (infracoes.isEmpty()) {
            System.out.println("Nenhuma infração cadastrada. Cadastre uma infração primeiro.");
            return null;
        }

        for (int i = 0; i < infracoes.size(); i++) {
            Infracao inf = infracoes.get(i);
            System.out.println((i + 1) + ". " + inf.getNome()
                    + " | R$ " + inf.getValorInfracao()
                    + " | " + inf.getPontosInfracao() + " ponto(s)");
        }

        System.out.print("Escolha o número da infração: ");
        int escolha = LeitorEntrada.lerInt();

        if (escolha < 1 || escolha > infracoes.size()) {
            System.out.println("Opção inválida.");
            return null;
        }

        return infracoes.get(escolha - 1);
    }

    private void exibirLinhaMulta(VwMultaAplicada m) {
        System.out.println("------------------------------------");
        System.out.println("ID       : " + m.getIdMulta());
        System.out.println("Data/Hora: " + m.getDataHora());
        System.out.println("Motorista: " + m.getMotorista() + " | CPF: " + m.getCpfMoto());
        System.out.println("Veículo  : " + m.getPlaca() + " - " + m.getVeiculo());
        System.out.println("Infração : " + m.getInfracao() + " | R$ " + m.getValor());
        System.out.println("Rodovia  : " + m.getRodovia());
        System.out.println("Policial : " + m.getPolicial());
>>>>>>> 6671099aa10199b404a8843ce6fa5e55c2f3e610
    }
}