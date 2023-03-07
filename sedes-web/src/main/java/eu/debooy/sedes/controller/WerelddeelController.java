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

import eu.debooy.doosutils.ComponentsConstants;
import eu.debooy.doosutils.DoosConstants;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import eu.debooy.sedes.Sedes;
import eu.debooy.sedes.domain.WerelddeelDto;
import eu.debooy.sedes.domain.WerelddeelnaamDto;
import eu.debooy.sedes.form.Werelddeel;
import eu.debooy.sedes.form.Werelddeelnaam;
import eu.debooy.sedes.validator.WerelddeelnaamValidator;
import java.util.Collection;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Named("sedesWerelddeel")
@SessionScoped
public class WerelddeelController extends Sedes {
  private static final  long    serialVersionUID  = 1L;
  private static final  Logger  LOGGER            =
      LoggerFactory.getLogger(WerelddeelController.class);

  private static final  String  DTIT_CREATE =
      "sedes.titel.werelddeelnaam.create";
  private static final  String  DTIT_UPDATE =
      "sedes.titel.werelddeelnaam.update";

  private static final  String  LBL_WERELDDEEL      = "label.land";
  private static final  String  LBL_WERELDDEELNAAM  = "label.landnaam";

  private static final  String  TIT_CREATE  = "sedes.titel.werelddeel.create";
  private static final  String  TIT_UPDATE  = "sedes.titel.werelddeel.update";

  private Werelddeel        werelddeel;
  private WerelddeelDto     werelddeelDto;
  private Werelddeelnaam    werelddeelnaam;

