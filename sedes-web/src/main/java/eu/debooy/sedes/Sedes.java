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

import eu.debooy.doos.component.bean.DoosBean;
import eu.debooy.doosutils.service.JNDI;
import eu.debooy.sedes.service.LandService;
import eu.debooy.sedes.service.LandnaamService;
import eu.debooy.sedes.service.MuntService;
import eu.debooy.sedes.service.PlaatsService;
import eu.debooy.sedes.service.RegioService;
import eu.debooy.sedes.service.WerelddeelService;
import eu.debooy.sedes.service.WerelddeelnaamService;
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

  private transient LandService           landService;
  private transient LandnaamService       landnaamService;
  private transient MuntService           muntService;
  private transient PlaatsService         plaatsService;
  private transient RegioService          regioService;
  private transient WerelddeelService     werelddeelService;
  private transient WerelddeelnaamService werelddeelnaamService;

  public static final String  ADMIN_ROLE              = "sedes-admin";
  public static final String  APPLICATIE_NAAM         = "Sedes";
  public static final String  DD_GEO                  = "geo";
  public static final String  LAND_REDIRECT           = "/landen/land.xhtml";
  public static final String  LANDEN_REDIRECT         = "/landen/landen.xhtml";
  public static final String  LANDNAAM_REDIRECT       =
      "/landen/landnaam.xhtml";
  public static final String  MUNT_REDIRECT           = "/munten/munt.xhtml";
  public static final String  MUNTEN_REDIRECT         = "/munten/munten.xhtml";
  public static final String  PLAATS_REDIRECT         =
      "/plaatsen/plaats.xhtml";
  public static final String  PLAATSEN_REDIRECT       =
      "/plaatsen/plaatsen.xhtml";
  public static final String  QUIZ_REDIRECT           = "/quiz/quiz.xhtml";
  public static final String  QUIZZEN_REDIRECT        = "/quiz/quizzen.xhtml";
  public static final String  REGIO_REDIRECT          = "/regios/regio.xhtml";
  public static final String  REGIOS_REDIRECT         = "/regios/regios.xhtml";
  public static final String  USER_ROLE               = "sedes-user";
  public static final String  VIEW_ROLE               = "sedes-view";
  public static final String  WERELDDEEL_REDIRECT     =
      "/werelddelen/werelddeel.xhtml";
  public static final String  WERELDDEELNAAM_REDIRECT =
      "/werelddelen/werelddeelnaam.xhtml";
  public static final String  WERELDDELEN_REDIRECT    =
      "/werelddelen/werelddelen.xhtml";

  public Sedes() {
    LOGGER.debug("Nieuwe Sedes Sessie geopend.");
    setAdminRole(getExternalContext().isUserInRole(ADMIN_ROLE));
    setApplicatieNaam(APPLICATIE_NAAM);
    setUserRole(getExternalContext().isUserInRole(USER_ROLE));
    setViewRole(getExternalContext().isUserInRole(VIEW_ROLE));
    setPath(getExternalContext().getRequestContextPath());
    if (isAdministrator()) {
      addMenuitem("Dropdown.admin", "menu.administratie");
      addDropdownmenuitem(DD_ADMIN, APP_LOGS_REDIRECT,
          "menu.applicatielogs");
      addDropdownmenuitem(DD_ADMIN, APP_PARAMS_REDIRECT,
          "menu.applicatieparameters");
    }
    addMenuitem("Dropdown.geo", "menu.geografie");
    addDropdownmenuitem(DD_GEO, LANDEN_REDIRECT,      "menu.landen");
    addDropdownmenuitem(DD_GEO, PLAATSEN_REDIRECT,    "menu.plaatsen");
    addDropdownmenuitem(DD_GEO, REGIOS_REDIRECT,      "menu.regios");
    addDropdownmenuitem(DD_GEO, WERELDDELEN_REDIRECT, "menu.werelddelen");
    addMenuitem(MUNTEN_REDIRECT,  "menu.munten");
    addMenuitem(QUIZZEN_REDIRECT, "menu.quizzen");
  }

  protected LandnaamService getLandnaamService() {
    if (null == landnaamService) {
      landnaamService = (LandnaamService)
          new JNDI.JNDINaam().metBean(LandnaamService.class).locate();
    }

    return landnaamService;
  }

  protected LandService getLandService() {
    if (null == landService) {
      landService = (LandService)
          new JNDI.JNDINaam().metBean(LandService.class).locate();
    }

    return landService;
  }

  protected MuntService getMuntService() {
    if (null == muntService) {
      muntService = (MuntService)
          new JNDI.JNDINaam().metBean(MuntService.class).locate();
    }

    return muntService;
  }

  protected PlaatsService getPlaatsService() {
    if (null == plaatsService) {
      plaatsService = (PlaatsService)
          new JNDI.JNDINaam().metBean(PlaatsService.class).locate();
    }

    return plaatsService;
  }

  protected RegioService getRegioService() {
    if (null == regioService) {
      regioService = (RegioService)
          new JNDI.JNDINaam().metBean(RegioService.class).locate();
    }

    return regioService;
  }

  protected WerelddeelnaamService getWerelddeelnaamService() {
    if (null == werelddeelnaamService) {
      werelddeelnaamService = (WerelddeelnaamService)
          new JNDI.JNDINaam().metBean(WerelddeelnaamService.class).locate();
    }

    return werelddeelnaamService;
  }

  protected WerelddeelService getWerelddeelService() {
    if (null == werelddeelService) {
      werelddeelService = (WerelddeelService)
          new JNDI.JNDINaam().metBean(WerelddeelService.class).locate();
    }

    return werelddeelService;
  }
}
