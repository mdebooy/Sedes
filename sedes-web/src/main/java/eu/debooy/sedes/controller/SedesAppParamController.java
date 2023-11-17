/**
 * Copyright 2017 Marco de Booij
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
package eu.debooy.sedes.controller;

import eu.debooy.doos.controller.AppParamController;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


/**
 * @author Marco de Booij
 */
@Named("sedesAppParam")
@SessionScoped
public class SedesAppParamController extends AppParamController {
  private static final  long  serialVersionUID  = 1L;

  public SedesAppParamController() {
    initSpeciaal();
    addSpeciaal("sedes.default.landid");
    addSpeciaal("sedes.default.taal");
    addSpeciaal("sedes.kontakt.type");
  }

  public Long getLandId() {
    return Long.valueOf(getWaarde());
  }

  public String getKontakttype() {
    return getWaarde();
  }

  public String getTaal() {
    return getWaarde();
  }

  public void setLandId(Long landId) {
    setWaarde(String.valueOf(landId));
  }

  public void setKontakttype(String kontakttype) {
    setWaarde(kontakttype);
  }

  public void setTaal(String taal) {
    setWaarde(taal);
  }
}
