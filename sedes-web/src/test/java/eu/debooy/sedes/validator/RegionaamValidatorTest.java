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
import eu.debooy.sedes.SedesTestConstants;
import eu.debooy.sedes.domain.RegionaamDto;
import eu.debooy.sedes.form.Regionaam;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 *
 * @author Marco de Booij
 */
public class RegionaamValidatorTest {
  public static final Message ERR_NAAM    =
      new Message.Builder()
                 .setAttribute(RegionaamDto.COL_NAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(
                    new Object[]{RegionaamValidator.LBL_NAAM,
                                 100})
                 .build();
  public static final Message ERR_TAAL    =
      new Message.Builder()
                 .setAttribute(RegionaamDto.COL_TAAL)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.FIXLENGTH)
                 .setParams(
                    new Object[]{RegionaamValidator.LBL_TAAL,
                                 3})
                 .build();
  public static final Message REQ_NAAM    =
      new Message.Builder()
                 .setAttribute(RegionaamDto.COL_NAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(
                    new Object[]{RegionaamValidator.LBL_NAAM})
                 .build();
  public static final Message REQ_REGIOID =
      new Message.Builder()
                 .setAttribute(RegionaamDto.COL_REGIOID)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(
                    new Object[]{RegionaamValidator.LBL_REGIOID})
                 .build();
  public static final Message REQ_TAAL    =
      new Message.Builder()
                 .setAttribute(RegionaamDto.COL_TAAL)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(
                    new Object[]{RegionaamValidator.LBL_TAAL})
                 .build();

  private void setFouten(List<Message> expResult) {
    expResult.add(ERR_NAAM);
    expResult.add(ERR_TAAL);
  }

  private void setLeeg(List<Message> expResult) {
    expResult.add(REQ_NAAM);
    expResult.add(REQ_REGIOID);
    expResult.add(REQ_TAAL);
  }

  @Test
  public void testFouteRegionaam() {
    var           regionaam = new Regionaam();
    List<Message> expResult = new ArrayList<>();

    regionaam.setNaam(DoosUtils.stringMetLengte(SedesTestConstants.REGIONAAM,
                                                101, "X"));
    regionaam.setRegioId(SedesTestConstants.REGIOID);
    regionaam.setTaal(DoosUtils.stringMetLengte(SedesTestConstants.ISO6392T,
                                                4, "X"));

    setFouten(expResult);

    List<Message> result    = RegionaamValidator.valideer(regionaam);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testFouteRegionaamDto() {
    var           regionaam = new RegionaamDto();
    List<Message> expResult = new ArrayList<>();

    regionaam.setNaam(DoosUtils.stringMetLengte(SedesTestConstants.REGIONAAM,
                                                101, "X"));
    regionaam.setRegioId(SedesTestConstants.REGIOID);
    regionaam.setTaal(DoosUtils.stringMetLengte(SedesTestConstants.ISO6392T,
                                                4, "X"));

    setFouten(expResult);

    List<Message> result    = RegionaamValidator.valideer(regionaam);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testGoedeRegionaam1() {
    var           regionaam = new Regionaam();

    regionaam.setNaam(SedesTestConstants.REGIONAAM);
    regionaam.setRegioId(SedesTestConstants.REGIOID);
    regionaam.setTaal(SedesTestConstants.ISO6392T);

    List<Message> result    = RegionaamValidator.valideer(regionaam);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeRegionaam2() {
    var           regionaam = new Regionaam();

    regionaam.setNaam(SedesTestConstants.REGIONAAM);
    regionaam.setRegioId(SedesTestConstants.REGIOID);
    regionaam.setTaal(SedesTestConstants.ISO6392T.toUpperCase());

    List<Message> result  = RegionaamValidator.valideer(regionaam);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeRegionaamDto1() {
    var           regionaam = new RegionaamDto();

    regionaam.setNaam(SedesTestConstants.REGIONAAM);
    regionaam.setRegioId(SedesTestConstants.REGIOID);
    regionaam.setTaal(SedesTestConstants.ISO6392T);

    List<Message> result    = RegionaamValidator.valideer(regionaam);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeRegionaamDto2() {
    var           regionaam = new RegionaamDto();

    regionaam.setNaam(SedesTestConstants.REGIONAAM);
    regionaam.setRegioId(SedesTestConstants.REGIOID);
    regionaam.setTaal(SedesTestConstants.ISO6392T.toUpperCase());

    List<Message> result    = RegionaamValidator.valideer(regionaam);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testLegeRegionaam() {
    var           regionaam = new Regionaam();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = RegionaamValidator.valideer(regionaam);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testLegeRegionaamDto() {
    var           regionaam = new RegionaamDto();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = RegionaamValidator.valideer(regionaam);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testNullRegionaam() {
    Regionaam     regionaam = null;
    List<Message> result    = RegionaamValidator.valideer(regionaam);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(Regionaam.class.getSimpleName(),
                 result.get(0).getAttribute());
  }

  @Test
  public void testNullRegionaamDto() {
    RegionaamDto  regionaam = null;
    List<Message> result    = RegionaamValidator.valideer(regionaam);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(RegionaamDto.class.getSimpleName(),
                 result.get(0).getAttribute());
  }
}
