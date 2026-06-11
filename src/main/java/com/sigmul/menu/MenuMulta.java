package com.sigmul.menu;

import com.sigmul.DAO.*;
import com.sigmul.model.*;
import com.sigmul.LeitorEntrada.LeitorEntrada;

import java.time.LocalDateTime;
import java.util.List;

public class MenuMulta {

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

    // Antes essa funcao apenas pedia a matricula e criava um Policial
    // "fake" so com esse numero, sem checar se ele existe no banco.
    // Agora listamos os policiais reais e o usuario escolhe pelo numero,
    // igual ja fazemos com veiculo, rodovia e infracao.
    private Policial selecionarPolicial() {
        System.out.println("\nPoliciais cadastrados:");

        List<Policial> policiais = policialDAO.listarTodos();

        if (policiais.isEmpty()) {
            System.out.println("Nenhum policial cadastrado. Cadastre um policial primeiro.");
            return null;
        }

        for (int i = 0; i < policiais.size(); i++) {
            Policial p = policiais.get(i);
            System.out.println((i + 1) + ". " + p.getNome() + " - " + p.getCargo()
                    + " | Matrícula: " + p.getMatricula());
        }

        System.out.print("Escolha o número do policial: ");
        int escolha = LeitorEntrada.lerInt();

        if (escolha < 1 || escolha > policiais.size()) {
            System.out.println("Opção inválida.");
            return null;
        }

        return policiais.get(escolha - 1);
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
    }
}