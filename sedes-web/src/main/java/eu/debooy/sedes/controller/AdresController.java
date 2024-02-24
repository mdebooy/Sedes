/*
 * Copyright (c) 2024 Marco de Booij
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
import eu.debooy.sedes.domain.AdresDto;
import eu.debooy.sedes.form.Adres;
import eu.debooy.sedes.validator.AdresValidator;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Named("sedesAdres")
@SessionScoped
public class AdresController extends Sedes {
  private static final  long    serialVersionUID  = 1L;
  private static final  Logger  LOGGER            =
      LoggerFactory.getLogger(AdresController.class);

  private static final  String  LBL_ADRES = "label.adres";

  private static final  String  TIT_CREATE  = "sedes.titel.adres.create";
  private static final  String  TIT_UPDATE  = "sedes.titel.adres.update";

  private Adres     adres;
  private AdresDto  adresDto;

  public void create() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    adres     = new Adres();
    adresDto  = new AdresDto();

    setAktie(PersistenceConstants.CREATE);
    setSubTitel(getTekst(TIT_CREATE));
    redirect(ADRES_REDIRECT);
  }

  public void delete() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    try {
      getAdresService().delete(adresDto);
      adres      = new Adres();
      adresDto   = new AdresDto();
      addInfo(PersistenceConstants.DELETED, adresDto.getAdresdata());
      redirect(ADRESSEN_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, adresDto.getAdresdata());
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public Adres getAdres() {
    return adres;
  }

  public void retrieve() {
    if (!isGerechtigd()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var ec      = FacesContext.getCurrentInstance().getExternalContext();

    if (!ec.getRequestParameterMap().containsKey(AdresDto.COL_ADRESID)) {
      addError(ComponentsConstants.GEENPARAMETER, AdresDto.COL_ADRESID);
      return;
    }

    var adresId  = Long.valueOf(ec.getRequestParameterMap()
                                  .get(AdresDto.COL_ADRESID));

    try {
      adresDto = getAdresService().adres(adresId);
      adres    = new Adres(adresDto);
      setAktie(PersistenceConstants.RETRIEVE);
      setSubTitel(adres.getAdresdata());
      redirect(ADRES_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, LBL_ADRES);
    }
  }

  public void save() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var messages  = AdresValidator.valideer(adres);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    var naam  = adres.getAdresdata();
    try {
      switch (getAktie().getAktie()) {
        case PersistenceConstants.CREATE:
          adres.persist(adresDto);
          getAdresService().save(adresDto);
          adres.setAdresId(adresDto.getAdresId());
          addInfo(PersistenceConstants.CREATED, naam);
          update();
          break;
        case PersistenceConstants.UPDATE:
          adres.persist(adresDto);
          getAdresService().save(adresDto);
          addInfo(PersistenceConstants.UPDATED, naam);
          update();
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

  public void update() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    setAktie(PersistenceConstants.UPDATE);
    setSubTitel(getTekst(TIT_UPDATE, adresDto.getAdresdata()));
  }
}
