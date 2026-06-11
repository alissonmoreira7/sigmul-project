package com.sigmul.DAO;

import com.sigmul.gestao_banco.ConexaoBanco;
import com.sigmul.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MultaAplicadaDAO {

    public int salvar(MultaAplicada multa) {

        String sql = "INSERT INTO multa_aplicada (matricula_pol, placa_vei, cnh_moto, id_rod, km_multa) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, multa.getPolicial().getMatricula());
            stmt.setString(2, multa.getVeiculo().getPlaca());
            stmt.setString(3, multa.getMotorista().getCnh());
            stmt.setInt(4, multa.getRodovia().getId());
            stmt.setInt(5, multa.getKm());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar multa: " + e.getMessage(), e);
        }

        return -1;
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
                        rs.getTimestamp("datahora_multa")
                );

                lista.add(multa);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar multas: " + e.getMessage(), e);
        }

        return lista;
    }
}
