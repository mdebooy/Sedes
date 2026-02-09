/*
 * Copyright (c) 2026 Marco de Booij
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

import eu.debooy.doosutils.Datum;
import eu.debooy.doosutils.DoosConstants;
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.sedes.SedesTestConstants;
import eu.debooy.sedes.domain.KontaktadresDto;
import eu.debooy.sedes.form.Kontaktadres;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Marco de Booij
 */
public class KontaktadresValidatorTest {
  private static final  Message ERR_EINDDATUM_TKMST   =
      new Message.Builder()
                 .setAttribute(KontaktadresDto.COL_EINDDATUM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.FUTURE)
                 .setParams(new Object[]{KontaktadresValidator.LBL_EINDDATUM})
                 .build();
  private static final  Message ERR_KONTAKTADRESTYPE  =
      new Message.Builder()
                 .setAttribute(KontaktadresDto.COL_KONTAKTADRESTYPE)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(
                    new Object[]{KontaktadresValidator.LBL_KONTAKTADRESTYPE,
                                 10})
                 .build();
  private static final  Message ERR_OPMERKING         =
      new Message.Builder()
                 .setAttribute(KontaktadresDto.COL_OPMERKING)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(
                    new Object[]{KontaktadresValidator.LBL_OPMERKING,
                                 2000})
                 .build();
  private static final  Message ERR_STARTDATUM_TKMST  =
      new Message.Builder()
                 .setAttribute(KontaktadresDto.COL_STARTDATUM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.FUTURE)
                 .setParams(new Object[]{KontaktadresValidator.LBL_STARTDATUM})
                 .build();
  private static final  Message ERR_STARTDATUM_VOOR   =
      new Message.Builder()
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.DATEBEFORE)
                 .setParams(new Object[]{KontaktadresValidator.LBL_EINDDATUM,
                                         KontaktadresValidator.LBL_STARTDATUM})
                 .build();
  private static final  Message ERR_SUBADRES          =
      new Message.Builder()
                 .setAttribute(KontaktadresDto.COL_SUBADRES)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(
                    new Object[]{KontaktadresValidator.LBL_SUBADRES, 255})
                 .build();
  private static final  Message ERR_TAAL                =
      new Message.Builder()
                 .setAttribute(KontaktadresDto.COL_TAAL)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.FIXLENGTH)
                 .setParams(new Object[]{KontaktadresValidator.LBL_TAAL, 3})
                 .build();
  private static final  Message REQ_ADRESID           =
      new Message.Builder()
                 .setAttribute(KontaktadresDto.COL_ADRESID)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{KontaktadresValidator.LBL_ADRESID})
                 .build();
  private static final  Message REQ_KONTAKTADRESTYPE  =
      new Message.Builder()
                 .setAttribute(KontaktadresDto.COL_KONTAKTADRESTYPE)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(
                      new Object[]{KontaktadresValidator.LBL_KONTAKTADRESTYPE})
                 .build();
  private static final  Message REQ_KONTAKTID         =
      new Message.Builder()
                 .setAttribute(KontaktadresDto.COL_KONTAKTID)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{KontaktadresValidator.LBL_KONTAKTID})
                 .build();
  private static final  Message REQ_STARTDATUM        =
      new Message.Builder()
                 .setAttribute(KontaktadresDto.COL_STARTDATUM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{KontaktadresValidator.LBL_STARTDATUM})
                 .build();
  private static final  Message REQ_TAAL                =
      new Message.Builder()
                 .setAttribute(KontaktadresDto.COL_TAAL)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{KontaktadresValidator.LBL_TAAL})
                 .build();

  private static Date einddatum;
  private static Date startdatum;
  private static Date toekomstdatum1;
  private static Date toekomstdatum2;

  @BeforeClass
  public static void setUpClass() throws ParseException {
    Calendar  kalender  = Calendar.getInstance();
    kalender.add(Calendar.DAY_OF_YEAR, 1);
    toekomstdatum1  = kalender.getTime();
    kalender.add(Calendar.DAY_OF_YEAR, 1);
    toekomstdatum2  = kalender.getTime();

    einddatum       = Datum.toDate(SedesTestConstants.EINDDATUM,
                                   DoosConstants.DATUM);
    startdatum      = Datum.toDate(SedesTestConstants.STARTDATUM,
                                   DoosConstants.DATUM);
  }
  
  private void setFouten(List<Message> expResult) {
    expResult.add(ERR_KONTAKTADRESTYPE);
    expResult.add(ERR_OPMERKING);
    expResult.add(ERR_STARTDATUM_VOOR);
    expResult.add(ERR_SUBADRES);
    expResult.add(ERR_TAAL);
  }

  private void setLeeg(List<Message> expResult) {
    expResult.add(REQ_ADRESID);
    expResult.add(REQ_KONTAKTADRESTYPE);
    expResult.add(REQ_KONTAKTID);
    expResult.add(REQ_STARTDATUM);
    expResult.add(REQ_TAAL);
  }

  @Test
  public void testFouteKontaktadres1() {
    var           kontaktadres  = new Kontaktadres();
    List<Message> expResult     = new ArrayList<>();

    kontaktadres.setAdresId(SedesTestConstants.ADRESID);
    kontaktadres.setEinddatum(startdatum);
    kontaktadres.setKontaktadrestype(
        DoosUtils.stringMetLengte(SedesTestConstants.KONTAKTADTYPE, 11, "X"));
    kontaktadres.setKontaktId(SedesTestConstants.KONTAKTID);
    kontaktadres.setOpmerking(
        DoosUtils.stringMetLengte(SedesTestConstants.OPMERKING, 2001, "X"));
    kontaktadres.setStartdatum(einddatum);
    kontaktadres.setSubAdres(
        DoosUtils.stringMetLengte(SedesTestConstants.SUBADRES, 256, "X"));
    kontaktadres.setTaal("X");

    setFouten(expResult);

    List<Message> result        = KontaktadresValidator.valideer(kontaktadres);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testFouteKontaktadres2() {
    var           kontaktadres  = new KontaktadresDto();

    kontaktadres.setAdresId(SedesTestConstants.ADRESID);
    kontaktadres.setEinddatum(toekomstdatum2);
    kontaktadres.setKontaktadrestype(SedesTestConstants.KONTAKTADTYPE);
    kontaktadres.setKontaktId(SedesTestConstants.KONTAKTID);
    kontaktadres.setOpmerking(SedesTestConstants.OPMERKING);
    kontaktadres.setStartdatum(startdatum);
    kontaktadres.setSubAdres(SedesTestConstants.SUBADRES);
    kontaktadres.setTaal(SedesTestConstants.TAAL.toUpperCase());

    List<Message> result        =
        KontaktadresValidator.valideer(kontaktadres);

    assertEquals(1, result.size());
    assertEquals(ERR_EINDDATUM_TKMST.toString(), result.get(0).toString());
  }

  @Test
  public void testFouteKontaktadres3() {
    var           kontaktadres  = new KontaktadresDto();

    kontaktadres.setAdresId(SedesTestConstants.ADRESID);
    kontaktadres.setKontaktadrestype(SedesTestConstants.KONTAKTADTYPE);
    kontaktadres.setKontaktId(SedesTestConstants.KONTAKTID);
    kontaktadres.setOpmerking(SedesTestConstants.OPMERKING);
    kontaktadres.setStartdatum(toekomstdatum1);
    kontaktadres.setSubAdres(SedesTestConstants.SUBADRES);
    kontaktadres.setTaal(SedesTestConstants.TAAL.toUpperCase());

    List<Message> result        =
        KontaktadresValidator.valideer(kontaktadres);

    assertEquals(1, result.size());
    assertEquals(ERR_STARTDATUM_TKMST.toString(), result.get(0).toString());
  }

  @Test
  public void testFouteKontaktadres4() {
    var           kontaktadres  = new KontaktadresDto();

    kontaktadres.setAdresId(SedesTestConstants.ADRESID);
    kontaktadres.setEinddatum(toekomstdatum2);
    kontaktadres.setKontaktadrestype(SedesTestConstants.KONTAKTADTYPE);
    kontaktadres.setKontaktId(SedesTestConstants.KONTAKTID);
    kontaktadres.setOpmerking(SedesTestConstants.OPMERKING);
    kontaktadres.setStartdatum(toekomstdatum1);
    kontaktadres.setSubAdres(SedesTestConstants.SUBADRES);
    kontaktadres.setTaal(SedesTestConstants.TAAL.toUpperCase());

    List<Message> result        =
        KontaktadresValidator.valideer(kontaktadres);

    assertEquals(2, result.size());
    assertEquals(ERR_STARTDATUM_TKMST.toString(), result.get(0).toString());
    assertEquals(ERR_EINDDATUM_TKMST.toString(), result.get(1).toString());
  }

  @Test
  public void testFouteKontaktadresDto1() {
    var           kontaktadres  = new KontaktadresDto();
    List<Message> expResult     = new ArrayList<>();

    kontaktadres.setAdresId(SedesTestConstants.ADRESID);
    kontaktadres.setEinddatum(startdatum);
    kontaktadres.setKontaktadrestype(
        DoosUtils.stringMetLengte(SedesTestConstants.KONTAKTADTYPE, 11, "X"));
    kontaktadres.setKontaktId(SedesTestConstants.KONTAKTID);
    kontaktadres.setOpmerking(
        DoosUtils.stringMetLengte(SedesTestConstants.OPMERKING, 2001, "X"));
    kontaktadres.setStartdatum(einddatum);
    kontaktadres.setSubAdres(
        DoosUtils.stringMetLengte(SedesTestConstants.SUBADRES, 256, "X"));
    kontaktadres.setTaal("X");

    setFouten(expResult);

    List<Message> result        = KontaktadresValidator.valideer(kontaktadres);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testGoedeKontaktadres1() {
    var           kontaktadres  = new KontaktadresDto();

    kontaktadres.setAdresId(SedesTestConstants.ADRESID);
    kontaktadres.setKontaktadrestype(SedesTestConstants.KONTAKTADTYPE);
    kontaktadres.setKontaktId(SedesTestConstants.KONTAKTID);
    kontaktadres.setStartdatum(startdatum);
    kontaktadres.setTaal(SedesTestConstants.TAAL);

    List<Message> result        =
        KontaktadresValidator.valideer(kontaktadres);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeKontaktadres2() {
    var           kontaktadres  = new KontaktadresDto();

    kontaktadres.setAdresId(SedesTestConstants.ADRESID);
    kontaktadres.setEinddatum(einddatum);
    kontaktadres.setKontaktadrestype(SedesTestConstants.KONTAKTADTYPE);
    kontaktadres.setKontaktId(SedesTestConstants.KONTAKTID);
    kontaktadres.setOpmerking(SedesTestConstants.OPMERKING);
    kontaktadres.setStartdatum(startdatum);
    kontaktadres.setSubAdres(SedesTestConstants.SUBADRES);
    kontaktadres.setTaal(SedesTestConstants.TAAL.toUpperCase());

    List<Message> result        =
        KontaktadresValidator.valideer(kontaktadres);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeKontaktadresDto1() {
    var           kontaktadres  = new KontaktadresDto();

    kontaktadres.setAdresId(SedesTestConstants.ADRESID);
    kontaktadres.setKontaktadrestype(SedesTestConstants.KONTAKTADTYPE);
    kontaktadres.setKontaktId(SedesTestConstants.KONTAKTID);
    kontaktadres.setStartdatum(startdatum);
    kontaktadres.setTaal(SedesTestConstants.TAAL);

    List<Message> result        =
        KontaktadresValidator.valideer(kontaktadres);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeKontaktadresDto2() {
    var           kontaktadres  = new KontaktadresDto();

    kontaktadres.setAdresId(SedesTestConstants.ADRESID);
    kontaktadres.setEinddatum(einddatum);
    kontaktadres.setKontaktadrestype(SedesTestConstants.KONTAKTADTYPE);
    kontaktadres.setKontaktId(SedesTestConstants.KONTAKTID);
    kontaktadres.setOpmerking(SedesTestConstants.OPMERKING);
    kontaktadres.setStartdatum(startdatum);
    kontaktadres.setSubAdres(SedesTestConstants.SUBADRES);
    kontaktadres.setTaal(SedesTestConstants.TAAL.toUpperCase());

    List<Message> result        =
        KontaktadresValidator.valideer(kontaktadres);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testLegeKontaktadres() {
    var           kontaktadres  = new Kontaktadres();
    List<Message> expResult     = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result        = KontaktadresValidator.valideer(kontaktadres);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testLegeKontaktadresDto() {
    var           kontaktadres  = new KontaktadresDto();
    List<Message> expResult     = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result        = KontaktadresValidator.valideer(kontaktadres);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testNullKontaktadres() {
    Kontaktadres    kontaktadres  = null;
    List<Message>   result        =
        KontaktadresValidator.valideer(kontaktadres);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(Kontaktadres.class.getSimpleName(),
                 result.get(0).getAttribute());
  }

  @Test
  public void testNullKontaktadresDto() {
    KontaktadresDto kontaktadres  = null;
    List<Message>   result        =
        KontaktadresValidator.valideer(kontaktadres);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(KontaktadresDto.class.getSimpleName(),
                 result.get(0).getAttribute());
  }
}
