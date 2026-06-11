package com.sigmul.DAO;

import com.sigmul.gestao_banco.ConexaoBanco;
import com.sigmul.model.Infracao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InfracaoDAO {
    public void salvar(Infracao infracao) {
        String sql = """
            INSERT INTO infracao (id_infra, nome_infra, descricacao_infra, valor_infra, pontos_infra) 
            VALUES (?, ?, ?, ?, ?
        )""";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, infracao.getId());
            stmt.setString(2, infracao.getNome());
            stmt.setString(3, infracao.getDescricao());
            stmt.setDouble(4, infracao.getValorInfracao());
            stmt.setInt(5, infracao.getPontosInfracao());

            stmt.executeUpdate();
            System.out.println("Infração cadastrada com sucesso no banco!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar infração: " + e.getMessage(), e);
        }
    }

    public List<Infracao> listarTodos() {
        String sql = "SELECT * FROM infracao";
        List<Infracao> listaInfracao = new ArrayList<>();

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Infracao infracao = new Infracao();
                infracao.setId(rs.getInt("id_infra"));
                infracao.setNome(rs.getString("nome_infra"));
                infracao.setDescricao(rs.getString("descricacao_infra"));
                infracao.setValorInfracao(rs.getDouble("valor_infra"));
                infracao.setPontosInfracao(rs.getInt("pontos_infra"));
                listaInfracao.add(infracao);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar infrações: " + e.getMessage(), e);
        }
        return listaInfracao;
    }

    public Infracao buscarPorId(int id) {
        String sql = "SELECT * FROM infracao WHERE id_infra = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Infracao infracao = new Infracao();
                    infracao.setId(rs.getInt("id_infra"));
                    infracao.setNome(rs.getString("nome_infra"));
                    infracao.setDescricao(rs.getString("descricacao_infra"));
                    infracao.setValorInfracao(rs.getDouble("valor_infra"));
                    infracao.setPontosInfracao(rs.getInt("pontos_infra"));
                    return infracao;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar infração: " + e.getMessage(), e);
        }
        return null;
    }

    public void atualizar(Infracao infracao) {
        String sql = "UPDATE infracao SET nome_infra = ?, descricacao_infra = ?, valor_infra = ?, pontos_infra = ? WHERE id_infra = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, infracao.getNome());
            stmt.setString(2, infracao.getDescricao());
            stmt.setDouble(3, infracao.getValorInfracao());
            stmt.setInt(4, infracao.getPontosInfracao());
            stmt.setInt(5, infracao.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Infração atualizada com sucesso!");
            } else {
                System.out.println("Nenhuma infração encontrada com esse ID.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar infração: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM infracao WHERE id_infra = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Infração deletada com sucesso!");
            } else {
                System.out.println("Nenhuma infração encontrada com esse ID.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar infração: " + e.getMessage(), e);
        }
    }
}
