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
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import eu.debooy.sedes.Sedes;
import static eu.debooy.sedes.Sedes.LAND_REDIRECT;
import eu.debooy.sedes.domain.RegioDto;
import eu.debooy.sedes.domain.RegionaamDto;
import eu.debooy.sedes.form.Regio;
import eu.debooy.sedes.form.Regionaam;
import eu.debooy.sedes.validator.RegioValidator;
import eu.debooy.sedes.validator.RegionaamValidator;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.json.simple.JSONArray;
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

  private static final  String  DTIT_CREATE = "sedes.titel.regionaam.create";
  private static final  String  DTIT_UPDATE = "sedes.titel.regionaam.update";

  private static final  String  LBL_REGIO     = "label.regio";
  private static final  String  LBL_REGIONAAM = "label.regionaam";

  private static final  String  TIT_CREATE    = "sedes.titel.regio.create";
  private static final  String  TIT_UPDATE    = "sedes.titel.regio.update";

  private Regio     regio;
  private RegioDto  regioDto;
  private Regionaam regionaam;

  public void create() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    regio     = new Regio();
    regioDto  = new RegioDto();
    regio.setLandId(Long.valueOf(getParameter(Sedes.PAR_DEFAULT_LANDID)));
    setAktie(PersistenceConstants.CREATE);
    setSubTitel(getTekst(TIT_CREATE));
    redirect(REGIO_REDIRECT);
  }

  public void createDetail() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    regionaam = new Regionaam();
    setDetailAktie(PersistenceConstants.CREATE);
    setDetailSubTitel(getTekst(DTIT_CREATE));
    redirect(REGIONAAM_REDIRECT);
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

  public void deleteDetail() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    try {
      regioDto.removeRegionaam(regionaam.getTaal());
      getRegioService().save(regioDto);
      regionaam = new Regionaam();
      addInfo(PersistenceConstants.DELETED, "'" + regionaam.getTaal() + "'");
      if (getGebruikersTaalInIso6392t().equals(regionaam.getTaal())) {
        setSubTitel(getTekst(TIT_UPDATE,
                             regioDto.getNaam(getGebruikersTaalInIso6392t())));
      }
      redirect(REGIO_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, regionaam.getTaal());
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public String getNaam() {
    if (regioDto.hasRegionaam(getGebruikersTaalInIso6392t())) {
      return regioDto.getRegionaam(getGebruikersTaalInIso6392t()).getNaam();
    }

    if (regioDto.hasRegionaam(getDefTaal())) {
      return regioDto.getRegionaam(getDefTaal()).getNaam();
    }

    return DoosUtils.onbekendeCode(getGebruikersTaalInIso6392t());
  }

  public Regio getRegio() {
    return regio;
  }

  public Regio getRegio(Long regioId) {
    return new Regio(getRegioService().regio(regioId));
  }

  public Regionaam getRegionaam() {
    return regionaam;
  }

  public JSONArray getRegionamen() {
    var regionamen  = new JSONArray();

    regioDto.getRegionamen().forEach(rij -> regionamen.add(rij.toJSON()));

    return regionamen;
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
      setDeletetekst(regio.getNaam());
      setSubTitel(regioDto.getRegionaam(getGebruikersTaalInIso6392t())
                          .getNaam());
      redirect(REGIO_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, LBL_REGIO);
    }
  }

  public void retrieveDetail() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var ec    = FacesContext.getCurrentInstance().getExternalContext();

    if (!ec.getRequestParameterMap().containsKey(RegionaamDto.COL_TAAL)) {
      addError(ComponentsConstants.GEENPARAMETER, RegionaamDto.COL_TAAL);
      return;
    }

    try {
      regionaam =
          new Regionaam(regioDto.getRegionaam(ec.getRequestParameterMap()
                                                .get(RegionaamDto.COL_TAAL)));
      setDetailAktie(PersistenceConstants.UPDATE);
      setDetailDeletetekst(String.format("%s %s", regionaam.getTaal(),
                                                  regionaam.getNaam()));
      setDetailSubTitel(getTekst(DTIT_UPDATE));
      redirect(REGIONAAM_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, LBL_REGIONAAM);
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
        case PersistenceConstants.CREATE -> {
          regio.persist(regioDto);
          getRegioService().save(regioDto);
          regio.setRegioId(regioDto.getRegioId());
          addInfo(PersistenceConstants.CREATED, naam);
          update();
        }
        case PersistenceConstants.UPDATE -> {
          regio.persist(regioDto);
          getRegioService().save(regioDto);
          addInfo(PersistenceConstants.UPDATED, naam);
        }
        default -> addError(ComponentsConstants.WRONGREDIRECT,
                            getAktie().getAktie());
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

  public void saveDetail() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var messages  = RegionaamValidator.valideer(regionaam);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    if (getDetailAktie().getAktie() == PersistenceConstants.CREATE
        && regioDto.hasRegionaam(regionaam.getTaal())) {
      addError(PersistenceConstants.DUPLICATE, regionaam.getTaal());
      return;
    }

    try {
      var regionaamDto = new RegionaamDto();
      switch (getAktie().getAktie()) {
        case PersistenceConstants.CREATE -> {
          regionaam.persist(regionaamDto);
          regioDto.addRegionaam(regionaamDto);
          getRegioService().save(regioDto);
          addInfo(PersistenceConstants.CREATED,
                  String.format("'%s'", regionaam.getTaal()));
        }
        case PersistenceConstants.UPDATE -> {
          regionaam.persist(regionaamDto);
          regioDto.addRegionaam(regionaamDto);
          getRegioService().save(regioDto);
          addInfo(PersistenceConstants.UPDATED,
                  String.format("'%s'", regionaam.getTaal()));
        }
        default -> addError(ComponentsConstants.WRONGREDIRECT,
                            getAktie().getAktie()) ;
      }
      if (regionaam.getTaal().equals(getGebruikersTaalInIso6392t())) {
        setSubTitel(
            getTekst(TIT_UPDATE,
                     regioDto.getRegionaam(getGebruikersTaalInIso6392t())
                             .getNaam()));
      }
      redirect(LAND_REDIRECT);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, regionaam.getTaal());
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, regionaam.getTaal());
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public Collection<SelectItem> selectRegios(String taal) {
    List<SelectItem>  items = new ArrayList<>();
    Set<Regio>        rijen = new TreeSet<>();

    try {
      rijen.addAll(getRegioService().query(taal));
      rijen.forEach(
        item -> items.add(new SelectItem(regio.getRegioId().toString(),
                                         regio.getNaam())));
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return items;
  }

  public void update() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    setAktie(PersistenceConstants.UPDATE);
    setDeletetekst(regioDto.getNaam(getGebruikersTaalInIso6392t()));
    setSubTitel(getTekst(TIT_UPDATE,
                         regioDto.getRegionaam(getGebruikersTaalInIso6392t())
                                 .getNaam()));
  }
}
