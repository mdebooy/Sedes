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

import eu.debooy.doosutils.ComponentsUtils;
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.validator.Validator;
import eu.debooy.sedes.domain.MuntDto;
import eu.debooy.sedes.form.Munt;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public final class MuntValidator {
  protected static final  String  LBL_DECIMALEN   = "_I18N.label.decimalen";
  protected static final  String  LBL_ISO3        = "_I18N.label.iso3";
  protected static final  String  LBL_MUNTTEKEN   = "_I18N.label.muntteken";
  protected static final  String  LBL_NAAM        = "_I18N.label.muntnaam";
  protected static final  String  LBL_SUBEENHEID  = "_I18N.label.subeenheid";

  private MuntValidator() {
   throw new IllegalStateException("Utility class");
  }

  public static List<Message> valideer(MuntDto munt) {
    if (null == munt) {
      return
          ComponentsUtils.objectIsNull(MuntDto.class.getSimpleName());
    }

    return valideer(new Munt(munt));
  }

  public static List<Message> valideer(Munt munt) {
    if (null == munt) {
      return
          ComponentsUtils.objectIsNull(Munt.class.getSimpleName());
    }

    List<Message> fouten  = new ArrayList<>();

    fouten.addAll(new Validator.Builder()
                               .setWaarde(munt.getDecimalen())
                               .setAttribute(MuntDto.COL_DECIMALEN)
                               .setLabel(LBL_DECIMALEN)
                               .setMinWaarde(0L)
                               .setMaxWaarde(99L)
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(munt.getIso3())
                               .setAttribute(MuntDto.COL_ISO3)
                               .setLabel(LBL_ISO3)
                               .setMaxLengte(3)
                               .setUpperCase()
                               .setRequired()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(munt.getMuntteken())
                               .setAttribute(MuntDto.COL_MUNTTEKEN)
                               .setLabel(LBL_MUNTTEKEN)
                               .setMaxLengte(3)
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(munt.getNaam())
                               .setAttribute(MuntDto.COL_NAAM)
                               .setLabel(LBL_NAAM)
                               .setMaxLengte(100)
                               .setRequired()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(munt.getSubeenheid())
                               .setAttribute(MuntDto.COL_SUBEENHEID)
                               .setLabel(LBL_SUBEENHEID)
                               .setMaxLengte(100)
                               .valideer().getFouten());

    return fouten;
  }
}
