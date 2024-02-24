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
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.sedes.domain.KontaktadresDto;
import eu.debooy.sedes.form.Kontaktadres;
import static eu.debooy.sedes.validator.SedesValidator.valideerOpmerking;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public class KontaktadresValidator extends SedesValidator {
  protected static final  String  LBL_ADRESID           =
      "_I18N.label.adres";
  protected static final  String  LBL_KONTAKTID         =
      "_I18N.label.kontakt";
  protected static final  String  LBL_KONTAKTADRESTYPE  =
      "_I18N.label.kontaktadrestype";
  protected static final  String  LBL_SUBADRES          =
      "_I18N.label.subadres";
  protected static final  String  LBL_TAAL              =
          "_I18N.label.taal";

  private KontaktadresValidator() {
   throw new IllegalStateException("Utility class");
  }

  public static List<Message> valideer(KontaktadresDto kontaktadres) {
  if (null == kontaktadres) {
     return ComponentsUtils.objectIsNull("KontaktadresDto");
   }

   return valideer(new Kontaktadres(kontaktadres));
  }

  public static List<Message> valideer(Kontaktadres kontaktadres) {
    if (null == kontaktadres) {
      return ComponentsUtils.objectIsNull("Kontaktadres");
    }

    List<Message> fouten  = new ArrayList<>();

    valideerAdresId(kontaktadres.getAdresId(), fouten);
    valideerDatums(kontaktadres.getStartdatum(), kontaktadres.getEinddatum(),
                   fouten);
    valideerKontaktId(kontaktadres.getKontaktId(), fouten);
    valideerKontaktadrestype(kontaktadres.getKontaktadrestype(), fouten);
    valideerOpmerking(kontaktadres.getOpmerking(), fouten);
    valideerSubAdres(kontaktadres.getSubAdres(), fouten);
    valideerTaal(kontaktadres.getTaal(), fouten);

    return fouten;
  }

  private static void valideerAdresId(Long adresId, List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(adresId)) {
      fouten.add(new Message.Builder()
                            .setAttribute(KontaktadresDto.COL_ADRESID)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_ADRESID})
                            .build());
    }
  }

  private static void valideerKontaktId(Long kontaktId, List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(kontaktId)) {
      fouten.add(new Message.Builder()
                            .setAttribute(KontaktadresDto.COL_KONTAKTID)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_KONTAKTID})
                            .build());
    }
  }

  private static void valideerKontaktadrestype(String kontaktadrestype,
                                               List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(kontaktadrestype)) {
      fouten.add(new Message.Builder()
                            .setAttribute(KontaktadresDto.COL_KONTAKTADRESTYPE)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_KONTAKTADRESTYPE})
                            .build());

      return;
    }

    if (kontaktadrestype.length() > 10) {
      fouten.add(new Message.Builder()
                            .setAttribute(KontaktadresDto.COL_KONTAKTADRESTYPE)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_KONTAKTADRESTYPE, 10})
                            .build());
    }
  }

  private static void valideerSubAdres(String subAdres,
                                        List<Message> fouten) {
    if (DoosUtils.nullToEmpty(subAdres).length() > 255) {
      fouten.add(new Message.Builder()
                            .setAttribute(KontaktadresDto.COL_SUBADRES)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_SUBADRES, 255})
                            .build());
    }
  }

  private static void valideerTaal(String taal,  List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(taal)) {
      return;
    }

    if (taal.length() != 3) {
      fouten.add(new Message.Builder()
                            .setAttribute(KontaktadresDto.COL_TAAL)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.FIXLENGTH)
                            .setParams(new Object[]{LBL_TAAL, 3})
                            .build());
    }
  }
}
