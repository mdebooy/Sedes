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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


/**
 * @author Marco de Booij
 */
@Named("sedesQuiz")
@SessionScoped
public class QuizController extends Sedes {
  private static final  long    serialVersionUID  = 1L;

  private Integer         goedeAntwoorden;
  private Quizvraag       quizvraag;
  private List<Quizvraag> quizvragen;
  private Integer         vraag;

  public void beantwoordVraag() {
    if (DoosUtils.isBlankOrNull(quizvraag.getAntwoord())) {
      addError("error.quiz.geen.antwoord");
      return;
    } else {
      quizvragen.set(vraag, quizvraag);
      if (quizvraag.isGoed()) {
        goedeAntwoorden++;
      }
      vraag++;
    }

    redirect(QUIZ_REDIRECT);
  }

  public Collection<Quizvraag> getAntwoorden() {
    Set<Quizvraag> antwoorden  = new HashSet<>();
    for (Quizvraag rij : quizvragen) {
      if (DoosUtils.isNotBlankOrNull(rij.getAntwoord())) {
        antwoorden.add(rij);
      }
    }

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

  public String getQuizResultaat() {
    return MessageFormat.format(getTekst("label.quiz.score"), goedeAntwoorden,
                                vraag);
  }

  private void prepareerVragen(List<Landnaam> landnamen)
      throws NoSuchAlgorithmException {
    var random  = SecureRandom.getInstanceStrong();
    var vragen  = Integer.parseInt(getParameter("sedes.quizvragen"));

    goedeAntwoorden = Integer.valueOf("0");
    vraag           = Integer.valueOf("0");

    for (var i = landnamen.size()-1; i >= 0; i--) {
      String  hoofdstad =
          DoosUtils.nullToEmpty(landnamen.get(i).getHoofdstad());
      if ("-".equals(hoofdstad) || "".equals(hoofdstad)) {
        landnamen.remove(i);
      }
    }
    if (vragen > landnamen.size()) {
      vragen  = landnamen.size();
    }
    quizvragen      = new ArrayList<>(vragen);

    for (int i = 0; i < vragen; i++) {
      int keuze = random.nextInt(landnamen.size());
      quizvragen.add(new Quizvraag(landnamen.get(keuze)));
      landnamen.remove(keuze);
    }
  }

  public void startQuiz() {
    setSubTitel(getTekst("sedes.titel.quiz.landenquiz"));
    List<Landnaam>  landnamen = new ArrayList<>();
        landnamen.addAll(
            getLandnaamService().bestaandeLandnamen(getGebruikersTaal()));
    try {
      prepareerVragen(landnamen);
      redirect(QUIZ_REDIRECT);
    } catch (NoSuchAlgorithmException e) {
      generateExceptionMessage(e);
    }
  }

  public void startQuiz(Long werelddeelId) {
    setSubTitel(
        MessageFormat
            .format(getTekst("sedes.titel.quiz.landenquiz.per.werelddeel"),
                getWerelddeelnaamService()
                    .werelddeelnaam(werelddeelId, getGebruikersTaal())
                    .getWerelddeelnaam()));

    List<Landnaam>  landnamen = new ArrayList<>();
        landnamen.addAll(
            getLandnaamService()
                .bestaandeLandnamenPerWerelddeel(getGebruikersTaal(),
                                                 werelddeelId));
    try {
      prepareerVragen(landnamen);
      redirect(QUIZ_REDIRECT);
    } catch (NoSuchAlgorithmException e) {
      generateExceptionMessage(e);
    }
  }
}
