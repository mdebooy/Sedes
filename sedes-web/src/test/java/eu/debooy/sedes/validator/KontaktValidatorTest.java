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

import eu.debooy.doosutils.Datum;
import eu.debooy.doosutils.DoosConstants;
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.sedes.Sedes;
import eu.debooy.sedes.SedesTestConstants;
import eu.debooy.sedes.domain.KontaktDto;
import eu.debooy.sedes.form.Kontakt;
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
public class KontaktValidatorTest {
  @SuppressWarnings("java:S3008")
  private static  Message ERR_GEBOORTEDATUM;

  private static final  Message ERR_AANSPREEKID     =
      new Message.Builder()
                 .setAttribute(KontaktDto.COL_AANSPREEKID)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{KontaktValidator.LBL_AANSPREEKID, 10})
                 .build();
  private static final  Message ERR_GEBRUIKERSNAAM  =
      new Message.Builder()
                 .setAttribute(KontaktDto.COL_GEBRUIKERSNAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{KontaktValidator.LBL_GEBRUIKERSNAAM,
                                         20})
                 .build();
  private static final  Message ERR_INITIALEN     =
      new Message.Builder()
                 .setAttribute(KontaktDto.COL_INITIALEN)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{KontaktValidator.LBL_INITIALEN,
                                         20})
                 .build();
  private static final  Message ERR_KONTAKTTYPE     =
      new Message.Builder()
                 .setAttribute(KontaktDto.COL_KONTAKTTYPE)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.WRONGVALUE)
                 .setParams(new Object[]{KontaktValidator.LBL_KONTAKTTYPE,
                                         String.format("%s, %s",
                                                    Sedes.TYP_GROEP,
                                                    Sedes.TYP_PERSOON),
                                                    Sedes.TYP_RECHTSPERSOON})
                 .build();
  private static final  Message ERR_NAAM            =
      new Message.Builder()
                 .setAttribute(KontaktDto.COL_NAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{KontaktValidator.LBL_NAAM, 255})
                 .build();
  private static final  Message ERR_PSEUDONIEM      =
      new Message.Builder()
                 .setAttribute(KontaktDto.COL_PSEUDONIEM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{KontaktValidator.LBL_PSEUDONIEM,
                                         255})
                 .build();
  private static final  Message ERR_ROEPNAAM        =
      new Message.Builder()
                 .setAttribute(KontaktDto.COL_ROEPNAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{KontaktValidator.LBL_ROEPNAAM, 255})
                 .build();
  private static final  Message ERR_TAAL            =
      new Message.Builder()
                 .setAttribute(KontaktDto.COL_TAAL)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.FIXLENGTH)
                 .setParams(new Object[]{KontaktValidator.LBL_TAAL, 3})
                 .build();
  private static final  Message ERR_TUSSENVOEGSEL   =
      new Message.Builder()
                 .setAttribute(KontaktDto.COL_TUSSENVOEGSEL)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{KontaktValidator.LBL_TUSSENVOEGSEL,
                                         10})
                 .build();
  private static final  Message ERR_VOORNAAM        =
      new Message.Builder()
                 .setAttribute(KontaktDto.COL_VOORNAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{KontaktValidator.LBL_VOORNAAM, 255})
                 .build();
  private static final  Message REQ_KONTAKTTYPE     =
      new Message.Builder()
                 .setAttribute(KontaktDto.COL_KONTAKTTYPE)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{KontaktValidator.LBL_KONTAKTTYPE})
                 .build();
  private static final  Message REQ_NAAM            =
      new Message.Builder()
                 .setAttribute(KontaktDto.COL_NAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{KontaktValidator.LBL_NAAM})
                 .build();
  private static final  Message REQ_TAAL            =
      new Message.Builder()
                 .setAttribute(KontaktDto.COL_TAAL)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{KontaktValidator.LBL_TAAL})
                 .build();

  private static  Date      morgen;

  @BeforeClass
  public static void setUpClass() {
    Calendar  kalender  = Calendar.getInstance();
    kalender.add(Calendar.DAY_OF_YEAR, 1);
    morgen    = kalender.getTime();
    ERR_GEBOORTEDATUM   =
      new Message.Builder()
                 .setAttribute(KontaktDto.COL_GEBOORTEDATUM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.FUTURE)
                 .setParams(new Object[]{Datum.fromDate(morgen,
                                                        DoosConstants.DATUM)})
                 .build();
  }

  private void setFout(List<Message> expResult) {
    expResult.add(ERR_AANSPREEKID);
    expResult.add(ERR_GEBOORTEDATUM);
    expResult.add(ERR_GEBRUIKERSNAAM);
    expResult.add(ERR_INITIALEN);
    expResult.add(ERR_KONTAKTTYPE);
    expResult.add(ERR_NAAM);
    expResult.add(SedesTestConstants.ERR_OPMERKING);
    expResult.add(ERR_PSEUDONIEM);
    expResult.add(ERR_ROEPNAAM);
    expResult.add(ERR_TAAL);
    expResult.add(ERR_TUSSENVOEGSEL);
    expResult.add(ERR_VOORNAAM);
  }

  private void setLeeg(List<Message> expResult) {
    expResult.add(REQ_KONTAKTTYPE);
    expResult.add(REQ_NAAM);
    expResult.add(REQ_TAAL);
  }

  @Test
  public void testFoutKontakt1() {
    var           kontakt   = new Kontakt();
    List<Message> expResult = new ArrayList<>();

    setFout(expResult);

    kontakt.setAanspreekId(DoosUtils.stringMetLengte(SedesTestConstants.AANSPREEKID,
                                                     11, "X"));
    kontakt.setGeboortedatum(morgen);
    kontakt.setGebruikersnaam(DoosUtils.stringMetLengte(SedesTestConstants.GEBRUIKERSNAAM, 21, "X"));
    kontakt.setInitialen(DoosUtils.stringMetLengte(SedesTestConstants.INITIALEN, 21, "X"));
    kontakt.setKontakttype(SedesTestConstants.KONTAKTTYPEF);
    kontakt.setNaam(DoosUtils.stringMetLengte(SedesTestConstants.KONTAKTNAAM,
                                              256, "X"));
    kontakt.setOpmerking(DoosUtils.stringMetLengte(SedesTestConstants.OPMERKING,
                                              2001, "X"));
    kontakt.setPseudoniem(DoosUtils.stringMetLengte(SedesTestConstants.PSEUDONIEM,
                                              256, "X"));
    kontakt.setRoepnaam(DoosUtils.stringMetLengte(SedesTestConstants.ROEPNAAM,
                                              256, "X"));
    kontakt.setTaal(DoosUtils.stringMetLengte(SedesTestConstants.KONTAKTTAAL,
                                              4, "X"));
    kontakt.setTussenvoegsel(DoosUtils.stringMetLengte(SedesTestConstants.TUSSENVOEGSEL, 11, "X"));
    kontakt.setVoornaam(DoosUtils.stringMetLengte(SedesTestConstants.VOORNAAM,
                                              256, "X"));

    List<Message> result    = KontaktValidator.valideer(kontakt);

    assertEquals(12, result.size());
    assertEquals(expResult.toString(), result.toString());

    kontakt.setTaal(SedesTestConstants.KONTAKTTAAL2);

    result  = KontaktValidator.valideer(kontakt);

    assertEquals(12, result.size());
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testGoedKontakt1() {
    var           kontakt   = new Kontakt();

    kontakt.setKontakttype(SedesTestConstants.KONTAKTTYPE);
    kontakt.setNaam(SedesTestConstants.KONTAKTNAAM);
    kontakt.setTaal(SedesTestConstants.KONTAKTTAAL);

    List<Message> result    = KontaktValidator.valideer(kontakt);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedKontakt2() {
    var           kontakt   = new Kontakt();

    kontakt.setAanspreekId(SedesTestConstants.AANSPREEKID);
    kontakt.setGeboortedatum(new Date());
    kontakt.setGebruikersnaam(SedesTestConstants.GEBRUIKERSNAAM);
    kontakt.setInitialen(SedesTestConstants.INITIALEN);
    kontakt.setKontakttype(Sedes.TYP_PERSOON);
    kontakt.setNaam(SedesTestConstants.KONTAKTNAAM);
    kontakt.setOpmerking(SedesTestConstants.OPMERKING);
    kontakt.setPseudoniem(SedesTestConstants.PSEUDONIEM);
    kontakt.setRoepnaam(SedesTestConstants.ROEPNAAM);
    kontakt.setTaal(SedesTestConstants.KONTAKTTAAL);
    kontakt.setTussenvoegsel(SedesTestConstants.TUSSENVOEGSEL);
    kontakt.setVoornaam(SedesTestConstants.VOORNAAM);

    List<Message> result    = KontaktValidator.valideer(kontakt);

    assertTrue(result.isEmpty());

    kontakt.setKontakttype(Sedes.TYP_GROEP);

    result  = KontaktValidator.valideer(kontakt);

    assertTrue(result.isEmpty());

    kontakt.setKontakttype(Sedes.TYP_RECHTSPERSOON);

    result  = KontaktValidator.valideer(kontakt);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testLeegKontakt() {
    var           kontakt   = new Kontakt();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = KontaktValidator.valideer(kontakt);

    assertEquals(3, result.size());
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testFoutKontaktDto1() {
    var           kontakt   = new KontaktDto();
    List<Message> expResult = new ArrayList<>();

    setFout(expResult);

    kontakt.setAanspreekId(DoosUtils.stringMetLengte(SedesTestConstants.AANSPREEKID,
                                                     11, "X"));
    kontakt.setGeboortedatum(morgen);
    kontakt.setGebruikersnaam(DoosUtils.stringMetLengte(SedesTestConstants.GEBRUIKERSNAAM, 21, "X"));
    kontakt.setInitialen(DoosUtils.stringMetLengte(SedesTestConstants.INITIALEN, 21, "X"));
    kontakt.setKontakttype(SedesTestConstants.KONTAKTTYPEF);
    kontakt.setNaam(DoosUtils.stringMetLengte(SedesTestConstants.KONTAKTNAAM,
                                              256, "X"));
    kontakt.setOpmerking(DoosUtils.stringMetLengte(SedesTestConstants.OPMERKING,
                                              2001, "X"));
    kontakt.setPseudoniem(DoosUtils.stringMetLengte(SedesTestConstants.PSEUDONIEM,
                                              256, "X"));
    kontakt.setRoepnaam(DoosUtils.stringMetLengte(SedesTestConstants.ROEPNAAM,
                                              256, "X"));
    kontakt.setTaal(DoosUtils.stringMetLengte(SedesTestConstants.KONTAKTTAAL,
                                              4, "X"));
    kontakt.setTussenvoegsel(DoosUtils.stringMetLengte(SedesTestConstants.TUSSENVOEGSEL, 11, "X"));
    kontakt.setVoornaam(DoosUtils.stringMetLengte(SedesTestConstants.VOORNAAM,
                                              256, "X"));

    List<Message> result    = KontaktValidator.valideer(kontakt);

    assertEquals(12, result.size());
    assertEquals(expResult.toString(), result.toString());

    kontakt.setTaal(SedesTestConstants.KONTAKTTAAL2);

    result  = KontaktValidator.valideer(kontakt);

    assertEquals(12, result.size());
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testGoedKontaktDto1() {
    var           kontakt   = new KontaktDto();

    kontakt.setKontakttype(SedesTestConstants.KONTAKTTYPE);
    kontakt.setNaam(SedesTestConstants.KONTAKTNAAM);
    kontakt.setTaal(SedesTestConstants.KONTAKTTAAL);

    List<Message> result    = KontaktValidator.valideer(kontakt);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedKontaktDto2() {
    var           kontakt   = new KontaktDto();

    kontakt.setAanspreekId(SedesTestConstants.AANSPREEKID);
    kontakt.setGeboortedatum(new Date());
    kontakt.setGebruikersnaam(SedesTestConstants.GEBRUIKERSNAAM);
    kontakt.setInitialen(SedesTestConstants.INITIALEN);
    kontakt.setKontakttype(Sedes.TYP_PERSOON);
    kontakt.setNaam(SedesTestConstants.KONTAKTNAAM);
    kontakt.setOpmerking(SedesTestConstants.OPMERKING);
    kontakt.setPseudoniem(SedesTestConstants.PSEUDONIEM);
    kontakt.setRoepnaam(SedesTestConstants.ROEPNAAM);
    kontakt.setTaal(SedesTestConstants.KONTAKTTAAL);
    kontakt.setTussenvoegsel(SedesTestConstants.TUSSENVOEGSEL);
    kontakt.setVoornaam(SedesTestConstants.VOORNAAM);

    List<Message> result    = KontaktValidator.valideer(kontakt);

    assertTrue(result.isEmpty());

    kontakt.setKontakttype(Sedes.TYP_GROEP);

    result  = KontaktValidator.valideer(kontakt);

    assertTrue(result.isEmpty());

    kontakt.setKontakttype(Sedes.TYP_RECHTSPERSOON);

    result  = KontaktValidator.valideer(kontakt);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testLeegKontaktDto() {
    var           kontakt   = new KontaktDto();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = KontaktValidator.valideer(kontakt);
    
    assertEquals(3, result.size());
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testNullKontakt() {
    Kontakt       kontakt   = null;
    List<Message> result    = KontaktValidator.valideer(kontakt);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(Kontakt.class.getSimpleName(), result.get(0).getAttribute());
  }

  @Test
  public void testNullKontaktDto() {
    KontaktDto    kontakt   = null;
    List<Message> result    = KontaktValidator.valideer(kontakt);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(KontaktDto.class.getSimpleName(),
                 result.get(0).getAttribute());
  }
}
