package com.sigmul.DAO;

import com.sigmul.gestao_banco.ConexaoBanco;
import com.sigmul.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MultaAplicadaDAO {
    public void salvar(MultaAplicada multaAplicada){
        String sql = "CALL public.sp_cadastrar_multa(?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, multaAplicada.getPolicial().getMatricula());
            stmt.setString(2, multaAplicada.getVeiculo().getPlaca());
            stmt.setString(3, multaAplicada.getMotorista().getCnh());
            stmt.setInt(4, multaAplicada.getRodovia().getId());
            stmt.setInt(5, multaAplicada.getKmMulta());
            stmt.setTimestamp(6, Timestamp.valueOf(multaAplicada.getDataHoraMulta()));

            stmt.executeUpdate();
            System.out.println("Multa registrada com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar multa: " + e.getMessage(), e);
        }
    }

    public List<MultaAplicada> listarTodos() {
        String sql = """
                SELECT
                       p.nome_pol, 
                       p.cargo_pol,
                       v.marca_vei, 
                       v.modelo_vei,
                       mo.cpf_moto, 
                       mo.cnh_moto,
                       mo.nome_moto, 
                       mo.cpf_moto,
                       mo.pontoAcumulados_moto,
                       r.id_rod,
                       r.codbr_rod, 
                       r.estado_rod, 
                       r.kms_cod
                FROM multa_aplicada multa
                    INNER JOIN policial p   ON ma.matricula_pol = p.matricula_pol
                    INNER JOIN veiculo v    ON ma.placa_vei     = v.placa_vei
                    INNER JOIN motorista mo ON ma.cnh_moto      = mo.cnh_moto
                    INNER JOIN rodovia r    ON ma.id_rod        = r.id_rod
                """;
        List<MultaAplicada> lista = new ArrayList<>();

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Policial policial = new Policial();
                policial.setMatricula(rs.getInt("matricula_pol"));
                policial.setNome(rs.getString("nome_pol"));
                policial.setCargo(rs.getString("cargo_pol"));

                Veiculo veiculo = new Veiculo();
                veiculo.setPlaca(rs.getString("placa_vei"));
                veiculo.setMarca(rs.getString("marca_vei"));
                veiculo.setModelo(rs.getString("modelo_vei"));

                Motorista motorista = new Motorista();
                motorista.setCnh(rs.getString("cnh_moto"));
                motorista.setCpf(rs.getString("cpf_moto"));
                motorista.setNome(rs.getString("nome_moto"));
                motorista.setPontoAcumulado(rs.getInt("pontoAcumulados_moto"));

                Rodovia rodovia = new Rodovia();
                rodovia.setId(rs.getInt("id_rod"));
                rodovia.setCodigoBR(rs.getString("codbr_rod"));
                rodovia.setEstado(rs.getString("estado_rod"));
                rodovia.setKilometros(rs.getInt("kms_cod"));

                MultaAplicada multa = new MultaAplicada();
                multa.setIdMulta(rs.getInt("id_multa"));
                multa.setPolicial(policial);
                multa.setVeiculo(veiculo);
                multa.setMotorista(motorista);
                multa.setRodovia(rodovia);
                multa.setKmMulta(rs.getInt("km_multa"));
                multa.setDataHoraMulta(rs.getTimestamp("datahora_multa").toLocalDateTime());

                lista.add(multa);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar multas: " + e.getMessage(), e);
        }
        return lista;
    }
}
