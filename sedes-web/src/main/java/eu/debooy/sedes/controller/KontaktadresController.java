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

import eu.debooy.doos.model.I18nSelectItem;
import eu.debooy.doosutils.ComponentsConstants;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import eu.debooy.sedes.Sedes;
import eu.debooy.sedes.SedesUtils;
import eu.debooy.sedes.domain.KontaktadresDto;
import eu.debooy.sedes.form.Adres;
import eu.debooy.sedes.form.Kontakt;
import eu.debooy.sedes.form.Kontaktadres;
import eu.debooy.sedes.form.Land;
import eu.debooy.sedes.form.Plaats;
import eu.debooy.sedes.validator.KontaktadresValidator;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Named("sedesKontaktadres")
@SessionScoped
public class KontaktadresController extends Sedes {
  private static final  long    serialVersionUID  = 1L;
  private static final  Logger  LOGGER            =
      LoggerFactory.getLogger(KontaktadresController.class);

  private static final  String  KONTAKTADRESTYPE  = "sedes.kontaktadres.type";

  private static final  String  TIT_CREATE    =
      "sedes.titel.kontaktadres.create";
  private static final  String  TIT_DELETE    =
      "sedes.titel.kontaktadres.delete";
  private static final  String  TIT_RETRIEVE  =
      "sedes.titel.kontaktadres.retrieve";
  private static final  String  TIT_UPDATE    =
      "sedes.titel.kontaktadres.update";

  private Adres           adres;
  private Kontakt         kontakt;
  private Kontaktadres    kontaktadres;
  private KontaktadresDto kontaktadresDto;
  private Land            land;
  private Plaats          plaats;

  private boolean checkParameters(ExternalContext ec) {
    boolean correct = true;

    if (isAdres()) {
      if(!ec.getRequestParameterMap()
            .containsKey(KontaktadresDto.COL_ADRESID)) {
        addError(ComponentsConstants.GEENPARAMETER,
                 KontaktadresDto.COL_ADRESID);
        correct = false;
      }
      if (ec.getRequestParameterMap()
            .containsKey(KontaktadresDto.COL_KONTAKTID)) {
        addError(ComponentsConstants.FOUTEPARAMETER,
                 KontaktadresDto.COL_KONTAKTID);
        correct = false;

      }

      return correct;
    }

    if (isKontakt()) {
      if(!ec.getRequestParameterMap()
            .containsKey(KontaktadresDto.COL_KONTAKTID)) {
        addError(ComponentsConstants.GEENPARAMETER,
                 KontaktadresDto.COL_KONTAKTID);
        correct = false;
      }
      if (ec.getRequestParameterMap()
            .containsKey(KontaktadresDto.COL_ADRESID)) {
        addError(ComponentsConstants.FOUTEPARAMETER,
                 KontaktadresDto.COL_ADRESID);
        correct = false;

      }

      return correct;
    }

    if (!ec.getRequestParameterMap()
           .containsKey(KontaktadresDto.COL_ADRESID)) {
      addError(ComponentsConstants.GEENPARAMETER,
               KontaktadresDto.COL_ADRESID);
      correct = false;

    }

    if(!ec.getRequestParameterMap()
          .containsKey(KontaktadresDto.COL_KONTAKTID)) {
      addError(ComponentsConstants.GEENPARAMETER,
               KontaktadresDto.COL_KONTAKTID);
      correct = false;
    }

    return correct;
  }

  public void create() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var ec      = FacesContext.getCurrentInstance().getExternalContext();

    setReturnTo(ec, KONTAKTADRES_REDIRECT);

    if (!checkParameters(ec)) {
      return;
    }

    if (ec.getRequestParameterMap()
           .containsKey(KontaktadresDto.COL_ADRESID)) {
      var id  = Long.valueOf(ec.getRequestParameterMap()
                               .get(KontaktadresDto.COL_ADRESID));
      adres   = new Adres(getAdresService().adres(id));
    } else {
      adres   = new Adres();
    }
    if (ec.getRequestParameterMap()
           .containsKey(KontaktadresDto.COL_KONTAKTID)) {
      var id  = Long.valueOf(ec.getRequestParameterMap()
                                 .get(KontaktadresDto.COL_KONTAKTID));
      kontakt = new Kontakt(getKontaktService().kontakt(id));
    } else {
      kontakt = new Kontakt();
    }

    kontaktadres    = new Kontaktadres();
    kontaktadresDto = new KontaktadresDto();

    kontaktadres.setAdresId(adres.getAdresId());
    kontaktadres.setKontaktId(kontakt.getKontaktId());
    kontaktadres.setStartdatum(new Date());
    kontaktadres.persist(kontaktadresDto);

