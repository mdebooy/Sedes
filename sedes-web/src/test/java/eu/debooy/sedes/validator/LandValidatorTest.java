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
import eu.debooy.sedes.domain.LandDto;
import eu.debooy.sedes.form.Land;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 *
 * @author Marco de Booij
 */
public class LandValidatorTest {
  private static final  Message ERR_ISO2                =
      new Message.Builder()
                 .setAttribute(LandDto.COL_ISO2)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.FIXLENGTH)
                 .setParams(new Object[]{LandValidator.LBL_ISO2, 2})
                 .build();
  private static final  Message ERR_ISO3                =
      new Message.Builder()
                 .setAttribute(LandDto.COL_ISO3)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.FIXLENGTH)
                 .setParams(new Object[]{LandValidator.LBL_ISO3, 3})
                 .build();
   private static final  Message ERR_POSTKODESCHEIDING  =
      new Message.Builder()
                 .setAttribute(LandDto.COL_POSTKODESCHEIDING)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{LandValidator.LBL_POSTKODESCHEIDING,
                                         10})
                 .build();
   private static final  Message ERR_POSTKODETYPE       =
      new Message.Builder()
                 .setAttribute(LandDto.COL_POSTKODETYPE)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.FIXLENGTH)
                 .setParams(new Object[]{LandValidator.LBL_POSTKODETYPE, 1})
                 .build();
  private static final  Message ERR_POSTLANDKODE        =
      new Message.Builder()
                 .setAttribute(LandDto.COL_POSTLANDKODE)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{LandValidator.LBL_POSTLANDKODE, 3})
                 .build();
  public static final Message ERR_TAAL                  =
      new Message.Builder()
                 .setAttribute(LandDto.COL_TAAL)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.FIXLENGTH)
                 .setParams(
                    new Object[]{LandValidator.LBL_TAAL,
                                 3})
                 .build();

  private static final  Message REQ_ISO3               =
      new Message.Builder()
                 .setAttribute(LandDto.COL_ISO3)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{LandValidator.LBL_ISO3})
                 .build();
   private static final  Message REQ_POSTKODETYPE       =
      new Message.Builder()
                 .setAttribute(LandDto.COL_POSTKODETYPE)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{LandValidator.LBL_POSTKODETYPE})
                 .build();
   private static final  Message REQ_POSTLANDKODE       =
      new Message.Builder()
                 .setAttribute(LandDto.COL_POSTLANDKODE)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{LandValidator.LBL_POSTLANDKODE})
                 .build();
  public static final Message REQ_TAAL                  =
      new Message.Builder()
                 .setAttribute(LandDto.COL_TAAL)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(
                    new Object[]{LandValidator.LBL_TAAL})
                 .build();
  public static final Message REQ_WERELDDEELID          =
      new Message.Builder()
                 .setAttribute(LandDto.COL_WERELDDEELID)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(
                    new Object[]{LandValidator.LBL_WERELDDEELID})
                 .build();

  private void setFouten(List<Message> expResult) {
    expResult.add(ERR_ISO2);
    expResult.add(ERR_ISO3);
    expResult.add(ERR_POSTKODESCHEIDING);
    expResult.add(ERR_POSTKODETYPE);
    expResult.add(ERR_POSTLANDKODE);
    expResult.add(ERR_TAAL);
  }

  private void setLeeg(List<Message> expResult) {
    expResult.add(REQ_ISO3);
    expResult.add(REQ_POSTKODETYPE);
    expResult.add(REQ_POSTLANDKODE);
    expResult.add(REQ_TAAL);
    expResult.add(REQ_WERELDDEELID);
  }

  @Test
  public void testFouteLand() {
    var           land      = new Land();
    List<Message> expResult = new ArrayList<>();

    land.setBestaat(false);
    land.setIso2(DoosUtils.stringMetLengte(SedesTestConstants.ISO2, 3, "X"));
    land.setIso3(DoosUtils.stringMetLengte(SedesTestConstants.ISO3, 4, "X"));
    land.setLandId(SedesTestConstants.LANDID);
    land.setLandnummer(SedesTestConstants.LANDNUMMER);
    land.setMuntId(SedesTestConstants.MUNTID);
    land.setPostkodeScheiding(
        DoosUtils.stringMetLengte(SedesTestConstants.POSTKODESCHEIDING,
                                  11, "X"));
    land.setPostkodeType(
        DoosUtils.stringMetLengte(SedesTestConstants.POSTKODETYPE, 2, "X"));
    land.setPostLandkode(
        DoosUtils.stringMetLengte(SedesTestConstants.POSTLANDKODE, 4, "X"));
    land.setTaal(DoosUtils.stringMetLengte(SedesTestConstants.TAAL, 4, "X"));
    land.setWerelddeelId(SedesTestConstants.WERELDDEELID);

    setFouten(expResult);

    List<Message> result    = LandValidator.valideer(land);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testFouteLandDto() {
    var           land     = new LandDto();
    List<Message> expResult = new ArrayList<>();

    land.setBestaat(false);
    land.setIso2("X");
    land.setIso3("X");
    land.setLandId(SedesTestConstants.LANDID);
    land.setLandnummer(SedesTestConstants.LANDNUMMER);
    land.setMuntId(SedesTestConstants.MUNTID);
    land.setPostkodeScheiding(
        DoosUtils.stringMetLengte(SedesTestConstants.POSTKODESCHEIDING,
                                  11, "X"));
    land.setPostkodeType(
        DoosUtils.stringMetLengte(SedesTestConstants.POSTKODETYPE, 2, "X"));
    land.setPostLandkode(
        DoosUtils.stringMetLengte(SedesTestConstants.POSTLANDKODE, 4, "X"));
    land.setTaal(DoosUtils.stringMetLengte(SedesTestConstants.TAAL, 4, "X"));
    land.setWerelddeelId(SedesTestConstants.WERELDDEELID);

    setFouten(expResult);

    List<Message> result    = LandValidator.valideer(land);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testGoedeLand1() {
    var           land     = new Land();

    land.setIso3(SedesTestConstants.ISO3);
    land.setPostkodeType(SedesTestConstants.POSTKODETYPE);
    land.setPostLandkode(SedesTestConstants.POSTLANDKODE);
    land.setTaal(SedesTestConstants.TAAL);
    land.setWerelddeelId(SedesTestConstants.WERELDDEELID);

    List<Message> result    = LandValidator.valideer(land);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeLand2() {
    var           land     = new Land();

    land.setBestaat(false);
    land.setIso2(SedesTestConstants.ISO2.toLowerCase());
    land.setIso3(SedesTestConstants.ISO3.toLowerCase());
    land.setLandId(SedesTestConstants.LANDID);
    land.setLandnummer(SedesTestConstants.LANDNUMMER);
    land.setMuntId(SedesTestConstants.MUNTID);
    land.setPostkodeScheiding(SedesTestConstants.POSTKODESCHEIDING);
    land.setPostkodeType(SedesTestConstants.POSTKODETYPE);
    land.setPostLandkode(SedesTestConstants.POSTLANDKODE.toLowerCase());
    land.setTaal(SedesTestConstants.TAAL.toUpperCase());
    land.setWerelddeelId(SedesTestConstants.WERELDDEELID);

    List<Message> result    = LandValidator.valideer(land);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeLandDto1() {
    var           land      = new LandDto();

    land.setIso3(SedesTestConstants.ISO3);
    land.setPostkodeType(SedesTestConstants.POSTKODETYPE);
    land.setPostLandkode(SedesTestConstants.POSTLANDKODE);
    land.setTaal(SedesTestConstants.TAAL);
    land.setWerelddeelId(SedesTestConstants.WERELDDEELID);

    List<Message> result    = LandValidator.valideer(land);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeLandDto2() {
    var           land      = new LandDto();

    land.setBestaat(false);
    land.setIso2(SedesTestConstants.ISO2.toLowerCase());
    land.setIso3(SedesTestConstants.ISO3.toLowerCase());
    land.setLandId(SedesTestConstants.LANDID);
    land.setLandnummer(SedesTestConstants.LANDNUMMER);
    land.setMuntId(SedesTestConstants.MUNTID);
    land.setPostkodeScheiding(SedesTestConstants.POSTKODESCHEIDING);
    land.setPostkodeType(SedesTestConstants.POSTKODETYPE);
    land.setPostLandkode(SedesTestConstants.POSTLANDKODE.toLowerCase());
    land.setTaal(SedesTestConstants.TAAL.toUpperCase());
    land.setWerelddeelId(SedesTestConstants.WERELDDEELID);

    List<Message> result    = LandValidator.valideer(land);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testLegeLand() {
    var           land      = new Land();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = LandValidator.valideer(land);

    assertEquals(5, result.size());
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testLegeLandDto() {
    var           land      = new LandDto();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = LandValidator.valideer(land);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testNullLand() {
    Land          land    = null;
    List<Message> result  = LandValidator.valideer(land);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(Land.class.getSimpleName(),
                 result.get(0).getAttribute());
  }

  @Test
  public void testNullLandnaamDto() {
    LandDto       land    = null;
    List<Message> result  = LandValidator.valideer(land);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(LandDto.class.getSimpleName(),
                 result.get(0).getAttribute());
  }
}
