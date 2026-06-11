-- public.vw_multas_aplicadas fonte

CREATE OR REPLACE VIEW public.vw_multas_aplicadas
AS SELECT ma.id_multa,
          ma.datahora_multa AS data_hora,
          mot.nome_moto AS motorista,
          mot.cpf_moto,
          vei.placa_vei AS placa,
          vei.modelo_vei AS veiculo,
          inf.nome_infra AS infracao,
          inf.valor_infra AS valor,
          r.codbr_rod AS rodovia,
          pol.nome_pol AS policial
   FROM item_multa im
            JOIN infracao inf ON inf.id_infra = im.id_infra
            JOIN multa_aplicada ma ON ma.id_multa = im.id_multa
            JOIN motorista mot ON mot.cnh_moto::text = ma.cnh_moto::text
     JOIN veiculo vei ON vei.placa_vei::text = ma.placa_vei::text
              JOIN rodovia r ON r.id_rod = ma.id_rod
              JOIN policial pol ON pol.matricula_pol = ma.matricula_pol;