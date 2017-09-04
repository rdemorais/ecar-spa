ALTER TABLE dbsitedemas.tb_meta_iniciativa ADD COLUMN ind_ativo character varying(1);
ALTER TABLE dbsitedemas.tb_oe ADD COLUMN ind_ativo character varying(1);
ALTER TABLE dbsitedemas.tb_atividade ADD COLUMN ind_ativo character varying(1);
ALTER TABLE dbsitedemas.tb_prod_inter ADD COLUMN ind_ativo character varying(1);
COMMENT ON COLUMN dbsitedemas.tb_atividade.ind_ativo
  IS 'indica se a atividade está ativa no sistema. As opções válidas são: S para ativa e F para inativa.';
COMMENT ON COLUMN dbsitedemas.tb_meta_iniciativa.ind_ativo
  IS 'indica se a meta ou iniciativa está ativa no sistema. As opções válidas são: S para ativa e F para inativa.';
COMMENT ON COLUMN dbsitedemas.tb_oe.ind_ativo
  IS 'indica se o objtivo estratégico está ativo no sistema. As opções válidas são: S para ativo e F para inativo.';
COMMENT ON COLUMN dbsitedemas.tb_prod_inter.ind_ativo
  IS 'indica se o produto intermediário está ativo no sistema. As opções válidas são: S para ativo e F para inativo.';
