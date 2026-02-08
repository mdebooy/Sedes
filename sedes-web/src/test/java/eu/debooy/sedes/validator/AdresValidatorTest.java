/*
 * Copyright (c) 2024 Marco de Booij
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
import eu.debooy.sedes.domain.AdresDto;
import eu.debooy.sedes.form.Adres;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class AdresValidatorTest {
  private static final  Message ERR_ADRESDATA   =
      new Message.Builder()
                 .setAttribute(AdresDto.COL_ADRESDATA)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{AdresValidator.LBL_ADRESDATA, 255})
                 .build();
  private static final  Message ERR_SUBPOSTKODE =
      new Message.Builder()
                 .setAttribute(AdresDto.COL_SUBPOSTKODE)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{AdresValidator.LBL_SUBPOSTKODE, 10})
                 .build();
  private static final  Message REQ_ADRESDATA   =
      new Message.Builder()
                 .setAttribute(AdresDto.COL_ADRESDATA)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{AdresValidator.LBL_ADRESDATA})
                 .build();

  private void setFout(List<Message> expResult) {
    expResult.add(ERR_ADRESDATA);
    expResult.add(SedesTestConstants.ERR_OPMERKING);
    expResult.add(ERR_SUBPOSTKODE);
  }

  @Test
  public void testFoutAdres() {
    var           adres     = new Adres();
    List<Message> expResult = new ArrayList<>();

    setFout(expResult);

    adres.setAdresdata(DoosUtils.stringMetLengte(SedesTestConstants.ADRESDATA,
                                                     256, "X"));
    adres.setOpmerking(DoosUtils.stringMetLengte(SedesTestConstants.OPMERKING, 2001, "X"));
    adres.setSubPostkode(DoosUtils.stringMetLengte(SedesTestConstants.SUBPOSTKODE, 11, "X"));

    var           result    = AdresValidator.valideer(adres);

    assertEquals(3, result.size());
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testFoutAdresDto() {
    var           adres     = new AdresDto();
    List<Message> expResult = new ArrayList<>();

    setFout(expResult);

    adres.setAdresdata(DoosUtils.stringMetLengte(SedesTestConstants.ADRESDATA,
                                                     256, "X"));
    adres.setOpmerking(DoosUtils.stringMetLengte(SedesTestConstants.OPMERKING, 2001, "X"));
    adres.setSubPostkode(DoosUtils.stringMetLengte(SedesTestConstants.SUBPOSTKODE, 11, "X"));

    var           result    = AdresValidator.valideer(adres);

    assertEquals(3, result.size());
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testGoedAdres() {
    var           adres     = new Adres();

    adres.setAdresdata(SedesTestConstants.ADRESDATA);
    adres.setSubPostkode(SedesTestConstants.SUBPOSTKODE);

    var           result    = AdresValidator.valideer(adres);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testFoutGoedDto() {
    var           adres     = new AdresDto();

    adres.setAdresdata(SedesTestConstants.ADRESDATA);
    adres.setSubPostkode(SedesTestConstants.SUBPOSTKODE);

    var           result    = AdresValidator.valideer(adres);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testLeegAdres() {
    var           adres     = new Adres();

    List<Message> result    = AdresValidator.valideer(adres);

    assertEquals(1, result.size());
    assertEquals(REQ_ADRESDATA.toString(), result.get(0).toString());
  }

  @Test
  public void testLeegAdresDto() {
    var           adres     = new AdresDto();

    List<Message> result    = AdresValidator.valideer(adres);

    assertEquals(1, result.size());
    assertEquals(REQ_ADRESDATA.toString(), result.get(0).toString());
  }

  @Test
  public void testNullAdres() {
    Adres         adres     = null;
    List<Message> result    = AdresValidator.valideer(adres);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(Adres.class.getSimpleName(), result.get(0).getAttribute());
  }

  @Test
  public void testNullAdresDto() {
    AdresDto      adres     = null;
    List<Message> result    = AdresValidator.valideer(adres);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(AdresDto.class.getSimpleName(), result.get(0).getAttribute());
  }
}
