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
package eu.debooy.sedes.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class LandnaamPK
    implements Cloneable, Comparable<LandnaamPK>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private Long    landId;
  private String  taal;

  /**
   * Standaard constructor.
   */
  public LandnaamPK() {}

  /**
   * @param Long landId
   * @param Long taal
   */
  public LandnaamPK(Long landId, String taal) {
    super();
    this.landId  = landId;
    this.taal = taal;
  }

  public LandnaamPK clone() throws CloneNotSupportedException {
    LandnaamPK  clone = (LandnaamPK) super.clone();

    return clone;
  }

  public int compareTo(LandnaamPK landnaamPK) {
    return new CompareToBuilder().append(landId, landnaamPK.landId)
                                 .append(taal, landnaamPK.taal)
                                 .toComparison();
  }

  public boolean equals(Object object) {
    if (!(object instanceof LandnaamPK)) {
      return false;
    }
    LandnaamPK landnaamPK = (LandnaamPK) object;
    return new EqualsBuilder().append(landId, landnaamPK.landId)
                              .append(taal, landnaamPK.taal)
                              .isEquals();
  }

  /**
   * @return Long de landId
   */
  public Long getLandId() {
    return landId;
  }

  /**
   * @return String de taal
   */
  public String getTaal() {
    return taal;
  }

  public int hashCode() {
    return new HashCodeBuilder().append(landId)
                                .append(taal).toHashCode();
  }

  /**
   * @param Long landId de waarde van landId
   */
  public void setLandId(Long landId) {
    this.landId  = landId;
  }

  /**
   * @param String taal de waarde van taal
   */
  public void setTaal(String taal) {
    this.taal = taal;
  }

  public String toString() {
    return new StringBuilder().append("LandnaamPK")
                              .append(" (landId=").append(landId)
                              .append(", taal=").append(taal)
                              .append(")").toString();
  }
}