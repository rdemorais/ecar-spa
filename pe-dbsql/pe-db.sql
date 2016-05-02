--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.4
-- Dumped by pg_dump version 9.3.1
-- Started on 2016-05-02 15:01:43 BRT

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 10 (class 2615 OID 8684744)
-- Name: dbsitedemas2016; Type: SCHEMA; Schema: -; Owner: sa_ecar
--

CREATE SCHEMA dbsitedemas2016;


ALTER SCHEMA dbsitedemas2016 OWNER TO sa_ecar;

SET search_path = dbsitedemas2016, pg_catalog;

--
-- TOC entry 512 (class 1259 OID 8685160)
-- Name: cod_monitoramento_seq; Type: SEQUENCE; Schema: dbsitedemas2016; Owner: sa_ecar
--

CREATE SEQUENCE cod_monitoramento_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dbsitedemas2016.cod_monitoramento_seq OWNER TO sa_ecar;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 508 (class 1259 OID 8684890)
-- Name: tb_atividade; Type: TABLE; Schema: dbsitedemas2016; Owner: sa_ecar; Tablespace: 
--

CREATE TABLE tb_atividade (
    cod_iett bigint NOT NULL,
    cod_pi bigint,
    sigla_atv character varying(10),
    nome_atv text,
    data_inicio date,
    data_termino date,
    cod_org bigint,
    sigla_org character varying(10),
    cod_usu bigint,
    ds_ppa_atv character varying(20)
);


ALTER TABLE dbsitedemas2016.tb_atividade OWNER TO sa_ecar;

--
-- TOC entry 509 (class 1259 OID 8684927)
-- Name: tb_etiqueta; Type: TABLE; Schema: dbsitedemas2016; Owner: sa_ecar; Tablespace: 
--

CREATE TABLE tb_etiqueta (
    cod_iett bigint,
    cod_etiqueta bigint NOT NULL,
    nome_etiqueta character varying(250),
    nome_etiqueta_fonetico text,
    ind_ativo character varying(1)
);


ALTER TABLE dbsitedemas2016.tb_etiqueta OWNER TO sa_ecar;

--
-- TOC entry 503 (class 1259 OID 8684771)
-- Name: tb_grupo_seg; Type: TABLE; Schema: dbsitedemas2016; Owner: sa_ecar; Tablespace: 
--

CREATE TABLE tb_grupo_seg (
    co_satb bigint NOT NULL,
    ds_nome character varying(250)
);


ALTER TABLE dbsitedemas2016.tb_grupo_seg OWNER TO sa_ecar;

--
-- TOC entry 511 (class 1259 OID 8684950)
-- Name: tb_iett; Type: TABLE; Schema: dbsitedemas2016; Owner: sa_ecar; Tablespace: 
--

CREATE TABLE tb_iett (
    cod_iett bigint NOT NULL,
    ds_estrutura character varying
);


ALTER TABLE dbsitedemas2016.tb_iett OWNER TO sa_ecar;

--
-- TOC entry 506 (class 1259 OID 8684821)
-- Name: tb_meta_iniciativa; Type: TABLE; Schema: dbsitedemas2016; Owner: sa_ecar; Tablespace: 
--

CREATE TABLE tb_meta_iniciativa (
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
    ds_ppa_mi character varying(20)
);


ALTER TABLE dbsitedemas2016.tb_meta_iniciativa OWNER TO sa_ecar;

--
-- TOC entry 510 (class 1259 OID 8684935)
-- Name: tb_monitoramento; Type: TABLE; Schema: dbsitedemas2016; Owner: sa_ecar; Tablespace: 
--

CREATE TABLE tb_monitoramento (
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
    cod_monitoramento bigint NOT NULL
);


ALTER TABLE dbsitedemas2016.tb_monitoramento OWNER TO sa_ecar;

--
-- TOC entry 505 (class 1259 OID 8684789)
-- Name: tb_oe; Type: TABLE; Schema: dbsitedemas2016; Owner: sa_ecar; Tablespace: 
--

