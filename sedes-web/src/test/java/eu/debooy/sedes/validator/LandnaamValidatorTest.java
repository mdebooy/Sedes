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
import eu.debooy.sedes.domain.LandnaamDto;
import eu.debooy.sedes.form.Landnaam;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 *
 * @author Marco de Booij
 */
public class LandnaamValidatorTest {
  public static final Message ERR_NAAM    =
      new Message.Builder()
                 .setAttribute(LandnaamDto.COL_NAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(
                    new Object[]{LandnaamValidator.LBL_NAAM,
                                 100})
                 .build();
  public static final Message ERR_TAAL    =
      new Message.Builder()
                 .setAttribute(LandnaamDto.COL_TAAL)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.FIXLENGTH)
                 .setParams(
                    new Object[]{LandnaamValidator.LBL_TAAL,
                                 3})
                 .build();
  public static final Message REQ_NAAM    =
      new Message.Builder()
                 .setAttribute(LandnaamDto.COL_NAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(
                    new Object[]{LandnaamValidator.LBL_NAAM})
                 .build();
  public static final Message REQ_LANDID =
      new Message.Builder()
                 .setAttribute(LandnaamDto.COL_LANDID)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(
                    new Object[]{LandnaamValidator.LBL_LANDID})
                 .build();
  public static final Message REQ_TAAL    =
      new Message.Builder()
                 .setAttribute(LandnaamDto.COL_TAAL)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(
                    new Object[]{LandnaamValidator.LBL_TAAL})
                 .build();

  private void setFouten(List<Message> expResult) {
    expResult.add(ERR_NAAM);
    expResult.add(ERR_TAAL);
  }

  private void setLeeg(List<Message> expResult) {
    expResult.add(REQ_LANDID);
    expResult.add(REQ_NAAM);
    expResult.add(REQ_TAAL);
  }

  @Test
  public void testFouteLandnaam() {
    var           landnaam  = new Landnaam();
    List<Message> expResult = new ArrayList<>();

    landnaam.setNaam(DoosUtils.stringMetLengte(SedesTestConstants.LANDNAAM,
                                                101, "X"));
    landnaam.setLandId(SedesTestConstants.LANDID);
    landnaam.setTaal(DoosUtils.stringMetLengte(SedesTestConstants.ISO6392T,
                                                4, "X"));

    setFouten(expResult);

    List<Message> result    = LandnaamValidator.valideer(landnaam);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testFouteLandnaamDto() {
    var           landnaam  = new LandnaamDto();
    List<Message> expResult = new ArrayList<>();

    landnaam.setNaam(DoosUtils.stringMetLengte(SedesTestConstants.LANDNAAM,
                                                101, "X"));
    landnaam.setLandId(SedesTestConstants.LANDID);
    landnaam.setTaal(DoosUtils.stringMetLengte(SedesTestConstants.ISO6392T,
                                                4, "X"));

    setFouten(expResult);

    List<Message> result    = LandnaamValidator.valideer(landnaam);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testGoedeLandnaam1() {
    var           landnaam  = new Landnaam();

    landnaam.setNaam(SedesTestConstants.LANDNAAM);
    landnaam.setLandId(SedesTestConstants.LANDID);
    landnaam.setTaal(SedesTestConstants.ISO6392T);

    List<Message> result    = LandnaamValidator.valideer(landnaam);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeLandnaam2() {
    var           landnaam  = new Landnaam();

    landnaam.setNaam(SedesTestConstants.LANDNAAM);
    landnaam.setLandId(SedesTestConstants.LANDID);
    landnaam.setTaal(SedesTestConstants.ISO6392T.toUpperCase());

    List<Message> result  = LandnaamValidator.valideer(landnaam);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeLandnaamDto1() {
    var           landnaam  = new LandnaamDto();

    landnaam.setNaam(SedesTestConstants.LANDNAAM);
    landnaam.setLandId(SedesTestConstants.LANDID);
    landnaam.setTaal(SedesTestConstants.ISO6392T);

    List<Message> result    = LandnaamValidator.valideer(landnaam);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeLandnaamDto2() {
    var           landnaam  = new LandnaamDto();

    landnaam.setNaam(SedesTestConstants.LANDNAAM);
    landnaam.setLandId(SedesTestConstants.LANDID);
    landnaam.setTaal(SedesTestConstants.ISO6392T.toUpperCase());

    List<Message> result    = LandnaamValidator.valideer(landnaam);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testLegeLandnaam() {
    var           landnaam  = new Landnaam();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = LandnaamValidator.valideer(landnaam);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testLegeLandnaamDto() {
    var           landnaam  = new LandnaamDto();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = LandnaamValidator.valideer(landnaam);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testNullLandnaam() {
    Landnaam      landnaam  = null;
    List<Message> result    = LandnaamValidator.valideer(landnaam);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(Landnaam.class.getSimpleName(),
                 result.get(0).getAttribute());
  }

  @Test
  public void testNullLandnaamDto() {
    LandnaamDto   landnaam  = null;
    List<Message> result    = LandnaamValidator.valideer(landnaam);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(LandnaamDto.class.getSimpleName(),
                 result.get(0).getAttribute());
  }
}
