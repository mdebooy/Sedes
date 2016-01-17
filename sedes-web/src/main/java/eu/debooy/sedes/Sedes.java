/**
 * Copyright 2016 Marco de Booij
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
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
package eu.debooy.sedes;

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.bean.DoosBean;
import eu.debooy.doosutils.service.JNDI;
import eu.debooy.sedes.form.Land;
import eu.debooy.sedes.form.Landnaam;
import eu.debooy.sedes.form.Quizvraag;
import eu.debooy.sedes.service.LandService;
import eu.debooy.sedes.service.LandnaamService;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Named("sedes")
@SessionScoped
public class Sedes extends DoosBean {
  private static final  long    serialVersionUID  = 1L;
  private static final  Logger  LOGGER            =
      LoggerFactory.getLogger(Sedes.class);

  private Integer   goedeAntwoorden;
  private Land      land;
  private Landnaam  landnaam;
  private Quizvraag quizvraag;
  private Integer   vraag;

  private List<Quizvraag> quizvragen;

  private transient LandService     landService;
  private transient LandnaamService landnaamService;

  public static final String  ADMIN_ROLE            = "sedes-admin";
  public static final String  APPLICATIE_NAAM       = "Sedes";
  public static final String  LAND_REDIRECT         = "/landen/land.jsf";
  public static final String  LANDEN_REDIRECT       = "/landen/landen.jsf";
  public static final String  QUIZ_REDIRECT         = "/quiz/quiz.jsf";
  public static final String  USER_ROLE             = "sedes-user";

  public Sedes() {
    setAdminRole(getExternalContext().isUserInRole(ADMIN_ROLE));
    setApplicatieNaam(APPLICATIE_NAAM);
    setUserRole(getExternalContext().isUserInRole(USER_ROLE));
    LOGGER.debug("Nieuwe Sedes Sessie geopend.");
  }

  // Landen
  public void beantwoordVraag() {
    if (DoosUtils.isBlankOrNull(quizvraag.getAntwoord())) {
      addError("error.quiz.geen.antwoord");
    } else {
      quizvragen.set(vraag, quizvraag);
      if (quizvraag.isGoed()) {
        goedeAntwoorden++;
      }
      vraag++;
    }

    redirect(QUIZ_REDIRECT);
  }

  /**
   * Zet de Land die gevraagd is klaar.
   * 
   * @param String rang
   */
  public void bekijkLand(Long landId) {
    land  = new Land(getLandService().land(landId));
    setAktie(PersistenceConstants.RETRIEVE);
    setSubTitel(getLandnaamService().landnaam(land.getLandId(),
                                              getGebruikersTaal())
                                    .getLandnaam());
    redirect(LAND_REDIRECT);
  }

  /**
   * Geef alle beantwoorde Quizvragen
   * 
   * @return Collection<Quizvraag>
   */
  public Collection<Quizvraag> getAntwoorden() {
    Set<Quizvraag> antwoorden  = new HashSet<Quizvraag>();
    for (Quizvraag rij : quizvragen) {
      if (DoosUtils.isNotBlankOrNull(rij.getAntwoord())) {
        antwoorden.add(rij);
      }
    }

    return antwoorden;
  }

  /**
   * Geef het geselecteerde land.
   * 
   * @return Land
   */
  public Land getLand() {
    return land;
  }

  /**
   * Geef de LandnaamService. Als die nog niet gekend is haal het dan op.
   * 
   * @return LandnaamService
   */
  private LandnaamService getLandnaamService() {
    if (null == landnaamService) {
      landnaamService = (LandnaamService)
          new JNDI.JNDINaam().metBean(LandnaamService.class).locate();
    }

    return landnaamService;
  }

  /**
   * Geef de LandService. Als die nog niet gekend is haal het dan op.
   * 
   * @return LandService
   */
  private LandService getLandService() {
    if (null == landService) {
      landService = (LandService)
          new JNDI.JNDINaam().metBean(LandService.class).locate();
    }

    return landService;
  }

  /**
   * Geef de lijst met landen.
   * 
   * @return Collection<Land> met Land objecten.
   */
  public Collection<Land> getLanden() {
    return getLandService().lijst();
  }

  /**
   * Geef de lijst met landnamen voor 1 land.
   * 
   * @param Long landId
   * @return Collection<Landnaam> met Landnaam objecten.
   */
  public Collection<Landnaam> getLandnamen(Long landId) {
    return getLandnaamService().lijst(landId);
  }

  /**
   * Geef het nummer van de vraag.
   * 
   * @return Integer
   */
  public Integer getVraag() {
    return vraag;
  }

  /**
   * Geef de volgende vraag.
   * 
   * @return Quizvraag
   */
  public Quizvraag getQuizvraag() {
    if (vraag == quizvragen.size()) {
      quizvraag = new Quizvraag();
    } else {
      quizvraag = quizvragen.get(vraag);
    }

    return quizvraag;
  }

  /**
   * Geef het resultaat in leesbare tekst.
   * 
   * @return String
   */
  public String getQuizResultaat() {
    return MessageFormat.format(getTekst("label.quiz.score"), goedeAntwoorden,
                                vraag);
  }

  /**
   * Start de Quiz
   */
  public void startQuiz() {
    Random  random  = new Random();
    int     vragen  = Integer.parseInt(getParameter("sedes.quizvragen"));

    goedeAntwoorden = Integer.valueOf("0");
    vraag           = Integer.valueOf("0");
    List<Landnaam>  landnamen = new ArrayList<Landnaam>();
        landnamen.addAll(
            getLandnaamService().bestaandeLandnamen(getGebruikersTaal()));
    if (vragen > landnamen.size()) {
      vragen  = landnamen.size();
    }
    quizvragen      = new ArrayList<Quizvraag>(vragen);

    for (int i=0 ; i<vragen; i++) {
      int keuze = random.nextInt(landnamen.size());
      quizvragen.add(new Quizvraag(landnamen.get(keuze)));
      landnamen.remove(keuze);
    }

    redirect(QUIZ_REDIRECT);
  }

  /**
   * Start de Quiz
   */
  public void startQuiz(Long werelddeel) {
    Random  random  = new Random();
    int     vragen  = Integer.parseInt(getParameter("sedes.quizvragen"));

    goedeAntwoorden = Integer.valueOf("0");
    vraag           = Integer.valueOf("0");
    List<Landnaam>  landnamen = new ArrayList<Landnaam>();
        landnamen.addAll(
            getLandnaamService()
                .bestaandeLandnamenPerWerelddeel(getGebruikersTaal(),
                                                 werelddeel));
    if (vragen > landnamen.size()) {
      vragen  = landnamen.size();
    }
    quizvragen      = new ArrayList<Quizvraag>(vragen);

    for (int i=0 ; i<vragen; i++) {
      int keuze = random.nextInt(landnamen.size());
      quizvragen.add(new Quizvraag(landnamen.get(keuze)));
      landnamen.remove(keuze);
    }

    redirect(QUIZ_REDIRECT);
  }
}