CREATE TABLE tb_oe (
    cod_iett bigint NOT NULL,
    sigla_oe character varying(10),
    nome_oe text
);


ALTER TABLE dbsitedemas2016.tb_oe OWNER TO sa_ecar;

--
-- TOC entry 507 (class 1259 OID 8684857)
-- Name: tb_prod_inter; Type: TABLE; Schema: dbsitedemas2016; Owner: sa_ecar; Tablespace: 
--

CREATE TABLE tb_prod_inter (
    cod_iett bigint NOT NULL,
    cod_mi bigint,
    sigla_pi character varying(10),
    nome_pi text,
    data_inicio date,
    data_termino date,
    cod_org bigint,
    sigla_org character varying(10),
    cod_usu bigint,
    ds_ppa_pi character varying(20)
);


ALTER TABLE dbsitedemas2016.tb_prod_inter OWNER TO sa_ecar;

--
-- TOC entry 504 (class 1259 OID 8684776)
-- Name: tb_usuario_grupo; Type: TABLE; Schema: dbsitedemas2016; Owner: sa_ecar; Tablespace: 
--

CREATE TABLE tb_usuario_grupo (
    co_usuario bigint,
    co_satb bigint
);


ALTER TABLE dbsitedemas2016.tb_usuario_grupo OWNER TO sa_ecar;

--
-- TOC entry 502 (class 1259 OID 8684763)
-- Name: tb_usuario_usu; Type: TABLE; Schema: dbsitedemas2016; Owner: sa_ecar; Tablespace: 
--

CREATE TABLE tb_usuario_usu (
    co_usuario bigint NOT NULL,
    co_usuario_sentinela bigint,
    ds_nome character varying(250),
    ds_email character varying(300)
);


ALTER TABLE dbsitedemas2016.tb_usuario_usu OWNER TO sa_ecar;

--
-- TOC entry 3381 (class 0 OID 0)
-- Dependencies: 512
-- Name: cod_monitoramento_seq; Type: SEQUENCE SET; Schema: dbsitedemas2016; Owner: sa_ecar
--

SELECT pg_catalog.setval('cod_monitoramento_seq', 607, true);


--
-- TOC entry 3372 (class 0 OID 8684890)
-- Dependencies: 508
-- Data for Name: tb_atividade; Type: TABLE DATA; Schema: dbsitedemas2016; Owner: sa_ecar
--

COPY tb_atividade (cod_iett, cod_pi, sigla_atv, nome_atv, data_inicio, data_termino, cod_org, sigla_org, cod_usu, ds_ppa_atv) FROM stdin;
\.


--
-- TOC entry 3373 (class 0 OID 8684927)
-- Dependencies: 509
-- Data for Name: tb_etiqueta; Type: TABLE DATA; Schema: dbsitedemas2016; Owner: sa_ecar
--

COPY tb_etiqueta (cod_iett, cod_etiqueta, nome_etiqueta, nome_etiqueta_fonetico, ind_ativo) FROM stdin;
\.


--
-- TOC entry 3367 (class 0 OID 8684771)
-- Dependencies: 503
-- Data for Name: tb_grupo_seg; Type: TABLE DATA; Schema: dbsitedemas2016; Owner: sa_ecar
--

COPY tb_grupo_seg (co_satb, ds_nome) FROM stdin;
\.


--
-- TOC entry 3375 (class 0 OID 8684950)
-- Dependencies: 511
-- Data for Name: tb_iett; Type: TABLE DATA; Schema: dbsitedemas2016; Owner: sa_ecar
--

COPY tb_iett (cod_iett, ds_estrutura) FROM stdin;
\.


--
-- TOC entry 3370 (class 0 OID 8684821)
-- Dependencies: 506
-- Data for Name: tb_meta_iniciativa; Type: TABLE DATA; Schema: dbsitedemas2016; Owner: sa_ecar
--

