package com.sigmul.DAO;

import com.sigmul.gestao_banco.ConexaoBanco;
import com.sigmul.model.Policial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PolicialDAO {

    public List<Policial> listarTodos() {

        String sql = "SELECT * FROM policial";
        List<Policial> lista = new ArrayList<>();

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Policial p = new Policial();
                p.setMatricula(rs.getInt("matricula_pol"));
                p.setNome(rs.getString("nome_pol"));
                p.setCargo(rs.getString("cargo_pol"));
                lista.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }
}