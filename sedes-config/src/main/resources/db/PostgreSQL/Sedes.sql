-- Kreatie van alle objecten voor het Sedes schema.
-- 
-- Copyright 2012 Marco de Booij
--
-- Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
-- the European Commission - subsequent versions of the EUPL (the "Licence");
-- you may not use this work except in compliance with the Licence. You may
-- obtain a copy of the Licence at:
--
-- http://www.osor.eu/eupl
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
-- WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the Licence for the specific language governing permissions and
-- limitations under the Licence.
--
-- Project: Sedes
-- Author: Marco de Booij

\echo    Passwords
\prompt 'SEDES_APP: ' sedes_app_pw
\set q_sedes_app_pw '\'':sedes_app_pw'\''

-- Gebruikers en rollen.
CREATE ROLE SEDES_APP LOGIN
  PASSWORD :q_sedes_app_pw
  NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE;

CREATE SCHEMA SEDES;

CREATE ROLE SEDES_SEL NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE;
CREATE ROLE SEDES_UPD NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE;

GRANT USAGE ON SCHEMA SEDES  TO SEDES_SEL;
GRANT USAGE ON SCHEMA SEDES  TO SEDES_UPD;

GRANT SEDES_UPD TO SEDES_APP;

GRANT CONNECT ON DATABASE :DBNAME TO SEDES_APP;

-- Sequences
CREATE SEQUENCE SEDES.SEQ_ADRESSEN
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE SEQUENCE SEDES.SEQ_KONTAKTADRESSEN
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE SEQUENCE SEDES.SEQ_KONTAKTEN
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE SEQUENCE SEDES.SEQ_LANDEN
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE SEQUENCE SEDES.SEQ_MUNTEN
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE SEQUENCE SEDES.SEQ_PLAATSEN
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE SEQUENCE SEDES.SEQ_POSTLIJSTEN
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE SEQUENCE SEDES.SEQ_REGIOS
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE SEQUENCE SEDES.SEQ_WERELDDELEN
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

-- Tabellen
CREATE TABLE SEDES.ADRESSEN (
  ADRES                           VARCHAR(255)    NOT NULL,
  ADRES_ID                        INTEGER         NOT NULL  DEFAULT NEXTVAL('SEDES.SEQ_ADRESSEN'::REGCLASS),
  OPMERKING                       TEXT,
  PLAATS_ID                       INTEGER,
  SUB_POSTKODE                    VARCHAR(10),
  CONSTRAINT PK_ADRESSEN PRIMARY KEY (ADRES_ID)
);

CREATE TABLE SEDES.KONTAKTADRESSEN (
  ADRES_ID                        INTEGER         NOT NULL,
  ADRESTYPE                       VARCHAR(50)     NOT NULL,
  KONTAKTADRES_ID                 INTEGER         NOT NULL  DEFAULT NEXTVAL('SEDES.SEQ_KONTAKTADRESSEN'::REGCLASS),
  KONTAKT_ID                      INTEGER         NOT NULL,
  KONTAKTTYPE                     VARCHAR(50)     NOT NULL,
  OPMERKING                       TEXT,
  SUB_ADRES                       VARCHAR(255),
  TAAL                            CHAR(2),
  CONSTRAINT PK_KONTAKTADRESSEN PRIMARY KEY (KONTAKTADRES_ID)
);

CREATE TABLE SEDES.KONTAKTEN (
  AANSPREEK_ID                    VARCHAR(50),
  GEBOORTEDATUM                   DATE,
  GEBRUIKERSNAAM                  VARCHAR(20),
  INITIALEN                       VARCHAR(20),
  KONTAKT_ID                      INTEGER         NOT NULL  DEFAULT NEXTVAL('SEDES.SEQ_KONTAKTEN'::REGCLASS),
  NAAM                            VARCHAR(255)    NOT NULL,
  OFFICIEEL                       CHAR(1)         NOT NULL  DEFAULT 'N',
  OPMERKING                       TEXT,
  SEXE                            CHAR(1)         NOT NULL,
  TAAL                            CHAR(2),
  VOORNAAM                        VARCHAR(255),
  CONSTRAINT PK_KONTAKTEN PRIMARY KEY (KONTAKT_ID)
);

CREATE TABLE SEDES.LANDEN (
  BESTAAT                         CHAR(2)         NOT NULL  DEFAULT 'J',
  ISO2                            CHAR(2),
  ISO3                            CHAR(3)         NOT NULL,
  LAND_ID                         INTEGER         NOT NULL  DEFAULT NEXTVAL('SEDES.SEQ_LANDEN'::REGCLASS),
  LANDNUMMER                      SMALLINT,
  MUNT_ID                         INTEGER         NOT NULL,
  POSTKODE_SCHEIDING              VARCHAR(10),
  POSTKODE_TYPE                   CHAR(1)         NOT NULL  DEFAULT '1',
  POST_LANDKODE                   CHAR(3)         NOT NULL,
  TAAL                            CHAR(2)         NOT NULL,
  VLAG                            BYTEA,
  WERELDDEEL_ID                   INTEGER         NOT NULL,
  CONSTRAINT PK_LANDEN PRIMARY KEY (LAND_ID)
);

