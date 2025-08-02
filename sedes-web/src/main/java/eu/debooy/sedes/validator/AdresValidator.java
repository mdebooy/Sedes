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
import eu.debooy.doosutils.validator.Validator;
import eu.debooy.sedes.domain.AdresDto;
import eu.debooy.sedes.form.Adres;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public class AdresValidator {
  protected static final  String  LBL_ADRESDATA   = "_I18N.label.adres";
  protected static final  String  LBL_OPMERKING   = "_I18N.label.opmerking";
  protected static final  String  LBL_SUBPOSTKODE = "_I18N.label.subpostkode";

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

    fouten.addAll(new Validator.Builder()
                               .setWaarde(adres.getAdresdata())
                               .setAttribute(AdresDto.COL_ADRESDATA)
                               .setLabel(LBL_ADRESDATA)
                               .setMaxLengte(255)
                               .setRequired()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(adres.getOpmerking())
                               .setAttribute(AdresDto.COL_OPMERKING)
                               .setLabel(LBL_OPMERKING)
                               .setMaxLengte(2000)
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(adres.getSubPostkode())
                               .setAttribute(AdresDto.COL_SUBPOSTKODE)
                               .setLabel(LBL_SUBPOSTKODE)
                               .setMaxLengte(10)
                               .valideer().getFouten());

    return fouten;
  }
}
