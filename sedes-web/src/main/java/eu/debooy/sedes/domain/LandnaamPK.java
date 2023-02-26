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
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class LandnaamPK implements Comparable<LandnaamPK>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private Long    landId;
  private String  taal;

  public LandnaamPK() {}

  public LandnaamPK(Long landId, String taal) {
    super();
    this.landId  = landId;
    this.taal = taal;
  }

  @Override
  public int compareTo(LandnaamPK landnaamPK) {
    return new CompareToBuilder().append(landId, landnaamPK.landId)
                                 .append(taal, landnaamPK.taal)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof LandnaamPK)) {
      return false;
    }
    var landnaamPK = (LandnaamPK) object;
    return new EqualsBuilder().append(landId, landnaamPK.landId)
                              .append(taal, landnaamPK.taal)
                              .isEquals();
  }

  public Long getLandId() {
    return landId;
  }

  /**
   * @return String de taal
   */
  public String getTaal() {
    return taal;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(landId)
                                .append(taal).toHashCode();
  }

  public void setLandId(Long landId) {
    this.landId  = landId;
  }

  public void setTaal(String taal) {
    this.taal = taal;
  }

  @Override
  public String toString() {
    return new StringBuilder().append("LandnaamPK")
                              .append(" (landId=").append(landId)
                              .append(", taal=").append(taal)
                              .append(")").toString();
  }
}