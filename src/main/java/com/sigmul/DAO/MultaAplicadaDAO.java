package com.sigmul.DAO;

import com.sigmul.gestao_banco.ConexaoBanco;
import com.sigmul.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MultaAplicadaDAO {

    public int salvar(MultaAplicada multa, int idInfracao) {

        String sqlMulta = "INSERT INTO multa_aplicada (matricula_pol, placa_vei, cnh_moto, id_rod, km_multa) VALUES (?, ?, ?, ?, ?)";
        String sqlItem = "INSERT INTO item_multa (id_infra, id_multa) VALUES (?, ?)";

        try (Connection conn = ConexaoBanco.conectar()) {

            conn.setAutoCommit(false);

            int idMultaGerado;

            try (PreparedStatement stmtMulta = conn.prepareStatement(sqlMulta, Statement.RETURN_GENERATED_KEYS)) {
                stmtMulta.setInt(1, multa.getPolicial().getMatricula());
                stmtMulta.setString(2, multa.getVeiculo().getPlaca());
                stmtMulta.setString(3, multa.getMotorista().getCnh());
                stmtMulta.setInt(4, multa.getRodovia().getId());
                stmtMulta.setInt(5, multa.getKmMulta());
                stmtMulta.executeUpdate();

                try (ResultSet rs = stmtMulta.getGeneratedKeys()) {
                    if (rs.next()) {
                        idMultaGerado = rs.getInt(1);
                    } else {
                        throw new RuntimeException("Não foi possível obter o ID da multa gerada.");
                    }
                }
            }

            try (PreparedStatement stmtItem = conn.prepareStatement(sqlItem)) {
                stmtItem.setInt(1, idInfracao);
                stmtItem.setInt(2, idMultaGerado);
                stmtItem.executeUpdate();
            }

            // Tudo certo: confirma as duas inserções de uma vez
            conn.commit();
            System.out.println("Multa registrada com sucesso! ID: " + idMultaGerado);
            return idMultaGerado;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar multa: " + e.getMessage(), e);
        }
    }

    //Funcion Calculadora de Multas
    public double calcularTotalMultas(String cnh) {

        String sql = "SELECT calcular_total_multas(?)";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnh);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble(1); // pega o valor retornado pela function
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao executar function: " + e.getMessage(), e);
        }
        return 0;
    }

    public ResumoMotorista resumoMotorista(String cnh) {

        String sql = "SELECT * FROM resumo_motorista(?)";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnh);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new ResumoMotorista(
                        rs.getString("nome"),
                        rs.getLong("total_multas"),
                        rs.getDouble("valor_total"),
                        rs.getInt("pontos_totais"),
                        rs.getString("infracao_mais_comum")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao executar function resumo_motorista: " + e.getMessage(), e);
        }

        return null; // motorista sem multas
    }

    //Procedure atualizadora de pontos
    public void atualizarPontosMotorista(String cnh, int pontos) {
        try (Connection conn = ConexaoBanco.conectar();
             CallableStatement cs = conn.prepareCall("{call atualizar_pontos_motorista(?, ?)}")) {

            cs.setString(1, cnh);
            cs.setInt(2, pontos);
            cs.execute();
            System.out.println("Pontos atualizados via Procedure!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao executar procedure: " + e.getMessage(), e);
        }
    }

    public List<MultaAplicada> listarTodos() {

        String sql = """
            SELECT DISTINCT 
                m.id_multa,
                m.km_multa,
                m.datahora_multa,
                
                pol.matricula_pol,
                pol.nome_pol,
                pol.cargo_pol,

                v.placa_vei,

                mo.cnh_moto,
                mo.nome_moto,
                mo.pontoacumulados_moto,

                r.id_rod,
                r.codbr_rod,
                r.estado_rod,
                r.kms_cod

            FROM multa_aplicada m
            JOIN policial pol ON m.matricula_pol = pol.matricula_pol
            JOIN veiculo v ON m.placa_vei = v.placa_vei
            JOIN motorista mo ON m.cnh_moto = mo.cnh_moto
            JOIN rodovia r ON m.id_rod = r.id_rod
            ORDER BY m.id_multa
        """;

        List<MultaAplicada> lista = new ArrayList<>();

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Policial pol = new Policial();
                pol.setMatricula(rs.getInt("matricula_pol"));
                pol.setNome(rs.getString("nome_pol"));
                pol.setCargo(rs.getString("cargo_pol"));

                Veiculo v = new Veiculo();
                v.setPlaca(rs.getString("placa_vei"));

                Motorista mo = new Motorista();
                mo.setCnh(rs.getString("cnh_moto"));
                mo.setNome(rs.getString("nome_moto"));
                mo.setPontoAcumulado(rs.getInt("pontoacumulados_moto"));

                Rodovia r = new Rodovia();
                r.setId(rs.getInt("id_rod"));
                r.setCodigoBR(rs.getString("codbr_rod"));
                r.setEstado(rs.getString("estado_rod"));
                r.setKilometros(rs.getInt("kms_cod"));

                MultaAplicada multa = new MultaAplicada(
                        rs.getInt("id_multa"),
                        pol,
                        v,
                        mo,
                        r,
                        rs.getInt("km_multa"),
                        rs.getTimestamp("datahora_multa").toLocalDateTime()
                );

                lista.add(multa);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar multas: " + e.getMessage(), e);
        }

        return lista;
    }
    public void deletar(int idMulta) {
        String sqlItens = "DELETE FROM item_multa WHERE id_multa = ?";
        String sqlMulta = "DELETE FROM multa_aplicada WHERE id_multa = ?";

        try (Connection conn = ConexaoBanco.conectar()) {

            conn.setAutoCommit(false);

            try (PreparedStatement stmtItens = conn.prepareStatement(sqlItens)) {
                stmtItens.setInt(1, idMulta);
                stmtItens.executeUpdate();
            }

            try (PreparedStatement stmtMulta = conn.prepareStatement(sqlMulta)) {
                stmtMulta.setInt(1, idMulta);
                int linhasAfetadas = stmtMulta.executeUpdate();

                if (linhasAfetadas > 0) {
                    conn.commit();
                    System.out.println("Multa deletada com sucesso!");
                } else {
                    conn.rollback();
                    System.out.println("Nenhuma multa encontrada com o ID: " + idMulta);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar multa: " + e.getMessage(), e);
        }
    }
}
