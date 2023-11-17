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
import eu.debooy.sedes.Sedes;
import eu.debooy.sedes.TestConstants;
import eu.debooy.sedes.domain.KontaktDto;
import eu.debooy.sedes.form.Kontakt;
import static eu.debooy.sedes.validator.KontaktValidator.LBL_KONTAKTTYPE;
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
  private static final  Message ERR_AANSPREEKID     =
      new Message.Builder()
                 .setAttribute(KontaktDto.COL_AANSPREEKID)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{KontaktValidator.LBL_AANSPREEKID, 10})
                 .build();
  private static final  Message ERR_GEBOORTEDATUM   =
      new Message.Builder()
                 .setAttribute(KontaktDto.COL_GEBOORTEDATUM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.FUTURE)
                 .setParams(new Object[]{KontaktValidator.LBL_GEBOORTEDATUM})
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
                 .setParams(new Object[]{LBL_KONTAKTTYPE,
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
  }

  private void setFout(List<Message> expResult) {
    expResult.add(ERR_AANSPREEKID);
    expResult.add(ERR_GEBOORTEDATUM);
    expResult.add(ERR_GEBRUIKERSNAAM);
    expResult.add(ERR_INITIALEN);
    expResult.add(ERR_KONTAKTTYPE);
    expResult.add(ERR_NAAM);
    expResult.add(TestConstants.ERR_OPMERKING);
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
  public void testValideerFoutKontakt1() {
    var           kontakt   = new Kontakt();
    List<Message> expResult = new ArrayList<>();

    setFout(expResult);

    kontakt.setAanspreekId(DoosUtils.stringMetLengte(TestConstants.AANSPREEKID,
                                                     11, "X"));
    kontakt.setGeboortedatum(morgen);
    kontakt.setGebruikersnaam(
        DoosUtils.stringMetLengte(TestConstants.GEBRUIKERSNAAM, 21, "X"));
    kontakt.setInitialen(
        DoosUtils.stringMetLengte(TestConstants.INITIALEN, 21, "X"));
    kontakt.setKontakttype(TestConstants.KONTAKTTYPEF);
    kontakt.setNaam(DoosUtils.stringMetLengte(TestConstants.KONTAKTNAAM,
                                              256, "X"));
    kontakt.setOpmerking(DoosUtils.stringMetLengte(TestConstants.OPMERKING,
                                              2001, "X"));
    kontakt.setPseudoniem(DoosUtils.stringMetLengte(TestConstants.PSEUDONIEM,
                                              256, "X"));
    kontakt.setRoepnaam(DoosUtils.stringMetLengte(TestConstants.ROEPNAAM,
                                              256, "X"));
    kontakt.setTaal(DoosUtils.stringMetLengte(TestConstants.KONTAKTTAAL,
                                              4, "X"));
    kontakt.setTussenvoegsel(
        DoosUtils.stringMetLengte(TestConstants.TUSSENVOEGSEL, 11, "X"));
    kontakt.setVoornaam(DoosUtils.stringMetLengte(TestConstants.VOORNAAM,
                                              256, "X"));

    List<Message> result    = KontaktValidator.valideer(kontakt);

    assertEquals(12, result.size());
    assertEquals(expResult.toString(), result.toString());

    kontakt.setTaal(TestConstants.KONTAKTTAAL2);

    result  = KontaktValidator.valideer(kontakt);

    assertEquals(12, result.size());
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedKontakt1() {
    var           kontakt   = new Kontakt();

    kontakt.setKontakttype(TestConstants.KONTAKTTYPE);
    kontakt.setNaam(TestConstants.KONTAKTNAAM);
    kontakt.setTaal(TestConstants.KONTAKTTAAL);

    List<Message> result    = KontaktValidator.valideer(kontakt);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testValideerGoedKontakt2() {
    var           kontakt   = new Kontakt();

    kontakt.setAanspreekId(TestConstants.AANSPREEKID);
    kontakt.setGeboortedatum(new Date());
    kontakt.setGebruikersnaam(TestConstants.GEBRUIKERSNAAM);
    kontakt.setInitialen(TestConstants.INITIALEN);
    kontakt.setKontakttype(Sedes.TYP_PERSOON);
    kontakt.setNaam(TestConstants.KONTAKTNAAM);
    kontakt.setOpmerking(TestConstants.OPMERKING);
    kontakt.setPseudoniem(TestConstants.PSEUDONIEM);
    kontakt.setRoepnaam(TestConstants.ROEPNAAM);
    kontakt.setTaal(TestConstants.KONTAKTTAAL);
    kontakt.setTussenvoegsel(TestConstants.TUSSENVOEGSEL);
    kontakt.setVoornaam(TestConstants.VOORNAAM);

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
  public void testValideerLeegKontakt() {
    var           kontakt   = new Kontakt();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = KontaktValidator.valideer(kontakt);

    assertEquals(3, result.size());
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerFoutKontaktDto1() {
    var           kontakt   = new Kontakt();
    List<Message> expResult = new ArrayList<>();

    setFout(expResult);

    kontakt.setAanspreekId(DoosUtils.stringMetLengte(TestConstants.AANSPREEKID,
                                                     11, "X"));
    kontakt.setGeboortedatum(morgen);
    kontakt.setGebruikersnaam(
        DoosUtils.stringMetLengte(TestConstants.GEBRUIKERSNAAM, 21, "X"));
    kontakt.setInitialen(
        DoosUtils.stringMetLengte(TestConstants.INITIALEN, 21, "X"));
    kontakt.setKontakttype(TestConstants.KONTAKTTYPEF);
    kontakt.setNaam(DoosUtils.stringMetLengte(TestConstants.KONTAKTNAAM,
                                              256, "X"));
    kontakt.setOpmerking(DoosUtils.stringMetLengte(TestConstants.OPMERKING,
                                              2001, "X"));
    kontakt.setPseudoniem(DoosUtils.stringMetLengte(TestConstants.PSEUDONIEM,
                                              256, "X"));
    kontakt.setRoepnaam(DoosUtils.stringMetLengte(TestConstants.ROEPNAAM,
                                              256, "X"));
    kontakt.setTaal(DoosUtils.stringMetLengte(TestConstants.KONTAKTTAAL,
                                              4, "X"));
    kontakt.setTussenvoegsel(
        DoosUtils.stringMetLengte(TestConstants.TUSSENVOEGSEL, 11, "X"));
    kontakt.setVoornaam(DoosUtils.stringMetLengte(TestConstants.VOORNAAM,
                                              256, "X"));

    List<Message> result    = KontaktValidator.valideer(kontakt);

    assertEquals(12, result.size());
    assertEquals(expResult.toString(), result.toString());

    kontakt.setTaal(TestConstants.KONTAKTTAAL2);

    result  = KontaktValidator.valideer(kontakt);

    assertEquals(12, result.size());
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedKontaktDto1() {
    var           kontakt   = new Kontakt();

    kontakt.setKontakttype(TestConstants.KONTAKTTYPE);
    kontakt.setNaam(TestConstants.KONTAKTNAAM);
    kontakt.setTaal(TestConstants.KONTAKTTAAL);

    List<Message> result    = KontaktValidator.valideer(kontakt);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testValideerGoedKontaktDto2() {
    var           kontakt   = new Kontakt();

    kontakt.setAanspreekId(TestConstants.AANSPREEKID);
    kontakt.setGeboortedatum(new Date());
    kontakt.setGebruikersnaam(TestConstants.GEBRUIKERSNAAM);
    kontakt.setInitialen(TestConstants.INITIALEN);
    kontakt.setKontakttype(Sedes.TYP_PERSOON);
    kontakt.setNaam(TestConstants.KONTAKTNAAM);
    kontakt.setOpmerking(TestConstants.OPMERKING);
    kontakt.setPseudoniem(TestConstants.PSEUDONIEM);
    kontakt.setRoepnaam(TestConstants.ROEPNAAM);
    kontakt.setTaal(TestConstants.KONTAKTTAAL);
    kontakt.setTussenvoegsel(TestConstants.TUSSENVOEGSEL);
    kontakt.setVoornaam(TestConstants.VOORNAAM);

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
  public void testValideerLeegKontaktDto() {
    var           kontakt   = new KontaktDto();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = KontaktValidator.valideer(kontakt);

    assertEquals(3, result.size());
    assertEquals(expResult.toString(), result.toString());
  }
}
