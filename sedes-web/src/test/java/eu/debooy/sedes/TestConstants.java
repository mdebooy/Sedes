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

/**
 * @author Marco de Booij
 */
public final class TestConstants {
  public static final Long    LANDID          = 100L;
  public static final String  REGIONAAM       = "Regio";
  public static final String  REGIONAAM_G     = "Z-Regio";
  public static final String  REGIONAAM_K     = "A-Regio";
  public static final int     REGIO_HASH      = 829;
  public static final Long    REGIOID         = 200L;
  public static final String  REGIOKODE       = "RK123";
  public static final String  REGIOKODE_G     = "ZK123";
  public static final String  REGIOKODE_K     = "AK123";
  public static final Long    WERELDDEELID    = 200L;
  public static final String  WERELDDEELNAAM  = "Werelddeelnaam";

  private TestConstants() {
    throw new IllegalStateException("Utility class");
  }
}
