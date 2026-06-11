package com.sigmul.DAO;

import com.sigmul.gestao_banco.ConexaoBanco;
import com.sigmul.model.Infracao;
import com.sigmul.model.ItemMulta;
import com.sigmul.model.MultaAplicada;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemMultaDAO {

    public void salvar(ItemMulta item) {
        String sql = "INSERT INTO item_multa (id_infra, id_multa) VALUES (?, ?)";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, item.getInfracao().getId());
            stmt.setInt(2, item.getMulta().getIdMulta());

            stmt.executeUpdate();
            System.out.println("Item de multa salvo com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar item_multa: " + e.getMessage(), e);
        }
    }

    public List<ItemMulta> listarPorMulta(int idMulta) {
        String sql = """
                SELECT
                    im.id_multa,
                    im.id_infra,
                    i.nome_infra,
                    i.descricao_infra,
                    i.valor_infra,
                    i.pontos_infra
                FROM 
                    item_multa im
                INNER JOIN infracao i ON im.id_infra = i.id_infra
                WHERE im.id_multa = ?
                """;
        List<ItemMulta> lista = new ArrayList<>();

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMulta);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Infracao infracao = new Infracao();
                infracao.setId(rs.getInt("id_infra"));
                infracao.setNome(rs.getString("nome_infra"));
                infracao.setDescricao(rs.getString("descricao_infra"));
                infracao.setValorInfracao(rs.getDouble("valor_infra"));
                infracao.setPontosInfracao(rs.getInt("pontos_infra"));

                MultaAplicada multa = new MultaAplicada();
                multa.setIdMulta(rs.getInt("id_multa"));

                lista.add(new ItemMulta(infracao, multa));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar itens da multa: " + e.getMessage(), e);
        }
        return lista;
    }

    public List<ItemMulta> listarPorInfracao(int idInfra) {
        String sql = """
                SELECT
                    im.id_multa,
                    im.id_infra,
                    ma.km_multa,
                    ma.datahora_multa
                FROM item_multa im
                INNER JOIN multa_aplicada ma ON im.id_multa = ma.id_multa
                WHERE im.id_infra = ?
                """;
        List<ItemMulta> lista = new ArrayList<>();

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idInfra);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                MultaAplicada multa = new MultaAplicada();
                multa.setIdMulta(rs.getInt("id_multa"));
                multa.setKmMulta(rs.getInt("km_multa"));
                multa.setDataHoraMulta(rs.getTimestamp("datahora_multa").toLocalDateTime());

                Infracao infracao = new Infracao();
                infracao.setId(rs.getInt("id_infra"));

                lista.add(new ItemMulta(infracao, multa));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar multas por infração: " + e.getMessage(), e);
        }
        return lista;
    }

    public void deletar(int idInfra, int idMulta) {
        String sql = "DELETE FROM item_multa WHERE id_infra = ? AND id_multa = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idInfra);
            stmt.setInt(2, idMulta);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Item removido da multa com sucesso!");
            } else {
                System.out.println("Nenhum item encontrado com esses IDs.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar item_multa: " + e.getMessage(), e);
        }
    }

    public void deletarTodosDaMulta(int idMulta) {
        String sql = "DELETE FROM item_multa WHERE id_multa = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMulta);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Todos os itens da multa " + idMulta + " removidos!");
            } else {
                System.out.println("Nenhum item encontrado para essa multa.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar itens da multa: " + e.getMessage(), e);
        }
    }
}