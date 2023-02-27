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
package eu.debooy.sedes.controller;

import eu.debooy.doosutils.DoosConstants;
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.sedes.Sedes;
import eu.debooy.sedes.form.Landnaam;
import eu.debooy.sedes.form.Quizvraag;
import eu.debooy.sedes.form.Werelddeelnaam;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 * @author Marco de Booij
 */
@Named("sedesQuiz")
@SessionScoped
public class QuizController extends Sedes {
  private static final  long    serialVersionUID  = 1L;

  private static final  String  ERR_GEENANTWOORD  = "error.quiz.geen.antwoord";
  private static final  String  LBL_SCORE         = "label.quiz.score";
  private static final  String  PAR_QUIZVRAGEN    = "sedes.quizvragen";
  private static final  String  TIT_QUIZ1         =
      "sedes.titel.quiz.landenquiz";
  private static final  String  TIT_QUIZ2         =
      "sedes.titel.quiz.landenquiz.per.werelddeel";

  private Integer         goedeAntwoorden;
  private Quizvraag       quizvraag;
  private List<Quizvraag> quizvragen;
  private Integer         vraag;

  public void beantwoordVraag() {
    if (DoosUtils.isBlankOrNull(quizvraag.getAntwoord())) {
      addError(ERR_GEENANTWOORD);
      return;
    }

    quizvragen.set(vraag, quizvraag);
    if (quizvraag.isGoed()) {
      goedeAntwoorden++;
    }
    vraag++;

    redirect(QUIZ_REDIRECT);
  }

  public JSONArray getAntwoorden() {
    var antwoorden  = new JSONArray();

    quizvragen.stream()
              .filter(rij -> DoosUtils.isNotBlankOrNull(rij.getAntwoord()))
              .forEach(rij -> {
      var antwoord  = new JSONObject();
      antwoord.put("antwoord", rij.getAntwoord());
      antwoord.put("goed", rij.isGoed());
      antwoord.put("hoofdstad", rij.getHoofdstad());
      antwoord.put("naam", rij.getNaam());
      antwoorden.add(antwoord);
    });

    return antwoorden;
  }

  public Integer getVraag() {
    return vraag;
  }

  public Collection<Werelddeelnaam> getQuizzen() {
    Set<Werelddeelnaam> werelddelen =
        new TreeSet<>(new Werelddeelnaam.NaamComparator());
    werelddelen
        .addAll(getWerelddeelnaamService()
            .werelddeelnamen(getGebruikersTaal()));

    return werelddelen;
  }

  public Quizvraag getQuizvraag() {
    if (vraag == quizvragen.size()) {
      quizvraag = new Quizvraag();
    } else {
      quizvraag = quizvragen.get(vraag);
    }

    return quizvraag;
  }

  public Integer getGoedeAntwoorden() {
    return goedeAntwoorden;
  }

  public String getQuizResultaat() {
    return MessageFormat.format(getTekst(LBL_SCORE), goedeAntwoorden, vraag);
  }

  private void prepareerVragen(List<Landnaam> landnamen)
      throws NoSuchAlgorithmException {
    var random      = SecureRandom.getInstanceStrong();
    var vragen      = Integer.parseInt(getParameter(PAR_QUIZVRAGEN));

    goedeAntwoorden = 0;
    vraag           = 0;

    if (vragen > landnamen.size()) {
      vragen  = landnamen.size();
    }
    quizvragen      = new ArrayList<>(vragen);

    for (var i = 0; i < vragen; i++) {
      var keuze = random.nextInt(landnamen.size());
      quizvragen.add(new Quizvraag(landnamen.get(keuze)));
      landnamen.remove(keuze);
    }
  }

  public void startQuiz() {
    setSubTitel(getTekst(TIT_QUIZ1));
    List<Landnaam>  landnamen = new ArrayList<>();
    for (var  landnaam :
            getLandnaamService().bestaandeLandnamen(getGebruikersTaal())) {
      if (!DoosUtils.nullToEmpty(landnaam.getHoofdstad()).replace("-", "")
                   .isBlank()) {
        landnamen.add(landnaam);
      }
    }
    try {
      prepareerVragen(landnamen);
      redirect(QUIZ_REDIRECT);
    } catch (NoSuchAlgorithmException | NumberFormatException e) {
      generateExceptionMessage(e);
    }
  }

  public void startQuiz(Long werelddeelId) {
    setSubTitel(
        MessageFormat
            .format(getTekst(TIT_QUIZ2),
                getWerelddeelnaamService()
                    .werelddeelnaam(werelddeelId, getGebruikersTaal())
                    .getNaam()));

    List<Landnaam>  landnamen = new ArrayList<>();
    for (var  landnaam :
            getLandnaamService()
                .bestaandeLandnamenPerWerelddeel(getGebruikersTaal(),
                                                 werelddeelId)) {
      if (!DoosUtils.nullToEmpty(landnaam.getHoofdstad()).replace("-", "")
                   .isBlank()) {
        landnamen.add(landnaam);
      }
    }
    try {
      prepareerVragen(landnamen);
      redirect(QUIZ_REDIRECT);
    } catch (NoSuchAlgorithmException e) {
      generateExceptionMessage(e);
    } catch (NumberFormatException e) {
      addError(DoosConstants.ERR_UNK_PARAM, PAR_QUIZVRAGEN);
    }
  }
}
