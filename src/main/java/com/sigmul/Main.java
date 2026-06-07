package com.sigmul;

import com.sigmul.model.Motorista;
import com.sigmul.DAO.MotoristaDAO;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MotoristaDAO motoristaDAO = new MotoristaDAO();

        System.out.println("\n--- LISTA DE MOTORISTAS NO BANCO ---");
        List<Motorista> motoristas = motoristaDAO.listarTodos();
        for (Motorista m : motoristas) {
            System.out.println("Nome: " + m.getNome() + " | CNH: " + m.getCnh() + " | Pontos: " + m.getPontoAcumulado());
        }
    }
}
