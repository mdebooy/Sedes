/*
 * Copyright (c) 2025 Marco de Booij
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
import eu.debooy.sedes.domain.PlaatsDto;
import eu.debooy.sedes.form.Plaats;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class PlaatsValidatorTest {
  private static final  Message ERR_PLAATSNAAM  =
      new Message.Builder()
                 .setAttribute(PlaatsDto.COL_PLAATSNAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{PlaatsValidator.LBL_PLAATSNAAM, 100})
                 .build();
  private static final  Message ERR_POSTKODE    =
      new Message.Builder()
                 .setAttribute(PlaatsDto.COL_POSTKODE)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{PlaatsValidator.LBL_POSTKODE, 15})
                 .build();
  private static final  Message ERR_ZONENUMMER  =
      new Message.Builder()
                 .setAttribute(PlaatsDto.COL_ZONENUMMER)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.RANGE)
                 .setParams(new Object[]{PlaatsValidator.LBL_ZONENUMMER,
                                         0, 999999})
                 .build();
  private static final  Message REQ_LANDID      =
      new Message.Builder()
                 .setAttribute(PlaatsDto.COL_LANDID)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{PlaatsValidator.LBL_LANDID})
                 .build();
  private static final  Message REQ_PLAATSNAAM  =
      new Message.Builder()
                 .setAttribute(PlaatsDto.COL_PLAATSNAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{PlaatsValidator.LBL_PLAATSNAAM})
                 .build();
  private static final  Message REQ_REGIOID     =
      new Message.Builder()
                 .setAttribute(PlaatsDto.COL_REGIOID)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{PlaatsValidator.LBL_REGIOID})
                 .build();

  private void setFouten(List<Message> expResult) {
    expResult.add(REQ_LANDID);
    expResult.add(ERR_PLAATSNAAM);
    expResult.add(ERR_POSTKODE);
    expResult.add(REQ_REGIOID);
    expResult.add(ERR_ZONENUMMER);
  }

  private void setLeeg(List<Message> expResult) {
    expResult.add(REQ_LANDID);
    expResult.add(REQ_PLAATSNAAM);
    expResult.add(REQ_REGIOID);
  }

  @Test
  public void testFoutePlaats1() {
    var           plaats    = new Plaats();
    List<Message> expResult = new ArrayList<>();

    plaats.setPlaatsnaam(DoosUtils.stringMetLengte(SedesTestConstants.PLAATSNAAM,
                                                   101, "X"));
    plaats.setPostkode(DoosUtils.stringMetLengte(SedesTestConstants.POSTKODE,
                                                 16, "X"));
    plaats.setZonenummer(1000000L);

    setFouten(expResult);

    List<Message> result    = PlaatsValidator.valideer(plaats);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testFoutePlaatsDto1() {
    var           plaats    = new PlaatsDto();
    List<Message> expResult = new ArrayList<>();

    plaats.setPlaatsnaam(DoosUtils.stringMetLengte(SedesTestConstants.PLAATSNAAM,
                                                   101, "X"));
    plaats.setPostkode(DoosUtils.stringMetLengte(SedesTestConstants.POSTKODE,
                                                 16, "X"));
    plaats.setZonenummer(-1L);

    setFouten(expResult);

    List<Message> result    = PlaatsValidator.valideer(plaats);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testGoedePlaats1() {
    var           plaats    = new Plaats();

    plaats.setLandId(SedesTestConstants.LANDID);
    plaats.setPlaatsnaam(SedesTestConstants.PLAATSNAAM);
    plaats.setRegioId(SedesTestConstants.REGIOID);

    List<Message> result    = PlaatsValidator.valideer(plaats);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedePlaatsDto1() {
    var           plaats    = new PlaatsDto();

    plaats.setLandId(SedesTestConstants.LANDID);
    plaats.setPlaatsnaam(SedesTestConstants.PLAATSNAAM);
    plaats.setRegioId(SedesTestConstants.REGIOID);

    List<Message> result    = PlaatsValidator.valideer(plaats);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testLegePlaats() {
    var           plaats    = new Plaats();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = PlaatsValidator.valideer(plaats);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testLegePlaatsDto() {
    var           plaats    = new PlaatsDto();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = PlaatsValidator.valideer(plaats);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testNullPlaatsnaam() {
    Plaats        plaats  = null;
    List<Message> result  = PlaatsValidator.valideer(plaats);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(Plaats.class.getSimpleName(),
                 result.get(0).getAttribute());
  }

  @Test
  public void testNullPlaatsDto() {
    PlaatsDto     plaats  = null;
    List<Message> result  = PlaatsValidator.valideer(plaats);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(PlaatsDto.class.getSimpleName(),
                 result.get(0).getAttribute());
  }
}
