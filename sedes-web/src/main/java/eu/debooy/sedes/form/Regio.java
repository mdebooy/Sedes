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

package eu.debooy.sedes.form;

import eu.debooy.doosutils.form.Formulier;
import eu.debooy.sedes.domain.RegioDto;
import java.io.Serializable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Regio
    extends Formulier implements Comparable<Regio>, Serializable {
  private Long    landId;
  private Long    regioId;
  private String  regiokode;
  private String  naam;

  public Regio() {}

  public Regio(RegioDto regioDto) {
    landId    = regioDto.getLandId();
    regioId   = regioDto.getRegioId();
    regiokode = regioDto.getRegiokode();
    naam      = regioDto.getNaam();
  }

  @Override
  public int compareTo(Regio andere) {
    return new CompareToBuilder().append(regiokode, andere.regiokode)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Regio)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var andere  = (Regio) object;
    return new EqualsBuilder().append(regiokode, andere.regiokode)
                              .isEquals();
  }

  public Long getLandId() {
    return landId;
  }

  public Long getRegioId() {
    return regioId;
  }

  public String getRegiokode() {
    return regiokode;
  }

  public String getNaam() {
    return naam;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(regiokode).toHashCode();
  }

  public void persist(RegioDto regioDto) {
    regioDto.setLandId(getLandId());
    regioDto.setRegioId(getRegioId());
    regioDto.setRegiokode(getRegiokode());
    regioDto.setNaam(getNaam());
  }

  public void setLandId(Long landId) {
    this.landId     = landId;
  }

  public void setRegioId(Long regioId) {
    this.regioId    = regioId;
  }

  public void setRegiokode(String regiokode) {
    this.regiokode  = regiokode;
  }

  public void setNaam(String naam) {
    this.naam       = naam;
  }
}
