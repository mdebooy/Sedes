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

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.sedes.domain.PlaatsDto;
import eu.debooy.sedes.form.Plaats;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public final class PlaatsValidator {
  protected static final  String  LBL_BREEDTE     = "_I18N.label.latitude";
  protected static final  String  LBL_LENGTE      = "_I18N.label.longitude";
  protected static final  String  LBL_PLAATSNAAM  = "_I18N.label.plaatsnaam";

  private PlaatsValidator() {
   throw new IllegalStateException("Utility class");
  }

  public static List<Message> valideer(PlaatsDto plaats) {
    return valideer(new Plaats(plaats));
  }

  public static List<Message> valideer(Plaats plaats) {
    List<Message> fouten  = new ArrayList<>();

    valideerBreedte(plaats.getBreedte(), fouten);
    valideerLengte(plaats.getLengte(), fouten);
    valideerPlaatsnaam(plaats.getPlaatsnaam(), fouten);

    return fouten;
  }

  private static void valideerBreedte(String breedte, List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(breedte)) {
      return;
    }

    if (breedte.length() > 1) {
      fouten.add(new Message.Builder()
                            .setAttribute(PlaatsDto.COL_BREEDTE)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_BREEDTE})
                            .build());
    }
  }

  private static void valideerLengte(String lengte, List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(lengte)) {
      return;
    }

    if (lengte.length() > 1) {
      fouten.add(new Message.Builder()
                            .setAttribute(PlaatsDto.COL_LENGTE)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_LENGTE})
                            .build());
    }
  }

  private static void valideerPlaatsnaam(String plaatsnaam,
                                        List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(plaatsnaam)) {
      fouten.add(new Message.Builder()
                            .setAttribute(PlaatsDto.COL_PLAATSNAAM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_PLAATSNAAM})
                            .build());
      return;
    }

    if (plaatsnaam.length() > 100) {
      fouten.add(new Message.Builder()
                            .setAttribute(PlaatsDto.COL_PLAATSNAAM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_PLAATSNAAM, 100})
                            .build());
    }
  }
}
