CREATE TABLE policial(
    matricula_pol INT PRIMARY KEY,
    nome_pol VARCHAR(100) NOT NULL,
    cargo_pol VARCHAR(50) NOT NULL
);

CREATE TABLE motorista(
    cnh_moto VARCHAR(11) PRIMARY KEY,
    cpf_moto VARCHAR(11) UNIQUE NOT NULL,
    nome_moto VARCHAR(100) NOT NULL,
    pontoAcumulados_moto INT NOT NULL
);

CREATE TABLE veiculo(
    placa_vei VARCHAR(7) PRIMARY KEY,
    marca_vei VARCHAR(50) NOT NULL,
    modelo_vei VARCHAR(50) NOT NULL,
    anoFabricacao_vei INT NOT NULL
);

CREATE TABLE infracao(
    id_infra INT PRIMARY KEY,
    nome_infra VARCHAR(200) NOT NULL,
    descricacao_infra TEXT NOT NULL,
    valor_infra NUMERIC(10, 2) NOT NULL,
    pontos_infra INT NOT NULL
);

CREATE TABLE rodovia(
    id_rod INT PRIMARY KEY,
    codbr_rod VARCHAR(10) NOT NULL,
    estado_rod VARCHAR(50) NOT NULL,
    kms_cod INT NOT NULL
);

CREATE TABLE multa_aplicada(
    id_multa INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    matricula_pol INT NOT NULL,
    placa_vei VARCHAR(7) NOT NULL,
    cnh_moto VARCHAR(11) NOT NULL,
    id_rod INT NOT NULL,
    km_multa INT NOT NULL,
    dataHora_multa TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_policial FOREIGN KEY (matricula_pol) REFERENCES policial(matricula_pol),
    CONSTRAINT fk_veiculo FOREIGN KEY (placa_vei) REFERENCES veiculo(placa_vei),
    CONSTRAINT fk_motorista FOREIGN KEY (cnh_moto) REFERENCES motorista(cnh_moto),
    CONSTRAINT fk_rodovia FOREIGN KEY (id_rod) REFERENCES rodovia(id_rod)
);



CREATE TABLE item_multa(

                           id_infra INT NOT NULL,

                           id_multa INT NOT NULL,



                           PRIMARY KEY(id_infra, id_multa),

                           CONSTRAINT fk_rel_infracao FOREIGN KEY (id_infra) REFERENCES 	infracao(id_infra),

                           CONSTRAINT fk_rel_multa FOREIGN KEY (id_multa) REFERENCES 		multa_aplicada(id_multa)

);

