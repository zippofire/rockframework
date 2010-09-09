CREATE OR REPLACE VIEW vw_votacao_partido AS
SELECT CAST(CONCAT(v.ID_VOTACAO, '|', p.ID_PARTIDO) AS CHAR CHARACTER SET latin1) AS ID_VOTACAO_PARTIDO,
       v.ID_VOTACAO,
       p.ID_PARTIDO,
       p.SGL_PARTIDO,
       p.NOM_PARTIDO,
       vp.ID_VOTO_PARTIDO,
       vp.ID_TIPO_VOTO
  FROM tb_votacao AS v
       JOIN tb_partido AS p
       LEFT OUTER JOIN tb_voto_partido AS vp ON (vp.ID_VOTACAO = v.ID_VOTACAO AND vp.ID_PARTIDO = p.ID_PARTIDO)
 WHERE p.FLG_ATIVO = 1
 ORDER BY v.ID_VOTACAO, p.ID_PARTIDO