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
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
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
import java.util.List;

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

  private Werelddeel        werelddeel;
  private WerelddeelDto     werelddeelDto;
  private Werelddeelnaam    werelddeelnaam;
  private WerelddeelnaamDto werelddeelnaamDto;

  /**
   * Prepareer een nieuw Werelddeel.
   */
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
      LOGGER.error("RT: " + e.getLocalizedMessage(), e);
      generateExceptionMessage(e);
    }
  }

  /**
   * Prepareer een nieuw Werelddeelnaam.
   */
  public void createWerelddeelnaam() {
    werelddeelnaamDto = new WerelddeelnaamDto();
    werelddeelnaam    = new Werelddeelnaam();
    setDetailAktie(PersistenceConstants.CREATE);
    setDetailSubTitel("sedes.titel.werelddeelnaam.create");
    redirect(WERELDDEELNAAM_REDIRECT);
  }

  /**
   * Verwijder de I18nCode
   * 
   * @param Long codeId
   * @param String code
   */
  public void delete(Long werelddeelId) {
    String  naam  = i18nWerelddeelnaam(werelddeelId);
    try {
      getWerelddeelService().delete(werelddeelId);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, naam);
      return;
    } catch (DoosRuntimeException e) {
      LOGGER.error("RT: " + e.getLocalizedMessage(), e);
      generateExceptionMessage(e);
      return;
    }
    addInfo(PersistenceConstants.DELETED, naam);
  }

  /**
   * Verwijder de Werelddeelnaam.
   * 
   * @param String taalKode
   */
  public void deleteWerelddeelnaam(String taalKode) {
    try {
      werelddeelDto.removeNaam(taalKode);
      getWerelddeelService().save(werelddeelDto);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, taalKode);
      return;
    } catch (DoosRuntimeException e) {
      LOGGER.error("RT: " + e.getLocalizedMessage(), e);
      generateExceptionMessage(e);
      return;
    }
    addInfo(PersistenceConstants.DELETED, "'" + taalKode + "'");
  }

  /**
   * Geef het geselecteerde werelddeel.
   * 
   * @return Werelddeel
   */
  public Werelddeel getWerelddeel() {
    return werelddeel;
  }

  /**
   * Geef de geselecteerde werelddeelnaam.
   * 
   * @return Werelddeelnaam
   */
  public Werelddeelnaam getWerelddeelnaam() {
    return werelddeelnaam;
  }

  /**
   * Geef de lijst met werelddeelnamen.
   * 
   * @return Collection<Werelddeelnaam>
   */
  public Collection<Werelddeelnaam> getWerelddeelnamen() {
    Collection<Werelddeelnaam>    werelddeelnamen =
        new HashSet<Werelddeelnaam>();
    for (WerelddeelnaamDto rij : werelddeelDto.getWerelddeelnamen()) {
      werelddeelnamen.add(new Werelddeelnaam(rij));
    }

    return werelddeelnamen;
  }

  /**
   * Geef de lijst met werelddelen.
   * 
   * @return Collection<Werelddeel>
   */
  public Collection<Werelddeel> getWerelddelen() {
    return getWerelddeelService().query();
  }

  /**
   * Persist het Werelddeel
   */
  public void save() {
    List<Message> messages  = WerelddeelValidator.valideer(werelddeel);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    try {
      werelddeel.persist(werelddeelDto);
      getWerelddeelService().save(werelddeelDto);
      switch (getAktie().getAktie()) {
      case PersistenceConstants.CREATE:
        addInfo(PersistenceConstants.CREATED, "");
        break;
      default:
        addError("error.aktie.wrong", getAktie().getAktie());
        break;
      }
      setAktie(PersistenceConstants.RETRIEVE);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, "");
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, "");
    } catch (DoosRuntimeException e) {
      LOGGER.error("RT: " + e.getLocalizedMessage(), e);
      generateExceptionMessage(e);
    }
  }

  /**
   * Persist de Werelddeelnaam
   */
  public void saveWerelddeelnaam() {
    List<Message> messages  = WerelddeelnaamValidator.valideer(werelddeelnaam);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    try {
      werelddeelnaamDto = new WerelddeelnaamDto();
      werelddeelnaam.persist(werelddeelnaamDto);
      werelddeelDto.addNaam(werelddeelnaamDto);
      getWerelddeelService().save(werelddeelDto);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, werelddeelnaam.getTaal());
      return;
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, werelddeelnaam.getTaal());
      return;
    } catch (DoosRuntimeException e) {
      LOGGER.error("RT: " + e.getLocalizedMessage(), e);
      generateExceptionMessage(e);
      return;
    }

    redirect(WERELDDEEL_REDIRECT);
  }

  /**
   * Geef alle werelddeelnamen (en werelddeelId).
   *  
   * @return Collection<SelectItem>
   */
  public Collection<SelectItem> selectWerelddeelnamen() {
    return getWerelddeelnaamService()
              .selectWerelddeelnamen(getGebruikersTaal());
  }

  /**
   * Geef de naam van het werelddeel in de taal van de gebruiker.
   * 
   * @param Long werelddeelId
   * @return String
   */
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

  /**
   * Zet het Werelddeel dat gevraagd is klaar.
   * 
   * @param Long werelddeelId
   */
  public void retrieve(Long werelddeelId) {
    werelddeelDto = getWerelddeelService().werelddeel(werelddeelId);
    werelddeel    = new Werelddeel(werelddeelDto);
    setAktie(PersistenceConstants.RETRIEVE);
    redirect(WERELDDEEL_REDIRECT);
  }

  /**
   * Zet de Landnaam die gewijzigd gaat worden klaar.
   * 
   * @param Long landId
   */
  public void updateWerelddeelnaam(String taal) {
    werelddeelnaamDto = werelddeelDto.getWerelddeelnaam(taal);
    werelddeelnaam    = new Werelddeelnaam(werelddeelnaamDto);
    setDetailAktie(PersistenceConstants.UPDATE);
    setDetailSubTitel("sedes.titel.werelddeelnaam.update");
    redirect(WERELDDEELNAAM_REDIRECT);
  }

  /**
   * Zet het Werelddeel die gewijzigd gaat worden klaar.
   * 
   * @param Long werelddeelId
   */
  public void update(Long werelddeelId) {
    werelddeelDto = getWerelddeelService().werelddeel(werelddeelId);
    werelddeel    = new Werelddeel(werelddeelDto);
    setAktie(PersistenceConstants.UPDATE);
    setSubTitel("sedes.titel.werelddeel.update");
    redirect(WERELDDEEL_REDIRECT);
  }
}
