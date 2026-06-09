package com.sigmul.DAO;

import com.sigmul.gestao_banco.ConexaoBanco;
import com.sigmul.model.Rodovia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RodoviaDAO {

    public void salvar(Rodovia rodovia) {
        String sql = "INSERT INTO rodovia (id_rod, codbr_rod, estado_rod, kms_cod) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, rodovia.getId());
            stmt.setString(2, rodovia.getCodigoBR());
            stmt.setString(3, rodovia.getEstado());
            stmt.setInt(4, rodovia.getKilometros());

            stmt.executeUpdate();
            System.out.println("Rodovia cadastrada com sucesso no banco!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar rodovia: " + e.getMessage(), e);
        }
    }

    public List<Rodovia> listarTodos() {
        String sql = "SELECT * FROM rodovia";
        List<Rodovia> listaRodovia = new ArrayList<>();

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Rodovia rodovia = new Rodovia();
                rodovia.setId(rs.getInt("id_rod"));
                rodovia.setCodigoBR(rs.getString("codbr_rod"));
                rodovia.setEstado(rs.getString("estado_rod"));
                rodovia.setKilometros(rs.getInt("kms_cod"));
                listaRodovia.add(rodovia);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar rodovias: " + e.getMessage(), e);
        }
        return listaRodovia;
    }

    public Rodovia buscarPorId(int id) {
        String sql = "SELECT * FROM rodovia WHERE id_rod = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Rodovia rodovia = new Rodovia();
                    rodovia.setId(rs.getInt("id_rod"));
                    rodovia.setCodigoBR(rs.getString("codbr_rod"));
                    rodovia.setEstado(rs.getString("estado_rod"));
                    rodovia.setKilometros(rs.getInt("kms_cod"));
                    return rodovia;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar rodovia: " + e.getMessage(), e);
        }
        return null;
    }

    public void atualizar(Rodovia rodovia) {
        String sql = "UPDATE rodovia SET codbr_rod = ?, estado_rod = ?, kms_cod = ? WHERE id_rod = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, rodovia.getCodigoBR());
            stmt.setString(2, rodovia.getEstado());
            stmt.setInt(3, rodovia.getKilometros());
            stmt.setInt(4, rodovia.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Rodovia atualizada com sucesso!");
            } else {
                System.out.println("Nenhuma rodovia encontrada com esse ID.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar rodovia: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM rodovia WHERE id_rod = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Rodovia deletada com sucesso!");
            } else {
                System.out.println("Nenhuma rodovia encontrada com esse ID.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar rodovia: " + e.getMessage(), e);
        }
    }
}