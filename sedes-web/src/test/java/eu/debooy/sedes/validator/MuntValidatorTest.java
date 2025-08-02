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
import eu.debooy.sedes.TestConstants;
import eu.debooy.sedes.domain.WerelddeelnaamDto;
import eu.debooy.sedes.form.Werelddeelnaam;
import static eu.debooy.sedes.validator.WerelddeelnaamValidatorTest.ERR_WERELDDEELNAAM;
import static eu.debooy.sedes.validator.WerelddeelnaamValidatorTest.REQ_TAAL;
import static eu.debooy.sedes.validator.WerelddeelnaamValidatorTest.REQ_WERELDDEELNAAM;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class MuntValidatorTest {
  public static final Message ERR_TAAL            =
      new Message.Builder()
                 .setAttribute(WerelddeelnaamDto.COL_TAAL)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.FIXLENGTH)
                 .setParams(
                    new Object[]{WerelddeelnaamValidator.LBL_TAAL,
                                 3})
                 .build();

  private void setFouten(List<Message> expResult) {
    expResult.add(ERR_WERELDDEELNAAM);
    expResult.add(ERR_TAAL);
  }

  private void setLeeg(List<Message> expResult) {
    expResult.add(REQ_WERELDDEELNAAM);
    expResult.add(REQ_TAAL);
  }

  @Test
  public void testValideerFouteWerelddeelnaam() {
    var           werelddeelnaam  = new Werelddeelnaam();
    List<Message> expResult       = new ArrayList<>();

    werelddeelnaam.setNaam(
            DoosUtils.stringMetLengte(TestConstants.WERELDDEELNAAM, 101, "X"));
    werelddeelnaam.setTaal(
            DoosUtils.stringMetLengte(TestConstants.ISO6392T, 4, "X"));
    werelddeelnaam.setWerelddeelId(null);

    setFouten(expResult);

    List<Message> result  = WerelddeelnaamValidator.valideer(werelddeelnaam);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerFouteWerelddeelnaamDto() {
    var           werelddeelnaam  = new WerelddeelnaamDto();
    List<Message> expResult       = new ArrayList<>();

    werelddeelnaam.setNaam(
            DoosUtils.stringMetLengte(TestConstants.WERELDDEELNAAM, 101, "X"));
    werelddeelnaam.setTaal(
            DoosUtils.stringMetLengte(TestConstants.ISO6392T, 4, "X"));
    werelddeelnaam.setWerelddeelId(null);

    setFouten(expResult);

    List<Message> result  = WerelddeelnaamValidator.valideer(werelddeelnaam);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeWerelddeelnaam1() {
    var             werelddeelnaam  = new Werelddeelnaam();

    werelddeelnaam.setNaam(TestConstants.WERELDDEELNAAM);
    werelddeelnaam.setTaal(TestConstants.ISO6392T);
    werelddeelnaam.setWerelddeelId(TestConstants.WERELDDEELID);

    List<Message> result  = WerelddeelnaamValidator.valideer(werelddeelnaam);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testValideerGoedeWerelddeelnaam2() {
    var             werelddeelnaam  = new Werelddeelnaam();

    werelddeelnaam.setNaam(TestConstants.WERELDDEELNAAM);
    werelddeelnaam.setTaal(TestConstants.ISO6392T.toUpperCase());
    werelddeelnaam.setWerelddeelId(TestConstants.WERELDDEELID);

    List<Message> result  = WerelddeelnaamValidator.valideer(werelddeelnaam);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testValideerGoedeWerelddeelnaamDto1() {
    var               werelddeelnaam  = new WerelddeelnaamDto();

    werelddeelnaam.setNaam(TestConstants.WERELDDEELNAAM);
    werelddeelnaam.setTaal(TestConstants.ISO6392T);
    werelddeelnaam.setWerelddeelId(TestConstants.WERELDDEELID);

    List<Message> result  = WerelddeelnaamValidator.valideer(werelddeelnaam);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testValideerGoedeWerelddeelnaamDto2() {
    var               werelddeelnaam  = new WerelddeelnaamDto();

    werelddeelnaam.setNaam(TestConstants.WERELDDEELNAAM);
    werelddeelnaam.setTaal(TestConstants.ISO6392T.toUpperCase());
    werelddeelnaam.setWerelddeelId(TestConstants.WERELDDEELID);

    List<Message> result  = WerelddeelnaamValidator.valideer(werelddeelnaam);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testValideerLegeWerelddeelnaam() {
    var           werelddeelnaam  = new Werelddeelnaam();
    List<Message> expResult       = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result          =
        WerelddeelnaamValidator.valideer(werelddeelnaam);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerLegeWerelddeelnaamDto() {
    var           werelddeelnaam  = new WerelddeelnaamDto();
    List<Message> expResult       = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result          =
        WerelddeelnaamValidator.valideer(werelddeelnaam);

    assertEquals(expResult.toString(), result.toString());
  }
}
