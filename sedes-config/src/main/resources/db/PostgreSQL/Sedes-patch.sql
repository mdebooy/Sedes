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

ALTER TABLE SEDES.REGIONAMEN
  ADD CONSTRAINT CHK_RNM_TAAL  CHECK (TAAL = LOWER(TAAL));

ALTER TABLE SEDES.REGIONAMEN
  ADD CONSTRAINT FK_RNM_REGIO_ID FOREIGN KEY (REGIO_ID)
  REFERENCES SEDES.REGIOS (REGIO_ID)
  ON DELETE CASCADE
  ON UPDATE RESTRICT;

GRANT SELECT                         ON TABLE SEDES.REGIONAMEN              TO SEDES_SEL;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE SEDES.REGIONAMEN              TO SEDES_UPD;

COMMENT ON TABLE  SEDES.LANDNAMEN                         IS 'Deze tabel bevat alle landnamen.';
COMMENT ON COLUMN SEDES.LANDNAMEN.HOOFDSTAD               IS 'De hoofdstad van het land.';
COMMENT ON COLUMN SEDES.LANDNAMEN.LAND_ID                 IS 'De sleutel van het land.';
COMMENT ON COLUMN SEDES.LANDNAMEN.NAAM                    IS 'De naam van het land.';
COMMENT ON COLUMN SEDES.LANDNAMEN.OFFICIELE_NAAM          IS 'De officiële naam van het land.';
COMMENT ON COLUMN SEDES.LANDNAMEN.TAAL                    IS 'De taal, ISO 639-2T, van deze naam.';
COMMENT ON TABLE  SEDES.REGIONAMEN                        IS 'Deze tabel bevat alle regionamen.';
COMMENT ON COLUMN SEDES.REGIONAMEN.NAAM                   IS 'De naam van de regio.';
COMMENT ON COLUMN SEDES.REGIONAMEN.REGIO_ID               IS 'De sleutel van de regio.';
COMMENT ON COLUMN SEDES.REGIONAMEN.TAAL                   IS 'De taal, ISO 639-2T, van deze naam..';
COMMENT ON TABLE  SEDES.REGIOS                            IS 'Deze tabel bevat alle regios.';
COMMENT ON COLUMN SEDES.REGIOS.LAND_ID                    IS 'De sleutel van het land.';
COMMENT ON COLUMN SEDES.REGIOS.REGIO_ID                   IS 'De sleutel van de regio.';
COMMENT ON COLUMN SEDES.REGIOS.REGIOKODE                  IS 'De NUTS code van de regio.';

INSERT INTO SEDES.REGIONAMEN
SELECT NAAM, REGIO_ID, 'nld'
FROM SEDES.REGIOS;

