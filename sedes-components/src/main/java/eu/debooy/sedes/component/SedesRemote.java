/*
 * Copyright (c) 2025 Marco de Booij
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

package eu.debooy.sedes.component;

import eu.debooy.sedes.component.business.ISedesRemote;
import eu.debooy.sedes.component.entity.Kontakt;
import eu.debooy.sedes.component.entity.Regio;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Collection;


/**
 * @author Marco de Booij
 */
@Named
@SessionScoped
public class SedesRemote implements Serializable {
  @EJB
  private ISedesRemote remote;

  protected SedesRemote() {}

  public void clear() {
    remote.clear();
  }

  public Collection<SelectItem> getSelectAdressen() {
    return remote.selectAdressen();
  }

  public Collection<SelectItem> getSelectKontakten() {
    return remote.selectKontakten();
  }

  public Collection<SelectItem> getSelectLandnamen() {
    return remote.selectLandnamen();
  }

  public Collection<SelectItem>  getSelectRegios() {
    return remote.getSelectRegios();
  }

  public int getLandnamenSize() {
    return remote.size();
  }

  public String i18nLandnaam(Long landId) {
    return remote.getI18nLandnaam(landId);
  }

  public String i18nLandnaam(Long landId, String taal) {
    return remote.getI18nLandnaam(landId, taal);
  }

  public Kontakt kontakt(Long kontaktId) {
    return remote.getKontakt(kontaktId);
  }

  public Regio regio(Long regioId, String taal) {
    return remote.getRegio(regioId, taal);
  }

  public Collection<SelectItem> selectLandnamen(String taal) {
    return remote.selectLandnamen(taal);
  }

  public Collection<SelectItem> selectRegios(String taal) {
    return remote.getSelectRegios(taal);
  }
}