CREATE TABLE SEDES.LANDNAMEN (
  HOOFDSTAD                       VARCHAR(100),
  LAND_ID                         INTEGER         NOT NULL,
  LANDNAAM                        VARCHAR(100)    NOT NULL,
  OFFICIELE_NAAM                  VARCHAR(225),
  TAAL                            CHAR(2)         NOT NULL,
  CONSTRAINT PK_LANDNAMEN PRIMARY KEY (LAND_ID, TAAL)
);

CREATE TABLE SEDES.MUNTEN (
  BESTAAT                         CHAR(2)         NOT NULL  DEFAULT 'J',
  DECIMALEN                       SMALLINT        NOT NULL  DEFAULT '2',
  MUNT                            VARCHAR(100)    NOT NULL,
  MUNT_ID                         INTEGER         NOT NULL  DEFAULT NEXTVAL('SEDES.SEQ_MUNTEN'::REGCLASS),
  ISO3                            CHAR(3)         NOT NULL,
  MUNTTEKEN                       CHAR(3),
  SUBEENHEID                      VARCHAR(100),
  CONSTRAINT PK_MUNTEN PRIMARY KEY (MUNT_ID)
);

CREATE TABLE SEDES.PLAATSEN (
  BREEDTE                         CHAR(1),
  BREEDTEGRAAD                    NUMERIC(4,2),
  LAND_ID                         INTEGER         NOT NULL,
  LENGTE                          CHAR(1),
  LENGTEGRAAD                     NUMERIC(5,2),
  PLAATS_ID                       INTEGER         NOT NULL  DEFAULT NEXTVAL('SEDES.SEQ_PLAATSEN'::REGCLASS),
  PLAATSNAAM                      VARCHAR(100)    NOT NULL,
  POSTKODE                        VARCHAR(15),
  REGIO_ID                        INTEGER,
  ZONENUMMER                      INTEGER,
  CONSTRAINT PK_PLAATSEN PRIMARY KEY (PLAATS_ID)
);

CREATE TABLE SEDES.POSTLIJST_KONTAKTEN (
  KONTAKT_ID                     INTEGER         NOT NULL,
  POSTLIJST_ID                   INTEGER         NOT NULL,
  CONSTRAINT PK_POSTLIJST_KONTAKTEN PRIMARY KEY (POSTLIJST_ID, KONTAKT_ID)
);

CREATE TABLE SEDES.POSTLIJSTEN (
  POSTLIJST                      VARCHAR(100)    NOT NULL,
  POSTLIJST_ID                   INTEGER         NOT NULL  DEFAULT NEXTVAL('SEDES.SEQ_POSTLIJSTEN'::REGCLASS),
  CONSTRAINT PK_POSTLIJSTEN PRIMARY KEY (POSTLIJST_ID)
);

CREATE TABLE SEDES.REGIOS (
  LAND_ID                         INTEGER         NOT NULL,
  REGIO                           VARCHAR(100)    NOT NULL,
  REGIOKODE                       VARCHAR(5)      NOT NULL,
  REGIO_ID                        INTEGER         NOT NULL  DEFAULT NEXTVAL('SEDES.SEQ_REGIOS'::REGCLASS),
  CONSTRAINT PK_REGIOS PRIMARY KEY (REGIO_ID)
);

CREATE TABLE SEDES.WERELDDEELNAMEN (
  WERELDDEELNAAM                  VARCHAR(100)    NOT NULL,
  TAAL                            CHAR(2)         NOT NULL,
  WERELDDEEL_ID                   INTEGER         NOT NULL,
  CONSTRAINT PK_WERELDDEELNAMEN PRIMARY KEY (WERELDDEEL_ID, TAAL)
);

CREATE TABLE SEDES.WERELDDELEN (
  WERELDDEEL_ID                   INTEGER         NOT NULL  DEFAULT NEXTVAL('SEDES.SEQ_WERELDDELEN'::REGCLASS),
  CONSTRAINT PK_WERELDDELEN PRIMARY KEY (WERELDDEEL_ID)
);

-- Views

