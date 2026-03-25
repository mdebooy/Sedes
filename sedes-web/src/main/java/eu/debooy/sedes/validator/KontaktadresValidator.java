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

package eu.debooy.sedes.validator;

import eu.debooy.doosutils.ComponentsUtils;
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.validator.ValiDatum;
import eu.debooy.doosutils.validator.Validator;
import eu.debooy.sedes.domain.KontaktadresDto;
import eu.debooy.sedes.form.Kontaktadres;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public class KontaktadresValidator {
  protected static final  String  LBL_ADRESID           = "_I18N.label.adres";
  protected static final  String  LBL_EINDDATUM         =
      "_I18N.label.totdatum";
  protected static final  String  LBL_KONTAKTID         = "_I18N.label.kontakt";
  protected static final  String  LBL_KONTAKTADRESTYPE  =
      "_I18N.label.kontaktadrestype";
  protected static final  String  LBL_OPMERKING         =
          "_I18N.label.opmerking";
  protected static final  String  LBL_STARTDATUM        =
      "_I18N.label.vandatum";
  protected static final  String  LBL_SUBADRES          =
      "_I18N.label.subadres";
  protected static final  String  LBL_TAAL              = "_I18N.label.taal";

  private KontaktadresValidator() {
   throw new IllegalStateException("Utility class");
  }

  public static List<Message> valideer(KontaktadresDto kontaktadres) {
  if (null == kontaktadres) {
     return ComponentsUtils.objectIsNull(KontaktadresDto.class.getSimpleName());
   }

   return valideer(new Kontaktadres(kontaktadres));
  }

  public static List<Message> valideer(Kontaktadres kontaktadres) {
    if (null == kontaktadres) {
      return ComponentsUtils.objectIsNull(Kontaktadres.class.getSimpleName());
    }

    List<Message> fouten  = new ArrayList<>();

    fouten.addAll(new Validator.Builder()
                               .setWaarde(kontaktadres.getAdresId())
                               .setAttribute(KontaktadresDto.COL_ADRESID)
                               .setLabel(LBL_ADRESID)
                               .setRequired()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(kontaktadres.getKontaktadrestype())
                               .setAttribute(
                                   KontaktadresDto.COL_KONTAKTADRESTYPE)
                               .setLabel(LBL_KONTAKTADRESTYPE)
                               .setMaxLengte(10)
                               .setRequired()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(kontaktadres.getKontaktId())
                               .setAttribute(KontaktadresDto.COL_KONTAKTID)
                               .setLabel(LBL_KONTAKTID)
                               .setRequired()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(kontaktadres.getOpmerking())
                               .setAttribute( KontaktadresDto.COL_OPMERKING)
                               .setLabel(LBL_OPMERKING)
                               .setMaxLengte(2000)
                               .valideer().getFouten());
    fouten.addAll(new ValiDatum.Builder()
                               .setStartdatum(kontaktadres.getStartdatum())
                               .setStartdatumAttribuut(
                                  KontaktadresDto.COL_STARTDATUM)
                               .setStartdatumLabel(LBL_STARTDATUM)
                               .setStartdatumRequired()
                               .setStartdatumVerleden()
                               .setEinddatum(kontaktadres.getEinddatum())
                               .setEinddatumAttribuut(
                                  KontaktadresDto.COL_EINDDATUM)
                               .setEinddatumLabel(LBL_EINDDATUM)
                               .setEinddatumVerleden()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(kontaktadres.getSubAdres())
                               .setAttribute( KontaktadresDto.COL_SUBADRES)
                               .setLabel(LBL_SUBADRES)
                               .setMaxLengte(255)
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(kontaktadres.getTaal())
                               .setAttribute(KontaktadresDto.COL_TAAL)
                               .setLabel(LBL_TAAL)
                               .setFixLengte(3)
                               .setRequired()
                               .setLowerCase()
                               .valideer().getFouten());

    return fouten;
  }
}