    setAktie(PersistenceConstants.CREATE);
    setSubTitel(getTekst(TIT_CREATE, getInTitel()));
    redirect(KONTAKTADRES_REDIRECT);
  }

  public void delete() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var omschrijving  = getInOmschrijving();
    try {
      getKontaktadresService().delete(kontaktadresDto);
      adres             = new Adres();
      kontakt           = new Kontakt();
      kontaktadres      = new Kontaktadres();
      kontaktadresDto   = new KontaktadresDto();
      addInfo(PersistenceConstants.DELETED, omschrijving);
      redirect(KONTAKTEN_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, omschrijving);
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public Adres getAdres() {
    return adres;
  }

  public String getDeletetitel() {
    return getTekst(TIT_DELETE, getInTitel());
  }

  public String getInOmschrijving() {
    if (isKontakt()) {
      return SedesUtils.getAdresMetPlaatsnaam(adres.getAdresdata(),
                                              plaats.getPlaatsnaam(),
                                              land.getPostLandkode());
    }

    if (isAdres()) {
      return kontakt.getDisplaynaam();
    }

    return "??";
  }

  public String getInTitel() {
    if (isAdres()) {
      return SedesUtils.getAdresMetPlaatsnaam(adres.getAdresdata(),
                                              plaats.getPlaatsnaam(),
                                              land.getPostLandkode());
    }

    if (isKontakt()) {
      return kontakt.getDisplaynaam();
    }

    return "??";
  }

  public Kontakt getKontakt() {
    return kontakt;
  }

  public Kontaktadres getKontaktadres() {
    return kontaktadres;
  }

  public Collection<SelectItem> getKontaktadrestypes() {
    Collection<SelectItem>  items = new LinkedList<>();

    items.add(new SelectItem("", "--"));
    items.addAll(getI18nLijst(KONTAKTADRESTYPE, getGebruikersTaal(),
                              new I18nSelectItem.WaardeComparator()));

    return items;
  }

  public String getTaal() {
    if (null == kontaktadres.getTaal()) {
      return "--";
    }

    return getDoosRemote().getIso6392tNaam(kontaktadres.getTaal(),
                                           getGebruikersTaalInIso6392t());
  }

  public Collection<SelectItem> getTalenIso6392t() {
    return getDoosRemote().getTalenIso6392t(getGebruikersTaalInIso6392t(),
                                            true);
  }

  public boolean isAdres() {
    return getReturnTo().equals(ADRES_REDIRECT);
  }

  public boolean isKontakt() {
    return getReturnTo().equals(KONTAKT_REDIRECT);
  }

  public String kontaktadrestype(String type) {
    return getTekst(KONTAKTADRESTYPE + "." + type);
  }

  public void retrieve() {
    if (!isGerechtigd()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var ec      = FacesContext.getCurrentInstance().getExternalContext();

    setReturnTo(ec, KONTAKTADRES_REDIRECT);

    if (!ec.getRequestParameterMap()
           .containsKey(KontaktadresDto.COL_KONTAKTADRESID)) {
      addError(ComponentsConstants.GEENPARAMETER,
               KontaktadresDto.COL_KONTAKTADRESID);
      return;
    }

    var kontaktadresId  = Long.valueOf(ec.getRequestParameterMap()
                                .get(KontaktadresDto.COL_KONTAKTADRESID));

    try {
      kontaktadresDto = getKontaktadresService().kontaktadres(kontaktadresId);
      kontaktadres    = new Kontaktadres(kontaktadresDto);
      setAdresEnKontakt();
      setAktie(PersistenceConstants.RETRIEVE);
      setSubTitel(getTekst(TIT_RETRIEVE, getInTitel()));
      redirect(KONTAKTADRES_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, getInTitel());
    }
  }

  public void save() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var messages  = KontaktadresValidator.valideer(kontaktadres);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    try {
      switch (getAktie().getAktie()) {
        case PersistenceConstants.CREATE:
          kontaktadres.persist(kontaktadresDto);
          getKontaktadresService().save(kontaktadresDto);
          kontaktadres.setKontaktadresId(kontaktadresDto.getKontaktadresId());
          setAdresEnKontakt();
          addInfo(PersistenceConstants.CREATED, getInOmschrijving());
          update();
          break;
        case PersistenceConstants.UPDATE:
          kontaktadres.persist(kontaktadresDto);
          getKontaktadresService().save(kontaktadresDto);
          setAdresEnKontakt();
          addInfo(PersistenceConstants.UPDATED, getInOmschrijving());
          update();
          break;
        default:
          addError(ComponentsConstants.WRONGREDIRECT, getAktie().getAktie());
          break;
      }
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, getInOmschrijving());
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, getInOmschrijving());
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  private void setAdres() {
    if (null == adres
        || null == adres.getAdresId()
        || !adres.getAdresId().equals(kontaktadres.getAdresId())) {
      adres   = new Adres(getAdresService().adres(kontaktadres.getAdresId()));
    }

    if (null == adres.getPlaatsId()) {
      land    = new Land();
      plaats  = new Plaats();

      return;
    }

    if (null == plaats
        || null == plaats.getPlaatsId()
        || ! adres.getPlaatsId().equals(plaats.getPlaatsId())) {
      plaats  = new Plaats(getPlaatsService().plaats(adres.getPlaatsId()));
    }

    if (null == plaats.getLandId()) {
      land    = new Land();

      return;
    }

    if (null == land
        || null == land.getLandId()
        || ! plaats.getLandId().equals(land.getLandId())) {
      land    = new Land(getLandService().land(plaats.getLandId()));
    }
  }

  private void setAdresEnKontakt() {
    setAdres();
    setKontakt();
  }

  private void setKontakt() {
    if (null == kontakt
        || null == kontakt.getKontaktId()
        || !kontakt.getKontaktId().equals(kontaktadres.getKontaktId())) {
      kontakt =
          new Kontakt(getKontaktService().kontakt(kontaktadres.getKontaktId()));
    }
  }

  public void update() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    setAktie(PersistenceConstants.UPDATE);
    setSubTitel(getTekst(TIT_UPDATE, getInTitel()));
  }
}
