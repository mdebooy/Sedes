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

import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.service.JNDI;
import eu.debooy.sedes.component.business.II18nLandnaam;
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


/**
 * @author Marco de Booij
 */
@Singleton
@Named("sedesI18nLandnaamService")
@Lock(LockType.WRITE)
public class I18nLandnaamService implements II18nLandnaam {
  private transient LandnaamService landnaamService;

  private Map<Long, Map<String, String>>
      landnamenCache  = new HashMap<Long, Map<String, String>>();
  // TODO Ophalen uit database.
  private String        standaardTaal = "nl";

  /**
   * Maak de cache (voor de remote bean) leeg.
   */
  public void clear() {
    landnamenCache.clear();
  }

  public String getI18nLandnaam(Long landId) {
    return getI18nLandnaam(landId, getStandaardTaal());
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public String getI18nLandnaam(Long landId, String taal) {
    Map<String, String> landnamen = new HashMap<String, String>();
    if (landnamenCache.containsKey(landId)) {
      landnamen = landnamenCache.get(landId);
    }
 
    if (landnamen.containsKey(taal)) {
      return landnamen.get(taal);
    }
 
    LandnaamDto landnaamDto;
    try {
      landnaamDto = getLandnaamService().landnaam(landId, taal);
      landnamen.put(taal, landnaamDto.getLandnaam());
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
      landnamen.put(getStandaardTaal(), landnaamDto.getLandnaam());
      landnamenCache.put(landId, landnamen);
      return landnamen.get(taal);
    } catch (ObjectNotFoundException e) {
      return "???" + landId + ":" + taal + "???";
    }
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

  public Collection<SelectItem> selectLandnamen() {
    return selectLandnamen(getStandaardTaal());
  }

  public Collection<SelectItem> selectLandnamen(String taal) {
    return getLandnaamService().selectLandnamen(taal);
  }

  public int size() {
    return landnamenCache.size();
  }
}
