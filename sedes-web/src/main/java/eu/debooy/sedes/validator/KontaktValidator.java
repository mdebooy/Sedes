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
import eu.debooy.sedes.form.Kontakt;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco de Booij
 */
public final class KontaktValidator {
  protected static final  String  LBL_AANSPREEKID = "label.aanspreektitel";

  private KontaktValidator() {
   throw new IllegalStateException("Utility class");
  }

  public static List<Message> valideer(KontaktDto kontakt) {
    return valideer(new Kontakt(kontakt));
  }

  public static List<Message> valideer(Kontakt kontakt) {
    List<Message> fouten  = new ArrayList<>();

    valideerAanspreekId(kontakt.getAanspreekId(), fouten);

    return fouten;
  }

  private static void valideerAanspreekId(String aanspreekId,
                                          List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(aanspreekId)) {
      return;
    }

    if (aanspreekId.length() > 10) {
      fouten.add(new Message.Builder()
                            .setAttribute(KontaktDto.COL_AANSPREEKID)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_AANSPREEKID, 10})
                            .build());
    }
  }
}
