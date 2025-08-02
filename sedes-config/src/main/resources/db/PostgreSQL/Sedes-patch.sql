/*
 * Copyright (c) 2024 Marco de Booij
 *
 * Licensed under the EUPL, Version 1.2 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the Licence. You may
 * obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */

/**
 * Author:  Marco de Booij
 */

CREATE TABLE SEDES.REGIONAMEN (
  NAAM                            VARCHAR(100)    NOT NULL,
  REGIO_ID                        INTEGER         NOT NULL,
  TAAL                            CHAR(3)         NOT NULL,
  CONSTRAINT PK_REGIONAMEN PRIMARY KEY (REGIO_ID, TAAL);
);

ALTER TABLE SEDES.LANDEN ALTER COLUMN TAAL TYPE CHAR(3);
ALTER TABLE SEDES.LANDEN
  ADD CONSTRAINT CHK_LND_TAAL  CHECK (TAAL = LOWER(TAAL));

ALTER TABLE SEDES.LANDNAMEN ALTER COLUMN TAAL TYPE CHAR(3);
ALTER TABLE SEDES.LANDNAMEN
  ADD CONSTRAINT CHK_LNM_TAAL  CHECK (TAAL = LOWER(TAAL));

ALTER TABLE SEDES.PLAATSEN DROP  COLUMN BREEDTE;
ALTER TABLE SEDES.PLAATSEN ALTER COLUMN BREEDTEGRAAD TYPE NUMERIC(8, 6);
ALTER TABLE SEDES.PLAATSEN DROP  COLUMN LENGTE;
ALTER TABLE SEDES.PLAATSEN ALTER COLUMN LENGTEGRAAD TYPE NUMERIC(9, 6);

ALTER TABLE SEDES.POSTLIJST_KONTAKTEN RENAME CONSTRAINT PK_POSTLIJST_KONTAKTEN TO PK_POSTLIJSTKONTAKTEN;
ALTER TABLE SEDES.POSTLIJST_KONTAKTEN RENAME TO POSTLIJSTKONTAKTEN;

ALTER TABLE SEDES.POSTLIJSTEN RENAME COLUMN POSTLIJST TO NAAM;

ALTER TABLE SEDES.REGIONAMEN
  ADD CONSTRAINT CHK_RNM_TAAL  CHECK (TAAL = LOWER(TAAL));

ALTER TABLE SEDES.REGIONAMEN
  ADD CONSTRAINT FK_RNM_REGIO_ID FOREIGN KEY (REGIO_ID)
  REFERENCES SEDES.REGIOS (REGIO_ID)
  ON DELETE CASCADE
  ON UPDATE RESTRICT;

ALTER TABLE SEDES.MUNTEN
  ADD CONSTRAINT CHK_MNT_ISO3 CHECK (ISO3 = UPPER(ISO3));

ALTER TABLE SEDES.REGIOS
  ADD CONSTRAINT CHK_REG_REGIOKODE  CHECK (REGIOKODE = UPPER(REGIOKODE));

ALTER TABLE SEDES.REGIONAMEN
  ADD CONSTRAINT CHK_RGN_TAAL  CHECK (TAAL = LOWER(TAAL));

ALTER TABLE SEDES.WERELDDEELNAMEN ALTER COLUMN TAAL TYPE CHAR(3);
ALTER TABLE SEDES.WERELDDEELNAMEN
  ADD CONSTRAINT CHK_WDN_TAAL  CHECK (TAAL = LOWER(TAAL));

GRANT SELECT                         ON TABLE SEDES.REGIONAMEN              TO SEDES_SEL;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE SEDES.REGIONAMEN              TO SEDES_UPD;