-- Constraints
ALTER TABLE SEDES.KONTAKTADRESSEN
  ADD CONSTRAINT FK_KAD_ADRES_ID FOREIGN KEY (ADRES_ID)
  REFERENCES SEDES.ADRESSEN (ADRES_ID)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE SEDES.KONTAKTADRESSEN
  ADD CONSTRAINT FK_KAD_KONTAKT_ID FOREIGN KEY (KONTAKT_ID)
  REFERENCES SEDES.KONTAKTEN (KONTAKT_ID)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE SEDES.LANDEN
  ADD CONSTRAINT FK_LND_MUNT_ID FOREIGN KEY (MUNT_ID)
  REFERENCES SEDES.MUNTEN (MUNT_ID)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE SEDES.LANDEN
  ADD CONSTRAINT FK_LND_WERELDDEEL_ID FOREIGN KEY (WERELDDEEL_ID)
  REFERENCES SEDES.WERELDDELEN (WERELDDEEL_ID)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE SEDES.LANDNAMEN
  ADD CONSTRAINT FK_LNM_LAND_ID FOREIGN KEY (LAND_ID)
  REFERENCES SEDES.LANDEN (LAND_ID)
  ON DELETE CASCADE
  ON UPDATE RESTRICT;

ALTER TABLE SEDES.PLAATSEN
  ADD CONSTRAINT FK_PLA_LAND_ID FOREIGN KEY (LAND_ID)
  REFERENCES SEDES.LANDEN (LAND_ID)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE SEDES.PLAATSEN
  ADD CONSTRAINT FK_PLA_REGIO_ID FOREIGN KEY (REGIO_ID)
  REFERENCES SEDES.REGIOS (REGIO_ID)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE SEDES.POSTLIJST_KONTAKTEN
  ADD CONSTRAINT FK_PLK_KONTAKT_ID FOREIGN KEY (KONTAKT_ID)
  REFERENCES SEDES.KONTAKTEN (KONTAKT_ID)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE SEDES.POSTLIJST_KONTAKTEN
  ADD CONSTRAINT FK_PLK_POSTLIJST_ID FOREIGN KEY (POSTLIJST_ID)
  REFERENCES SEDES.POSTLIJSTEN (POSTLIJST_ID)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE SEDES.REGIOS
  ADD CONSTRAINT FK_REG_LAND_ID FOREIGN KEY (LAND_ID)
  REFERENCES SEDES.LANDEN (LAND_ID)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE SEDES.WERELDDEELNAMEN
  ADD CONSTRAINT FK_WDN_WERELDDEEL_ID FOREIGN KEY (WERELDDEEL_ID)
  REFERENCES SEDES.WERELDDELEN (WERELDDEEL_ID)
  ON DELETE CASCADE
  ON UPDATE RESTRICT;

-- Grant rechten
GRANT SELECT                         ON TABLE SEDES.ADRESSEN                TO SEDES_SEL;
GRANT SELECT                         ON TABLE SEDES.KONTAKTADRESSEN         TO SEDES_SEL;
GRANT SELECT                         ON TABLE SEDES.KONTAKTEN               TO SEDES_SEL;
GRANT SELECT                         ON TABLE SEDES.LANDEN                  TO SEDES_SEL;
GRANT SELECT                         ON TABLE SEDES.LANDNAMEN               TO SEDES_SEL;
GRANT SELECT                         ON TABLE SEDES.MUNTEN                  TO SEDES_SEL;
GRANT SELECT                         ON TABLE SEDES.PLAATSEN                TO SEDES_SEL;
GRANT SELECT                         ON TABLE SEDES.POSTLIJST_KONTAKTEN     TO SEDES_SEL;
GRANT SELECT                         ON TABLE SEDES.POSTLIJSTEN             TO SEDES_SEL;
GRANT SELECT                         ON TABLE SEDES.REGIOS                  TO SEDES_SEL;
GRANT SELECT                         ON TABLE SEDES.WERELDDEELNAMEN         TO SEDES_SEL;
GRANT SELECT                         ON TABLE SEDES.WERELDDELEN             TO SEDES_SEL;

GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE SEDES.ADRESSEN                TO SEDES_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE SEDES.KONTAKTADRESSEN         TO SEDES_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE SEDES.KONTAKTEN               TO SEDES_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE SEDES.LANDEN                  TO SEDES_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE SEDES.LANDNAMEN               TO SEDES_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE SEDES.MUNTEN                  TO SEDES_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE SEDES.PLAATSEN                TO SEDES_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE SEDES.POSTLIJST_KONTAKTEN     TO SEDES_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE SEDES.POSTLIJSTEN             TO SEDES_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE SEDES.REGIOS                  TO SEDES_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE SEDES.WERELDDEELNAMEN         TO SEDES_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE SEDES.WERELDDELEN             TO SEDES_UPD;

INSERT INTO SEDES.WERELDDELEN VALUES (0);

INSERT INTO SEDES.WERELDDEELNAMEN VALUES ('Aarde', 'nl', 0);

INSERT INTO SEDES.MUNTEN VALUES ('N', 2, 'Onbekend', 0, '-', NULL, NULL);

INSERT INTO SEDES.LANDEN VALUES ('N', NULL, '-', 0, NULL, 0, NULL, '1', '-', '??', NULL, 0);

INSERT INTO SEDES.LANDNAMEN VALUES (NULL, 0, 'Onbekend', NULL, 'nl');

