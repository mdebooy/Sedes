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
import eu.debooy.sedes.domain.LandDto;
import eu.debooy.sedes.form.Land;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public final class LandValidator {
  protected static final  String  LBL_BESTAAT           = "label.bestaat";
  protected static final  String  LBL_ISO3              = "label.iso3";
  protected static final  String  LBL_POSTKODESCHEIDING =
      "label.postkodeScheiding";
  protected static final  String  LBL_POSTKODETYPE      = "label.postkodeType";
  protected static final  String  LBL_POSTLANDKODE      = "label.postLandkode";
  protected static final  String  LBL_TAAL              = "label.taal";
  protected static final  String  LBL_WERELDDEELID      = "label.werelddeel";

  private LandValidator() {
   throw new IllegalStateException("Utility class");
  }

  public static List<Message> valideer(LandDto land) {
    return valideer(new Land(land));
  }

  public static List<Message> valideer(Land land) {
    List<Message> fouten  = new ArrayList<>();

    valideerBestaat(land.getBestaat(), fouten);
    valideerIso3(land.getIso3(), fouten);
    valideerPostkodeScheiding(land.getPostkodeScheiding(), fouten);
    valideerPostkodeType(land.getPostkodeType(), fouten);
    valideerPostLandkode(land.getPostLandkode(), fouten);
    valideerTaal(land.getTaal(), fouten);
    valideerWerelddeelId(land.getWerelddeelId(), fouten);

    return fouten;
  }

  private static void valideerBestaat(Boolean bestaat, List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(bestaat)) {
      fouten.add(new Message.Builder()
                            .setAttribute(LandDto.COL_BESTAAT)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_BESTAAT})
                            .build());
    }
  }

  private static void valideerIso3(String iso3, List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(iso3)) {
      fouten.add(new Message.Builder()
                            .setAttribute(LandDto.COL_ISO3)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_ISO3})
                            .build());
      return;
    }

    if (iso3.length() != 3) {
      fouten.add(new Message.Builder()
                            .setAttribute(LandDto.COL_ISO3)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.FIXLENGTH)
                            .setParams(new Object[]{LBL_ISO3, 3})
                            .build());
      return;
    }

    if (!iso3.equals(iso3.toUpperCase())) {
      fouten.add(new Message.Builder()
                            .setAttribute(LandDto.COL_ISO3)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.NIETUCASE)
                            .setParams(new Object[]{LBL_ISO3})
                            .build());
    }
  }

  private static void valideerPostkodeScheiding(String postkodeScheiding,
                                                List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(postkodeScheiding)) {
      return;
    }

    if (postkodeScheiding.length() > 10) {
      fouten.add(new Message.Builder()
                            .setAttribute(LandDto.COL_POSTKODESCHEIDING)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_POSTKODESCHEIDING, 10})
                            .build());
    }
  }

  private static void valideerPostkodeType(String postkodeType,
                                           List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(postkodeType)) {
      fouten.add(new Message.Builder()
                            .setAttribute(LandDto.COL_POSTKODETYPE)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_POSTKODETYPE})
                            .build());
      return;
    }

    if (postkodeType.length() > 1) {
      fouten.add(new Message.Builder()
                            .setAttribute(LandDto.COL_POSTKODETYPE)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_POSTKODETYPE, 1})
                            .build());
    }
  }

  private static void valideerPostLandkode(String postLandkodeType,
                                           List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(postLandkodeType)) {
      fouten.add(new Message.Builder()
                            .setAttribute(LandDto.COL_POSTLANDKODE)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_POSTLANDKODE})
                            .build());
      return;
    }

    if (postLandkodeType.length() > 3) {
      fouten.add(new Message.Builder()
                            .setAttribute(LandDto.COL_POSTLANDKODE)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_POSTLANDKODE, 3})
                            .build());
    }
  }

  private static void valideerTaal(String taal, List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(taal)) {
      fouten.add(new Message.Builder()
                            .setAttribute(LandDto.COL_TAAL)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_TAAL})
                            .build());
      return;
    }

    if (taal.length() != 2) {
      fouten.add(new Message.Builder()
                            .setAttribute(LandDto.COL_TAAL)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.FIXLENGTH)
                            .setParams(new Object[]{LBL_TAAL, 2})
                            .build());
      return;
    }

    if (!taal.equals(taal.toLowerCase())) {
      fouten.add(new Message.Builder()
                            .setAttribute(LandDto.COL_TAAL)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.NIETLCASE)
                            .setParams(new Object[]{LBL_TAAL})
                            .build());
    }
  }

  private static void valideerWerelddeelId(Long werelddeelId,
                                           List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(werelddeelId)) {
      fouten.add(new Message.Builder()
                            .setAttribute(LandDto.COL_WERELDDEELID)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_WERELDDEELID})
                            .build());
    }
  }
}
