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

import java.io.Serializable;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author Marco de Booij
 */
public class Quizvraag implements Comparable<Quizvraag>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private String  antwoord;
  private String  hoofdstad;
  private Long    landId;
  private String  landnaam;

  public Quizvraag() {}

  public Quizvraag(Landnaam landnaam) {
    hoofdstad     = landnaam.getHoofdstad();
    landId        = landnaam.getLandId();
    this.landnaam = landnaam.getLandnaam(); 
  }

  @Override
  public int compareTo(Quizvraag andere) {
    return new CompareToBuilder().append(landId, andere.landId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Land)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    Quizvraag andere  = (Quizvraag) object;
    return new EqualsBuilder().append(landId, andere.landId).isEquals();
  }

  /**
   * @return de antwoord
   */
  public String getAntwoord() {
    return antwoord;
  }

  /**
   * @return de hoofdstad
   */
  public String getHoofdstad() {
    return hoofdstad;
  }

  /**
   * @return de landId
   */
  public Long getLandId() {
    return landId;
  }

  /**
   * @return de landnaam
   */
  public String getLandnaam() {
    return landnaam;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(landId).toHashCode();
  }

  public boolean isGoed() {
    return new EqualsBuilder().append(antwoord.toLowerCase(),
                                      hoofdstad.toLowerCase()).isEquals();
  }

  /**
   * @param antwoord de waarde van antwoord
   */
  public void setAntwoord(String antwoord) {
    this.antwoord = antwoord;
  }

  /**
   * @param hoofdstad de waarde van hoofdstad
   */
  public void setHoofdstad(String hoofdstad) {
    this.hoofdstad = hoofdstad;
  }

  /**
   * @param landId de waarde van landId
   */
  public void setLandId(Long landId) {
    this.landId = landId;
  }

  /**
   * @param landnaam de waarde van landnaam
   */
  public void setLandnaam(String landnaam) {
    this.landnaam = landnaam;
  }
}