COPY tb_meta_iniciativa (cod_iett, cod_oe, sigla_mi, nome_mi, sigla_ett, data_inicio, data_termino, cod_org, sigla_org, cod_usu, ds_ppa_mi) FROM stdin;
\.


--
-- TOC entry 3374 (class 0 OID 8684935)
-- Dependencies: 510
-- Data for Name: tb_monitoramento; Type: TABLE DATA; Schema: dbsitedemas2016; Owner: sa_ecar
--

COPY tb_monitoramento (cod_iett, mes, ano, cod_exe, cod_cor, nome_cor, significado_cor, data_parecer, cod_usu, cod_sit, descricao_sit, parecer, ultimo_parecer, nao_monitorado, cod_monitoramento) FROM stdin;
\.


--
-- TOC entry 3369 (class 0 OID 8684789)
-- Dependencies: 505
-- Data for Name: tb_oe; Type: TABLE DATA; Schema: dbsitedemas2016; Owner: sa_ecar
--

COPY tb_oe (cod_iett, sigla_oe, nome_oe) FROM stdin;
\.


--
-- TOC entry 3371 (class 0 OID 8684857)
-- Dependencies: 507
-- Data for Name: tb_prod_inter; Type: TABLE DATA; Schema: dbsitedemas2016; Owner: sa_ecar
--

COPY tb_prod_inter (cod_iett, cod_mi, sigla_pi, nome_pi, data_inicio, data_termino, cod_org, sigla_org, cod_usu, ds_ppa_pi) FROM stdin;
\.


--
-- TOC entry 3368 (class 0 OID 8684776)
-- Dependencies: 504
-- Data for Name: tb_usuario_grupo; Type: TABLE DATA; Schema: dbsitedemas2016; Owner: sa_ecar
--

COPY tb_usuario_grupo (co_usuario, co_satb) FROM stdin;
\.


--
-- TOC entry 3366 (class 0 OID 8684763)
-- Dependencies: 502
-- Data for Name: tb_usuario_usu; Type: TABLE DATA; Schema: dbsitedemas2016; Owner: sa_ecar
--

COPY tb_usuario_usu (co_usuario, co_usuario_sentinela, ds_nome, ds_email) FROM stdin;
\.


--
-- TOC entry 3226 (class 2606 OID 8684775)
-- Name: co_satb_pk; Type: CONSTRAINT; Schema: dbsitedemas2016; Owner: sa_ecar; Tablespace: 
--

ALTER TABLE ONLY tb_grupo_seg
    ADD CONSTRAINT co_satb_pk PRIMARY KEY (co_satb);


--
-- TOC entry 3224 (class 2606 OID 8684770)
-- Name: co_usuario_pk; Type: CONSTRAINT; Schema: dbsitedemas2016; Owner: sa_ecar; Tablespace: 
--

ALTER TABLE ONLY tb_usuario_usu
    ADD CONSTRAINT co_usuario_pk PRIMARY KEY (co_usuario);


--
-- TOC entry 3230 (class 2606 OID 8685066)
-- Name: cod_iett_mi_pk; Type: CONSTRAINT; Schema: dbsitedemas2016; Owner: sa_ecar; Tablespace: 
--

ALTER TABLE ONLY tb_meta_iniciativa
    ADD CONSTRAINT cod_iett_mi_pk PRIMARY KEY (cod_iett);


--
-- TOC entry 3228 (class 2606 OID 8685073)
-- Name: cod_iett_oe_pk; Type: CONSTRAINT; Schema: dbsitedemas2016; Owner: sa_ecar; Tablespace: 
--

ALTER TABLE ONLY tb_oe
    ADD CONSTRAINT cod_iett_oe_pk PRIMARY KEY (cod_iett);


--
-- TOC entry 3235 (class 2606 OID 8685057)
-- Name: cod_iett_p; Type: CONSTRAINT; Schema: dbsitedemas2016; Owner: sa_ecar; Tablespace: 
--

ALTER TABLE ONLY tb_atividade
    ADD CONSTRAINT cod_iett_p PRIMARY KEY (cod_iett);


