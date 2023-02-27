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
  protected static final  String  LBL_LANDID  = "_I18N.label.land";
  protected static final  String  LBL_NAAM    = "_I18N.label.landnaam";
  protected static final  String  LBL_TAAL    = "_I18N.label.taal";

  public static List<Message> valideer(LandnaamDto landnaam) {
    return valideer(new Landnaam(landnaam));
  }

  public static List<Message> valideer(Landnaam landnaam) {
    List<Message> fouten  = new ArrayList<>();

    valideerLandId(landnaam.getLandId(), fouten);
    valideerNaam(landnaam.getNaam(), fouten);
    valideerTaal(landnaam.getTaal(), fouten);

    return fouten;
  }

  private static void valideerLandId(Long landId, List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(landId)) {
      fouten.add(new Message.Builder()
                            .setAttribute(LandnaamDto.COL_LANDID)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_LANDID})
                            .build());
    }
  }

  private static void valideerNaam(String naam, List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(naam)) {
      fouten.add(new Message.Builder()
                            .setAttribute(LandnaamDto.COL_NAAM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_NAAM})
                            .build());
      return;
    }

    if (naam.length() > 100) {
      fouten.add(new Message.Builder()
                            .setAttribute(LandnaamDto.COL_NAAM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_NAAM, 100})
                            .build());
    }
  }

  private static void valideerTaal(String taal, List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(taal)) {
      fouten.add(new Message.Builder()
                            .setAttribute(LandnaamDto.COL_TAAL)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_TAAL})
                            .build());
      return;
    }

    if (taal.length() != 2) {
      fouten.add(new Message.Builder()
                            .setAttribute(LandnaamDto.COL_TAAL)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.FIXLENGTH)
                            .setParams(new Object[]{LBL_TAAL, 2})
                            .build());
    }
  }
}
