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
import eu.debooy.sedes.domain.WerelddeelnaamDto;
import eu.debooy.sedes.form.Werelddeelnaam;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public final class WerelddeelnaamValidator {
  protected static final  String  LBL_NAAM  = "_I18N.label.werelddeelnaam";
  protected static final  String  LBL_TAAL   = "_I18N.label.taal";

  private WerelddeelnaamValidator() {
   throw new IllegalStateException("Utility class");
  }

  public static List<Message> valideer(WerelddeelnaamDto werelddeelnaam) {
    if (null == werelddeelnaam) {
      return
          ComponentsUtils.objectIsNull(WerelddeelnaamDto.class.getSimpleName());
    }

    return valideer(new Werelddeelnaam(werelddeelnaam));
  }

  public static List<Message> valideer(Werelddeelnaam werelddeelnaam) {
    if (null == werelddeelnaam) {
      return ComponentsUtils.objectIsNull(Werelddeelnaam.class.getSimpleName());
    }

    List<Message> fouten  = new ArrayList<>();

    fouten.addAll(new Validator.Builder()
                               .setWaarde(werelddeelnaam.getNaam())
                               .setAttribute(WerelddeelnaamDto.COL_NAAM)
                               .setLabel(LBL_NAAM)
                               .setMaxLengte(100)
                               .setRequired()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(werelddeelnaam.getTaal())
                               .setAttribute(WerelddeelnaamDto.COL_TAAL)
                               .setLabel(LBL_TAAL)
                               .setFixLengte(3)
                               .setLowerCase()
                               .setRequired()
                               .valideer().getFouten());

    return fouten;
  }
}
