-- Invoeren van alle Codes voor de Sedes Applicatie.
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

INSERT INTO DOOS.I18N_CODES
       (CODE, CODE_ID)
VALUES ('aanspreektitel.hr', nextval('DOOS.SEQ_I18N_CODES'));

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'nl', 'Hr.');

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'en', 'Mr.');

INSERT INTO DOOS.I18N_CODES
       (CODE, CODE_ID)
VALUES ('aanspreektitel.hrmevr', nextval('DOOS.SEQ_I18N_CODES'));

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'nl', 'Hr. en Mevr.');

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'en', 'Mr. and Mrs.');

INSERT INTO DOOS.I18N_CODES
       (CODE, CODE_ID)
VALUES ('aanspreektitel.mej', nextval('DOOS.SEQ_I18N_CODES'));

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'nl', 'Mej.');

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'en', 'Miss');

INSERT INTO DOOS.I18N_CODES
       (CODE, CODE_ID)
VALUES ('aanspreektitel.mevr', nextval('DOOS.SEQ_I18N_CODES')); 

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'nl', 'Mevr.');

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'en', 'Mrs.');

INSERT INTO DOOS.I18N_CODES
       (CODE, CODE_ID)
VALUES ('aanspreektitel.dr', nextval('DOOS.SEQ_I18N_CODES'));

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'nl', 'Dr.');

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'en', 'Dr.');

INSERT INTO DOOS.I18N_CODES
       (CODE, CODE_ID)
VALUES ('aanspreektitel.fam', nextval('DOOS.SEQ_I18N_CODES'));

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'nl', 'Fam.');

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'en', 'Fam.');

INSERT INTO DOOS.I18N_CODES
       (CODE, CODE_ID)
VALUES ('adrestype.woonadres', nextval('DOOS.SEQ_I18N_CODES'));

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'nl', 'Woonadres');

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'en', 'Homeaddress');

INSERT INTO DOOS.I18N_CODES
       (CODE, CODE_ID)
VALUES ('adrestype.postadres', nextval('DOOS.SEQ_I18N_CODES'));

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'nl', 'Postadres');

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'en', 'Postaladdress');

INSERT INTO DOOS.I18N_CODES
       (CODE, CODE_ID)
VALUES ('adrestype.werk', nextval('DOOS.SEQ_I18N_CODES'));

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'nl', 'Werk');

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'en', 'Work');

INSERT INTO DOOS.I18N_CODES
       (CODE, CODE_ID)
VALUES ('adrestype.gsm', nextval('DOOS.SEQ_I18N_CODES'));

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'nl', 'GSM');

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'en', 'GSM');

INSERT INTO DOOS.I18N_CODES
       (CODE, CODE_ID)
VALUES ('adrestype.fax', nextval('DOOS.SEQ_I18N_CODES'));

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'nl', 'Fax');

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'en', 'Fax');

INSERT INTO DOOS.I18N_CODES
       (CODE, CODE_ID)
VALUES ('adrestype.Internet', nextval('DOOS.SEQ_I18N_CODES'));

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'nl', 'Internet');

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'en', 'Internet');

INSERT INTO DOOS.I18N_CODES
       (CODE, CODE_ID)
VALUES ('adrestype.telefoon', nextval('DOOS.SEQ_I18N_CODES'));

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'nl', 'Telefoon');

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'en', 'Phone');

INSERT INTO DOOS.I18N_CODES
       (CODE, CODE_ID)
VALUES ('adrestype.email', nextval('DOOS.SEQ_I18N_CODES'));

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'nl', 'E-Mail');

INSERT INTO DOOS.I18N_CODE_TEKSTEN
       (CODE_ID, TAAL_KODE, TEKST)
VALUES (currval('DOOS.SEQ_I18N_CODES'), 'en', 'Email');
