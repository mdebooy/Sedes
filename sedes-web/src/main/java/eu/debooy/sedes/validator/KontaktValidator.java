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

package eu.debooy.sedes.validator;

import eu.debooy.doosutils.ComponentsUtils;
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.validator.Validator;
import eu.debooy.sedes.Sedes;
import eu.debooy.sedes.domain.KontaktDto;
import eu.debooy.sedes.form.Kontakt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author Marco de Booij
 */
public final class KontaktValidator {
  protected static final  String  LBL_AANSPREEKID     =
      "_I18N.label.aanspreektitel";
  protected static final  String  LBL_GEBOORTEDATUM   =
      "_I18N.label.geboortedatum";
  protected static final  String  LBL_GEBRUIKERSNAAM  =
      "_I18N.label.gebruikersnaam";
  protected static final  String  LBL_INITIALEN       = "_I18N.label.initialen";
  protected static final  String  LBL_KONTAKTTYPE     =
      "_I18N.label.kontakttype";
  protected static final  String  LBL_NAAM            = "_I18N.label.naam";
  protected static final  String  LBL_OPMERKING       = "_I18N.label.opmerking";
  protected static final  String  LBL_PSEUDONIEM      =
      "_I18N.label.pseudoniem";
  protected static final  String  LBL_ROEPNAAM        = "_I18N.label.roepnaam";
  protected static final  String  LBL_TAAL            = "_I18N.label.taal";
  protected static final  String  LBL_TUSSENVOEGSEL   =
      "_I18N.label.tussenvoegsel";
  protected static final  String  LBL_VOORNAAM        = "_I18N.label.voornaam";

  private static final  String[]  kontakttypes  = {Sedes.TYP_GROEP,
                                                   Sedes.TYP_PERSOON,
                                                   Sedes.TYP_RECHTSPERSOON};

  private KontaktValidator() {
   throw new IllegalStateException("Utility class");
  }

  public static List<Message> valideer(KontaktDto kontakt) {
    if (null == kontakt) {
      return ComponentsUtils.objectIsNull("KontaktDto");
    }

    switch (DoosUtils.nullToEmpty(kontakt.getKontakttype())) {
      case Sedes.TYP_GROEP -> {
        kontakt.setAanspreekId(null);
        kontakt.setInitialen(null);
        kontakt.setPseudoniem(null);
        kontakt.setRoepnaam(null);
        kontakt.setTussenvoegsel(null);
      }
      case Sedes.TYP_RECHTSPERSOON -> {
        kontakt.setAanspreekId(null);
        kontakt.setInitialen(null);
        kontakt.setPseudoniem(null);
        kontakt.setTussenvoegsel(null);
      }
      default -> {
      }
    }

    return valideer(new Kontakt(kontakt));
  }

  public static List<Message> valideer(Kontakt kontakt) {
    if (null == kontakt) {
      return ComponentsUtils.objectIsNull("Kontakt");
    }

    switch (DoosUtils.nullToEmpty(kontakt.getKontakttype())) {
      case Sedes.TYP_GROEP -> {
        kontakt.setAanspreekId(null);
        kontakt.setInitialen(null);
        kontakt.setPseudoniem(null);
        kontakt.setRoepnaam(null);
        kontakt.setTussenvoegsel(null);
      }
      case Sedes.TYP_RECHTSPERSOON -> {
        kontakt.setAanspreekId(null);
        kontakt.setInitialen(null);
        kontakt.setPseudoniem(null);
        kontakt.setTussenvoegsel(null);
      }
      default -> {
      }
    }

    List<Message> fouten  = new ArrayList<>();

    fouten.addAll(new Validator.Builder()
                               .setWaarde(kontakt.getAanspreekId())
                               .setAttribute(KontaktDto.COL_AANSPREEKID)
                               .setLabel(LBL_AANSPREEKID)
                               .setMaxLengte(10)
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(kontakt.getGeboortedatum())
                               .setAttribute(KontaktDto.COL_GEBOORTEDATUM)
                               .setLabel(LBL_GEBOORTEDATUM)
                               .setVerleden()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(kontakt.getGebruikersnaam())
                               .setAttribute(KontaktDto.COL_GEBRUIKERSNAAM)
                               .setLabel(LBL_GEBRUIKERSNAAM)
                               .setMaxLengte(20)
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(kontakt.getInitialen())
                               .setAttribute(KontaktDto.COL_INITIALEN)
                               .setLabel(LBL_INITIALEN)
                               .setMaxLengte(20)
                               .valideer().getFouten());
    valideerKontakttype(kontakt.getKontakttype(), fouten);
    fouten.addAll(new Validator.Builder()
                               .setWaarde(kontakt.getNaam())
                               .setAttribute(KontaktDto.COL_NAAM)
                               .setLabel(LBL_NAAM)
                               .setMaxLengte(255)
                               .setRequired()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(kontakt.getOpmerking())
                               .setAttribute(KontaktDto.COL_OPMERKING)
                               .setLabel(LBL_OPMERKING)
                               .setMaxLengte(2000)
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(kontakt.getPseudoniem())
                               .setAttribute(KontaktDto.COL_PSEUDONIEM)
                               .setLabel(LBL_PSEUDONIEM)
                               .setMaxLengte(255)
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(kontakt.getRoepnaam())
                               .setAttribute(KontaktDto.COL_ROEPNAAM)
                               .setLabel(LBL_ROEPNAAM)
                               .setMaxLengte(255)
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(kontakt.getTaal())
                               .setAttribute(KontaktDto.COL_TAAL)
                               .setLabel(LBL_TAAL)
                               .setFixLengte(3)
                               .setLowerCase()
                               .setRequired()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(kontakt.getTussenvoegsel())
                               .setAttribute(KontaktDto.COL_TUSSENVOEGSEL)
                               .setLabel(LBL_TUSSENVOEGSEL)
                               .setMaxLengte(10)
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(kontakt.getVoornaam())
                               .setAttribute(KontaktDto.COL_VOORNAAM)
                               .setLabel(LBL_VOORNAAM)
                               .setMaxLengte(255)
                               .valideer().getFouten());

    return fouten;
  }

  private static void valideerKontakttype(String kontakttype,
                                          List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(kontakttype)) {
      fouten.add(new Message.Builder()
                            .setAttribute(KontaktDto.COL_KONTAKTTYPE)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_KONTAKTTYPE})
                            .build());
      return;
    }

    if (!Arrays.asList(kontakttypes).contains(kontakttype)) {
      fouten.add(new Message.Builder()
                            .setAttribute(KontaktDto.COL_KONTAKTTYPE)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.WRONGVALUE)
                            .setParams(new Object[]{LBL_KONTAKTTYPE,
                                                    String.format("%s, %s",
                                                            Sedes.TYP_GROEP,
                                                            Sedes.TYP_PERSOON),
                                                    Sedes.TYP_RECHTSPERSOON})
                            .build());
    }
  }
}
