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

import eu.debooy.doos.model.I18nSelectItem;
import eu.debooy.doosutils.ComponentsConstants;
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import eu.debooy.sedes.Sedes;
import eu.debooy.sedes.domain.KontaktDto;
import eu.debooy.sedes.form.Kontakt;
import eu.debooy.sedes.validator.KontaktValidator;
import java.util.Collection;
import java.util.LinkedList;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Named("sedesKontakt")
@SessionScoped
public class KontaktController extends Sedes {
  private static final  long    serialVersionUID  = 1L;
  private static final  Logger  LOGGER            =
      LoggerFactory.getLogger(KontaktController.class);

  private static final  String  AANSPREEKTITEL  = "sedes.aanspreektitel";
  private static final  String  KONTAKTTYPE     = "sedes.kontakt.type";

  private static final  String  LBL_KONTAKT = "label.kontakt";

  private static final  String  TIT_CREATE  = "sedes.titel.kontakt.create";
  private static final  String  TIT_UPDATE  = "sedes.titel.kontakt.update";

  private Kontakt     kontakt;
  private KontaktDto  kontaktDto;

  public String aanspreektitel(String type) {
    if (DoosUtils.isBlankOrNull(type)) {
      return "";
    }

    return getTekst(AANSPREEKTITEL + "." + type);
  }

  public void create() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    kontakt     = new Kontakt();
    kontaktDto  = new KontaktDto();

    kontakt.setTaal(getParameter(Sedes.PAR_DEFAULT_TAAL));
    kontakt.setKontakttype(getParameter(Sedes.PAR_DEFAULT_TYPE));
    kontakt.persist(kontaktDto);

    setAktie(PersistenceConstants.CREATE);
    setSubTitel(getTekst(TIT_CREATE));
    redirect(KONTAKT_REDIRECT);
  }

  public void delete() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var kontaktId  = kontakt.getKontaktId();
    try {
      getKontaktService().delete(kontaktId);
      kontakt      = new Kontakt();
      kontaktDto   = new KontaktDto();
      addInfo(PersistenceConstants.DELETED, kontaktDto.getNaam());
      redirect(KONTAKTEN_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, kontaktId);
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public Collection<SelectItem> getAanspreektitels() {
    Collection<SelectItem>  items = new LinkedList<>();
    items.add(new SelectItem("", "---"));
    items.addAll(getSelectitems(AANSPREEKTITEL));
   return items;
  }

  public Kontakt getKontakt() {
    return kontakt;
  }

  public Collection<SelectItem> getKontakttypes() {
    return getSelectitems(KONTAKTTYPE);
  }

  public Collection<SelectItem> getSelectitems(String parameter) {
    Collection<SelectItem>  items = new LinkedList<>();
    items.addAll(getI18nLijst(parameter, getGebruikersTaal(),
                              new I18nSelectItem.WaardeComparator()));

    return items;
  }

  public String kontakttype(String type) {
    return getTekst(KONTAKTTYPE + "." + type);
  }

  public void retrieve() {
    if (!isGerechtigd()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var ec      = FacesContext.getCurrentInstance().getExternalContext();

    if (!ec.getRequestParameterMap().containsKey(KontaktDto.COL_KONTAKTID)) {
      addError(ComponentsConstants.GEENPARAMETER, KontaktDto.COL_KONTAKTID);
      return;
    }

    var kontaktId  = Long.valueOf(ec.getRequestParameterMap()
                                 .get(KontaktDto.COL_KONTAKTID));

    try {
      kontaktDto = getKontaktService().kontakt(kontaktId);
      kontakt    = new Kontakt(kontaktDto);
      setAktie(PersistenceConstants.RETRIEVE);
      setSubTitel(kontakt.getDisplaynaam());
      redirect(KONTAKT_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, LBL_KONTAKT);
    }
  }

  public void save() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var messages  = KontaktValidator.valideer(kontakt);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    var naam  = kontakt.getDisplaynaam();
    try {
      switch (getAktie().getAktie()) {
        case PersistenceConstants.CREATE:
          kontakt.persist(kontaktDto);
          getKontaktService().save(kontaktDto);
          kontakt.setKontaktId(kontaktDto.getKontaktId());
          addInfo(PersistenceConstants.CREATED, naam);
          update();
          break;
        case PersistenceConstants.UPDATE:
          kontakt.persist(kontaktDto);
          getKontaktService().save(kontaktDto);
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
    setSubTitel(getTekst(TIT_UPDATE, kontaktDto.getDisplaynaam()));
  }
}
