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

package eu.debooy.sedes.component.business;

import eu.debooy.sedes.component.entity.Kontakt;
import eu.debooy.sedes.component.entity.Regio;
import java.util.Collection;
import javax.ejb.Remote;
import javax.faces.model.SelectItem;


/**
 * @author Marco de Booij
 */
@Remote
public interface ISedesRemote {
  void                    clear();
  // Voor iedereen
  String                  getI18nLandnaam(Long landId);
  String                  getI18nLandnaam(Long landId, String taal);
  Kontakt                 getKontakt(Long kontaktId);
  Collection<SelectItem>  selectKontakten();
  Collection<SelectItem>  selectLandnamen();
  Collection<SelectItem>  selectLandnamen(String taal);
  Collection<SelectItem>  getSelectRegios();
  Regio                   getRegio(Long regioId);
  int                     size();
}
