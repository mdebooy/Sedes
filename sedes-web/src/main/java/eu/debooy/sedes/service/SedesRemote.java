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

package eu.debooy.sedes.service;

import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.service.JNDI;
import eu.debooy.sedes.component.business.ISedesRemote;
import eu.debooy.sedes.component.entity.Kontakt;
import eu.debooy.sedes.component.entity.Regio;
import eu.debooy.sedes.domain.LandnaamDto;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Singleton
@Named("sedesRemote")
@Lock(LockType.WRITE)
public class SedesRemote implements ISedesRemote {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(SedesRemote.class);

  private AdresService    adresService;
  private KontaktService  kontaktService;
  private LandnaamService landnaamService;
  private RegioService    regioService;

  private final Map<Long, Map<String, String>>
                                landnamenCache  = new HashMap<>();

  private static final  String  STANDAARDTAAL = "nl";

  public SedesRemote() {
    LOGGER.debug("init SedesRemote");
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void clear() {
    landnamenCache.clear();
  }

  private AdresService getAdresService() {
    if (null == adresService) {
      adresService  = (AdresService)
          new JNDI.JNDINaam().metBean(AdresService.class).locate();
    }

    return adresService;
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public String getI18nLandnaam(Long landId) {
    return getI18nLandnaam(landId, getStandaardTaal());
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public String getI18nLandnaam(Long landId, String taal) {
    Map<String, String> landnamen = new HashMap<>();
    if (landnamenCache.containsKey(landId)) {
      landnamen = landnamenCache.get(landId);
    }

    if (landnamen.containsKey(taal)) {
      return landnamen.get(taal);
    }

    LandnaamDto landnaamDto;
    try {
      landnaamDto = getLandnaamService().landnaam(landId, taal);
      landnamen.put(taal, landnaamDto.getNaam());
      landnamenCache.put(landId, landnamen);
      return landnamen.get(taal);
    } catch (ObjectNotFoundException e) {
      // Probeer het nu met de standaardtaal.
    }

    if (landnamen.containsKey(getStandaardTaal())) {
      return landnamen.get(getStandaardTaal());
    }

    try {
      landnaamDto = getLandnaamService().landnaam(landId, getStandaardTaal());
      landnamen.put(getStandaardTaal(), landnaamDto.getNaam());
      landnamenCache.put(landId, landnamen);
      return landnamen.get(taal);
    } catch (ObjectNotFoundException e) {
      return "???" + landId + ":" + taal + "???";
    }
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Kontakt getKontakt(Long kontaktId) {
    var kontakt = new Kontakt.Builder();
    try {
      var item  = getKontaktService().kontakt(kontaktId);

      kontakt.setAanspreekId(item.getAanspreekId())
             .setDisplaynaam(item.getDisplaynaam())
             .setGeboortedatum(item.getGeboortedatum())
             .setGebruikersnaam(item.getGebruikersnaam())
             .setInitialen(item.getInitialen())
             .setKontaktId(item.getKontaktId())
             .setKontakttype(item.getKontakttype())
             .setNaam(item.getNaam())
             .setPseudoniem(item.getPseudoniem())
             .setRoepnaam(item.getRoepnaam())
             .setTaal(item.getTaal())
             .setTussenvoegsel(item.getTussenvoegsel())
             .setVoornaam(item.getVoornaam());
    } catch (ObjectNotFoundException e) {
      // Geef een lege Kontakt.
    }

    return kontakt.build();
  }

  private KontaktService getKontaktService() {
    if (null == kontaktService) {
      kontaktService = (KontaktService)
          new JNDI.JNDINaam().metBean(KontaktService.class).locate();
    }

    return kontaktService;
  }

  private LandnaamService getLandnaamService() {
    if (null == landnaamService) {
      landnaamService = (LandnaamService)
          new JNDI.JNDINaam().metBean(LandnaamService.class).locate();
    }

    return landnaamService;
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Regio getRegio(Long regioId) {
    var regio = new Regio.Builder();
    try {
      var item  = getRegioService().regio(regioId);

      regio.setLandId(item.getLandId())
           .setNaam(item.getNaam())
           .setRegioId(item.getRegioId())
           .setRegiokode(item.getRegiokode());
    } catch (ObjectNotFoundException e) {
      // Geef een lege Regio.
    }

    return regio.build();
  }

  private RegioService getRegioService() {
    if (null == regioService) {
      regioService = (RegioService)
          new JNDI.JNDINaam().metBean(RegioService.class).locate();
    }

    return regioService;
  }

  private String getStandaardTaal() {
    return STANDAARDTAAL;
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<SelectItem> selectAdressen() {
    return getAdresService().selectAdressen();
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<SelectItem> selectKontakten() {
    return getKontaktService().selectKontakten();
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<SelectItem> selectLandnamen() {
    return selectLandnamen(getStandaardTaal());
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<SelectItem> selectLandnamen(String taal) {
    return getLandnaamService().selectLandnamen(taal);
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<SelectItem> getSelectRegios() {
    return getRegioService().selectRegios();
  }

  @Override
  public int size() {
    return landnamenCache.size();
  }
}
