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

import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.validator.Validator;
import eu.debooy.sedes.domain.LandDto;
import eu.debooy.sedes.form.Land;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public final class LandValidator {
  protected static final  String  LBL_BESTAAT           = "_I18N.label.bestaat";
  protected static final  String  LBL_ISO3              = "_I18N.label.iso3";
  protected static final  String  LBL_POSTKODESCHEIDING =
      "label.postkodeScheiding";
  protected static final  String  LBL_POSTKODETYPE      =
      "_I18N.label.postkodeType";
  protected static final  String  LBL_POSTLANDKODE      =
      "_I18N.label.postLandkode";
  protected static final  String  LBL_TAAL              = "_I18N.label.taal";
  protected static final  String  LBL_WERELDDEELID      =
      "_I18N.label.werelddeel";

  private LandValidator() {
   throw new IllegalStateException("Utility class");
  }

  public static List<Message> valideer(LandDto land) {
    return valideer(new Land(land));
  }

  public static List<Message> valideer(Land land) {
    List<Message> fouten  = new ArrayList<>();

    fouten.addAll(new Validator.Builder()
                               .setWaarde(land.getBestaat())
                               .setAttribute(LandDto.COL_BESTAAT)
                               .setLabel(LBL_BESTAAT)
                               .setRequired()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(land.getIso3())
                               .setAttribute(LandDto.COL_ISO3)
                               .setLabel(LBL_ISO3)
                               .setMaxLengte(3)
                               .setUpperCase()
                               .setRequired()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(land.getPostLandkode())
                               .setAttribute(LandDto.COL_POSTLANDKODE)
                               .setLabel(LBL_ISO3)
                               .setMaxLengte(3)
                               .setUpperCase()
                               .setRequired()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(land.getPostkodeScheiding())
                               .setAttribute(LandDto.COL_POSTKODESCHEIDING)
                               .setLabel(LBL_POSTKODESCHEIDING)
                               .setMaxLengte(10)
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(land.getPostkodeType())
                               .setAttribute(LandDto.COL_POSTKODETYPE)
                               .setLabel(LBL_POSTKODETYPE)
                               .setFixLengte(1)
                               .setRequired()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(land.getTaal())
                               .setAttribute(LandDto.COL_TAAL)
                               .setLabel(LBL_TAAL)
                               .setFixLengte(3)
                               .setLowerCase()
                               .setRequired()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(land.getWerelddeelId())
                               .setAttribute(LandDto.COL_WERELDDEELID)
                               .setLabel(LBL_WERELDDEELID)
                               .setRequired()
                               .valideer().getFouten());

    return fouten;
  }
}