  public void create() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    werelddeel    = new Werelddeel();
    werelddeelDto = new WerelddeelDto();
    setAktie(PersistenceConstants.CREATE);
    setSubTitel(getTekst(TIT_CREATE));
    redirect(WERELDDEEL_REDIRECT);
  }

  public void createDetail() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    werelddeelnaam    = new Werelddeelnaam();
    setDetailAktie(PersistenceConstants.CREATE);
    setDetailSubTitel(getTekst(DTIT_CREATE));
    redirect(WERELDDEELNAAM_REDIRECT);
  }

  public void delete() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var werelddeelId  = werelddeel.getWerelddeelId();
    var naam          = i18nWerelddeelnaam(werelddeelId);

    try {
      getWerelddeelService().delete(werelddeelId);
      werelddeel      = new Werelddeel();
      werelddeelDto   = new WerelddeelDto();
      addInfo(PersistenceConstants.DELETED, naam);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, naam);
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public void deleteDetail() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var taalKode      = werelddeelnaam.getTaal();
    try {
      werelddeelDto.removeNaam(taalKode);
      getWerelddeelService().save(werelddeelDto);
      werelddeelnaam  = new Werelddeelnaam();
      addInfo(PersistenceConstants.DELETED, "'" + taalKode + "'");
      werelddeelnaam  = new Werelddeelnaam();
      redirect(WERELDDEEL_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, taalKode);
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public Werelddeel getWerelddeel() {
    return werelddeel;
  }

  public Werelddeelnaam getWerelddeelnaam() {
    return werelddeelnaam;
  }

  public JSONArray getWerelddeelnamen() {
    var werelddeelnamen = new JSONArray();

    werelddeelDto.getWerelddeelnamen()
                 .forEach(rij -> werelddeelnamen.add(rij.toJSON()));

    return werelddeelnamen;
  }

  public String i18nWerelddeelnaam(Long werelddeelId) {
    String  i18nWerelddeelnaam;
    try {
      i18nWerelddeelnaam  =
        getWerelddeelnaamService()
            .werelddeelnaam(werelddeelId, getGebruikersTaal()).getNaam();
    } catch(ObjectNotFoundException e) {
      try {
        i18nWerelddeelnaam  =
            getWerelddeelnaamService()
                .werelddeelnaam(werelddeelId, getDefTaal()).getNaam();
      } catch(ObjectNotFoundException ex) {
        i18nWerelddeelnaam  = DoosConstants.NA;
      }
    }

    return i18nWerelddeelnaam;
  }

  public void retrieve() {
    if (!isGerechtigd()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var ec            = FacesContext.getCurrentInstance().getExternalContext();

    if (!ec.getRequestParameterMap()
           .containsKey(WerelddeelDto.COL_WERELDDEELID)) {
      addError(ComponentsConstants.GEENPARAMETER,
               WerelddeelDto.COL_WERELDDEELID);
      return;
    }

    var werelddeelId  = Long.valueOf(ec.getRequestParameterMap()
                                       .get(WerelddeelDto.COL_WERELDDEELID));

    try {
      werelddeelDto = getWerelddeelService().werelddeel(werelddeelId);
      werelddeel    = new Werelddeel(werelddeelDto);
      setAktie(PersistenceConstants.RETRIEVE);
      setSubTitel(werelddeelDto.getWerelddeelnaam(getGebruikersTaal())
                               .getNaam());
      redirect(WERELDDEEL_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, LBL_WERELDDEEL);
    }
  }

  public void retrieveDetail() {
    if (!isGerechtigd()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var ec    = FacesContext.getCurrentInstance().getExternalContext();

    if (!ec.getRequestParameterMap().containsKey(WerelddeelnaamDto.COL_TAAL)) {
      addError(ComponentsConstants.GEENPARAMETER, WerelddeelnaamDto.COL_TAAL);
      return;
    }

    try {
      werelddeelnaam  =
          new Werelddeelnaam(
            werelddeelDto.getWerelddeelnaam(
                ec.getRequestParameterMap().get(WerelddeelnaamDto.COL_TAAL)));
      setDetailAktie(PersistenceConstants.UPDATE);
      setDetailSubTitel(getTekst(DTIT_UPDATE));
      redirect(WERELDDEELNAAM_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, LBL_WERELDDEELNAAM);
    }
  }

  public void save() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    try {
      switch (getAktie().getAktie()) {
        case PersistenceConstants.CREATE:
          werelddeel.persist(werelddeelDto);
          getWerelddeelService().save(werelddeelDto);
          werelddeel.setWerelddeelId(werelddeelDto.getWerelddeelId());
          addInfo(PersistenceConstants.CREATED, "");
          update();
          break;
        case PersistenceConstants.UPDATE:
          werelddeel.persist(werelddeelDto);
          getWerelddeelService().save(werelddeelDto);
          addInfo(PersistenceConstants.UPDATED, "");
          break;
        default:
          addError(ComponentsConstants.WRONGREDIRECT, getAktie().getAktie());
          break;
      }
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, "");
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, "");
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public void saveDetail() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var messages  = WerelddeelnaamValidator.valideer(werelddeelnaam);

    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    try {
      var werelddeelnaamDto = new WerelddeelnaamDto();
      switch (getDetailAktie().getAktie()) {
        case PersistenceConstants.CREATE:
          werelddeelnaam.persist(werelddeelnaamDto);
          werelddeelDto.addNaam(werelddeelnaamDto);
          getWerelddeelService().save(werelddeelDto);
          addInfo(PersistenceConstants.CREATED,
                  "'" + werelddeelnaam.getTaal() + "'");
          break;
        case PersistenceConstants.UPDATE:
          werelddeelnaam.persist(werelddeelnaamDto);
          werelddeelDto.addNaam(werelddeelnaamDto);
          getWerelddeelService().save(werelddeelDto);
          addInfo(PersistenceConstants.UPDATED,
                  "'" + werelddeelnaam.getTaal() + "'");
          break;
        default:
          addError(ComponentsConstants.WRONGREDIRECT, getAktie().getAktie()) ;
          break;
      }
      setSubTitel(getTekst(TIT_UPDATE,
                           werelddeelDto.getWerelddeelnaam(getGebruikersTaal())
                                        .getNaam()));
      redirect(WERELDDEEL_REDIRECT);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, werelddeelnaam.getTaal());
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, werelddeelnaam.getTaal());
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public Collection<SelectItem> selectWerelddeelnamen() {
    return getWerelddeelnaamService()
              .selectWerelddeelnamen(getGebruikersTaal());
  }

  public void update() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    setAktie(PersistenceConstants.UPDATE);
    setSubTitel(getTekst(TIT_UPDATE,
                         werelddeelDto.getWerelddeelnaam(getGebruikersTaal())
                                      .getNaam()));
  }
}
