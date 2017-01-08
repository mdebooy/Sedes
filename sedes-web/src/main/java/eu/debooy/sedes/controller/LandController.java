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

import eu.debooy.doos.component.Export;
import eu.debooy.doos.model.ExportData;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.TechnicalException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import eu.debooy.sedes.Sedes;
import eu.debooy.sedes.domain.LandDto;
import eu.debooy.sedes.domain.LandnaamDto;
import eu.debooy.sedes.form.Land;
import eu.debooy.sedes.form.Landnaam;
import eu.debooy.sedes.form.Werelddeelnaam;
import eu.debooy.sedes.validator.LandValidator;
import eu.debooy.sedes.validator.LandnaamValidator;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Named("sedesLand")
@SessionScoped
public class LandController extends Sedes {
  private static final  long    serialVersionUID  = 1L;
  private static final  Logger  LOGGER            =
      LoggerFactory.getLogger(LandController.class);

  private Land              land;
  private LandDto           landDto;
  private Landnaam          landnaam;
  private LandnaamDto       landnaamDto;

  /**
   * Prepareer een nieuw Land.
   */
  public void create() {
    landDto = new LandDto();
    land    = new Land();
    setAktie(PersistenceConstants.CREATE);
    setSubTitel("sedes.titel.land.create");
    redirect(LAND_REDIRECT);
  }

  /**
   * Prepareer een nieuw Landnaam.
   */
  public void createLandnaam() {
    landnaamDto = new LandnaamDto();
    landnaam    = new Landnaam();
    setDetailAktie(PersistenceConstants.CREATE);
    setDetailSubTitel("sedes.titel.landnaam.create");
    redirect(LANDNAAM_REDIRECT);
  }

