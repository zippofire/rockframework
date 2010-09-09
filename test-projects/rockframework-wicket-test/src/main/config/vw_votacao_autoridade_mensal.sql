CREATE OR REPLACE VIEW vw_votacao_autoridade_mensal AS
SELECT CAST(CONCAT(a.ID_AUTORIDADE, '|', DATE_FORMAT(v.DT_VOTACAO, '%m'), '|', DATE_FORMAT(v.DT_VOTACAO, '%Y')) AS CHAR CHARACTER SET latin1) AS ID_VOTACAO_AUTORIDADE,
       a.ID_AUTORIDADE,
       DATE_FORMAT(v.DT_VOTACAO, '%m') AS NUM_MES,
       DATE_FORMAT(v.DT_VOTACAO, '%Y') AS NUM_ANO,
       # Somatorio
       COUNT(*) AS NUM_VOTO,
       SUM(CASE WHEN IFNULL(va.ID_TIPO_VOTO, 3) = IFNULL(vp.ID_TIPO_VOTO, 3) THEN 1 ELSE 0 END) AS NUM_VOTO_PARTIDO,
       SUM(CASE WHEN IFNULL(va.ID_TIPO_VOTO, 3) = IFNULL(v.ID_VOTO_GOVERNO, 3)
              OR (v.ID_VOTO_GOVERNO = 7 AND IFNULL(va.ID_TIPO_VOTO, 3) = 3)
            THEN 1 ELSE 0
       END) AS NUM_VOTO_GOVERNO,
       SUM(CASE WHEN IFNULL(va.ID_TIPO_VOTO, 3) = IFNULL(v.ID_VOTO_ORGAO, 3)
              OR (v.ID_VOTO_ORGAO = 7 AND IFNULL(va.ID_TIPO_VOTO, 3) = 3)
            THEN 1 ELSE 0
       END) AS NUM_VOTO_ORGAO,
       SUM(CASE WHEN IFNULL(va.ID_TIPO_VOTO, 3) <> IFNULL(vp.ID_TIPO_VOTO, 3)
             AND ((IFNULL(va.ID_TIPO_VOTO, 3) <> IFNULL(v.ID_VOTO_GOVERNO, 3)
                   OR v.ID_VOTO_GOVERNO = 7 AND IFNULL(va.ID_TIPO_VOTO, 3) <> 3))
            THEN 1 ELSE 0
       END) AS NUM_VOTO_PROPRIO,
       # Percentual
       SUM(CASE WHEN IFNULL(va.ID_TIPO_VOTO, 3) = IFNULL(vp.ID_TIPO_VOTO, 3) THEN 1 ELSE 0 END) * 100 / COUNT(*) AS NUM_PERCENTUAL_PARTIDO,
       SUM(CASE WHEN IFNULL(va.ID_TIPO_VOTO, 3) = IFNULL(v.ID_VOTO_GOVERNO, 3)
              OR (v.ID_VOTO_GOVERNO = 7 AND IFNULL(va.ID_TIPO_VOTO, 3) = 3)
            THEN 1 ELSE 0
       END) * 100 / COUNT(*) AS NUM_PERCENTUAL_GOVERNO,
       SUM(CASE WHEN IFNULL(va.ID_TIPO_VOTO, 3) = IFNULL(v.ID_VOTO_ORGAO, 3)
              OR (v.ID_VOTO_ORGAO = 7 AND IFNULL(va.ID_TIPO_VOTO, 3) = 3)
            THEN 1 ELSE 0
       END) * 100 / COUNT(*) AS NUM_PERCENTUAL_ORGAO,
       SUM(CASE WHEN IFNULL(va.ID_TIPO_VOTO, 3) <> IFNULL(vp.ID_TIPO_VOTO, 3)
             AND ((IFNULL(va.ID_TIPO_VOTO, 3) <> IFNULL(v.ID_VOTO_GOVERNO, 3)
                   OR v.ID_VOTO_GOVERNO = 7 AND IFNULL(va.ID_TIPO_VOTO, 3) <> 3))
            THEN 1 ELSE 0
       END) * 100 / COUNT(*) AS NUM_PERCENTUAL_PROPRIO
  FROM tb_votacao AS v
       JOIN tb_proposicao AS p ON (v.id_proposicao = p.id_proposicao)
       JOIN tb_autoridade AS a ON (p.id_casa = a.id_casa)
       LEFT OUTER JOIN tb_partido AS pa ON (a.id_partido = pa.id_partido)
       LEFT OUTER JOIN tb_voto_autoridade AS va ON (v.id_votacao = va.id_votacao AND a.id_autoridade = va.id_autoridade)
       LEFT OUTER JOIN tb_voto_partido AS vp ON (v.id_votacao = vp.id_votacao AND a.id_partido = vp.id_partido)
 GROUP BY a.ID_AUTORIDADE,
          DATE_FORMAT(v.DT_VOTACAO, '%m'),
          DATE_FORMAT(v.DT_VOTACAO, '%Y')