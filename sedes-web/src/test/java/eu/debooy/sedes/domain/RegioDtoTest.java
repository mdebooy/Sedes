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
package eu.debooy.sedes.domain;

import eu.debooy.sedes.SedesTestConstants;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 *
 * @author Marco de Booij
 */
public class RegioDtoTest {
  private static  RegioDto  regioDto;

  @BeforeClass
  public static void setUpClass() {
    regioDto  = new RegioDto();

    regioDto.setLandId(SedesTestConstants.LANDID);
    regioDto.setRegioId(SedesTestConstants.REGIOID);
    regioDto.setRegiokode(SedesTestConstants.REGIOKODE);
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new RegioDto();
    var groter  = new RegioDto();
    var kleiner = new RegioDto();

    gelijk.setRegiokode(SedesTestConstants.REGIOKODE);
    groter.setRegiokode(SedesTestConstants.REGIOKODE_G);
    kleiner.setRegiokode(SedesTestConstants.REGIOKODE_K);

    assertTrue(regioDto.compareTo(groter) < 0);
    assertEquals(0, regioDto.compareTo(gelijk));
    assertTrue(regioDto.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new RegioDto();

    assertEquals(regioDto, regioDto);
    assertNotEquals(regioDto, null);
    assertNotEquals(regioDto, SedesTestConstants.REGIONAAM);
    assertNotEquals(regioDto, instance);

    instance.setRegioId(SedesTestConstants.REGIOID);
    assertEquals(regioDto, instance);
  }

  @Test
  public void testGetLandId() {
    assertEquals(SedesTestConstants.LANDID, regioDto.getLandId());
  }

  @Test
  public void testGetRegioId() {
    assertEquals(SedesTestConstants.REGIOID, regioDto.getRegioId());
  }

  @Test
  public void testGetRegiokode() {
    assertEquals(SedesTestConstants.REGIOKODE, regioDto.getRegiokode());
  }

  @Test
  public void testHashCode() {
    assertEquals(SedesTestConstants.REGIO_HASH, regioDto.hashCode());
  }

  @Test
  public void testSetLandId() {
    var instance  = new RegioDto();

    assertNotEquals(SedesTestConstants.LANDID, instance.getLandId());

    instance.setLandId(SedesTestConstants.LANDID);

    assertEquals(SedesTestConstants.LANDID, instance.getLandId());
  }

  @Test
  public void testSetRegioId() {
    var instance  = new RegioDto();

    assertNotEquals(SedesTestConstants.REGIOID, instance.getRegioId());

    instance.setRegioId(SedesTestConstants.REGIOID);

    assertEquals(SedesTestConstants.REGIOID, instance.getRegioId());
  }

  @Test
  public void testSetRegiokode() {
    var instance  = new RegioDto();

    assertNotEquals(SedesTestConstants.REGIOKODE, instance.getRegiokode());

    instance.setRegiokode(SedesTestConstants.REGIOKODE.toUpperCase());

    assertEquals(SedesTestConstants.REGIOKODE, instance.getRegiokode());
  }
}
