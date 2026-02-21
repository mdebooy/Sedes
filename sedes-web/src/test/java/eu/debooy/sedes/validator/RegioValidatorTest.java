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
import eu.debooy.sedes.domain.RegioDto;
import eu.debooy.sedes.form.Regio;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 *
 * @author Marco de Booij
 */
public class RegioValidatorTest {
  private static final  Message ERR_REGIOKODE =
      new Message.Builder()
                 .setAttribute(RegioDto.COL_REGIOKODE)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{RegioValidator.LBL_REGIOKODE, 5})
                 .build();
  private static final  Message REQ_LANDID    =
      new Message.Builder()
                 .setAttribute(RegioDto.COL_LANDID)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{RegioValidator.LBL_LANDID})
                 .build();
  private static final  Message REQ_REGIOKODE =
      new Message.Builder()
                 .setAttribute(RegioDto.COL_REGIOKODE)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{RegioValidator.LBL_REGIOKODE})
                 .build();

  private void setFouten(List<Message> expResult) {
    expResult.add(ERR_REGIOKODE);
  }

  private void setLeeg(List<Message> expResult) {
    expResult.add(REQ_LANDID);
    expResult.add(REQ_REGIOKODE);
  }

  @Test
  public void testFouteRegio() {
    var           regio     = new Regio();
    List<Message> expResult = new ArrayList<>();

    regio.setLandId(SedesTestConstants.LANDID);
    regio.setRegiokode(DoosUtils.stringMetLengte(SedesTestConstants.REGIOKODE,
                       6, "X"));

    setFouten(expResult);

    List<Message> result    = RegioValidator.valideer(regio);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testFouteRegioDto() {
    var           regio     = new RegioDto();
    List<Message> expResult = new ArrayList<>();

    regio.setLandId(SedesTestConstants.LANDID);
    regio.setRegiokode(DoosUtils.stringMetLengte(SedesTestConstants.REGIOKODE,
                       6, "X"));

    setFouten(expResult);

    List<Message> result    = RegioValidator.valideer(regio);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testGoedeRegio1() {
    var           regio     = new Regio();

    regio.setLandId(SedesTestConstants.LANDID);
    regio.setRegiokode(SedesTestConstants.REGIOKODE);

    List<Message> result    = RegioValidator.valideer(regio);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeRegio2() {
    var           regio     = new Regio();

    regio.setLandId(SedesTestConstants.LANDID);
    regio.setRegiokode(SedesTestConstants.REGIOKODE.toLowerCase());

    List<Message> result    = RegioValidator.valideer(regio);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeRegioDto1() {
    var           regio      = new RegioDto();

    regio.setLandId(SedesTestConstants.LANDID);
    regio.setRegiokode(SedesTestConstants.REGIOKODE);

    List<Message> result    = RegioValidator.valideer(regio);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeRegioDto2() {
    var           regio      = new RegioDto();

    regio.setLandId(SedesTestConstants.LANDID);
    regio.setRegiokode(SedesTestConstants.REGIOKODE.toLowerCase());

    List<Message> result    = RegioValidator.valideer(regio);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testLegeRegio() {
    var           regio     = new Regio();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = RegioValidator.valideer(regio);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testLegeRegioDto() {
    var           regio     = new RegioDto();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = RegioValidator.valideer(regio);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testNullRegio() {
    Regio         regio   = null;
    List<Message> result  = RegioValidator.valideer(regio);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(Regio.class.getSimpleName(),
                 result.get(0).getAttribute());
  }

  @Test
  public void testNullWerelddeelnaamDto() {
    RegioDto      regio   = null;
    List<Message> result  = RegioValidator.valideer(regio);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(RegioDto.class.getSimpleName(),
                 result.get(0).getAttribute());
  }
}
