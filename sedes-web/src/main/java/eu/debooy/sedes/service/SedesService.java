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

import eu.debooy.doosutils.service.JNDI;
import eu.debooy.sedes.component.business.ISedesComponent;
import java.util.Collection;
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
@Named("sedesService")
@Lock(LockType.WRITE)
public class SedesService implements ISedesComponent {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(SedesService.class);

  private KontaktService  kontaktService;

  public SedesService() {
    LOGGER.debug("init SedesService");
  }

  private KontaktService getKontaktService() {
    if (null == kontaktService) {
      kontaktService = (KontaktService)
          new JNDI.JNDINaam().metBean(KontaktService.class).locate();
    }

    return kontaktService;
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<SelectItem> selectKontakten() {
    return getKontaktService().selectKontakten();
  }

}
