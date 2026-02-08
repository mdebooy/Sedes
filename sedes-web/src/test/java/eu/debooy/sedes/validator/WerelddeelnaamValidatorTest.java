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
import eu.debooy.sedes.domain.WerelddeelnaamDto;
import eu.debooy.sedes.form.Werelddeelnaam;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 *
 * @author Marco de Booij
 */
public class WerelddeelnaamValidatorTest {
  public static final Message ERR_TAAL            =
      new Message.Builder()
                 .setAttribute(WerelddeelnaamDto.COL_TAAL)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.FIXLENGTH)
                 .setParams(
                    new Object[]{WerelddeelnaamValidator.LBL_TAAL,
                                 3})
                 .build();
  public static final Message ERR_WERELDDEELNAAM  =
      new Message.Builder()
                 .setAttribute(WerelddeelnaamDto.COL_NAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(
                    new Object[]{WerelddeelnaamValidator.LBL_NAAM,
                                 100})
                 .build();
  public static final Message REQ_TAAL            =
      new Message.Builder()
                 .setAttribute(WerelddeelnaamDto.COL_TAAL)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(
                    new Object[]{WerelddeelnaamValidator.LBL_TAAL})
                 .build();
  public static final Message REQ_WERELDDEELNAAM  =
      new Message.Builder()
                 .setAttribute(WerelddeelnaamDto.COL_NAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(
                    new Object[]{WerelddeelnaamValidator.LBL_NAAM})
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
  public void testFouteWerelddeelnaam() {
    var           werelddeelnaam  = new Werelddeelnaam();
    List<Message> expResult       = new ArrayList<>();

    werelddeelnaam.setNaam(DoosUtils.stringMetLengte(SedesTestConstants.WERELDDEELNAAM, 101, "X"));
    werelddeelnaam.setTaal(DoosUtils.stringMetLengte(SedesTestConstants.ISO6392T, 4, "X"));
    werelddeelnaam.setWerelddeelId(null);

    setFouten(expResult);

    List<Message> result  = WerelddeelnaamValidator.valideer(werelddeelnaam);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testFouteWerelddeelnaamDto() {
    var           werelddeelnaam  = new WerelddeelnaamDto();
    List<Message> expResult       = new ArrayList<>();

    werelddeelnaam.setNaam(DoosUtils.stringMetLengte(SedesTestConstants.WERELDDEELNAAM, 101, "X"));
    werelddeelnaam.setTaal(DoosUtils.stringMetLengte(SedesTestConstants.ISO6392T, 4, "X"));
    werelddeelnaam.setWerelddeelId(null);

    setFouten(expResult);

    List<Message> result  = WerelddeelnaamValidator.valideer(werelddeelnaam);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testGoedeWerelddeelnaam1() {
    var             werelddeelnaam  = new Werelddeelnaam();

    werelddeelnaam.setNaam(SedesTestConstants.WERELDDEELNAAM);
    werelddeelnaam.setTaal(SedesTestConstants.ISO6392T);
    werelddeelnaam.setWerelddeelId(SedesTestConstants.WERELDDEELID);

    List<Message> result  = WerelddeelnaamValidator.valideer(werelddeelnaam);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeWerelddeelnaam2() {
    var             werelddeelnaam  = new Werelddeelnaam();

    werelddeelnaam.setNaam(SedesTestConstants.WERELDDEELNAAM);
    werelddeelnaam.setTaal(SedesTestConstants.ISO6392T.toUpperCase());
    werelddeelnaam.setWerelddeelId(SedesTestConstants.WERELDDEELID);

    List<Message> result  = WerelddeelnaamValidator.valideer(werelddeelnaam);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeWerelddeelnaamDto1() {
    var               werelddeelnaam  = new WerelddeelnaamDto();

    werelddeelnaam.setNaam(SedesTestConstants.WERELDDEELNAAM);
    werelddeelnaam.setTaal(SedesTestConstants.ISO6392T);
    werelddeelnaam.setWerelddeelId(SedesTestConstants.WERELDDEELID);

    List<Message> result  = WerelddeelnaamValidator.valideer(werelddeelnaam);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeWerelddeelnaamDto2() {
    var               werelddeelnaam  = new WerelddeelnaamDto();

    werelddeelnaam.setNaam(SedesTestConstants.WERELDDEELNAAM);
    werelddeelnaam.setTaal(SedesTestConstants.ISO6392T.toUpperCase());
    werelddeelnaam.setWerelddeelId(SedesTestConstants.WERELDDEELID);

    List<Message> result  = WerelddeelnaamValidator.valideer(werelddeelnaam);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testLegeWerelddeelnaam() {
    var           werelddeelnaam  = new Werelddeelnaam();
    List<Message> expResult       = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result          =
        WerelddeelnaamValidator.valideer(werelddeelnaam);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testLegeWerelddeelnaamDto() {
    var           werelddeelnaam  = new WerelddeelnaamDto();
    List<Message> expResult       = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result          =
        WerelddeelnaamValidator.valideer(werelddeelnaam);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testNullWerelddeelnaam() {
    Werelddeelnaam  werelddeelnaam  = null;
    List<Message>   result          =
        WerelddeelnaamValidator.valideer(werelddeelnaam);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(Werelddeelnaam.class.getSimpleName(),
                 result.get(0).getAttribute());
  }

  @Test
  public void testNullWerelddeelnaamDto() {
    WerelddeelnaamDto werelddeelnaam    = null;
    List<Message>     result            =
        WerelddeelnaamValidator.valideer(werelddeelnaam);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(WerelddeelnaamDto.class.getSimpleName(),
                 result.get(0).getAttribute());
  }
}
