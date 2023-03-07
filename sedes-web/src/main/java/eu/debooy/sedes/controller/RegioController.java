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
import eu.debooy.sedes.domain.RegioDto;
import eu.debooy.sedes.form.Regio;
import eu.debooy.sedes.validator.RegioValidator;
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
@Named("sedesRegio")
@SessionScoped
public class RegioController extends Sedes {
  private static final  long    serialVersionUID  = 1L;
  private static final  Logger  LOGGER            =
      LoggerFactory.getLogger(RegioController.class);

  private static final  String  LBL_REGIO = "label.regio";

  private static final  String  TIT_CREATE    = "sedes.titel.regio.create";
  private static final  String  TIT_RETRIEVE  = "sedes.titel.regio.retrieve";
  private static final  String  TIT_UPDATE    = "sedes.titel.regio.update";

  private Regio     regio;
  private RegioDto  regioDto;

  public void create() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    regio     = new Regio();
    regioDto  = new RegioDto();
    setAktie(PersistenceConstants.CREATE);
    setSubTitel(getTekst(TIT_CREATE));
    redirect(REGIO_REDIRECT);
  }

  public void delete() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var naam    = regio.getNaam();
    try {
      getRegioService().delete(regio.getRegioId());
      regio     = new Regio();
      regioDto  = new RegioDto();
      addInfo(PersistenceConstants.DELETED, naam);
      redirect(REGIOS_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, naam);
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public Regio getRegio() {
    return regio;
  }

  public void retrieve() {
    if (!isGerechtigd()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var ec      = FacesContext.getCurrentInstance().getExternalContext();

    if (!ec.getRequestParameterMap().containsKey(RegioDto.COL_REGIOID)) {
      addError(ComponentsConstants.GEENPARAMETER, RegioDto.COL_REGIOID);
      return;
    }

    var regioId = Long.valueOf(ec.getRequestParameterMap()
                                 .get(RegioDto.COL_REGIOID));

    try {
      regioDto  = getRegioService().regio(regioId);
      regio     = new Regio(regioDto);
      setAktie(PersistenceConstants.RETRIEVE);
      setSubTitel(getTekst(TIT_RETRIEVE));
      redirect(REGIO_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, LBL_REGIO);
    }
  }

  public void save() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var messages  = RegioValidator.valideer(regio);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    var naam  = regio.getNaam();
    try {
      switch (getAktie().getAktie()) {
        case PersistenceConstants.CREATE:
          regio.persist(regioDto);
          getRegioService().save(regioDto);
          regio.setRegioId(regioDto.getRegioId());
          addInfo(PersistenceConstants.CREATED, naam);
          update();
          break;
        case PersistenceConstants.UPDATE:
          regio.persist(regioDto);
          getRegioService().save(regioDto);
          addInfo(PersistenceConstants.UPDATED, naam);
          break;
        default:
          addError(ComponentsConstants.WRONGREDIRECT, getAktie().getAktie());
          break;
      }
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, naam);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, naam);
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public Collection<SelectItem> selectRegios() {
    return getRegioService().selectRegios();
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
