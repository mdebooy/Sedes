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
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import eu.debooy.sedes.Sedes;
import eu.debooy.sedes.domain.WerelddeelDto;
import eu.debooy.sedes.domain.WerelddeelnaamDto;
import eu.debooy.sedes.form.Werelddeel;
import eu.debooy.sedes.form.Werelddeelnaam;
import eu.debooy.sedes.validator.WerelddeelValidator;
import eu.debooy.sedes.validator.WerelddeelnaamValidator;
import java.util.Collection;
import java.util.HashSet;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;
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
  private static final  String  TIT_UPDATE  = "sedes.titel.werelddeel.update";

  private Werelddeel        werelddeel;
  private WerelddeelDto     werelddeelDto;
  private Werelddeelnaam    werelddeelnaam;
  private WerelddeelnaamDto werelddeelnaamDto;

  public void create() {
    werelddeelDto = new WerelddeelDto();
    try {
      getWerelddeelService().save(werelddeelDto);
      addInfo(PersistenceConstants.CREATED, "");
      setAktie(PersistenceConstants.RETRIEVE);
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

  public void createWerelddeelnaam() {
    werelddeelnaamDto = new WerelddeelnaamDto();
    werelddeelnaam    = new Werelddeelnaam();
    setDetailAktie(PersistenceConstants.CREATE);
    setDetailSubTitel(DTIT_CREATE);
    redirect(WERELDDEELNAAM_REDIRECT);
  }

  public void delete(Long werelddeelId) {
    String  naam  = i18nWerelddeelnaam(werelddeelId);

    try {
      getWerelddeelService().delete(werelddeelId);
      addInfo(PersistenceConstants.DELETED, naam);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, naam);
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public void deleteWerelddeelnaam(String taalKode) {
    try {
      werelddeelDto.removeNaam(taalKode);
      getWerelddeelService().save(werelddeelDto);
      addInfo(PersistenceConstants.DELETED, "'" + taalKode + "'");
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

  public Collection<Werelddeelnaam> getWerelddeelnamen() {
    Collection<Werelddeelnaam>  werelddeelnamen = new HashSet<>();

    for (WerelddeelnaamDto rij : werelddeelDto.getWerelddeelnamen()) {
      werelddeelnamen.add(new Werelddeelnaam(rij));
    }

    return werelddeelnamen;
  }

  public Collection<Werelddeel> getWerelddelen() {
    return getWerelddeelService().query();
  }

  public void save() {
    var messages  = WerelddeelValidator.valideer(werelddeel);

    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    try {
      werelddeel.persist(werelddeelDto);
      getWerelddeelService().save(werelddeelDto);
      switch (getAktie().getAktie()) {
        case PersistenceConstants.CREATE:
          werelddeel.setWerelddeelId(werelddeelDto.getWerelddeelId());
          addInfo(PersistenceConstants.CREATED, "");
          break;
        case PersistenceConstants.UPDATE:
          addInfo(PersistenceConstants.UPDATED, "");
          break;
        default:
          addError(ComponentsConstants.WRONGREDIRECT, getAktie().getAktie());
          break;
      }
      setAktie(PersistenceConstants.RETRIEVE);
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

  public void saveWerelddeelnaam() {
    var messages  = WerelddeelnaamValidator.valideer(werelddeelnaam);

    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    try {
      werelddeelnaamDto = new WerelddeelnaamDto();
      werelddeelnaam.persist(werelddeelnaamDto);
      werelddeelDto.addNaam(werelddeelnaamDto);
      getWerelddeelService().save(werelddeelDto);
      switch (getAktie().getAktie()) {
        case PersistenceConstants.CREATE:
          addInfo(PersistenceConstants.CREATED,
                  "'" + werelddeelnaam.getTaal() + "'");
          break;
        case PersistenceConstants.UPDATE:
          addInfo(PersistenceConstants.UPDATED,
                  "'" + werelddeelnaam.getTaal() + "'");
          break;
        default:
          addError(ComponentsConstants.WRONGREDIRECT, getAktie().getAktie()) ;
          break;
      }
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

  public String i18nWerelddeelnaam(Long werelddeelId) {
    String  i18nWerelddeelnaam  =
        getWerelddeelnaamService()
            .werelddeelnaam(werelddeelId, getGebruikersTaal())
            .getWerelddeelnaam();
    if (DoosUtils.isBlankOrNull(i18nWerelddeelnaam)) {
      i18nWerelddeelnaam  =
          getWerelddeelnaamService()
              .werelddeelnaam(werelddeelId, getParameter("default.taal"))
              .getWerelddeelnaam();
    }

    return i18nWerelddeelnaam;
  }

  public void retrieve(Long werelddeelId) {
    werelddeelDto = getWerelddeelService().werelddeel(werelddeelId);
    werelddeel    = new Werelddeel(werelddeelDto);
    setAktie(PersistenceConstants.RETRIEVE);
    redirect(WERELDDEEL_REDIRECT);
  }

  public void update(Long werelddeelId) {
    werelddeelDto = getWerelddeelService().werelddeel(werelddeelId);
    werelddeel    = new Werelddeel(werelddeelDto);
    setAktie(PersistenceConstants.UPDATE);
    setSubTitel(TIT_UPDATE);
    redirect(WERELDDEEL_REDIRECT);
  }

  public void updateWerelddeelnaam(String taal) {
    werelddeelnaamDto = werelddeelDto.getWerelddeelnaam(taal);
    werelddeelnaam    = new Werelddeelnaam(werelddeelnaamDto);
    setDetailAktie(PersistenceConstants.UPDATE);
    setDetailSubTitel(DTIT_UPDATE);
    redirect(WERELDDEELNAAM_REDIRECT);
  }
}
