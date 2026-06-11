CREATE OR REPLACE PROCEDURE public.sp_cadastrar_multa(
    IN p_matricula_pol  INTEGER,
    IN p_placa_vei      VARCHAR,
    IN p_cnh_moto       VARCHAR,
    IN p_id_rod         INTEGER,
    IN p_km_multa       INTEGER,
    IN p_datahora       TIMESTAMP,
    IN p_id_infra       INTEGER
)
LANGUAGE plpgsql
AS $$
DECLARE
v_id_multa     INTEGER;
    v_pontos_infra INTEGER;
BEGIN

    -- 1. Insere a multa e captura o ID
INSERT INTO multa_aplicada
(matricula_pol, placa_vei, cnh_moto, id_rod, km_multa, datahora_multa)
VALUES
    (p_matricula_pol, p_placa_vei, p_cnh_moto, p_id_rod, p_km_multa, p_datahora)
    RETURNING id_multa INTO v_id_multa;

-- 2. Vincula a infração
INSERT INTO item_multa (id_infra, id_multa)
VALUES (p_id_infra, v_id_multa);

-- 3. Busca os pontos da infração
SELECT pontos_infra INTO v_pontos_infra
FROM infracao
WHERE id_infra = p_id_infra;

-- 4. Atualiza pontos do motorista
UPDATE motorista
SET pontoAcumulados_moto = pontoAcumulados_moto + v_pontos_infra
WHERE cnh_moto = p_cnh_moto;

EXCEPTION
    WHEN OTHERS THEN
        RAISE EXCEPTION 'Erro ao cadastrar multa: %', SQLERRM;
END;
$$;