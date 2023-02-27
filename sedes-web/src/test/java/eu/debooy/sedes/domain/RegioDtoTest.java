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

import eu.debooy.sedes.TestConstants;
import java.util.Set;
import java.util.TreeSet;
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
  private static  RegioDto regioDto;

  @BeforeClass
  public static void setUpClass() {
    regioDto = new RegioDto();

    regioDto.setLandId(TestConstants.LANDID);
    regioDto.setRegioId(TestConstants.REGIOID);
    regioDto.setRegiokode(TestConstants.REGIOKODE);
    regioDto.setNaam(TestConstants.REGIONAAM);
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new RegioDto();
    var groter  = new RegioDto();
    var kleiner = new RegioDto();

    gelijk.setRegioId(TestConstants.REGIOID);
    groter.setRegioId(TestConstants.REGIOID + 1);
    kleiner.setRegioId(TestConstants.REGIOID - 1);

    assertTrue(regioDto.compareTo(groter) < 0);
    assertEquals(0, regioDto.compareTo(gelijk));
    assertTrue(regioDto.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new RegioDto();

    assertEquals(regioDto, regioDto);
    assertNotEquals(regioDto, null);
    assertNotEquals(regioDto, TestConstants.REGIONAAM);
    assertNotEquals(regioDto, instance);

    instance.setRegioId(TestConstants.REGIOID);
    assertEquals(regioDto, instance);
  }

  @Test
  public void testGetLandId() {
    assertEquals(TestConstants.LANDID, regioDto.getLandId());
  }

  @Test
  public void testGetNaam() {
    assertEquals(TestConstants.REGIONAAM, regioDto.getNaam());
  }

  @Test
  public void testGetRegioId() {
    assertEquals(TestConstants.REGIOID, regioDto.getRegioId());
  }

  @Test
  public void testGetRegiokode() {
    assertEquals(TestConstants.REGIOKODE, regioDto.getRegiokode());
  }

  @Test
  public void testHashCode() {
    assertEquals(TestConstants.REGIO_HASH, regioDto.hashCode());
  }

  @Test
  public void testNaamComparator() {
    var groter  = new RegioDto();
    var kleiner = new RegioDto();

    groter.setNaam(TestConstants.REGIONAAM_G);
    kleiner.setNaam(TestConstants.REGIONAAM_K);

    var             comparator  = new RegioDto.NaamComparator();
    Set<RegioDto>   regios      = new TreeSet<>(comparator);
    regios.add(groter);
    regios.add(regioDto);
    regios.add(kleiner);

    assertEquals(3, regios.size());

    var tabel = new RegioDto[regios.size()];
    System.arraycopy(regios.toArray(), 0, tabel, 0, regios.size());

      assertEquals(kleiner.getNaam(), tabel[0].getNaam());
    assertEquals(regioDto.getNaam(), tabel[1].getNaam());
    assertEquals(groter.getNaam(), tabel[2].getNaam());
  }

  @Test
  public void testLandId() {
    var instance  = new RegioDto();

    assertNotEquals(TestConstants.LANDID, instance.getLandId());

    instance.setLandId(TestConstants.LANDID);

    assertEquals(TestConstants.LANDID, instance.getLandId());
  }

  @Test
  public void testSetNaam() {
    var instance  = new RegioDto();

    assertNotEquals(TestConstants.REGIONAAM, instance.getNaam());

    instance.setNaam(TestConstants.REGIONAAM);

    assertEquals(TestConstants.REGIONAAM, instance.getNaam());
  }

  @Test
  public void testSetRegioId() {
    var instance  = new RegioDto();

    assertNotEquals(TestConstants.REGIOID, instance.getRegioId());

    instance.setRegioId(TestConstants.REGIOID);

    assertEquals(TestConstants.REGIOID, instance.getRegioId());
  }

  @Test
  public void testSetRegiokode() {
    var instance  = new RegioDto();

    assertNotEquals(TestConstants.REGIOKODE, instance.getRegiokode());

    instance.setRegiokode(TestConstants.REGIOKODE.toUpperCase());

    assertEquals(TestConstants.REGIOKODE, instance.getRegiokode());
  }
}
