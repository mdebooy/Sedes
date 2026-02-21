/*
 * Copyright (c) 2023 Marco de Booij
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

package eu.debooy.sedes;

import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.sedes.domain.KontaktDto;


/**
 * @author Marco de Booij
 */
public final class SedesTestConstants {
  public static final String  AANSPREEKID       = "Mr.";
  public static final Long    ADRESID           = 246L;
  public static final String  BREEDTE1          = "N";
  public static final String  BREEDTE2          = "S";
  public static final String  ADRESDATA         = "Adres 12";
  public static final String  EINDDATUM         = "08-02-2026";
  public static final String  GEBRUIKERSNAAM    = "login";
  public static final String  INITIALEN         = "SI";
  public static final String  ISO2              = "IS";
  public static final String  ISO3              = "ISO";
  public static final String  ISO6391           = "nl";
  public static final String  ISO6392T          = "nld";
  public static final String  KONTAKTADTYPE     = "prive";
  public static final Long    KONTAKTID         = 64L;
  public static final String  KONTAKTTYPE       = "P";
  public static final String  KONTAKTTYPEF      = "@";
  public static final String  KONTAKTNAAM       = "Kontakt";
  public static final String  KONTAKTTAAL       = "nld";
  public static final String  KONTAKTTAAL2      = "nl";
  public static final String  LANDNAAM          = "Landje";
  public static final Long    LANDID            = 100L;
  public static final Long    LANDNUMMER        = 32L;
  public static final String  LENGTE1           = "E";
  public static final String  LENGTE2           = "W";
  public static final Long    MUNTID            = 584L;
  public static final String  MUNTNAAM          = "Valuta";
  public static final String  MUNTTEKEN         = "cnt";
  public static final String  OPMERKING         = "Opmerking";
  public static final String  PLAATSNAAM        = "Plaats";
  public static final String  POSTKODE          = "PK-001";
  public static final String  POSTKODESCHEIDING = "PK-001";
  public static final String  POSTKODETYPE      = "a";
  public static final String  POSTLANDKODE      = "B";
  public static final String  PSEUDONIEM        = "Pseudo";
  public static final String  REGIONAAM         = "Regio";
  public static final String  REGIONAAM_G       = "Z-Regio";
  public static final String  REGIONAAM_K       = "A-Regio";
  public static final int     REGIO_HASH        = 829;
  public static final Long    REGIOID           = 200L;
  public static final String  REGIOKODE         = "RK123";
  public static final String  REGIOKODE_G       = "ZK123";
  public static final String  REGIOKODE_K       = "AK123";
  public static final String  ROEPNAAM          = "Roepnaam";
  public static final String  STARTDATUM        = "01-01-2026";
  public static final String  SUBEENHEID        = "CentiValuta";
  public static final String  SUBADRES          = "sub-adres";
  public static final String  SUBPOSTKODE       = "1234 AB";
  public static final String  TAAL              = "nld";
  public static final String  TUSSENVOEGSEL     = "van der";
  public static final String  VOORNAAM          = "Voornaam";
  public static final Long    WERELDDEELID      = 200L;
  public static final String  WERELDDEELNAAM    = "Werelddeelnaam";

  protected static final  String  LBL_OPMERKING = "_I18N.label.opmerking";

  public static final  Message ERR_OPMERKING =
    new Message.Builder()
               .setAttribute(KontaktDto.COL_OPMERKING)
               .setSeverity(Message.ERROR)
               .setMessage(PersistenceConstants.MAXLENGTH)
               .setParams(new Object[]{LBL_OPMERKING, 2000})
               .build();

  private SedesTestConstants() {
    throw new IllegalStateException("Utility class");
  }
}
