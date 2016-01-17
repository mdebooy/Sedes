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

import eu.debooy.sedes.domain.LandnaamDto;

import java.io.Serializable;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author Marco de Booij
 */
public class Landnaam implements Cloneable, Comparable<Landnaam>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private boolean gewijzigd = false;

  private String    hoofdstad;
  private Long      landId;
  private String    landnaam;
  private String    officieleNaam;
  private String    taal;

  public Landnaam() {}

  public Landnaam(LandnaamDto landnaamDto) {
    hoofdstad     = landnaamDto.getHoofdstad();
    landId        = landnaamDto.getLandId();
    landnaam      = landnaamDto.getLandnaam();
    officieleNaam = landnaamDto.getOfficieleNaam();
    taal          = landnaamDto.getTaal();
  }

  @Override
  public Land clone() throws CloneNotSupportedException {
    Land  clone = (Land) super.clone();

    return clone;
  }

  @Override
  public int compareTo(Landnaam andere) {
    return new CompareToBuilder().append(landId, andere.landId)
                                 .append(taal, andere.taal)
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

    Landnaam  andere  = (Landnaam) object;
    return new EqualsBuilder().append(landId, andere.landId)
                              .append(taal, andere.taal).isEquals();
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

  /**
   * @return de officieleNaam
   */
  public String getOfficieleNaam() {
    return officieleNaam;
  }

  /**
   * @return de taal
   */
  public String getTaal() {
    return taal;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(landId).append(taal).toHashCode();
  }

  /**
   * @return de gewijzigd
   */
  public boolean isGewijzigd() {
    return gewijzigd;
  }

  /**
   * Zet de gegevens in een LandnaamDto
   *
   * @param LandnaamDto
   */
  public void persist(LandnaamDto parameter) {
    if (!new EqualsBuilder().append(hoofdstad,
                                    parameter.getHoofdstad()).isEquals()) {
      parameter.setHoofdstad(hoofdstad);
    }
    if (!new EqualsBuilder().append(landId,
                                    parameter.getLandId()).isEquals()) {
      parameter.setLandId(landId);
    }
    if (!new EqualsBuilder().append(landnaam,
                                    parameter.getLandnaam()).isEquals()) {
      parameter.setLandnaam(landnaam);
    }
    if (!new EqualsBuilder().append(officieleNaam,
                                    parameter.getOfficieleNaam()).isEquals()) {
      parameter.setOfficieleNaam(officieleNaam);
    }
    if (!new EqualsBuilder().append(taal,
                                    parameter.getTaal()).isEquals()) {
      parameter.setTaal(taal);
    }
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

  /**
   * @param officieleNaam de waarde van officieleNaam
   */
  public void setOfficieleNaam(String officieleNaam) {
    this.officieleNaam = officieleNaam;
  }

  /**
   * @param taal de waarde van taal
   */
  public void setTaal(String taal) {
    this.taal = taal;
  }
}
