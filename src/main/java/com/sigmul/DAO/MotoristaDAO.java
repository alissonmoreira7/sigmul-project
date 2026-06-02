package com.sigmul.DAO;

import com.sigmul.gestao_banco.ConexaoBanco;
import com.sigmul.model.Motorista;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MotoristaDAO {
    public void salvar(Motorista motorista){
        String sql = "INSERT INTO motorista (cnh_moto, cpf_moto, nome_moto, pontoAcumulados_moto) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, motorista.getCnh());
            stmt.setString(2, motorista.getCpf());
            stmt.setString(3, motorista.getNome());
            stmt.setInt(4, motorista.getPontoAcumulado());

            stmt.executeUpdate();
            System.out.println("Motorista cadastrado com sucesso no banco!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar motorista: " + e.getMessage(), e);
        }
    }

    public List<Motorista> listarTodos(){
        String sql = "SELECT * FROM motorista";
        List<Motorista> listaMotorista = new ArrayList<>();

        try (Connection conn = ConexaoBanco.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Motorista motorista = new Motorista();
                motorista.setCnh(rs.getString("cnh_moto"));
                motorista.setCpf(rs.getString("cpf_moto"));
                motorista.setPontoAcumulado(rs.getInt("pontoAcumulados_moto"));
                listaMotorista.add(motorista);
            }
        } catch (SQLException e){
            throw new RuntimeException("Erro ao listar motoristas: " + e.getMessage(), e);
        }
        return listaMotorista;
    }
}
