/**
 * Copyright 2015 Marco de Booij
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
package eu.debooy.sedes.service;

import eu.debooy.sedes.access.LandnaamDao;
import eu.debooy.sedes.component.business.II18nLandnaam;
import eu.debooy.sedes.domain.LandnaamDto;
import eu.debooy.sedes.domain.LandnaamPK;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Singleton
@Named("sedesLandnaamService")
@Lock(LockType.WRITE)
public class LandnaamService implements II18nLandnaam {
  private static  Logger  logger  =
      LoggerFactory.getLogger(LandnaamService.class);

  @Inject
  private LandnaamDao   landnaamDao;
  private Map<Long, Map<String, String>>
      landnamenCache  = new HashMap<Long, Map<String, String>>();
  // TODO Ophalen uit database.
  private String        standaardTaal = "nl";

  /**
   * Initialisatie.
   */
  public LandnaamService() {
    logger.debug("init LandnaamService");
  }

  /**
   * Maak de cache (voor de remote bean) leeg.
   */
  @Override
  public void clear() {
    landnamenCache.clear();
  }

  /**
   * Geef de landnaam in de standaard taal.
   * 
   * @param landId
   * @return String
   */
  @Override
  public String getI18nLandnaam(Long landId) {
    return getI18nLandnaam(landId, getStandaardTaal());
  }

  /**
   * Geef de landnaam in de gevraagde taal.
   * 
   * @param landId
   * @param taal
   * @return String
   */
  @Override
  public String getI18nLandnaam(Long landId, String taal) {
    Map<String, String> landnamen = new HashMap<String, String>();
    if (landnamenCache.containsKey(landId)) {
      landnamen = landnamenCache.get(landId);
    }
 
    if (landnamen.containsKey(taal)) {
      return landnamen.get(taal);
    }
 
    LandnaamDto landnaamDto = landnaam(landId, taal);
    if (null != landnaamDto) {
      logger.debug(landnaamDto.toString());
      landnamen.put(taal, landnaamDto.getLandnaam());
      landnamenCache.put(landId, landnamen);
      return landnamen.get(taal);
    }
 
    if (landnamen.containsKey(getStandaardTaal())) {
      return landnamen.get(getStandaardTaal());
    }
 
    landnaamDto = landnaam(landId);
    if (null != landnaamDto) {
      logger.debug(landnaamDto.toString());
      landnamen.put(getStandaardTaal(), landnaamDto.getLandnaam());
      landnamenCache.put(landId, landnamen);
      return landnamen.get(taal);
    }
 
    return "???" + landId + ":" + taal + "???";
  }

  private String getStandaardTaal() {
    return standaardTaal;
  }

  /**
   * Geef een LandnaamDto.
   * 
   * @param landId
   * @return LandnaamDto
   */
  public LandnaamDto landnaam(Long landId) {
    return landnaam(landId, getStandaardTaal());
  }

  /**
   * Geef een LandnaamDto.
   * 
   * @param landId
   * @param taal
   * @return LandnaamDto
   */
  public LandnaamDto landnaam(Long landId, String taal) {
    LandnaamPK  sleutel   = new LandnaamPK(landId, taal);
    LandnaamDto landnaam  = landnaamDao.getByPrimaryKey(sleutel);

    return landnaam;
  }

  /**
   * Geef alle landnamen (en landId) in de gevraagde taal.
   *  
   * @return List<SelectItem>
   */
  @Override
  public List<SelectItem> selectLandnamen() {
    return selectLandnamen(getStandaardTaal());
  }

  /**
   * Geef alle landnamen (en landId) in de gevraagde taal als SelectItems.
   *  
   * @param taal
   * @return List<SelectItem>
   */
  @Override
  public List<SelectItem> selectLandnamen(String taal) {
    List<SelectItem>  items = new LinkedList<SelectItem>();
    List<LandnaamDto> rijen =
        new LinkedList<LandnaamDto>(landnaamDao.getPerTaal(taal));
    Collections.sort(rijen, new LandnaamDto.NaamComparator());
    for (LandnaamDto rij : rijen) {
      items.add(new SelectItem(rij.getLandId(), rij.getLandnaam()));
    }

    return items;
  }
  
  @Override
  public int size() {
    return landnamenCache.size();
  }
}
