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
import eu.debooy.sedes.access.WerelddeelDao;
import eu.debooy.sedes.domain.WerelddeelDto;
import eu.debooy.sedes.form.Werelddeel;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Singleton
@Named("sedesWerelddeelService")
@Lock(LockType.WRITE)
public class WerelddeelService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(WerelddeelService.class);

  @Inject
  private WerelddeelDao             werelddeelDao;

  /**
   * Initialisatie.
   */
  public WerelddeelService() {
    LOGGER.debug("init WerelddeelService");
  }


  /**
   * Verwijder de WerelddeelDto.
   * 
   * @param Long werelddeelId
   */
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void delete(Long werelddeelId) {
    WerelddeelDto dto = werelddeelDao.getByPrimaryKey(werelddeelId);
    werelddeelDao.delete(dto);
  }

  /**
   * Geef de werelddelen.
   * 
   * @return Collection<WerelddeelDto>
   */
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<Werelddeel> query() {
    Collection<Werelddeel>  werelddelen  = new ArrayList<Werelddeel>();
    try {
      Collection<WerelddeelDto> rijen    = werelddeelDao.getAll();
      for (WerelddeelDto rij : rijen) {
        werelddelen.add(new Werelddeel(rij));
      }
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return werelddelen;
  }

  /**
   * Maak of wijzig de Werelddeel in de database.
   * 
   * @param WerelddeelDto werelddee
   */
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void save(WerelddeelDto werelddeel) {
    if (null == werelddeel.getWerelddeelId()) {
      werelddeelDao.create(werelddeel);
      werelddeel.setWerelddeelId(werelddeel.getWerelddeelId());
    } else {
      werelddeelDao.update(werelddeel);
    }
  }

  /**
   * Geef het werelddeel
   * 
   * @param Long werelddeelId
   * @return WerelddeelDto
   */
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public WerelddeelDto werelddeel(Long werelddeelId) {
    WerelddeelDto werelddeel  = werelddeelDao.getByPrimaryKey(werelddeelId);

    return werelddeel;
  }
}
