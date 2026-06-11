CREATE TABLE public.policial (
    matricula_pol int4 NOT NULL,
    nome_pol varchar(100) NOT NULL,
    cargo_pol varchar(50) NOT NULL,
    CONSTRAINT policial_pkey PRIMARY KEY (matricula_pol)
);
CREATE TABLE infracao (
     id_infra int4 NOT NULL,
     nome_infra varchar(200) NOT NULL,
     descricacao_infra text NOT NULL,
     valor_infra numeric(10, 2) NOT NULL,
     pontos_infra int4 NOT NULL,
     CONSTRAINT infracao_pkey PRIMARY KEY (id_infra)
);

CREATE TABLE item_multa (
   id_infra int4 NOT NULL,
   id_multa int4 NOT NULL,
   CONSTRAINT item_multa_pkey PRIMARY KEY (id_infra, id_multa),
   CONSTRAINT fk_item_multa_infracao FOREIGN KEY (id_infra) REFERENCES public.infracao(id_infra),
   CONSTRAINT fk_rel_multa FOREIGN KEY (id_multa) REFERENCES public.multa_aplicada(id_multa)
);

CREATE TABLE motorista (
    cnh_moto varchar(11) NOT NULL,
    cpf_moto varchar(11) NOT NULL,
    nome_moto varchar(100) NOT NULL,
    pontoacumulados_moto int4 NOT NULL,
    CONSTRAINT motorista_cpf_moto_key UNIQUE (cpf_moto),
    CONSTRAINT motorista_pkey PRIMARY KEY (cnh_moto)
);

CREATE TABLE public.veiculo (
    placa_vei varchar(7) NOT NULL,
    marca_vei varchar(50) NOT NULL,
    modelo_vei varchar(50) NOT NULL,
    anofabricacao_vei int4 NOT NULL,
    cpf_moto varchar(11) NULL,
CONSTRAINT veiculo_pkey PRIMARY KEY (placa_vei)
);


CREATE TABLE public.rodovia (
    id_rod int4 NOT NULL,
    codbr_rod varchar(10) NOT NULL,
    estado_rod varchar(50) NOT NULL,
    kms_cod int4 NOT NULL,
    CONSTRAINT rodovia_pkey PRIMARY KEY (id_rod)
);

CREATE TABLE public.multa_aplicada (
   id_multa int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
   matricula_pol int4 NOT NULL,
   placa_vei varchar(7) NOT NULL,
   cnh_moto varchar(11) NULL,
   id_rod int4 NOT NULL,
   km_multa int4 NOT NULL,
   datahora_multa timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
   CONSTRAINT multa_aplicada_pkey PRIMARY KEY (id_multa),
   CONSTRAINT fk_motorista FOREIGN KEY (cnh_moto) REFERENCES public.motorista(cnh_moto),
   CONSTRAINT fk_policial FOREIGN KEY (matricula_pol) REFERENCES public.policial(matricula_pol),
   CONSTRAINT fk_rodovia FOREIGN KEY (id_rod) REFERENCES public.rodovia(id_rod),
   CONSTRAINT fk_veiculo FOREIGN KEY (placa_vei) REFERENCES public.veiculo(placa_vei)
);

CREATE TABLE item_multa(
   id_infra INT NOT NULL,
   id_multa INT NOT NULL,
   PRIMARY KEY(id_infra, id_multa),
   CONSTRAINT fk_rel_infracao FOREIGN KEY (id_infra) REFERENCES infracao(id_infra),
   CONSTRAINT fk_rel_multa FOREIGN KEY (id_multa) REFERENCES multa_aplicada(id_multa)
);

