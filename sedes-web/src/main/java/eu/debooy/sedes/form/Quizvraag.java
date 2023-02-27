/**
 * Copyright 2016 Marco de Booij
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
package eu.debooy.sedes.form;

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.form.Formulier;
import eu.debooy.sedes.domain.LandnaamDto;
import java.io.Serializable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author Marco de Booij
 */
public class Quizvraag
    extends Formulier implements Comparable<Quizvraag>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private String  antwoord;
  private String  hoofdstad;
  private Long    landId;
  private String  naam;

  public Quizvraag() {}

  public Quizvraag(Landnaam landnaam) {
    hoofdstad = landnaam.getHoofdstad();
    landId    = landnaam.getLandId();
    naam      = landnaam.getNaam();
  }

  public Quizvraag(LandnaamDto landnaam) {
    hoofdstad = landnaam.getHoofdstad();
    landId    = landnaam.getLandId();
    naam      = landnaam.getNaam();
  }

  @Override
  public int compareTo(Quizvraag andere) {
    return new CompareToBuilder().append(landId, andere.landId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Quizvraag)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    Quizvraag andere  = (Quizvraag) object;
    return new EqualsBuilder().append(landId, andere.landId).isEquals();
  }

  public String getAntwoord() {
    return antwoord;
  }

  public String getHoofdstad() {
    return hoofdstad;
  }

  public Long getLandId() {
    return landId;
  }

  public String getNaam() {
    return naam;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(landId).toHashCode();
  }

  public boolean isGoed() {
    return new EqualsBuilder().append(DoosUtils.nullToEmpty(antwoord)
                                               .toLowerCase(),
                                      DoosUtils.nullToEmpty(hoofdstad)
                                               .toLowerCase()).isEquals();
  }

  public void setAntwoord(String antwoord) {
    this.antwoord   = antwoord;
  }

  public void setHoofdstad(String hoofdstad) {
    this.hoofdstad  = hoofdstad;
  }

  public void setLandId(Long landId) {
    this.landId     = landId;
  }

  public void setNaam(String naam) {
    this.naam       = naam;
  }
}
