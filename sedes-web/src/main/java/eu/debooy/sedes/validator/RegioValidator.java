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
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.validator.Validator;
import eu.debooy.sedes.domain.RegioDto;
import eu.debooy.sedes.form.Regio;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public final class RegioValidator {
  protected static final  String  LBL_LANDID    = "_I18N.label.land";
  protected static final  String  LBL_REGIOKODE = "_I18N.label.regiokode";

  private RegioValidator() {
   throw new IllegalStateException("Utility class");
  }

  public static List<Message> valideer(RegioDto regio) {
    if (null == regio) {
      return ComponentsUtils.objectIsNull("RegioDto");
    }

    return valideer(new Regio(regio));
  }

  public static List<Message> valideer(Regio regio) {
    if (null == regio) {
      return ComponentsUtils.objectIsNull("Regio");
    }

    List<Message> fouten  = new ArrayList<>();

    fouten.addAll(new Validator.Builder()
                               .setWaarde(regio.getLandId())
                               .setAttribute(RegioDto.COL_LANDID)
                               .setLabel(LBL_LANDID)
                               .setRequired()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(regio.getRegiokode())
                               .setAttribute(RegioDto.COL_REGIOKODE)
                               .setLabel(LBL_REGIOKODE)
                               .setMaxLengte(5)
                               .setUpperCase()
                               .setRequired()
                               .valideer().getFouten());

    return fouten;
  }
}