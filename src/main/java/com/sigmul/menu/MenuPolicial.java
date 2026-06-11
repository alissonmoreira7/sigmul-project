package com.sigmul.menu;

import com.sigmul.DAO.PolicialDAO;
import com.sigmul.model.Policial;

import java.util.List;

public class MenuPolicial {

    private static final PolicialDAO dao = new PolicialDAO();

    public static void exibir() {

        List<Policial> lista = dao.listarTodos();

        if (lista.isEmpty()) {
            System.out.println("Nenhum policial encontrado.");
            return;
        }

        for (Policial p : lista) {
            System.out.println(
                    "Matrícula: " + p.getMatricula() +
                            " | Nome: " + p.getNome() +
                            " | Cargo: " + p.getCargo()
            );
        }
    }
}