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
package eu.debooy.sedes.service;

import eu.debooy.doosutils.service.JNDI;
import eu.debooy.sedes.component.business.II18nLandnaam;
import eu.debooy.sedes.domain.LandnaamDto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Singleton
@Named("sedesI18nLandnaamService")
@Lock(LockType.WRITE)
public class I18nLandnaamService implements II18nLandnaam {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(I18nLandnaamService.class);

  private transient LandnaamService landnaamService;

  private Map<Long, Map<String, String>>
      landnamenCache  = new HashMap<Long, Map<String, String>>();
  // TODO Ophalen uit database.
  private String        standaardTaal = "nl";

  /**
   * Maak de cache (voor de remote bean) leeg.
   */
  @Override
  public void clear() {
    landnamenCache.clear();
  }

  @Override
  public String getI18nLandnaam(Long landId) {
    return getI18nLandnaam(landId, getStandaardTaal());
  }

  @Override
  public String getI18nLandnaam(Long landId, String taal) {
    Map<String, String> landnamen = new HashMap<String, String>();
    if (landnamenCache.containsKey(landId)) {
      landnamen = landnamenCache.get(landId);
    }
 
    if (landnamen.containsKey(taal)) {
      return landnamen.get(taal);
    }
 
    LandnaamDto landnaamDto = getLandnaamService().landnaam(landId, taal);
    if (null != landnaamDto) {
      LOGGER.debug(landnaamDto.toString());
      landnamen.put(taal, landnaamDto.getLandnaam());
      landnamenCache.put(landId, landnamen);
      return landnamen.get(taal);
    }
 
    if (landnamen.containsKey(getStandaardTaal())) {
      return landnamen.get(getStandaardTaal());
    }
 
    landnaamDto = getLandnaamService().landnaam(landId, getStandaardTaal());
    if (null != landnaamDto) {
      LOGGER.debug(landnaamDto.toString());
      landnamen.put(getStandaardTaal(), landnaamDto.getLandnaam());
      landnamenCache.put(landId, landnamen);
      return landnamen.get(taal);
    }
 
    return "???" + landId + ":" + taal + "???";
  }

  /**
   * Geef de LandnaamService. Als die nog niet gekend is haal het dan op.
   * 
   * @return LandnaamService
   */
  private LandnaamService getLandnaamService() {
    if (null == landnaamService) {
      landnaamService = (LandnaamService)
          new JNDI.JNDINaam().metBean(LandnaamService.class).locate();
    }

    return landnaamService;
  }

  private String getStandaardTaal() {
    return standaardTaal;
  }

  @Override
  public Collection<SelectItem> selectLandnamen() {
    return selectLandnamen(getStandaardTaal());
  }

  @Override
  public Collection<SelectItem> selectLandnamen(String taal) {
    return getLandnaamService().selectLandnamen(taal);
  }

 
  @Override
  public int size() {
    return landnamenCache.size();
  }
}
