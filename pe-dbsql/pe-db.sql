CREATE SCHEMA dbsitedemas
  AUTHORIZATION sa_ecar;

CREATE TABLE dbsitedemas.tb_situacao
(
  co_situacao bigint NOT NULL,
  ds_situacao character varying,
  CONSTRAINT co_situacao_pk PRIMARY KEY (co_situacao)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE dbsitedemas.tb_situacao
  OWNER TO sa_ecar;
  
CREATE SEQUENCE dbsitedemas.cod_monitoramento_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE dbsitedemas.cod_monitoramento_seq
  OWNER TO sa_ecar;

CREATE TABLE dbsitedemas.tb_usuario_usu
(
  co_usuario bigint NOT NULL,
  co_usuario_sentinela bigint,
  ds_nome character varying(250),
  ds_email character varying(300),
  CONSTRAINT co_usuario_pk PRIMARY KEY (co_usuario)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE dbsitedemas.tb_usuario_usu
  OWNER TO sa_ecar;

CREATE TABLE dbsitedemas.tb_iett
(
  cod_iett bigint NOT NULL,
  ds_estrutura character varying,
  CONSTRAINT cod_iett_pri PRIMARY KEY (cod_iett)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE dbsitedemas.tb_iett
  OWNER TO sa_ecar;

CREATE TABLE dbsitedemas.tb_oe
(
  cod_iett bigint NOT NULL,
  sigla_oe character varying(10),
  nome_oe text,
  CONSTRAINT cod_iett_oe_pk PRIMARY KEY (cod_iett),
  CONSTRAINT cod_iett_fk FOREIGN KEY (cod_iett)
      REFERENCES dbsitedemas.tb_iett (cod_iett) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE dbsitedemas.tb_oe
  OWNER TO sa_ecar;

-- Table: dbsitedemas.tb_meta_iniciativa

-- DROP TABLE dbsitedemas.tb_meta_iniciativa;

CREATE TABLE dbsitedemas.tb_meta_iniciativa
(
  cod_iett bigint NOT NULL,
  cod_oe bigint,
  sigla_mi character varying(10),
  nome_mi text,
  sigla_ett character varying(10),
  data_inicio date,
  data_termino date,
  cod_org bigint,
  sigla_org character varying(10),
  cod_usu bigint,
  ds_ppa_mi character varying(20),
  ds_oe_pns_mi character varying(15),
  CONSTRAINT cod_iett_mi_pk PRIMARY KEY (cod_iett),
  CONSTRAINT co_usuario_fk FOREIGN KEY (cod_usu)
      REFERENCES dbsitedemas.tb_usuario_usu (co_usuario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT cod_iett_fk FOREIGN KEY (cod_iett)
      REFERENCES dbsitedemas.tb_iett (cod_iett) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT cod_oe_fk FOREIGN KEY (cod_oe)
      REFERENCES dbsitedemas.tb_oe (cod_iett) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE dbsitedemas.tb_meta_iniciativa
  OWNER TO sa_ecar;


CREATE TABLE dbsitedemas.tb_prod_inter
(
  cod_iett bigint NOT NULL,
  cod_mi bigint,
  sigla_pi character varying(10),
  nome_pi text,
  data_inicio date,
  data_termino date,
  cod_org bigint,
  sigla_org character varying(10),
  cod_usu bigint,
  ds_ppa_pi character varying(20),
  CONSTRAINT cod_iett_prod_pk PRIMARY KEY (cod_iett),
  CONSTRAINT cod_iett_fk FOREIGN KEY (cod_iett)
      REFERENCES dbsitedemas.tb_iett (cod_iett) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT cod_mi_fk FOREIGN KEY (cod_mi)
      REFERENCES dbsitedemas.tb_meta_iniciativa (cod_iett) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT cod_usu_fk FOREIGN KEY (cod_usu)
      REFERENCES dbsitedemas.tb_usuario_usu (co_usuario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE dbsitedemas.tb_prod_inter
  OWNER TO sa_ecar;

-- Index: dbsitedemas.idx_tb_prod_inter_lookup

-- DROP INDEX dbsitedemas.idx_tb_prod_inter_lookup;

CREATE INDEX idx_tb_prod_inter_lookup
  ON dbsitedemas.tb_prod_inter
  USING btree
  (cod_iett);

CREATE TABLE dbsitedemas.tb_atividade
(
  cod_iett bigint NOT NULL,
  cod_pi bigint,
  sigla_atv character varying(10),
  nome_atv text,
  data_inicio date,
  data_termino date,
  cod_org bigint,
  sigla_org character varying(10),
  cod_usu bigint,
  ds_ppa_atv character varying(20),
  CONSTRAINT cod_iett_p PRIMARY KEY (cod_iett),
  CONSTRAINT cod_iett_fk FOREIGN KEY (cod_iett)
      REFERENCES dbsitedemas.tb_iett (cod_iett) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT cod_pi_fk FOREIGN KEY (cod_pi)
      REFERENCES dbsitedemas.tb_prod_inter (cod_iett) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT cod_usu_fk FOREIGN KEY (cod_usu)
      REFERENCES dbsitedemas.tb_usuario_usu (co_usuario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE dbsitedemas.tb_atividade
  OWNER TO sa_ecar;

-- Index: dbsitedemas.idx_tb_atividade_lookup

-- DROP INDEX dbsitedemas.idx_tb_atividade_lookup;

CREATE INDEX idx_tb_atividade_lookup
  ON dbsitedemas.tb_atividade
  USING btree
  (cod_iett);

CREATE TABLE dbsitedemas.tb_etiqueta
(
  cod_iett bigint,
  cod_etiqueta bigint NOT NULL,
  nome_etiqueta character varying(250),
  nome_etiqueta_fonetico text,
  ind_ativo character varying(1),
  CONSTRAINT cod_iett_fk FOREIGN KEY (cod_iett)
      REFERENCES dbsitedemas.tb_iett (cod_iett) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE dbsitedemas.tb_etiqueta
  OWNER TO sa_ecar;

CREATE TABLE dbsitedemas.tb_monitoramento
(
  cod_iett bigint NOT NULL,
  mes character varying(2),
  ano character varying(4),
  cod_exe bigint,
  cod_cor bigint,
  nome_cor character varying(20),
  significado_cor character varying(20),
  data_parecer date,
  cod_usu bigint,
  cod_sit bigint,
  descricao_sit text,
  parecer text,
  ultimo_parecer character varying,
  nao_monitorado character varying,
  cod_monitoramento bigint NOT NULL,
  CONSTRAINT cod_monitoramento_pk PRIMARY KEY (cod_monitoramento),
  CONSTRAINT cod_iett_fk FOREIGN KEY (cod_iett)
      REFERENCES dbsitedemas.tb_iett (cod_iett) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT cod_usu_fk FOREIGN KEY (cod_usu)
      REFERENCES dbsitedemas.tb_usuario_usu (co_usuario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE dbsitedemas.tb_monitoramento
  OWNER TO sa_ecar;