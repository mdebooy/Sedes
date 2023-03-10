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

package eu.debooy.sedes.access;

import eu.debooy.doosutils.access.Dao;
import eu.debooy.doosutils.errorhandling.handler.interceptor.PersistenceExceptionHandlerInterceptor;
import eu.debooy.sedes.domain.PlaatsDto;
import java.util.Collection;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;


/**
 * @author Marco de Booij
 */
@Interceptors({PersistenceExceptionHandlerInterceptor.class})
public class PlaatsDao extends Dao<PlaatsDto> {
  @PersistenceContext(unitName="sedes", type=PersistenceContextType.TRANSACTION)
  private EntityManager em;

  public PlaatsDao() {
    super(PlaatsDto.class);
  }

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public Collection<PlaatsDto> getPerLand(Long landId) {
    var query = getEntityManager().createNamedQuery(PlaatsDto.QRY_PERLAND)
                                  .setParameter(PlaatsDto.PAR_LANDID, landId);

    return query.getResultList();
  }

  public Collection<PlaatsDto> getPerRegio(Long regioId) {
    var query = getEntityManager().createNamedQuery(PlaatsDto.QRY_PERREGIO)
                                  .setParameter(PlaatsDto.PAR_REGIOID, regioId);

    return query.getResultList();
  }
}
