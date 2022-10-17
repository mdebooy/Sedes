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
package eu.debooy.sedes.access;

import eu.debooy.doosutils.access.Dao;
import eu.debooy.doosutils.errorhandling.handler.interceptor.PersistenceExceptionHandlerInterceptor;
import eu.debooy.sedes.domain.LandnaamDto;
import java.util.Collection;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;


/**
 * @author Marco de Booij
 */
@Interceptors({PersistenceExceptionHandlerInterceptor.class})
public class LandnaamDao extends Dao<LandnaamDto> {
  @PersistenceContext(unitName="sedes", type=PersistenceContextType.TRANSACTION)
  private EntityManager em;

  public LandnaamDao() {
    super(LandnaamDto.class);
  }

  public Collection<LandnaamDto> getBestaandeLandnamenPerTaal(String taal) {
    Query   query         =
        getEntityManager().createNamedQuery(LandnaamDto.QRY_BESTPERTAAL)
                          .setParameter(LandnaamDto.PAR_TAAL, taal);

    return query.getResultList();
  }

  public Collection<LandnaamDto>
      getBestaandeLandnamenPerWerelddeelPerTaal(String taal,
                                                Long werelddeelId) {
    Query   query         =
        getEntityManager()
            .createNamedQuery(LandnaamDto.QRY_BESTPERWERELDDEELTAAL)
                          .setParameter(LandnaamDto.PAR_TAAL, taal)
                          .setParameter(LandnaamDto.PAR_WERELDDEEL,
                                        werelddeelId);

    return query.getResultList();
  }

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public Collection<LandnaamDto> getPerLand(Long landId) {
    Query   query         =
        getEntityManager().createNamedQuery(LandnaamDto.QRY_PERLAND)
                          .setParameter(LandnaamDto.PAR_LANDID, landId);

    return query.getResultList();
  }

  public Collection<LandnaamDto> getPerTaal(String taal) {
    Query   query         =
        getEntityManager().createNamedQuery(LandnaamDto.QRY_PERTAAL)
                          .setParameter(LandnaamDto.PAR_TAAL, taal);

    return query.getResultList();
  }
}