--
-- TOC entry 3240 (class 2606 OID 8684954)
-- Name: cod_iett_pri; Type: CONSTRAINT; Schema: dbsitedemas2016; Owner: sa_ecar; Tablespace: 
--

ALTER TABLE ONLY tb_iett
    ADD CONSTRAINT cod_iett_pri PRIMARY KEY (cod_iett);


--
-- TOC entry 3232 (class 2606 OID 8685059)
-- Name: cod_iett_prod_pk; Type: CONSTRAINT; Schema: dbsitedemas2016; Owner: sa_ecar; Tablespace: 
--

ALTER TABLE ONLY tb_prod_inter
    ADD CONSTRAINT cod_iett_prod_pk PRIMARY KEY (cod_iett);


--
-- TOC entry 3238 (class 2606 OID 8685159)
-- Name: cod_monitoramento_pk; Type: CONSTRAINT; Schema: dbsitedemas2016; Owner: sa_ecar; Tablespace: 
--

ALTER TABLE ONLY tb_monitoramento
    ADD CONSTRAINT cod_monitoramento_pk PRIMARY KEY (cod_monitoramento);


--
-- TOC entry 3236 (class 1259 OID 8684896)
-- Name: idx_tb_atividade_lookup; Type: INDEX; Schema: dbsitedemas2016; Owner: sa_ecar; Tablespace: 
--

CREATE INDEX idx_tb_atividade_lookup ON tb_atividade USING btree (cod_iett);


--
-- TOC entry 3233 (class 1259 OID 8684863)
-- Name: idx_tb_prod_inter_lookup; Type: INDEX; Schema: dbsitedemas2016; Owner: sa_ecar; Tablespace: 
--

CREATE INDEX idx_tb_prod_inter_lookup ON tb_prod_inter USING btree (cod_iett);


--
-- TOC entry 3242 (class 2606 OID 8684784)
-- Name: co_satb_fk; Type: FK CONSTRAINT; Schema: dbsitedemas2016; Owner: sa_ecar
--

ALTER TABLE ONLY tb_usuario_grupo
    ADD CONSTRAINT co_satb_fk FOREIGN KEY (co_satb) REFERENCES tb_grupo_seg(co_satb);


--
-- TOC entry 3241 (class 2606 OID 8684779)
-- Name: co_usuario_fk; Type: FK CONSTRAINT; Schema: dbsitedemas2016; Owner: sa_ecar
--

ALTER TABLE ONLY tb_usuario_grupo
    ADD CONSTRAINT co_usuario_fk FOREIGN KEY (co_usuario) REFERENCES tb_usuario_usu(co_usuario);


--
-- TOC entry 3244 (class 2606 OID 8684834)
-- Name: co_usuario_fk; Type: FK CONSTRAINT; Schema: dbsitedemas2016; Owner: sa_ecar
--

ALTER TABLE ONLY tb_meta_iniciativa
    ADD CONSTRAINT co_usuario_fk FOREIGN KEY (cod_usu) REFERENCES tb_usuario_usu(co_usuario);


--
-- TOC entry 3251 (class 2606 OID 8684990)
-- Name: cod_iett_fk; Type: FK CONSTRAINT; Schema: dbsitedemas2016; Owner: sa_ecar
--

ALTER TABLE ONLY tb_atividade
    ADD CONSTRAINT cod_iett_fk FOREIGN KEY (cod_iett) REFERENCES tb_iett(cod_iett);


--
-- TOC entry 3248 (class 2606 OID 8685060)
-- Name: cod_iett_fk; Type: FK CONSTRAINT; Schema: dbsitedemas2016; Owner: sa_ecar
--

ALTER TABLE ONLY tb_prod_inter
    ADD CONSTRAINT cod_iett_fk FOREIGN KEY (cod_iett) REFERENCES tb_iett(cod_iett);


--
-- TOC entry 3245 (class 2606 OID 8685067)
-- Name: cod_iett_fk; Type: FK CONSTRAINT; Schema: dbsitedemas2016; Owner: sa_ecar
--

