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

import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.sedes.access.WerelddeelnaamDao;
import eu.debooy.sedes.domain.WerelddeelnaamDto;
import eu.debooy.sedes.domain.WerelddeelnaamPK;
import eu.debooy.sedes.form.Werelddeelnaam;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Singleton
@Named("sedesWerelddeelnaamService")
@Lock(LockType.WRITE)
public class WerelddeelnaamService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(WerelddeelnaamService.class);

  @Inject
  private WerelddeelnaamDao werelddeelnaamDao;

  /**
   * Initialisatie.
   */
  public WerelddeelnaamService() {
    LOGGER.debug("init WerelddeelnaamService");
  }

  /**
   * Geef een WerelddeelnaamDto.
   * 
   * @param werelddeelId
   * @param taal
   * @return WerelddeelnaamDto
   */
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public WerelddeelnaamDto werelddeelnaam(Long werelddeelId, String taal) {
    WerelddeelnaamPK  sleutel         =
        new WerelddeelnaamPK(taal, werelddeelId);
    WerelddeelnaamDto werelddeelnaam  =
        werelddeelnaamDao.getByPrimaryKey(sleutel);

    return werelddeelnaam;
  }

  /**
   * Geef de landnamen in een taal.
   * 
   * @param String taal
   * @return Collection<Werelddeelnaam>
   */
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<Werelddeelnaam> werelddeelnamen(String taal) {
    Set<Werelddeelnaam>             werelddeelnamen =
        new HashSet<Werelddeelnaam>();
    try {
      Collection<WerelddeelnaamDto> rijen           =
          werelddeelnaamDao.getPerTaal(taal);
      for (WerelddeelnaamDto rij : rijen) {
        werelddeelnamen.add(new Werelddeelnaam(rij));
      }
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return werelddeelnamen;
  }

  /**
   * Geef de werelddeelnamen van een werelddeel.
   * 
   * @param Long werelddeelId
   * @return Collection<Werelddeelnaam>
   */
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<Werelddeelnaam> query(Long werelddeelId) {
    Set<Werelddeelnaam>             werelddeelnamen =
        new HashSet<Werelddeelnaam>();
    try {
      Collection<WerelddeelnaamDto> rijen           =
          werelddeelnaamDao.getPerWerelddeel(werelddeelId);
      for (WerelddeelnaamDto rij : rijen) {
        werelddeelnamen.add(new Werelddeelnaam(rij));
      }
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return werelddeelnamen;
  }

  /**
   * Geef alle werelddeelnamen (en werelddeelId) in de gevraagde taal als
   * SelectItems.
   *  
   * @param taal
   * @return Collection<SelectItem>
   */
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<SelectItem> selectWerelddeelnamen(String taal) {
    Collection<SelectItem> items = new LinkedList<SelectItem>();
    Set<WerelddeelnaamDto> rijen =
        new TreeSet<WerelddeelnaamDto>(new WerelddeelnaamDto.NaamComparator());
    try {
      rijen.addAll(werelddeelnaamDao.getPerTaal(taal));
      for (WerelddeelnaamDto rij : rijen) {
        items.add(new SelectItem(rij.getWerelddeelId(),
                                 rij.getWerelddeelnaam()));
      }
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return items;
  }
}
