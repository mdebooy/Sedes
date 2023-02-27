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
import eu.debooy.sedes.domain.WerelddeelnaamDto;
import eu.debooy.sedes.form.Werelddeelnaam;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 *
 * @author Marco de Booij
 */
public class WerelddeelnaamValidatorTest {
  public static final Message ERR_WERELDDEELNAAM  =
      new Message.Builder()
                 .setAttribute(WerelddeelnaamDto.COL_NAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(
                    new Object[]{WerelddeelnaamValidator.LBL_NAAM,
                                 100})
                 .build();
  public static final Message REQ_WERELDDEELNAAM  =
      new Message.Builder()
                 .setAttribute(WerelddeelnaamDto.COL_NAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(
                    new Object[]{WerelddeelnaamValidator.LBL_NAAM})
                 .build();

  @Test
  public void testValideerFouteWerelddeelnaam() {
    Werelddeelnaam  werelddeelnaam  = new Werelddeelnaam();

    werelddeelnaam.setNaam(
            DoosUtils.stringMetLengte(TestConstants.WERELDDEELNAAM, 101, "X"));
    werelddeelnaam.setWerelddeelId(null);

    List<Message> result  = WerelddeelnaamValidator.valideer(werelddeelnaam);

    assertEquals(1, result.size());
    assertEquals(ERR_WERELDDEELNAAM.toString(), result.get(0).toString());
  }

  @Test
  public void testValideerGoedeWerelddeelnaam() {
    Werelddeelnaam  werelddeelnaam  = new Werelddeelnaam();

    werelddeelnaam.setNaam(TestConstants.WERELDDEELNAAM);
    werelddeelnaam.setWerelddeelId(TestConstants.WERELDDEELID);

    List<Message> result  = WerelddeelnaamValidator.valideer(werelddeelnaam);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testValideerLegeWerelddeelnaam() {
    Werelddeelnaam  werelddeelnaam  = new Werelddeelnaam();
    List<Message>   result          =
        WerelddeelnaamValidator.valideer(werelddeelnaam);

    assertEquals(1, result.size());
    assertEquals(REQ_WERELDDEELNAAM.toString(), result.get(0).toString());
  }

  @Test
  public void testValideerFouteWerelddeelnaamDto() {
    WerelddeelnaamDto werelddeelnaam  = new WerelddeelnaamDto();

    werelddeelnaam.setNaam(
            DoosUtils.stringMetLengte(TestConstants.WERELDDEELNAAM, 101, "X"));
    werelddeelnaam.setWerelddeelId(null);

    List<Message> result  = WerelddeelnaamValidator.valideer(werelddeelnaam);

    assertEquals(1, result.size());
    assertEquals(ERR_WERELDDEELNAAM.toString(), result.get(0).toString());
  }

  @Test
  public void testValideerGoedeWerelddeelnaamDto() {
    WerelddeelnaamDto werelddeelnaam  = new WerelddeelnaamDto();

    werelddeelnaam.setNaam(TestConstants.WERELDDEELNAAM);
    werelddeelnaam.setWerelddeelId(TestConstants.WERELDDEELID);

    List<Message> result  = WerelddeelnaamValidator.valideer(werelddeelnaam);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testValideerLegeWerelddeelnaamDto() {
    WerelddeelnaamDto werelddeelnaam  = new WerelddeelnaamDto();
    List<Message>     result          =
        WerelddeelnaamValidator.valideer(werelddeelnaam);

    assertEquals(1, result.size());
    assertEquals(REQ_WERELDDEELNAAM.toString(), result.get(0).toString());
  }
}
