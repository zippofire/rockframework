CREATE OR REPLACE VIEW vw_votacao_autoridade AS
SELECT CAST(CONCAT(v.ID_VOTACAO, '|', a.ID_AUTORIDADE) AS CHAR CHARACTER SET latin1) AS ID_VOTACAO_AUTORIDADE,
       v.ID_VOTACAO,
       v.DSC_RESUMO,
       v.DT_VOTACAO,
       DATE_FORMAT(v.DT_VOTACAO, '%m') AS NUM_MES,
       DATE_FORMAT(v.DT_VOTACAO, '%Y') AS NUM_ANO,
       a.ID_AUTORIDADE,
       pe.NOM_PESSOA AS NOM_AUTORIDADE,
       pa.ID_PARTIDO,
       pa.SGL_PARTIDO,
       pa.NOM_PARTIDO,
       va.ID_VOTO_AUTORIDADE,
       vp.ID_VOTO_PARTIDO,
       va.ID_TIPO_VOTO AS ID_TIPO_VOTO_AUTORIDADE,
       vp.ID_TIPO_VOTO AS ID_TIPO_VOTO_PARTIDO,
       v.ID_VOTO_GOVERNO AS ID_TIPO_VOTO_GOVERNO,
       v.ID_VOTO_ORGAO AS ID_VOTO_ORGAO,
       CASE WHEN va.ID_TIPO_VOTO = vp.ID_TIPO_VOTO THEN 1 ELSE 0 END AS FLG_VOTOU_PARTIDO,
       CASE WHEN (va.ID_TIPO_VOTO = v.ID_VOTO_GOVERNO)
              OR (v.ID_VOTO_GOVERNO = 6)
              OR (v.ID_VOTO_GOVERNO = 7 AND va.ID_TIPO_VOTO = 3)
            THEN 1 ELSE 0
       END AS FLG_VOTOU_GOVERNO,
       CASE WHEN (va.ID_TIPO_VOTO = v.ID_VOTO_ORGAO)
              OR (v.ID_VOTO_ORGAO = 6)
              OR (v.ID_VOTO_ORGAO = 7 AND va.ID_TIPO_VOTO = 3)
            THEN 1 ELSE 0
       END AS FLG_VOTOU_ORGAO,
       CASE WHEN (va.ID_TIPO_VOTO IS NOT NULL)
             AND (va.ID_TIPO_VOTO <> vp.ID_TIPO_VOTO)
             AND (va.ID_TIPO_VOTO <> v.ID_VOTO_GOVERNO)
             AND (v.ID_VOTO_GOVERNO <> 6)
             AND (v.ID_VOTO_GOVERNO = 7 AND va.ID_TIPO_VOTO <> 3)
            THEN 1 ELSE 0
       END AS FLG_VOTOU_PROPRIO,
       CASE WHEN IFNULL(va.ID_TIPO_VOTO, 8) <> 8 THEN 1 ELSE 0 END AS FLG_VOTO_VALIDO
  FROM tb_votacao AS v
       JOIN tb_proposicao AS p ON (v.id_proposicao = p.id_proposicao)
       JOIN tb_autoridade AS a ON (p.id_casa = a.id_casa)
       JOIN tb_pessoa AS pe ON (a.id_autoridade = pe.id_pessoa)
       LEFT OUTER JOIN tb_partido AS pa ON (a.id_partido = pa.id_partido)
       LEFT OUTER JOIN tb_voto_autoridade AS va ON (v.id_votacao = va.id_votacao AND a.id_autoridade = va.id_autoridade)
       LEFT OUTER JOIN tb_voto_partido AS vp ON (v.id_votacao = vp.id_votacao AND a.id_partido = vp.id_partido)
 WHERE v.ID_VOTO_GOVERNO IS NOT NULL