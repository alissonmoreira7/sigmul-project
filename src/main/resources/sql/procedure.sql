CREATE OR REPLACE PROCEDURE public.atualizar_pontos_motorista(p_cnh VARCHAR, p_pontos INT)
LANGUAGE plpgsql AS $$
BEGIN
UPDATE motorista
SET pontoacumulados_moto = pontoacumulados_moto + p_pontos
WHERE cnh_moto = p_cnh;
END;
$$;