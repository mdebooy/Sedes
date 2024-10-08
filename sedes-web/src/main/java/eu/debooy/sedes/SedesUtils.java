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

package eu.debooy.sedes;

import eu.debooy.doosutils.DoosUtils;


/**
 * @author Marco de Booij
 */
public final class SedesUtils {
  private SedesUtils() {
   throw new IllegalStateException("Utility class");
  }

  public static String getKontaktnaam(String kontakttype,
                                      String naam, String voornaam,
                                      String tussenvoegsel, String initialen,
                                      String roepnaam) {
    if (null == kontakttype) {
      return "??";
    }

    switch (kontakttype) {
      case Sedes.TYP_GROEP:
        return String.format("%s %s%s",
                             naam,
                             DoosUtils.isNotBlankOrNull(voornaam) ? "- " : "",
                             DoosUtils.nullToEmpty(voornaam)).trim();
      case Sedes.TYP_RECHTSPERSOON:
        return String.format("%s %s%s",
                             DoosUtils.nullToValue(roepnaam, naam),
                             DoosUtils.isNotBlankOrNull(voornaam) ? "- " : "",
                             DoosUtils.nullToEmpty(voornaam)).trim();
      default:
        var tekst = voornaam;
        if (DoosUtils.isBlankOrNull(tekst)) {
          tekst   = initialen;
        }
        return String.format("%s %s%s %s%s",
                             DoosUtils.nullToEmpty(tussenvoegsel).toUpperCase(),
                             naam.toUpperCase(),
                             DoosUtils.isNotBlankOrNull(tekst) ? "," : "",
                             DoosUtils.nullToEmpty(tekst),
                             DoosUtils.isNotBlankOrNull(roepnaam)
                                 ? " (" + roepnaam + ")" : "")
                     .trim();
    }
  }

  public static String getAdresMetPlaatsnaam(String adres, String plaatsnaam,
                                             String postLandkode) {
    var adresMetPlaatsnaam  = new StringBuilder();

    adresMetPlaatsnaam.append(adres);
    if (DoosUtils.isNotBlankOrNull(plaatsnaam)) {
      adresMetPlaatsnaam.append(" - ").append(plaatsnaam);
    }
    if (DoosUtils.isNotBlankOrNull(postLandkode)) {
      adresMetPlaatsnaam.append(" (").append(postLandkode.trim()).append(")");
    }

    return adresMetPlaatsnaam.toString().trim();
  }
}