  /**
   * Verwijder de Plaats.
   */
  public void delete(Long landId) {
    try {
      landDto = getLandService().land(landId);
      getLandService().delete(landId);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, landId);
      return;
    } catch (DoosRuntimeException e) {
      LOGGER.error("RT: " + e.getLocalizedMessage(), e);
      generateExceptionMessage(e);
      return;
    }
    addInfo(PersistenceConstants.DELETED,
            landDto.getLandnaam(getGebruikersTaal()));
  }

  /**
   * Geef het geselecteerde land.
   * 
   * @return Land
   */
  public Land getLand() {
    return land;
  }

  /**
   * Geef de lijst met landen.
   * 
   * @return Collection<Land> met Land objecten.
   */
  public Collection<Land> getLanden() {
    return getLandService().query();
  }

  /**
   * Geef het geselecteerde landnaam.
   * 
   * @return Landnaam
   */
  public Landnaam getLandnaam() {
    return landnaam;
  }

  /**
   * Geef de lijst met landnamen voor 1 land.
   * 
   * @return Collection<Landnaam> met Landnaam objecten.
   */
  public Collection<Landnaam> getLandnamen() {
    Collection<Landnaam>    landnamen = new HashSet<Landnaam>();
    Collection<LandnaamDto> rijen     = landDto.getLandnamen();
    for (LandnaamDto rij : rijen) {
      landnamen.add(new Landnaam(rij));
    }

    return landnamen;
  }

  /**
   * Rapport met de landen van de wereld.
   */
  public void landenlijst() {
    ExportData  exportData  = new ExportData();

    exportData.addMetadata("application", getApplicatieNaam());
    exportData.addMetadata("auteur",      getGebruikerNaam());
    exportData.addMetadata("lijstnaam",   "landenlijst");
    exportData.setKleuren(getLijstKleuren());

    exportData.setKolommen(new String[] { "werelddeelnaam", "landnaam",
                                          "hoofdstad" });
    exportData.setType(getType());
    exportData.addVeld("ReportTitel",
                       getTekst("sedes.titel.landenlijst"));

    String              taal    = getGebruikersTaal();
    Set<Werelddeelnaam> groepen =
        new TreeSet<Werelddeelnaam>(new Werelddeelnaam.NaamComparator());
    groepen.addAll(getWerelddeelnaamService().werelddeelnamen(taal));
    for (Werelddeelnaam groep : groepen) {
      Long          werelddeelId    = groep.getWerelddeelId();
      String        werelddeelnaam  = groep.getWerelddeelnaam();
      Set<Landnaam> rijen           =
          new TreeSet<Landnaam>(new Landnaam.NaamComparator());
      rijen.addAll(getLandnaamService()
                     .bestaandeLandnamenPerWerelddeel(taal, werelddeelId));
      for (Landnaam rij : rijen) {
        exportData.addData(new String[] {werelddeelnaam,
                                         rij.getLandnaam(),
                                         rij.getHoofdstad()});
  
      }
    }

    HttpServletResponse response  =
        (HttpServletResponse) FacesContext.getCurrentInstance()
                                          .getExternalContext().getResponse();
    try {
      Export.export(response, exportData);
    } catch (IllegalArgumentException e) {
      generateExceptionMessage(e);
      return;
    } catch (TechnicalException e) {
      generateExceptionMessage(e);
      return;
    }

    FacesContext.getCurrentInstance().responseComplete();
  }

  /**
   * Zet het Land dat gevraagd is klaar.
   * 
   * @param Long landId
   */
  public void retrieve(Long landId) {
    landDto = getLandService().land(landId);
    land    = new Land(landDto);
    setAktie(PersistenceConstants.RETRIEVE);

    redirect(LAND_REDIRECT);
  }

  /**
   * Persist het Land
   */
  public void save() {
    List<Message> messages  = LandValidator.valideer(land);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    try {
      land.persist(landDto);
      getLandService().save(landDto);
      switch (getAktie().getAktie()) {
      case PersistenceConstants.CREATE:
        addInfo(PersistenceConstants.CREATED, land.getIso3());
        break;
      case PersistenceConstants.UPDATE:
        addInfo(PersistenceConstants.UPDATED, land.getIso3());
        break;
      default:
        addError("error.aktie.wrong", getAktie().getAktie());
        break;
      }
      setAktie(PersistenceConstants.RETRIEVE);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, land.getIso3());
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, land.getIso3());
    } catch (DoosRuntimeException e) {
      LOGGER.error("RT: " + e.getLocalizedMessage(), e);
      generateExceptionMessage(e);
    }
  }

  /**
   * Persist de Landnaam
   */
  public void saveLandnaam() {
    List<Message> messages  = LandnaamValidator.valideer(landnaam);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    try {
      LandnaamDto landnaamDto = new LandnaamDto();
      landnaam.persist(landnaamDto);
      landDto.addLandnaam(landnaamDto);
      getLandService().save(landDto);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, landnaam.getTaal());
      return;
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, landnaam.getTaal());
      return;
    } catch (DoosRuntimeException e) {
      LOGGER.error("RT: " + e.getLocalizedMessage(), e);
      generateExceptionMessage(e);
      return;
    }

    redirect(LAND_REDIRECT);
  }

  /**
   * Zet de Land die gewijzigd gaat worden klaar.
   * 
   * @param Long landId
   */
  public void update(Long landId) {
    landDto = getLandService().land(landId);
    land    = new Land(landDto);
    setAktie(PersistenceConstants.UPDATE);
    setSubTitel("sedes.titel.land.update");
    redirect(LAND_REDIRECT);
  }

  /**
   * Zet de Landnaam die gewijzigd gaat worden klaar.
   * 
   * @param Long landId
   */
  public void updateLandnaam(Long landId, String taal) {
    landnaamDto = getLandnaamService().landnaam(landId, taal);
    landnaam    = new Landnaam(landnaamDto);
    setDetailAktie(PersistenceConstants.UPDATE);
    setDetailSubTitel("sedes.titel.landnaam.update");
    redirect(LANDNAAM_REDIRECT);
  }

  /**
   * Zet de Landnaam die gewijzigd gaat worden klaar.
   * 
   * @param Long landId
   */
  public void updateLandnaam(String taal) {
    landnaamDto = getLandnaamService().landnaam(land.getLandId(), taal);
    landnaam    = new Landnaam(landnaamDto);
    setDetailAktie(PersistenceConstants.UPDATE);
    setDetailSubTitel("sedes.titel.landnaam.update");
    redirect(LANDNAAM_REDIRECT);
  }
}
