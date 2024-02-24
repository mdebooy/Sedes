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
import eu.debooy.sedes.Sedes;
import eu.debooy.sedes.domain.KontaktDto;
import eu.debooy.sedes.form.Kontakt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * @author Marco de Booij
 */
public final class KontaktValidator extends SedesValidator {
  protected static final  String  LBL_AANSPREEKID     =
      "_I18N.label.aanspreektitel";
  protected static final  String  LBL_GEBOORTEDATUM   =
      "_I18N.label.geboortedatum";
  protected static final  String  LBL_GEBRUIKERSNAAM  =
      "_I18N.label.gebruikersnaam";
  protected static final  String  LBL_INITIALEN       =
      "_I18N.label.initialen";
  protected static final  String  LBL_KONTAKTTYPE     =
      "_I18N.label.kontakttype";
  protected static final  String  LBL_NAAM            = "_I18N.label.naam";
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
      case Sedes.TYP_GROEP:
        kontakt.setAanspreekId(null);
        kontakt.setInitialen(null);
        kontakt.setPseudoniem(null);
        kontakt.setRoepnaam(null);
        kontakt.setTussenvoegsel(null);
        break;
      case Sedes.TYP_RECHTSPERSOON:
        kontakt.setAanspreekId(null);
        kontakt.setInitialen(null);
        kontakt.setPseudoniem(null);
        kontakt.setTussenvoegsel(null);
        break;
      default:
        break;
    }

    return valideer(new Kontakt(kontakt));
  }

  public static List<Message> valideer(Kontakt kontakt) {
    if (null == kontakt) {
      return ComponentsUtils.objectIsNull("Kontakt");
    }

    switch (DoosUtils.nullToEmpty(kontakt.getKontakttype())) {
      case Sedes.TYP_GROEP:
        kontakt.setAanspreekId(null);
        kontakt.setInitialen(null);
        kontakt.setPseudoniem(null);
        kontakt.setRoepnaam(null);
        kontakt.setTussenvoegsel(null);
        break;
      case Sedes.TYP_RECHTSPERSOON:
        kontakt.setAanspreekId(null);
        kontakt.setInitialen(null);
        kontakt.setPseudoniem(null);
        kontakt.setTussenvoegsel(null);
        break;
      default:
        break;
    }

    List<Message> fouten  = new ArrayList<>();

    valideerAanspreekId(kontakt.getAanspreekId(), fouten);
    valideerGeboortedatum(kontakt.getGeboortedatum(), fouten);
    valideerGebruikersnaam(kontakt.getGebruikersnaam(), fouten);
    valideerInitialen(kontakt.getInitialen(), fouten);
    valideerKontakttype(kontakt.getKontakttype(), fouten);
    valideerNaam(kontakt.getNaam(), fouten);
    valideerOpmerking(kontakt.getOpmerking(), fouten);
    valideerPseudoniem(kontakt.getPseudoniem(), fouten);
    valideerRoepnaam(kontakt.getRoepnaam(), fouten);
    valideerTaal(kontakt.getTaal(), fouten);
    valideerTussenvoegsel(kontakt.getTussenvoegsel(), fouten);
    valideerVoornaam(kontakt.getVoornaam(), fouten);

    return fouten;
  }

  private static void valideerAanspreekId(String aanspreekId,
                                          List<Message> fouten) {
    if (DoosUtils.nullToEmpty(aanspreekId).length() > 10) {
      fouten.add(new Message.Builder()
                            .setAttribute(KontaktDto.COL_AANSPREEKID)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_AANSPREEKID, 10})
                            .build());
    }
  }

  private static void valideerGeboortedatum(Date geboortedatum,
                                            List<Message> fouten) {
    if (null == geboortedatum) {
      return;
    }

    if (geboortedatum.after(new Date())) {
      fouten.add(new Message.Builder()
                            .setAttribute(KontaktDto.COL_GEBOORTEDATUM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.FUTURE)
                            .setParams(new Object[]{LBL_GEBOORTEDATUM})
                            .build());
    }
  }

  private static void valideerGebruikersnaam(String gebruikersnaam,
                                             List<Message> fouten) {
    if (DoosUtils.nullToEmpty(gebruikersnaam).length() > 20) {
      fouten.add(new Message.Builder()
                            .setAttribute(KontaktDto.COL_GEBRUIKERSNAAM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_GEBRUIKERSNAAM, 20})
                            .build());
    }
  }

  private static void valideerInitialen(String initialen,
                                        List<Message> fouten) {
    if (DoosUtils.nullToEmpty(initialen).length() > 20) {
      fouten.add(new Message.Builder()
                            .setAttribute(KontaktDto.COL_INITIALEN)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_INITIALEN, 20})
                            .build());
    }
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

  private static void valideerNaam(String naam, List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(naam)) {
      fouten.add(new Message.Builder()
                            .setAttribute(KontaktDto.COL_NAAM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_NAAM})
                            .build());
      return;
    }

    if (naam.length() > 255) {
      fouten.add(new Message.Builder()
                            .setAttribute(KontaktDto.COL_NAAM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_NAAM, 255})
                            .build());
    }
  }

  private static void valideerPseudoniem(String pseudoniem,
                                         List<Message> fouten) {
    if (DoosUtils.nullToEmpty(pseudoniem).length() > 255) {
      fouten.add(new Message.Builder()
                            .setAttribute(KontaktDto.COL_PSEUDONIEM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_PSEUDONIEM, 255})
                            .build());
    }
  }

  private static void valideerRoepnaam(String roepnaam,
                                       List<Message> fouten) {
    if (DoosUtils.nullToEmpty(roepnaam).length() > 255) {
      fouten.add(new Message.Builder()
                            .setAttribute(KontaktDto.COL_ROEPNAAM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_ROEPNAAM, 255})
                            .build());
    }
  }

  private static void valideerTaal(String taal, List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(taal)) {
      fouten.add(new Message.Builder()
                            .setAttribute(KontaktDto.COL_TAAL)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_TAAL})
                            .build());
      return;
    }

    if (taal.length() != 3) {
      fouten.add(new Message.Builder()
                            .setAttribute(KontaktDto.COL_TAAL)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.FIXLENGTH)
                            .setParams(new Object[]{LBL_TAAL, 3})
                            .build());
    }
  }

  private static void valideerTussenvoegsel(String tussenvoegsel,
                                            List<Message> fouten) {
    if (DoosUtils.nullToEmpty(tussenvoegsel).length() > 10) {
      fouten.add(new Message.Builder()
                            .setAttribute(KontaktDto.COL_TUSSENVOEGSEL)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_TUSSENVOEGSEL, 10})
                            .build());
    }
  }

  private static void valideerVoornaam(String voornaam,
                                       List<Message> fouten) {
    if (DoosUtils.nullToEmpty(voornaam).length() > 255) {
      fouten.add(new Message.Builder()
                            .setAttribute(KontaktDto.COL_VOORNAAM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_VOORNAAM, 255})
                            .build());
    }
  }
}
