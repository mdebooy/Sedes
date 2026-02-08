/**
 * Copyright 2025 Marco de Booij
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
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
import eu.debooy.sedes.domain.PlaatsDto;
import eu.debooy.sedes.domain.RegionaamDto;
import eu.debooy.sedes.form.Regionaam;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public final class RegionaamValidator {
  protected static final  String  LBL_NAAM    = "_I18N.label.regionaam";
  protected static final  String  LBL_REGIOID = "_I18N.label.regio";
  protected static final  String  LBL_TAAL    = "_I18N.label.taal";

  private RegionaamValidator() {
   throw new IllegalStateException("Utility class");
  }

  public static List<Message> valideer(RegionaamDto regionaam) {
    if (null == regionaam) {
      return ComponentsUtils.objectIsNull(RegionaamDto.class.getSimpleName());
    }

    return valideer(new Regionaam(regionaam));
  }

  public static List<Message> valideer(Regionaam regionaam) {
    if (null == regionaam) {
      return ComponentsUtils.objectIsNull(Regionaam.class.getSimpleName());
    }

    List<Message> fouten  = new ArrayList<>();

    fouten.addAll(new Validator.Builder()
                               .setWaarde(regionaam.getNaam())
                               .setAttribute(RegionaamDto.COL_NAAM)
                               .setLabel(LBL_NAAM)
                               .setMaxLengte(100)
                               .setRequired()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(regionaam.getRegioId())
                               .setAttribute(PlaatsDto.COL_REGIOID)
                               .setLabel(LBL_REGIOID)
                               .setRequired()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(regionaam.getTaal())
                               .setAttribute(RegionaamDto.COL_TAAL)
                               .setLabel(LBL_NAAM)
                               .setFixLengte(3)
                               .setRequired()
                               .setLowerCase()
                               .valideer().getFouten());

    return fouten;
  }
}
