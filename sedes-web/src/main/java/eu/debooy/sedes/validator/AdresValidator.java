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
import eu.debooy.sedes.domain.AdresDto;
import eu.debooy.sedes.form.Adres;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public class AdresValidator extends SedesValidator {
  protected static final  String  LBL_ADRESDATA   =
      "_I18N.label.adres";
  protected static final  String  LBL_SUBPOSTKODE =
      "_I18N.label.subpostkode";

  private AdresValidator() {
   throw new IllegalStateException("Utility class");
  }

  public static List<Message> valideer(AdresDto adres) {
  if (null == adres) {
     return ComponentsUtils.objectIsNull("AdresDto");
   }

   return valideer(new Adres(adres));
  }

  public static List<Message> valideer(Adres adres) {
    if (null == adres) {
      return ComponentsUtils.objectIsNull("Adres");
    }

    List<Message> fouten  = new ArrayList<>();

    valideerAdresdata(adres.getAdresdata(), fouten);
    valideerOpmerking(adres.getOpmerking(), fouten);
    valideerSubPostkode(adres.getSubPostkode(), fouten);

    return fouten;
  }

  private static void valideerAdresdata(String adresdata,
                                        List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(adresdata)) {
      fouten.add(new Message.Builder()
                            .setAttribute(AdresDto.COL_ADRESDATA)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_ADRESDATA})
                            .build());
      return;
    }

    if (adresdata.length() > 255) {
      fouten.add(new Message.Builder()
                            .setAttribute(AdresDto.COL_ADRESDATA)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_ADRESDATA, 255})
                            .build());
    }
  }

  private static void valideerSubPostkode(String subPostkode,
                                                 List<Message> fouten) {
    if (DoosUtils.nullToEmpty(subPostkode).length() > 10) {
      fouten.add(new Message.Builder()
                            .setAttribute(AdresDto.COL_SUBPOSTKODE)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_SUBPOSTKODE, 10})
                            .build());
    }
  }
}
