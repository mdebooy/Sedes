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
import eu.debooy.sedes.domain.MuntDto;
import eu.debooy.sedes.form.Munt;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class MuntValidatorTest {
  private static final  Message ERR_DECIMALEN   =
      new Message.Builder()
                 .setAttribute(MuntDto.COL_DECIMALEN)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.RANGE)
                 .setParams(new Object[]{MuntValidator.LBL_DECIMALEN, 0, 99})
                 .build();
  private static final  Message ERR_ISO3        =
      new Message.Builder()
                 .setAttribute(MuntDto.COL_ISO3)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{MuntValidator.LBL_ISO3, 3})
                 .build();
  private static final  Message ERR_MUNTTEKEN   =
      new Message.Builder()
                 .setAttribute(MuntDto.COL_MUNTTEKEN)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{MuntValidator.LBL_MUNTTEKEN, 3})
                 .build();
  private static final  Message ERR_NAAM        =
      new Message.Builder()
                 .setAttribute(MuntDto.COL_NAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{MuntValidator.LBL_NAAM, 100})
                 .build();
  private static final  Message ERR_SUBEENHEID  =
      new Message.Builder()
                 .setAttribute(MuntDto.COL_SUBEENHEID)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{MuntValidator.LBL_SUBEENHEID, 100})
                 .build();
  private static final  Message REQ_ISO3        =
      new Message.Builder()
                 .setAttribute(MuntDto.COL_ISO3)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{MuntValidator.LBL_ISO3})
                 .build();
  private static final  Message REQ_NAAM        =
      new Message.Builder()
                 .setAttribute(MuntDto.COL_NAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{MuntValidator.LBL_NAAM})
                 .build();

  private void setFouten(List<Message> expResult) {
    expResult.add(ERR_DECIMALEN);
    expResult.add(ERR_ISO3);
    expResult.add(ERR_MUNTTEKEN);
    expResult.add(ERR_NAAM);
    expResult.add(ERR_SUBEENHEID);
  }

  private void setLeeg(List<Message> expResult) {
    expResult.add(REQ_ISO3);
    expResult.add(REQ_NAAM);
  }

  @Test
  public void testFouteMunt() {
    var           munt      = new Munt();
    List<Message> expResult = new ArrayList<>();

    munt.setDecimalen(-1);
    munt.setIso3(DoosUtils.stringMetLengte(SedesTestConstants.ISO3, 4, "X"));
    munt.setMuntteken(DoosUtils.stringMetLengte(SedesTestConstants.MUNTTEKEN,
                                                4, "X"));
    munt.setNaam(DoosUtils.stringMetLengte(SedesTestConstants.MUNTNAAM,
                                           101, "X"));
    munt.setSubeenheid(DoosUtils.stringMetLengte(SedesTestConstants.SUBEENHEID,
                                                 101, "X"));

    setFouten(expResult);

    List<Message> result    = MuntValidator.valideer(munt);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testFouteMuntDto() {
    var           munt      = new MuntDto();
    List<Message> expResult = new ArrayList<>();

    munt.setDecimalen(100);
    munt.setIso3(DoosUtils.stringMetLengte(SedesTestConstants.ISO3, 4, "X"));
    munt.setMuntteken(DoosUtils.stringMetLengte(SedesTestConstants.MUNTTEKEN,
                                                4, "X"));
    munt.setNaam(DoosUtils.stringMetLengte(SedesTestConstants.MUNTNAAM,
                                           101, "X"));
    munt.setSubeenheid(DoosUtils.stringMetLengte(SedesTestConstants.SUBEENHEID,
                                                 101, "X"));

    setFouten(expResult);

    List<Message> result    = MuntValidator.valideer(munt);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testGoedeMunt1() {
    var           munt      = new Munt();

    munt.setIso3(SedesTestConstants.ISO3);
    munt.setNaam(SedesTestConstants.MUNTNAAM);

    List<Message> result    = MuntValidator.valideer(munt);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeMunt2() {
    var           munt      = new Munt();

    munt.setDecimalen(0);
    munt.setIso3(SedesTestConstants.ISO3.toLowerCase());
    munt.setMuntteken(SedesTestConstants.MUNTTEKEN);
    munt.setNaam(SedesTestConstants.MUNTNAAM);
    munt.setSubeenheid(SedesTestConstants.SUBEENHEID);

    List<Message> result    = MuntValidator.valideer(munt);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeMuntDto1() {
    var           munt      = new MuntDto();

    munt.setIso3(SedesTestConstants.ISO3);
    munt.setNaam(SedesTestConstants.MUNTNAAM);

    List<Message> result    = MuntValidator.valideer(munt);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeMuntDto2() {
    var           munt      = new MuntDto();

    munt.setDecimalen(99);
    munt.setIso3(SedesTestConstants.ISO3.toLowerCase());
    munt.setMuntteken(SedesTestConstants.MUNTTEKEN);
    munt.setNaam(SedesTestConstants.MUNTNAAM);
    munt.setSubeenheid(SedesTestConstants.SUBEENHEID);

    List<Message> result    = MuntValidator.valideer(munt);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testLegeMunt() {
    var           munt      = new Munt();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = MuntValidator.valideer(munt);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testLegeMuntDto() {
    var           munt      = new MuntDto();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = MuntValidator.valideer(munt);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testNullMunt() {
    Munt          munt    = null;
    List<Message> result  = MuntValidator.valideer(munt);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(Munt.class.getSimpleName(),
                 result.get(0).getAttribute());
  }

  @Test
  public void testNullMuntDto() {
    MuntDto       munt    = null;
    List<Message> result  = MuntValidator.valideer(munt);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(MuntDto.class.getSimpleName(),
                 result.get(0).getAttribute());
  }
}
