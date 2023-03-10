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
  protected static final  String  LBL_NAAM      = "_I18N.label.regionaam";

  private RegioValidator() {
   throw new IllegalStateException("Utility class");
  }

  public static List<Message> valideer(RegioDto regio) {
    return valideer(new Regio(regio));
  }

  public static List<Message> valideer(Regio regio) {
    List<Message> fouten  = new ArrayList<>();

    valideerLandId(regio.getLandId(), fouten);
    valideerNaam(regio.getNaam(), fouten);
    valideerRegiokode(regio.getRegiokode(), fouten);

    return fouten;
  }

  private static void valideerLandId(Long landId, List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(landId)) {
      fouten.add(new Message.Builder()
                            .setAttribute(RegioDto.COL_LANDID)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_LANDID})
                            .build());
    }
  }

  private static void valideerNaam(String naam, List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(naam)) {
      fouten.add(new Message.Builder()
                            .setAttribute(RegioDto.COL_NAAM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_NAAM})
                            .build());
      return;
    }

    if (naam.length() > 100) {
      fouten.add(new Message.Builder()
                            .setAttribute(RegioDto.COL_NAAM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_NAAM, 100})
                            .build());
    }
  }

  private static void valideerRegiokode(String regiokode,
                                        List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(regiokode)) {
      fouten.add(new Message.Builder()
                            .setAttribute(RegioDto.COL_REGIOKODE)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_REGIOKODE})
                            .build());
      return;
    }

    if (regiokode.length() > 5) {
      fouten.add(new Message.Builder()
                            .setAttribute(RegioDto.COL_REGIOKODE)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_REGIOKODE, 5})
                            .build());
    }
  }
}
