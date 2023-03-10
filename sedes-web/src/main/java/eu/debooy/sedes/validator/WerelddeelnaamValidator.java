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
import eu.debooy.sedes.domain.WerelddeelnaamDto;
import eu.debooy.sedes.form.Werelddeelnaam;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public final class WerelddeelnaamValidator {
  protected static final  String  LBL_NAAM  = "_I18N.label.werelddeelnaam";

  private WerelddeelnaamValidator() {
   throw new IllegalStateException("Utility class");
  }

  public static List<Message> valideer(WerelddeelnaamDto werelddeelnaam) {
    return valideer(new Werelddeelnaam(werelddeelnaam));
  }

  public static List<Message> valideer(Werelddeelnaam werelddeelnaam) {
    List<Message> fouten  = new ArrayList<>();

    valideerNaam(werelddeelnaam.getNaam(), fouten);

    return fouten;
  }

  private static void valideerNaam(String werelddeelnaam,
                                             List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(werelddeelnaam)) {
      fouten.add(new Message.Builder()
                            .setAttribute(WerelddeelnaamDto.COL_NAAM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(
                                new Object[]{LBL_NAAM})
                            .build());
      return;
    }

    if (werelddeelnaam.length() > 100) {
      fouten.add(new Message.Builder()
                            .setAttribute(WerelddeelnaamDto.COL_NAAM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_NAAM, 100})
                            .build());
    }
  }
}
