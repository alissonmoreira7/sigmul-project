package com.sigmul.gestao_banco;

import org.junit.jupiter.api.Test;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;

public class ConexaoBancoTest {
    @Test
    public void deveConectar(){
        Connection con = ConexaoBanco.conectar();
        assertNotNull(con, "A conexão não deveria ser nula!");
    }
}
