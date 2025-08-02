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
import eu.debooy.sedes.domain.PlaatsDto;
import eu.debooy.sedes.form.Plaats;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public final class PlaatsValidator {
  protected static final  String  ERR_BREEDTEGRAAD  =
      "_I18N.error.breedtegraat";
  protected static final  String  ERR_LENGTEGRAAD   = "_I18N.error.lengtegraad";

  protected static final  String  LBL_BREEDTEGRAAD  =
      "_I18N.label.breedtegraat";
  protected static final  String  LBL_LANDID        = "_I18N.label.land";
  protected static final  String  LBL_LENGTEGRAAD   = "_I18N.label.lengtegraad";
  protected static final  String  LBL_PLAATSNAAM    = "_I18N.label.plaatsnaam";
  protected static final  String  LBL_POSTKODE      = "_I18N.label.postkode";
  protected static final  String  LBL_REGIOID       = "_I18N.label.regionaam";
  protected static final  String  LBL_ZONENUMMER    = "_I18N.label.zonenummer";

  private PlaatsValidator() {
   throw new IllegalStateException("Utility class");
  }

  public static List<Message> valideer(PlaatsDto plaats) {
    if (null == plaats) {
      return ComponentsUtils.objectIsNull("PlaatsDto");
    }

    return valideer(new Plaats(plaats));
  }

  public static List<Message> valideer(Plaats plaats) {
    if (null == plaats) {
      return ComponentsUtils.objectIsNull("Plaats");
    }

    List<Message> fouten  = new ArrayList<>();

    fouten.addAll(new Validator.Builder()
                               .setWaarde(plaats.getBreedtegraad())
                               .setAttribute(PlaatsDto.COL_BREEDTEGRAAD)
                               .setLabel(LBL_BREEDTEGRAAD)
                               .setMinWaarde(-90L)
                               .setMaxWaarde(90L)
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(plaats.getLandId())
                               .setAttribute(PlaatsDto.COL_LANDID)
                               .setLabel(LBL_LANDID)
                               .setRequired()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(plaats.getLengtegraad())
                               .setAttribute(PlaatsDto.COL_LENGTEGRAAD)
                               .setLabel(LBL_LENGTEGRAAD)
                               .setMinWaarde(-180L)
                               .setMaxWaarde(180L)
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(plaats.getPlaatsnaam())
                               .setAttribute(PlaatsDto.COL_PLAATSNAAM)
                               .setLabel(LBL_PLAATSNAAM)
                               .setMaxLengte(100)
                               .setRequired()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(plaats.getPostkode())
                               .setAttribute(PlaatsDto.COL_POSTKODE)
                               .setLabel(LBL_POSTKODE)
                               .setMaxLengte(15)
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(plaats.getRegioId())
                               .setAttribute(PlaatsDto.COL_REGIOID)
                               .setLabel(LBL_REGIOID)
                               .setRequired()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(plaats.getZonenummer())
                               .setAttribute(PlaatsDto.COL_ZONENUMMER)
                               .setLabel(LBL_ZONENUMMER)
                               .setMinWaarde(0L)
                               .setMaxWaarde(999999L)
                               .valideer().getFouten());

    return fouten;
  }
}
