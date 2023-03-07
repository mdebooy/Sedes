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
import eu.debooy.sedes.domain.MuntDto;
import eu.debooy.sedes.form.Munt;
import eu.debooy.sedes.validator.MuntValidator;
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
@Named("sedesMunt")
@SessionScoped
public class MuntController extends Sedes {
  private static final  long    serialVersionUID  = 1L;
  private static final  Logger  LOGGER            =
      LoggerFactory.getLogger(MuntController.class);

  private static final  String  LBL_MUNT  = "label.munt";

  private static final  String  PAR_DECIMALEN = "sedes.munt.decimalen";

  private static final  String  TIT_CREATE    = "sedes.titel.munt.create";
  private static final  String  TIT_RETRIEVE  = "sedes.titel.munt.retrieve";
  private static final  String  TIT_UPDATE    = "sedes.titel.munt.update";

  private Munt    munt;
  private MuntDto muntDto;

  public void create() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    munt    = new Munt();
    muntDto = new MuntDto();
    try {
      munt.setDecimalen(Integer.valueOf(getParameter(PAR_DECIMALEN)));
      munt.persist(muntDto);
    } catch (NumberFormatException e) {
      // Geen default waarde
    }
    setAktie(PersistenceConstants.CREATE);
    setSubTitel(getTekst(TIT_CREATE));
    redirect(MUNT_REDIRECT);
  }

  public void delete() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var naam  = munt.getNaam();
    try {
      getMuntService().delete(munt.getMuntId());
      munt    = new Munt();
      muntDto = new MuntDto();
      addInfo(PersistenceConstants.DELETED, naam);
      redirect(MUNTEN_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, naam);
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public Munt getMunt() {
    return munt;
  }

  public Munt munt(Long muntId) {
    try {
      return new Munt(getMuntService().munt(muntId));
    } catch (ObjectNotFoundException e) {
      var valuta  = new Munt();
      valuta.setNaam(getTekst(PersistenceConstants.NOTFOUND, muntId));
      return valuta;
    }
  }

  public void retrieve() {
    if (!isGerechtigd()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var ec      = FacesContext.getCurrentInstance().getExternalContext();

    if (!ec.getRequestParameterMap().containsKey(MuntDto.COL_MUNTID)) {
      addError(ComponentsConstants.GEENPARAMETER, MuntDto.COL_MUNTID);
      return;
    }

    var muntId  = Long.valueOf(ec.getRequestParameterMap()
                                 .get(MuntDto.COL_MUNTID));

    try {
      muntDto = getMuntService().munt(muntId);
      munt    = new Munt(muntDto);
      setAktie(PersistenceConstants.RETRIEVE);
      setSubTitel(getTekst(TIT_RETRIEVE));
      redirect(MUNT_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, LBL_MUNT);
    }
  }

  public void save() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var messages  = MuntValidator.valideer(munt);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    var naam  = munt.getNaam();
    try {
      switch (getAktie().getAktie()) {
        case PersistenceConstants.CREATE:
          munt.persist(muntDto);
          getMuntService().save(muntDto);
          munt.setMuntId(muntDto.getMuntId());
          addInfo(PersistenceConstants.CREATED, naam);
          update();
          break;
        case PersistenceConstants.UPDATE:
          munt.persist(muntDto);
          getMuntService().save(muntDto);
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

  public Collection<SelectItem> selectMunten() {
    return getMuntService().selectMunten();
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
