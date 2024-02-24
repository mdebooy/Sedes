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
import eu.debooy.sedes.domain.KontaktDto;
import eu.debooy.sedes.domain.KontaktadresDto;
import java.util.Date;
import java.util.List;


/**
 * @author Marco de Booij
 */
@SuppressWarnings("java:S1118")
public abstract class SedesValidator {
  public static final String  LBL_EINDDATUM   = "_I18N.label.totdatum";
  public static final String  LBL_OPMERKING   = "_I18N.label.opmerking";
  public static final String  LBL_STARTDATUM  = "_I18N.label.vandatum";

  protected static void valideerDatums(Date startdatum, Date einddatum,
                                       List<Message> fouten) {
    var correctS  = valideerStartdatum(startdatum, fouten);
    var correctE  = valideerEinddatum(einddatum, fouten);
    if (!(correctS && correctE)) {
      return;
    }

    if (einddatum.before(startdatum)) {
      fouten.add(new Message.Builder()
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.DATEBEFORE)
                            .setParams(new Object[]{LBL_EINDDATUM,
                                                    LBL_STARTDATUM})
                            .build());
    }
  }

  private static boolean valideerEinddatum(Date einddatum,
                                           List<Message> fouten) {
    // Voorkomt dat er wordt getest of einddatum voor startdatum ligt.
    if (null == einddatum) {
       return false;
    }

    var correct = true;

    if ((new Date()).before(einddatum)) {
      fouten.add(new Message.Builder()
                            .setAttribute(KontaktadresDto.COL_EINDDATUM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.FUTURE)
                            .setParams(new Object[]{LBL_EINDDATUM})
                            .build());

      correct = false;
    }

    return correct;
  }

  protected static void valideerOpmerking(String opmerking,
                                          List<Message> fouten) {
    if (DoosUtils.nullToEmpty(opmerking).length() > 2000) {
      fouten.add(new Message.Builder()
                            .setAttribute(KontaktDto.COL_OPMERKING)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_OPMERKING, 2000})
                            .build());
    }
  }

  private static boolean valideerStartdatum(Date startdatum,
                                         List<Message> fouten) {
    if (null == startdatum) {
      fouten.add(new Message.Builder()
                            .setAttribute(KontaktadresDto.COL_STARTDATUM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_STARTDATUM})
                            .build());

      return false;
    }

    var correct = true;

    if ((new Date()).before(startdatum)) {
      fouten.add(new Message.Builder()
                            .setAttribute(KontaktadresDto.COL_STARTDATUM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.FUTURE)
                            .setParams(new Object[]{LBL_STARTDATUM})
                            .build());
      correct = false;
    }

    return correct;
   }
}
