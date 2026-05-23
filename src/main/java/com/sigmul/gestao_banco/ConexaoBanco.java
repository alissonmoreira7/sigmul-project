package com.sigmul.gestao_banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

public class ConexaoBanco {
    private static final Dotenv dotenv = Dotenv.load();

    private static final String url = dotenv.get("DB_URL");
    private static final String usuario = dotenv.get("DB_USER");
    private static final String senha = dotenv.get("DB_PASSWORD");

    public static Connection conectar(){
        try {
            return DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e){
            throw new RuntimeException("Erro crítico: Não foi possível se conectar ao banco." + e.getMessage());
        }
    }
}
