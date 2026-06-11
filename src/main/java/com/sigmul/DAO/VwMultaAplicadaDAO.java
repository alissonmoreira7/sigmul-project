package com.sigmul.DAO;

import com.sigmul.gestao_banco.ConexaoBanco;
import com.sigmul.model.VwMultaAplicada;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VwMultaAplicadaDAO {

    public List<VwMultaAplicada> listarTodos() {
        String sql = "SELECT * FROM vw_multas_aplicadas ORDER BY data_hora DESC";

        return executarConsulta(sql, stmt -> {});
    }

    public List<VwMultaAplicada> listarPorMotorista(String cpf) {
        String sql = "SELECT * FROM vw_multas_aplicadas WHERE cpf_moto = ? ORDER BY data_hora DESC";

        return executarConsulta(sql, stmt -> stmt.setString(1, cpf));
    }

    public List<VwMultaAplicada> listarPorPlaca(String placa) {
        String sql = "SELECT * FROM vw_multas_aplicadas WHERE placa = ? ORDER BY data_hora DESC";

        return executarConsulta(sql, stmt -> stmt.setString(1, placa));
    }

    public List<VwMultaAplicada> listarPorRodovia(String codbr) {
        String sql = "SELECT * FROM vw_multas_aplicadas WHERE rodovia = ? ORDER BY data_hora DESC";

        return executarConsulta(sql, stmt -> stmt.setString(1, codbr));
    }

    public VwMultaAplicada buscarPorId(int idMulta) {
        String sql = "SELECT * FROM vw_multas_aplicadas WHERE id_multa = ?";
        List<VwMultaAplicada> resultado = executarConsulta(sql, stmt -> stmt.setInt(1, idMulta));

        return resultado.isEmpty() ? null : resultado.get(0);
    }

    private List<VwMultaAplicada> executarConsulta(String sql, ParametrosSetter setter) {
        List<VwMultaAplicada> lista = new ArrayList<>();

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setter.set(stmt);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                VwMultaAplicada vm = new VwMultaAplicada();
                vm.setIdMulta(rs.getInt("id_multa"));
                vm.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                vm.setMotorista(rs.getString("motorista"));
                vm.setCpfMoto(rs.getString("cpf_moto"));
                vm.setPlaca(rs.getString("placa"));
                vm.setVeiculo(rs.getString("veiculo"));
                vm.setInfracao(rs.getString("infracao"));
                vm.setValor(rs.getDouble("valor"));
                vm.setRodovia(rs.getString("rodovia"));
                vm.setPolicial(rs.getString("policial"));

                lista.add(vm);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar view de multas: " + e.getMessage(), e);
        }
        return lista;
    }

    @FunctionalInterface
    private interface ParametrosSetter {
        void set(PreparedStatement stmt) throws SQLException;
    }
}