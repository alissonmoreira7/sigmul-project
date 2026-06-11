package com.sigmul.DAO;

import com.sigmul.gestao_banco.ConexaoBanco;
import com.sigmul.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MultaAplicadaDAO {

    // Salva a multa E o item_multa (infração) na mesma transação.
    // Se uma das duas operações falhar, a outra é desfeita (rollback) —
    // assim nunca fica uma multa sem infração ou vice-versa.
    public int salvar(MultaAplicada multa, int idInfracao) {

        String sqlMulta = "INSERT INTO multa_aplicada (matricula_pol, placa_vei, cnh_moto, id_rod, km_multa) VALUES (?, ?, ?, ?, ?)";
        String sqlItem = "INSERT INTO item_multa (id_infra, id_multa) VALUES (?, ?)";

        try (Connection conn = ConexaoBanco.conectar()) {

            // Desliga o autocommit: as duas inserções só são gravadas
            // de fato no banco quando chamarmos conn.commit() no final.
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

    public List<MultaAplicada> listarTodos() {

        String sql = """
            SELECT 
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
}
