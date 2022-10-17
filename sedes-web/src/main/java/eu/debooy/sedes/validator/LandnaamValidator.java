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

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.sedes.domain.LandnaamDto;
import eu.debooy.sedes.form.Landnaam;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public final class LandnaamValidator {
  private LandnaamValidator() {
  }

  public static List<Message> valideer(LandnaamDto landnaam) {
    return valideer(new Landnaam(landnaam));
  }

  public static List<Message> valideer(Landnaam landnaam) {
    List<Message> fouten  = new ArrayList<>();

    valideerLandnaam(landnaam.getLandnaam(), fouten);

    return fouten;
  }

  private static void valideerLandnaam(String landnaam,
                                       List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(landnaam)) {
      fouten.add(new Message.Builder()
                            .setAttribute(LandnaamDto.COL_LANDNAAM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{"_I18N.label.landnaam"})
                            .build());
      return;
    }

    if (landnaam.length() > 100) {
      fouten.add(new Message.Builder()
                            .setAttribute(LandnaamDto.COL_LANDNAAM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{"_I18N.label.landnaam",
                                                    100})
                            .build());
    }
  }
}
