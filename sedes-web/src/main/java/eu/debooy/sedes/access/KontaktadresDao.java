/*
 * Copyright (c) 2024 Marco de Booij
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
import eu.debooy.sedes.domain.KontaktDto;
import eu.debooy.sedes.domain.KontaktadresDto;
import java.util.Collection;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;


/**
 * @author Marco de Booij
 */
@Interceptors({PersistenceExceptionHandlerInterceptor.class})
public class KontaktadresDao extends Dao<KontaktadresDto> {
  @PersistenceContext(unitName="sedes", type=PersistenceContextType.TRANSACTION)
  private EntityManager em;

  public KontaktadresDao() {
    super(KontaktadresDto.class);
  }

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public Collection<KontaktDto> getPerAdres(Long adresId) {
    var query =
            getEntityManager().createNamedQuery(KontaktadresDto.QRY_PERADRES)
                              .setParameter(KontaktadresDto.PAR_ADRES, adresId);

    return query.getResultList();
  }

  public Collection<KontaktDto> getPerKontakt(Long kontaktId) {
    var query =
            getEntityManager().createNamedQuery(KontaktadresDto.QRY_PERKONTAKT)
                              .setParameter(KontaktadresDto.PAR_KONTAKT,
                                            kontaktId);

    return query.getResultList();
  }
}
