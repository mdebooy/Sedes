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
package eu.debooy.sedes.component;

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.components.bean.Gebruiker;
import eu.debooy.doosutils.service.CDI;
import eu.debooy.sedes.component.business.II18nLandnaam;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;


/**
 * @author Marco de Booij
 */
@Named
@SessionScoped
public class I18nLandnaam implements Serializable {
  private static final  long  serialVersionUID  = 1L;

  // TODO Uit de database halen.
  private String        taal              = "nl";

  @EJB
  private II18nLandnaam i18nLandnaamBean;
  
  private Gebruiker     gebruiker;

  /**
   * Geef de naam van het land in de taal van de Gebruiker.
   * 
   * @param landId
   * @return String
   */
  public String landnaam(Long landId) {
    if (null == gebruiker) {
      gebruiker = (Gebruiker) CDI.getBean("gebruiker");
      if (null != gebruiker) {
        taal  = gebruiker.getLocale().getLanguage();
      }
    }

    return landnaam(landId, taal);
  }

  /**
   * Geef de naam van het land in de gevraagde taal.
   * 
   * @param landId
   * @param taal
   * @return String
   */
  public String landnaam(Long landId, String taal) {
    if (DoosUtils.isBlankOrNull(landId)) {
      return "<null>";
    }

    return i18nLandnaamBean.getI18nLandnaam(landId, taal);
  }

  /**
   * Geef alle landnamen (en landId) in de gevraagde taal.
   *  
   * @return List<SelectItem>
   */
  public List<SelectItem> selectLandnamen() {
    return i18nLandnaamBean.selectLandnamen();
  }

  /**
   * Geef alle landnamen (en landId) in de gevraagde taal.
   *  
   * @param taal
   * @return List<SelectItem>
   */
  public List<SelectItem> selectLandnamen(String taal) {
    return i18nLandnaamBean.selectLandnamen(taal);
  }
}
