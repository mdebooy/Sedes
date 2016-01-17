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
import eu.debooy.sedes.domain.LandnaamDto;
import eu.debooy.sedes.domain.LandnaamPK;
import eu.debooy.sedes.form.Landnaam;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

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
public class LandnaamService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(LandnaamService.class);

  @Inject
  private LandnaamDao   landnaamDao;

  /**
   * Initialisatie.
   */
  public LandnaamService() {
    LOGGER.debug("init LandnaamService");
  }

  /**
   * Geef de landnamen van landen die nog bestaan.
   * 
   * @param taal
   * @return Collection<Landnaam>
   */
  public Collection<Landnaam> bestaandeLandnamen(String taal) {
    Set<Landnaam>           landnamen = new HashSet<Landnaam>();
    Collection<LandnaamDto> rijen     =
        landnaamDao.getBestaandeLandnamenPerTaal(taal);
    for (LandnaamDto rij : rijen) {
      landnamen.add(new Landnaam(rij));
    }

    return landnamen;
  }

  /**
   * Geef de landnamen van landen die nog bestaan voor een werelddeel.
   * 
   * @param String taal
   * @param Long werelddeelId
   * @return Collection<Landnaam>
   */
  public Collection<Landnaam>
      bestaandeLandnamenPerWerelddeel(String taal, Long werelddeelId) {
    Set<Landnaam>           landnamen = new HashSet<Landnaam>();
    Collection<LandnaamDto> rijen     =
        landnaamDao
            .getBestaandeLandnamenPerWerelddeelPerTaal(taal, werelddeelId);
    for (LandnaamDto rij : rijen) {
      landnamen.add(new Landnaam(rij));
    }

    return landnamen;
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
   * Geef de landnamen in een taal.
   * 
   * @param String taal
   * @return Collection<Landnaam>
   */
  public Collection<Landnaam> landnamen(String taal) {
    Set<Landnaam>           landnamen = new HashSet<Landnaam>();
    Collection<LandnaamDto> rijen     = landnaamDao.getPerTaal(taal);
    for (LandnaamDto rij : rijen) {
      landnamen.add(new Landnaam(rij));
    }

    return landnamen;
  }

  /**
   * Geef de landnamen van een land.
   * 
   * @param Long landId
   * @return Collection<Landnaam>
   */
  public Collection<Landnaam> lijst(Long landId) {
    Set<Landnaam>           landnamen = new HashSet<Landnaam>();
    Collection<LandnaamDto> rijen     = landnaamDao.getPerLand(landId);
    for (LandnaamDto rij : rijen) {
      landnamen.add(new Landnaam(rij));
    }

    return landnamen;
  }

  /**
   * Geef alle landnamen (en landId) in de gevraagde taal als SelectItems.
   *  
   * @param taal
   * @return Collection<SelectItem>
   */
  public Collection<SelectItem> selectLandnamen(String taal) {
    Collection<SelectItem>  items = new LinkedList<SelectItem>();
    Set<LandnaamDto> rijen =
        new TreeSet<LandnaamDto>(new LandnaamDto.NaamComparator());
    rijen.addAll(landnaamDao.getPerTaal(taal));
    for (LandnaamDto rij : rijen) {
      items.add(new SelectItem(rij.getLandId(), rij.getLandnaam()));
    }

    return items;
  }
}