COMMENT ON TABLE  SEDES.KONTAKTKONTAKTEN                    IS 'Deze tabel bevat alle kontakten die in kontakt staat met een ander kontakt.';
COMMENT ON COLUMN SEDES.KONTAKTKONTAKTEN.CHILDKONTAKT       IS 'De sleutel van het ´child´ kontakt.';
COMMENT ON COLUMN SEDES.KONTAKTKONTAKTEN.EINDDATUM          IS 'De datum waarna dit kontaktkontakt niet meer geldig is.';
COMMENT ON COLUMN SEDES.KONTAKTKONTAKTEN.KONTAKTKONTAKT_ID  IS 'De sleutel van het kontaktkontakt.';
COMMENT ON COLUMN SEDES.KONTAKTKONTAKTEN.PARENTKONTAKT      IS 'De sleutel van het ´parent´ kontakt.';
COMMENT ON COLUMN SEDES.KONTAKTKONTAKTEN.STARTDATUM         IS 'De datum waarop dit kontaktkontakt is ontstaan.';
COMMENT ON TABLE  SEDES.LANDEN                              IS 'Deze tabel bevat alle landen.';
COMMENT ON COLUMN SEDES.LANDEN.BESTAAT                      IS 'Bestaat het land nog (J/N)?';
COMMENT ON COLUMN SEDES.LANDEN.ISO2                         IS 'De ISO2 code van de munt.';
COMMENT ON COLUMN SEDES.LANDEN.ISO3                         IS 'De ISO3 code van de munt.';
COMMENT ON COLUMN SEDES.LANDEN.LAND_ID                      IS 'De sleutel van het land.';
COMMENT ON COLUMN SEDES.LANDEN.LANDNUMMER                   IS 'Het telefoon landnummer van het land.';
COMMENT ON COLUMN SEDES.LANDEN.MUNT_ID                      IS 'De sleutel van de munt.';
COMMENT ON COLUMN SEDES.LANDEN.POSTKODE_SCHEIDING           IS 'Het scheidingsteken van de postkode.';
COMMENT ON COLUMN SEDES.LANDEN.POSTKODE_TYPE                IS 'Het type postkode.';
COMMENT ON COLUMN SEDES.LANDEN.POST_LANDKODE                IS 'Het postkode van het land.';
COMMENT ON COLUMN SEDES.LANDEN.TAAL                         IS 'De officiële taal, ISO 639-2T, van het land.';
COMMENT ON COLUMN SEDES.LANDEN.VLAG                         IS 'De vlag van het land.';
COMMENT ON COLUMN SEDES.LANDEN.WERELDDEEL_ID                IS 'Het werelddeel waarin het land ligt.';
COMMENT ON TABLE  SEDES.LANDNAMEN                           IS 'Deze tabel bevat alle landnamen.';
COMMENT ON COLUMN SEDES.LANDNAMEN.HOOFDSTAD                 IS 'De hoofdstad van het land.';
COMMENT ON COLUMN SEDES.LANDNAMEN.LAND_ID                   IS 'De sleutel van het land.';
COMMENT ON COLUMN SEDES.LANDNAMEN.NAAM                      IS 'De naam van het land.';
COMMENT ON COLUMN SEDES.LANDNAMEN.OFFICIELE_NAAM            IS 'De officiële naam van het land.';
COMMENT ON COLUMN SEDES.LANDNAMEN.TAAL                      IS 'De taal, ISO 639-2T, van deze naam.';
COMMENT ON TABLE  SEDES.PLAATSEN                            IS 'Deze tabel bevat alle plaatsen.';
COMMENT ON COLUMN SEDES.PLAATSEN.BREEDTEGRAAD               IS 'De breedtegraad waarop de plaats ligt.';
COMMENT ON COLUMN SEDES.PLAATSEN.LAND_ID                    IS 'De sleutel van het land.';
COMMENT ON COLUMN SEDES.PLAATSEN.LENGTEGRAAD                IS 'De lengtegraad waarop de plaats ligt.';
COMMENT ON COLUMN SEDES.PLAATSEN.PLAATS_ID                  IS 'De sleutel van de plaat.';
COMMENT ON COLUMN SEDES.PLAATSEN.PLAATSNAAM                 IS 'De naam van de plaats.';
COMMENT ON COLUMN SEDES.PLAATSEN.POSTKODE                   IS 'De naam van de plaats.';
COMMENT ON COLUMN SEDES.PLAATSEN.REGIO_ID                   IS 'De sleutel van de regio.';
COMMENT ON COLUMN SEDES.PLAATSEN.ZONENUMMER                 IS 'Het zonenummer van de plaats.';
COMMENT ON TABLE  SEDES.POSTLIJSTEN                         IS 'Deze tabel bevat alle postlijsten.';
COMMENT ON COLUMN SEDES.POSTLIJSTEN.NAAM                    IS 'De naam van de postlijst.';
COMMENT ON COLUMN SEDES.POSTLIJSTEN.POSTLIJST_ID            IS 'De sleutel van de postlijst.';
COMMENT ON TABLE  SEDES.POSTLIJSTKONTAKTEN                  IS 'Deze tabel bevat alle kontakten op een postlijst.';
COMMENT ON COLUMN SEDES.POSTLIJSTKONTAKTEN.EINDDATUM        IS 'De datum waarna dit postlijstkontakt niet meer geldig is.';
COMMENT ON COLUMN SEDES.POSTLIJSTKONTAKTEN.KONTAKT_ID       IS 'De sleutel van het kontakt.';
COMMENT ON COLUMN SEDES.POSTLIJSTKONTAKTEN.POSTLIJST_ID     IS 'De sleutel van de postlijst.';
COMMENT ON COLUMN SEDES.POSTLIJSTKONTAKTEN.STARTDATUM       IS 'De datum waarop dit postlijstkontakt is ontstaan.';
COMMENT ON TABLE  SEDES.REGIONAMEN                          IS 'Deze tabel bevat alle regionamen.';
COMMENT ON COLUMN SEDES.REGIONAMEN.NAAM                     IS 'De naam van de regio.';
COMMENT ON COLUMN SEDES.REGIONAMEN.REGIO_ID                 IS 'De sleutel van de regio.';
COMMENT ON COLUMN SEDES.REGIONAMEN.TAAL                     IS 'De taal, ISO 639-2T, van deze naam.';
COMMENT ON TABLE  SEDES.REGIOS                              IS 'Deze tabel bevat alle regios.';
COMMENT ON COLUMN SEDES.REGIOS.LAND_ID                      IS 'De sleutel van het land.';
COMMENT ON COLUMN SEDES.REGIOS.REGIO_ID                     IS 'De sleutel van de regio.';
COMMENT ON COLUMN SEDES.REGIOS.REGIOKODE                    IS 'De NUTS code van de regio.';
COMMENT ON TABLE  SEDES.WERELDDEELNAMEN                     IS 'Deze tabel bevat alle werelddelen.';
COMMENT ON COLUMN SEDES.WERELDDEELNAMEN.NAAM                IS 'De naam van dit werelddeel.';
COMMENT ON COLUMN SEDES.WERELDDEELNAMEN.TAAL                IS 'De taal, ISO 639-2T, van dit werelddeel.';
COMMENT ON COLUMN SEDES.WERELDDEELNAMEN.WERELDDEEL_ID       IS 'De sleutel van het werelddeel.';
COMMENT ON TABLE  SEDES.WERELDDELEN                         IS 'Deze tabel bevat alle werelddelen.';
COMMENT ON COLUMN SEDES.WERELDDELEN.WERELDDEEL_ID           IS 'De sleutel van het werelddeel.';