ALTER TABLE ONLY tb_meta_iniciativa
    ADD CONSTRAINT cod_iett_fk FOREIGN KEY (cod_iett) REFERENCES tb_iett(cod_iett);


--
-- TOC entry 3243 (class 2606 OID 8685074)
-- Name: cod_iett_fk; Type: FK CONSTRAINT; Schema: dbsitedemas2016; Owner: sa_ecar
--

ALTER TABLE ONLY tb_oe
    ADD CONSTRAINT cod_iett_fk FOREIGN KEY (cod_iett) REFERENCES tb_iett(cod_iett);


--
-- TOC entry 3253 (class 2606 OID 8685095)
-- Name: cod_iett_fk; Type: FK CONSTRAINT; Schema: dbsitedemas2016; Owner: sa_ecar
--

ALTER TABLE ONLY tb_etiqueta
    ADD CONSTRAINT cod_iett_fk FOREIGN KEY (cod_iett) REFERENCES tb_iett(cod_iett);


--
-- TOC entry 3254 (class 2606 OID 8685131)
-- Name: cod_iett_fk; Type: FK CONSTRAINT; Schema: dbsitedemas2016; Owner: sa_ecar
--

ALTER TABLE ONLY tb_monitoramento
    ADD CONSTRAINT cod_iett_fk FOREIGN KEY (cod_iett) REFERENCES tb_iett(cod_iett);


--
-- TOC entry 3249 (class 2606 OID 8685084)
-- Name: cod_mi_fk; Type: FK CONSTRAINT; Schema: dbsitedemas2016; Owner: sa_ecar
--

ALTER TABLE ONLY tb_prod_inter
    ADD CONSTRAINT cod_mi_fk FOREIGN KEY (cod_mi) REFERENCES tb_meta_iniciativa(cod_iett);


--
-- TOC entry 3246 (class 2606 OID 8685079)
-- Name: cod_oe_fk; Type: FK CONSTRAINT; Schema: dbsitedemas2016; Owner: sa_ecar
--

ALTER TABLE ONLY tb_meta_iniciativa
    ADD CONSTRAINT cod_oe_fk FOREIGN KEY (cod_oe) REFERENCES tb_oe(cod_iett);


--
-- TOC entry 3252 (class 2606 OID 8685089)
-- Name: cod_pi_fk; Type: FK CONSTRAINT; Schema: dbsitedemas2016; Owner: sa_ecar
--

ALTER TABLE ONLY tb_atividade
    ADD CONSTRAINT cod_pi_fk FOREIGN KEY (cod_pi) REFERENCES tb_prod_inter(cod_iett);


--
-- TOC entry 3247 (class 2606 OID 8684871)
-- Name: cod_usu_fk; Type: FK CONSTRAINT; Schema: dbsitedemas2016; Owner: sa_ecar
--

ALTER TABLE ONLY tb_prod_inter
    ADD CONSTRAINT cod_usu_fk FOREIGN KEY (cod_usu) REFERENCES tb_usuario_usu(co_usuario);


--
-- TOC entry 3250 (class 2606 OID 8684921)
-- Name: cod_usu_fk; Type: FK CONSTRAINT; Schema: dbsitedemas2016; Owner: sa_ecar
--

ALTER TABLE ONLY tb_atividade
    ADD CONSTRAINT cod_usu_fk FOREIGN KEY (cod_usu) REFERENCES tb_usuario_usu(co_usuario);


--
-- TOC entry 3255 (class 2606 OID 8685175)
-- Name: cod_usu_fk; Type: FK CONSTRAINT; Schema: dbsitedemas2016; Owner: sa_ecar
--

ALTER TABLE ONLY tb_monitoramento
    ADD CONSTRAINT cod_usu_fk FOREIGN KEY (cod_usu) REFERENCES tb_usuario_usu(co_usuario);


-- Completed on 2016-05-02 15:01:44 BRT

--
-- PostgreSQL database dump complete
--

