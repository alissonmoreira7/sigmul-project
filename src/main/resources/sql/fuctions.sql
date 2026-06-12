-- DROP FUNCTION public.resumo_motorista(varchar);

CREATE OR REPLACE FUNCTION public.resumo_motorista(p_cnh character varying)
 RETURNS TABLE(nome character varying, total_multas bigint, valor_total numeric, pontos_totais integer, infracao_mais_comum character varying)
 LANGUAGE plpgsql
AS $function$
BEGIN
RETURN QUERY
SELECT
    mo.nome_moto,
    COUNT(DISTINCT ma.id_multa),
    SUM(inf.valor_infra),
    mo.pontoacumulados_moto,
    (
        SELECT inf2.nome_infra
        FROM item_multa im2
                 JOIN multa_aplicada ma2 ON ma2.id_multa = im2.id_multa
                 JOIN infracao inf2       ON inf2.id_infra = im2.id_infra
        WHERE ma2.cnh_moto = p_cnh
        GROUP BY inf2.nome_infra
        ORDER BY COUNT(*) DESC
        LIMIT 1
    )
FROM motorista mo
    JOIN multa_aplicada ma ON ma.cnh_moto = mo.cnh_moto
    JOIN item_multa im     ON im.id_multa  = ma.id_multa
    JOIN infracao inf      ON inf.id_infra = im.id_infra
WHERE mo.cnh_moto = p_cnh
GROUP BY mo.nome_moto, mo.pontoacumulados_moto;
END;
$function$
;