INSERT INTO SEDES.REGIONAMEN
SELECT NAAM, REGIO_ID, 'nld'
FROM SEDES.REGIOS;

ALTER TABLE SEDES.REGIOS DROP COLUMN NAAM;

UPDATE SEDES.KONTAKTADRESSEN N
SET    TAAL = T.ISO_639_2T
FROM   DOOS.TALEN T
WHERE  T.ISO_639_1    = N.TAAL
AND    LENGTH(N.TAAL) = 2;

UPDATE SEDES.KONTAKTEN N
SET    TAAL = T.ISO_639_2T
FROM   DOOS.TALEN T
WHERE  T.ISO_639_1    = N.TAAL
AND    LENGTH(N.TAAL) = 2;

UPDATE SEDES.LANDEN N
SET    TAAL = T.ISO_639_2T
FROM   DOOS.TALEN T
WHERE  T.ISO_639_1    = N.TAAL
AND    LENGTH(N.TAAL) = 2;

UPDATE SEDES.LANDNAMEN N
SET    TAAL = T.ISO_639_2T
FROM   DOOS.TALEN T
WHERE  T.ISO_639_1    = N.TAAL
AND    LENGTH(N.TAAL) = 2;

UPDATE SEDES.REGIONAMEN N
SET    TAAL = T.ISO_639_2T
FROM   DOOS.TALEN T
WHERE  T.ISO_639_1    = N.TAAL
AND    LENGTH(N.TAAL) = 2;

UPDATE SEDES.WERELDDEELNAMEN N
SET    TAAL = T.ISO_639_2T
FROM   DOOS.TALEN T
WHERE  T.ISO_639_1    = N.TAAL
AND    LENGTH(N.TAAL) = 2;
