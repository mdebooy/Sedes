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

package eu.debooy.sedes.controller;

import eu.debooy.doosutils.ComponentsConstants;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import eu.debooy.sedes.Sedes;
import eu.debooy.sedes.domain.PlaatsDto;
import eu.debooy.sedes.form.Plaats;
import eu.debooy.sedes.validator.PlaatsValidator;
import java.util.Collection;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Named("sedesPlaats")
@SessionScoped
public class PlaatsController extends Sedes {
  private static final  long    serialVersionUID  = 1L;
  private static final  Logger  LOGGER            =
      LoggerFactory.getLogger(PlaatsController.class);

  private static final  String  LBL_PLAATS    = "label.plaats";
  private static final  String  TIT_CREATE    = "sedes.titel.plaats.create";
  private static final  String  TIT_RETRIEVE  = "sedes.titel.plaats.retrieve";
  private static final  String  TIT_UPDATE    = "sedes.titel.plaats.update";

  private Plaats     plaats;
  private PlaatsDto  plaatsDto;

  public void create() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    plaats    = new Plaats();
    plaatsDto = new PlaatsDto();
    setAktie(PersistenceConstants.CREATE);
    setSubTitel(getTekst(TIT_CREATE));
    redirect(REGIO_REDIRECT);
  }

  public void delete() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var plaatsnaam  = plaats.getPlaatsnaam();
    try {
      getPlaatsService().delete(plaats.getPlaatsId());
      plaats        = new Plaats();
      plaatsDto     = new PlaatsDto();
      addInfo(PersistenceConstants.DELETED, plaatsnaam);
      redirect(REGIOS_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, plaatsnaam);
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public Plaats getPlaats() {
    return plaats;
  }

  public void retrieve() {
    if (!isGerechtigd()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var ec      = FacesContext.getCurrentInstance().getExternalContext();

    if (!ec.getRequestParameterMap().containsKey(PlaatsDto.COL_REGIOID)) {
      addError(ComponentsConstants.GEENPARAMETER, PlaatsDto.COL_REGIOID);
      return;
    }

    var plaatsId  = Long.valueOf(ec.getRequestParameterMap()
                                   .get(PlaatsDto.COL_REGIOID));

    try {
      plaatsDto  = getPlaatsService().plaats(plaatsId);
      plaats     = new Plaats(plaatsDto);
      setAktie(PersistenceConstants.RETRIEVE);
      setSubTitel(getTekst(TIT_RETRIEVE));
      redirect(REGIO_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, LBL_PLAATS);
    }
  }

  public void save() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var messages  = PlaatsValidator.valideer(plaats);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    var plaatsnaam  = plaats.getPlaatsnaam();
    try {
      switch (getAktie().getAktie()) {
        case PersistenceConstants.CREATE:
          plaats.persist(plaatsDto);
          getPlaatsService().save(plaatsDto);
          plaats.setPlaatsId(plaatsDto.getPlaatsId());
          addInfo(PersistenceConstants.CREATED, plaatsnaam);
          update();
          break;
        case PersistenceConstants.UPDATE:
          plaats.persist(plaatsDto);
          getPlaatsService().save(plaatsDto);
          addInfo(PersistenceConstants.UPDATED, plaatsnaam);
          break;
        default:
          addError(ComponentsConstants.WRONGREDIRECT, getAktie().getAktie());
          break;
      }
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, plaatsnaam);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, plaatsnaam);
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public Collection<SelectItem> selectPlaatsen() {
    return getPlaatsService().selectPlaatsen();
  }

  public void update() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    setAktie(PersistenceConstants.UPDATE);
    setSubTitel(getTekst(TIT_UPDATE));
  }
}
