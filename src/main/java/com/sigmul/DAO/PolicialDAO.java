package com.sigmul.DAO;

import com.sigmul.gestao_banco.ConexaoBanco;
import com.sigmul.model.Policial;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PolicialDAO {
    public void salvar(Policial policial){
        String sql = "INSERT INTO policial (matricula_pol, nome_pol, cargo_pol) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, policial.getMatricula());
            stmt.setString(2, policial.getNome());
            stmt.setString(3, policial.getCargo());

            stmt.executeUpdate();
            System.out.println("Policial cadastrado com sucesso no banco!");

        } catch (SQLException e){
            throw new RuntimeException("Erro ao salvar policial: " + e.getMessage(), e);
        }
    }
}