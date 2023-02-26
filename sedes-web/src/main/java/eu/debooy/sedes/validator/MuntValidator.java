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
  protected static final  String  LBL_MUNTNAAM    = "_I18N.label.muntnaam";
  protected static final  String  LBL_MUNTTEKEN   = "_I18N.label.muntteken";
  protected static final  String  LBL_SUBEENHEID  = "_I18N.label.subeenheid";

  private MuntValidator() {
  }

  public static List<Message> valideer(MuntDto munt) {
    return valideer(new Munt(munt));
  }

  public static List<Message> valideer(Munt munt) {
    List<Message> fouten  = new ArrayList<>();

    valideerDecimalen(munt.getDecimalen(), fouten);
    valideerIso3(munt.getIso3(), fouten);
    valideerMuntnaam(munt.getMuntnaam(), fouten);
    valideerMuntteken(munt.getMuntteken(), fouten);
    valideerSubeenheid(munt.getSubeenheid(), fouten);

    return fouten;
  }

  private static void valideerDecimalen(Integer decimalen,
                                        List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(decimalen)) {
      fouten.add(new Message.Builder()
                            .setAttribute(MuntDto.COL_DECIMALEN)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_DECIMALEN})
                            .build());
      return;
    }

    if (decimalen < 0 || decimalen > 99) {
      fouten.add(new Message.Builder()
                            .setAttribute(MuntDto.COL_DECIMALEN)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.RANGE)
                            .setParams(new Object[]{LBL_DECIMALEN, 0, 99})
                            .build());
    }
  }

  private static void valideerIso3(String iso3,
                                        List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(iso3)) {
      fouten.add(new Message.Builder()
                            .setAttribute(MuntDto.COL_ISO3)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_ISO3})
                            .build());
      return;
    }

    if (iso3.length() != 3) {
      fouten.add(new Message.Builder()
                            .setAttribute(MuntDto.COL_ISO3)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.FIXLENGTH)
                            .setParams(new Object[]{LBL_ISO3, 3})
                            .build());
    }
  }

  private static void valideerMuntnaam(String muntnaam,
                                        List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(muntnaam)) {
      fouten.add(new Message.Builder()
                            .setAttribute(MuntDto.COL_MUNTNAAM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_MUNTNAAM})
                            .build());
      return;
    }

    if (muntnaam.length() > 100) {
      fouten.add(new Message.Builder()
                            .setAttribute(MuntDto.COL_MUNTNAAM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_MUNTNAAM, 100})
                            .build());
    }
  }

  private static void valideerMuntteken(String muntteken,
                                        List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(muntteken)) {
      return;
    }

    if (muntteken.length() > 3) {
      fouten.add(new Message.Builder()
                            .setAttribute(MuntDto.COL_MUNTTEKEN)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_MUNTTEKEN, 3})
                            .build());
    }
  }

  private static void valideerSubeenheid(String subeenheid,
                                        List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(subeenheid)) {
      return;
    }

    if (subeenheid.length() > 100) {
      fouten.add(new Message.Builder()
                            .setAttribute(MuntDto.COL_SUBEENHEID)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_MUNTTEKEN, 100})
                            .build());
    }
  }
}
