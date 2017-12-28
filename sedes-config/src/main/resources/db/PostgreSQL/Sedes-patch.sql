ALTER TABLE SEDES.KONTAKTADRESSEN
  DROP CONSTRAINT FK_KAD_ADRES_ID;
  
ALTER TABLE SEDES.KONTAKTADRESSEN
  ADD CONSTRAINT FK_KAD_ADRES_ID FOREIGN KEY (ADRES_ID)
  REFERENCES SEDES.ADRESSEN (ADRES_ID)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE SEDES.KONTAKTADRESSEN
  DROP CONSTRAINT FK_KAD_KONTAKT_ID;

ALTER TABLE SEDES.KONTAKTADRESSEN
  ADD CONSTRAINT FK_KAD_KONTAKT_ID FOREIGN KEY (KONTAKT_ID)
  REFERENCES SEDES.KONTAKTEN (KONTAKT_ID)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE SEDES.LANDEN
  DROP CONSTRAINT FK_LND_MUNT_ID;

ALTER TABLE SEDES.LANDEN
  ADD CONSTRAINT FK_LND_MUNT_ID FOREIGN KEY (MUNT_ID)
  REFERENCES SEDES.MUNTEN (MUNT_ID)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE SEDES.LANDEN
  DROP CONSTRAINT FK_LND_WERELDDEEL_ID;

ALTER TABLE SEDES.LANDEN
  ADD CONSTRAINT FK_LND_WERELDDEEL_ID FOREIGN KEY (WERELDDEEL_ID)
  REFERENCES SEDES.WERELDDELEN (WERELDDEEL_ID)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE SEDES.PLAATSEN
  DROP CONSTRAINT FK_PLA_LAND_ID;

ALTER TABLE SEDES.PLAATSEN
  ADD CONSTRAINT FK_PLA_LAND_ID FOREIGN KEY (LAND_ID)
  REFERENCES SEDES.LANDEN (LAND_ID)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE SEDES.PLAATSEN
  DROP CONSTRAINT FK_PLA_REGIO_ID;

ALTER TABLE SEDES.PLAATSEN
  ADD CONSTRAINT FK_PLA_REGIO_ID FOREIGN KEY (REGIO_ID)
  REFERENCES SEDES.REGIOS (REGIO_ID)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE SEDES.POSTLIJST_KONTAKTEN
  DROP CONSTRAINT FK_PLK_KONTAKT_ID;

ALTER TABLE SEDES.POSTLIJST_KONTAKTEN
  ADD CONSTRAINT FK_PLK_KONTAKT_ID FOREIGN KEY (KONTAKT_ID)
  REFERENCES SEDES.KONTAKTEN (KONTAKT_ID)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE SEDES.POSTLIJST_KONTAKTEN
  DROP CONSTRAINT FK_PLK_POSTLIJST_ID;

ALTER TABLE SEDES.POSTLIJST_KONTAKTEN
  ADD CONSTRAINT FK_PLK_POSTLIJST_ID FOREIGN KEY (POSTLIJST_ID)
  REFERENCES SEDES.POSTLIJSTEN (POSTLIJST_ID)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE SEDES.REGIOS
  DROP CONSTRAINT FK_REG_LAND_ID;

ALTER TABLE SEDES.REGIOS
  ADD CONSTRAINT FK_REG_LAND_ID FOREIGN KEY (LAND_ID)
  REFERENCES SEDES.LANDEN (LAND_ID)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;