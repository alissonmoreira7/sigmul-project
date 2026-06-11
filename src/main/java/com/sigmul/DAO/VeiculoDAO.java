package com.sigmul.DAO;

import com.sigmul.gestao_banco.ConexaoBanco;
import com.sigmul.model.Motorista;
import com.sigmul.model.Veiculo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {

    public void salvar(Veiculo veiculo) {
        String sql = "INSERT INTO veiculo (placa_vei, marca_vei, modelo_vei, anofabricacao_vei, cpf_moto) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getMarca());
            stmt.setString(3, veiculo.getModelo());
            stmt.setInt(4, veiculo.getAnoDeFabricacao());
            stmt.setString(5, veiculo.getMotorista().getCpf());

            stmt.executeUpdate();
            System.out.println("Veículo cadastrado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar veículo: " + e.getMessage(), e);
        }
    }

    public List<Veiculo> listarTodos() {
        String sql = """
            SELECT 
                v.placa_vei,
                v.modelo_vei,
                v.anofabricacao_vei,
                m.cnh_moto, 
                m.nome_moto, 
                m.pontoAcumulados_moto
            FROM 
                veiculo v
            INNER JOIN motorista m ON v.cpf_moto = m.cpf_moto
            """;
        List<Veiculo> lista = new ArrayList<>();

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Motorista motorista = new Motorista();
                motorista.setCnh(rs.getString("cnh_moto"));
                motorista.setNome(rs.getString("nome_moto"));
                motorista.setPontoAcumulado(rs.getInt("pontoAcumulados_moto"));

                Veiculo veiculo = new Veiculo();
                veiculo.setPlaca(rs.getString("placa_vei"));
                veiculo.setModelo(rs.getString("modelo_vei"));
                veiculo.setAnoDeFabricacao(rs.getInt("anofabricacao_vei"));
                veiculo.setMotorista(motorista);

                lista.add(veiculo);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar veículos: " + e.getMessage(), e);
        }
        return lista;
    }

    public void atualizar(Veiculo veiculo) {
        String sql = "UPDATE veiculo SET marca_vei = ?, modelo_vei = ?, anofabricacao_vei = ?, cpf_moto = ? WHERE placa_vei = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, veiculo.getMarca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setInt(3, veiculo.getAnoDeFabricacao());
            stmt.setString(4, veiculo.getMotorista().getCpf());
            stmt.setString(5, veiculo.getPlaca());

            stmt.executeUpdate();
            System.out.println("Veículo atualizado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar veículo: " + e.getMessage(), e);
        }
    }

    public void deletar(String placa) {
        String sql = "DELETE FROM veiculo WHERE placa_vei = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, placa);
            stmt.executeUpdate();
            System.out.println("Veículo removido com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar veículo: " + e.getMessage(), e);
        }
    }

    public Veiculo buscarPorPlaca(String placa) {

        String sql = "SELECT * FROM veiculo WHERE placa_vei = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, placa);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Veiculo v = new Veiculo();
                v.setPlaca(rs.getString("placa_vei"));
                return v;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}