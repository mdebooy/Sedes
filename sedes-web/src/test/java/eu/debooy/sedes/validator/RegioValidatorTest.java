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
import eu.debooy.sedes.TestConstants;
import eu.debooy.sedes.domain.RegioDto;
import eu.debooy.sedes.form.Regio;
import static eu.debooy.sedes.validator.RegioValidator.LBL_NAAM;
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
  private static final  Message ERR_NAAM      =
      new Message.Builder()
                 .setAttribute(RegioDto.COL_NAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{RegioValidator.LBL_NAAM, 100})
                 .build();
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
  private static final  Message REQ_NAAM      =
      new Message.Builder()
                 .setAttribute(RegioDto.COL_NAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{LBL_NAAM})
                 .build();
  private static final  Message REQ_REGIOKODE =
      new Message.Builder()
                 .setAttribute(RegioDto.COL_REGIOKODE)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{RegioValidator.LBL_REGIOKODE})
                 .build();

  private void setFouten(List<Message> expResult) {
    expResult.add(REQ_LANDID);
    expResult.add(ERR_NAAM);
    expResult.add(ERR_REGIOKODE);
  }

  private void setLeeg(List<Message> expResult) {
    expResult.add(REQ_LANDID);
    expResult.add(REQ_NAAM);
    expResult.add(REQ_REGIOKODE);
  }

  @Test
  public void testValideerFouteRegio() {
    Regio         regio     = new Regio();
    List<Message> expResult = new ArrayList<>();

    regio.setNaam(DoosUtils.stringMetLengte(TestConstants.REGIONAAM, 101, "X"));
    regio.setRegiokode(
        DoosUtils.stringMetLengte(TestConstants.REGIOKODE, 6, "X"));

    setFouten(expResult);

    List<Message> result    = RegioValidator.valideer(regio);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeRegio() {
    Regio         regio     = new Regio();

    regio.setLandId(TestConstants.LANDID);
    regio.setNaam(TestConstants.REGIONAAM);
    regio.setRegiokode(TestConstants.REGIOKODE);

    List<Message> result    = RegioValidator.valideer(regio);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testValideerLegeRegio() {
    Regio         regio     = new Regio();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = RegioValidator.valideer(regio);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerFouteRegioDto() {
    RegioDto      regio     = new RegioDto();
    List<Message> expResult = new ArrayList<>();

    regio.setNaam(DoosUtils.stringMetLengte(TestConstants.REGIONAAM, 101, "X"));
    regio.setRegiokode(
        DoosUtils.stringMetLengte(TestConstants.REGIOKODE, 6, "X"));

    setFouten(expResult);

    List<Message> result    = RegioValidator.valideer(regio);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeRegioDto() {
    RegioDto       regio      = new RegioDto();

    regio.setLandId(TestConstants.LANDID);
    regio.setNaam(TestConstants.REGIONAAM);
    regio.setRegiokode(TestConstants.REGIOKODE);

    List<Message> result    = RegioValidator.valideer(regio);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testValideerLegeRegioDto() {
    RegioDto      regio     = new RegioDto();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = RegioValidator.valideer(regio);

    assertEquals(expResult.toString(), result.toString());
  }
}
