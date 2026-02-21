/**
 * Copyright 2016 Marco de Booij
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
import eu.debooy.sedes.domain.LandnaamDto;
import eu.debooy.sedes.domain.PlaatsDto;
import eu.debooy.sedes.form.Landnaam;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public final class LandnaamValidator {
  protected static final  String  LBL_LANDID  = "_I18N.label.land";
  protected static final  String  LBL_NAAM    = "_I18N.label.landnaam";
  protected static final  String  LBL_TAAL    = "_I18N.label.taal";

  private LandnaamValidator() {
   throw new IllegalStateException("Utility class");
  }

  public static List<Message> valideer(LandnaamDto landnaam) {
    if (null == landnaam) {
      return
          ComponentsUtils.objectIsNull(LandnaamDto.class.getSimpleName());
    }

    return valideer(new Landnaam(landnaam));
  }

  public static List<Message> valideer(Landnaam landnaam) {
    if (null == landnaam) {
      return
          ComponentsUtils.objectIsNull(Landnaam.class.getSimpleName());
    }

    List<Message> fouten  = new ArrayList<>();

    fouten.addAll(new Validator.Builder()
                               .setWaarde(landnaam.getLandId())
                               .setAttribute(PlaatsDto.COL_LANDID)
                               .setLabel(LBL_LANDID)
                               .setRequired()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(landnaam.getNaam())
                               .setAttribute(LandnaamDto.COL_NAAM)
                               .setLabel(LBL_NAAM)
                               .setMaxLengte(100)
                               .setRequired()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(landnaam.getTaal())
                               .setAttribute(LandnaamDto.COL_TAAL)
                               .setLabel(LBL_TAAL)
                               .setFixLengte(3)
                               .setRequired()
                               .setLowerCase()
                               .valideer().getFouten());

    return fouten;
  }
}
