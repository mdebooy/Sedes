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
import eu.debooy.doosutils.ComponentsConstants;
import eu.debooy.doosutils.PersistenceConstants;
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

  private static final  String  DTIT_CREATE = "sedes.titel.landnaam.create";
  private static final  String  DTIT_UPDATE = "sedes.titel.landnaam.update";
  private static final  String  TIT_CREATE  = "sedes.titel.land.create";
  private static final  String  TIT_UPDATE  = "sedes.titel.land.update";

  private Land        land;
  private LandDto     landDto;
  private Landnaam    landnaam;
  private LandnaamDto landnaamDto;

  public void create() {
    landDto = new LandDto();
    land    = new Land();
    setAktie(PersistenceConstants.CREATE);
    setSubTitel(TIT_CREATE);
    redirect(LAND_REDIRECT);
  }

  public void createLandnaam() {
    landnaamDto = new LandnaamDto();
    landnaam    = new Landnaam();
    setDetailAktie(PersistenceConstants.CREATE);
    setDetailSubTitel(DTIT_CREATE);
    redirect(LANDNAAM_REDIRECT);
  }

  public void delete(Long landId) {
    try {
      landDto = getLandService().land(landId);
      getLandService().delete(landId);
      addInfo(PersistenceConstants.DELETED,
              landDto.getLandnaam(getGebruikersTaal()));
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, landId);
    } catch (DoosRuntimeException e) {
      LOGGER.error("RT: " + e.getLocalizedMessage(), e);
      generateExceptionMessage(e);
    }
  }

  public Land getLand() {
    return land;
  }

  public Collection<Land> getLanden() {
    return getLandService().query();
  }

  public Landnaam getLandnaam() {
    return landnaam;
  }

  public Collection<Landnaam> getLandnamen() {
    Collection<Landnaam>  landnamen = new HashSet<>();
    var                   rijen     = landDto.getLandnamen();
    for (LandnaamDto rij : rijen) {
      landnamen.add(new Landnaam(rij));
    }

    return landnamen;
  }

  public void landenlijst() {
    var exportData        = new ExportData();

    exportData.addMetadata("application", getApplicatieNaam());
    exportData.addMetadata("auteur",      getGebruikerNaam());
    exportData.addMetadata("lijstnaam",   "landenlijst");
    exportData.setParameters(getLijstParameters());

    exportData.setKolommen(new String[] { "werelddeelnaam", "landnaam",
                                          "hoofdstad" });
    exportData.setType(getType());
    exportData.addVeld("ReportTitel",
                       getTekst("sedes.titel.landenlijst"));

    var taal              = getGebruikersTaal();
    Set<Werelddeelnaam> groepen =
        new TreeSet<>(new Werelddeelnaam.NaamComparator());
    groepen.addAll(getWerelddeelnaamService().werelddeelnamen(taal));
    for (Werelddeelnaam groep : groepen) {
      var werelddeelId    = groep.getWerelddeelId();
      var werelddeelnaam  = groep.getWerelddeelnaam();
      Set<Landnaam> rijen           =
          new TreeSet<>(new Landnaam.NaamComparator());
      rijen.addAll(getLandnaamService()
                     .bestaandeLandnamenPerWerelddeel(taal, werelddeelId));
      for (Landnaam rij : rijen) {
        exportData.addData(new String[] {werelddeelnaam,
                                         rij.getLandnaam(),
                                         rij.getHoofdstad()});

      }
    }

    var response  =
        (HttpServletResponse) FacesContext.getCurrentInstance()
                                          .getExternalContext().getResponse();
    try {
      Export.export(response, exportData);
      FacesContext.getCurrentInstance().responseComplete();
    } catch (IllegalArgumentException | TechnicalException e) {
      generateExceptionMessage(e);
    }
  }

  public void retrieve(Long landId) {
    landDto = getLandService().land(landId);
    land    = new Land(landDto);
    setAktie(PersistenceConstants.RETRIEVE);

    redirect(LAND_REDIRECT);
  }

  public void save() {
    var messages  = LandValidator.valideer(land);
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
        addError(ComponentsConstants.WRONGREDIRECT, getAktie().getAktie());
        break;
      }
      setAktie(PersistenceConstants.RETRIEVE);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, land.getIso3());
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, land.getIso3());
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public void saveLandnaam() {
    var messages  = LandnaamValidator.valideer(landnaam);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    try {
      var nieuweLandnaamDto = new LandnaamDto();
      landnaam.persist(nieuweLandnaamDto);
      landDto.addLandnaam(nieuweLandnaamDto);
      getLandService().save(landDto);
      switch (getAktie().getAktie()) {
        case PersistenceConstants.CREATE:
          addInfo(PersistenceConstants.CREATED, "'" + landnaam.getTaal() + "'");
          break;
        case PersistenceConstants.UPDATE:
          addInfo(PersistenceConstants.UPDATED, "'" + landnaam.getTaal() + "'");
          break;
        default:
          addError(ComponentsConstants.WRONGREDIRECT, getAktie().getAktie()) ;
          break;
      }
      redirect(LAND_REDIRECT);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, landnaam.getTaal());
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, landnaam.getTaal());
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public void update(Long landId) {
    landDto = getLandService().land(landId);
    land    = new Land(landDto);
    setAktie(PersistenceConstants.UPDATE);
    setSubTitel(TIT_UPDATE);
    redirect(LAND_REDIRECT);
  }

  public void updateLandnaam(Long landId, String taal) {
    landnaamDto = getLandnaamService().landnaam(landId, taal);
    landnaam    = new Landnaam(landnaamDto);
    setDetailAktie(PersistenceConstants.UPDATE);
    setDetailSubTitel(DTIT_UPDATE);
    redirect(LANDNAAM_REDIRECT);
  }

  public void updateLandnaam(String taal) {
    landnaamDto = getLandnaamService().landnaam(land.getLandId(), taal);
    landnaam    = new Landnaam(landnaamDto);
    setDetailAktie(PersistenceConstants.UPDATE);
    setDetailSubTitel(DTIT_UPDATE);
    redirect(LANDNAAM_REDIRECT);
  }
}
