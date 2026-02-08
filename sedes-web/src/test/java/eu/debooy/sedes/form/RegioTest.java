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
package eu.debooy.sedes.form;

import eu.debooy.sedes.SedesTestConstants;
import eu.debooy.sedes.domain.RegioDto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Marco de Booij
 */
public class RegioTest {
  private static  Regio regio;

  @BeforeClass
  public static void setUpClass() {
    regio    = new Regio();
    regio.setLandId(SedesTestConstants.LANDID);
    regio.setNaam(SedesTestConstants.REGIONAAM);
    regio.setRegioId(SedesTestConstants.REGIOID);
    regio.setRegiokode(SedesTestConstants.REGIOKODE);
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new Regio();
    var groter  = new Regio();
    var kleiner = new Regio();

    gelijk.setRegiokode(SedesTestConstants.REGIOKODE);
    groter.setRegiokode(SedesTestConstants.REGIOKODE_G);
    kleiner.setRegiokode(SedesTestConstants.REGIOKODE_K);

    assertTrue(regio.compareTo(groter) < 0);
    assertEquals(0, regio.compareTo(gelijk));
    assertTrue(regio.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new Regio();

    assertEquals(regio, regio);
    assertNotEquals(regio, null);
    assertNotEquals(regio, SedesTestConstants.REGIONAAM);
    assertNotEquals(regio, instance);

    instance.setRegioId(SedesTestConstants.REGIOID);
    assertEquals(regio, instance);
  }

  @Test
  public void testGetLandId() {
    assertEquals(SedesTestConstants.LANDID, regio.getLandId());
  }

  @Test
  public void testGetNaam() {
    assertEquals(SedesTestConstants.REGIONAAM, regio.getNaam());
  }

  @Test
  public void testGetRegioId() {
    assertEquals(SedesTestConstants.REGIOID, regio.getRegioId());
  }

  @Test
  public void testGetRegiokode() {
    assertEquals(SedesTestConstants.REGIOKODE, regio.getRegiokode());
  }

  @Test
  public void testHashCode() {
    assertEquals(SedesTestConstants.REGIO_HASH, regio.hashCode());
  }

  @Test
  public void testInit1() {
    var instance  = new Regio();

    assertNull(instance.getLandId());
    assertNull(instance.getNaam());
    assertNull(instance.getRegioId());
    assertNull(instance.getRegiokode());
  }

  @Test
  public void testInit2() {
    var regioDto = new RegioDto();

    regioDto.setLandId(SedesTestConstants.LANDID);
    regioDto.setRegioId(SedesTestConstants.REGIOID);
    regioDto.setRegiokode(SedesTestConstants.REGIOKODE);

    var instance  = new Regio(regioDto);

    assertEquals(regioDto.getLandId(), instance.getLandId());
    assertEquals(regioDto.getRegioId(), instance.getRegioId());
    assertEquals(regioDto.getRegiokode(), instance.getRegiokode());
  }

  @Test
  public void testPersist() {
    var parameter = new RegioDto();

    regio.persist(parameter);

    assertEquals(regio.getLandId(), parameter.getLandId());
    assertEquals(regio.getRegioId(), parameter.getRegioId());
    assertEquals(regio.getRegiokode(), parameter.getRegiokode());
  }

  @Test
  public void testSetLandId() {
    var instance  = new Regio();

    assertNotEquals(SedesTestConstants.LANDID, instance.getLandId());

    instance.setLandId(SedesTestConstants.LANDID);

    assertEquals(SedesTestConstants.LANDID, instance.getLandId());
  }

  @Test
  public void testSetNaam() {
    var instance  = new Regio();

    assertNotEquals(SedesTestConstants.REGIONAAM, instance.getNaam());

    instance.setNaam(SedesTestConstants.REGIONAAM);

    assertEquals(SedesTestConstants.REGIONAAM, instance.getNaam());
  }

  @Test
  public void testSetRegioId() {
    var instance  = new Regio();

    assertNotEquals(SedesTestConstants.REGIOID, instance.getRegioId());

    instance.setRegioId(SedesTestConstants.REGIOID);

    assertEquals(SedesTestConstants.REGIOID, instance.getRegioId());
  }

  @Test
  public void testSetRegiokode() {
    var instance  = new Regio();

    assertNotEquals(SedesTestConstants.REGIOKODE, instance.getRegiokode());

    instance.setRegiokode(SedesTestConstants.REGIOKODE.toUpperCase());

    assertEquals(SedesTestConstants.REGIOKODE, instance.getRegiokode());
  }
}
