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

package eu.debooy.sedes.domain;

import eu.debooy.doosutils.DoosUtils;
import java.io.Serializable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class RegionaamPK implements Comparable<RegionaamPK>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private Long    regioId;
  private String  taal;

  public RegionaamPK() {}

  public RegionaamPK(Long regioId, String taal) {
    super();
    this.regioId  = regioId;
    this.taal     = taal;
  }

  @Override
  public int compareTo(RegionaamPK landnaamPK) {
    return new CompareToBuilder().append(regioId, landnaamPK.regioId)
                                 .append(taal, landnaamPK.taal)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof RegionaamPK)) {
      return false;
    }
    var landnaamPK = (RegionaamPK) object;
    return new EqualsBuilder().append(regioId, landnaamPK.regioId)
                              .append(taal, landnaamPK.taal)
                              .isEquals();
  }

  public Long getRegioId() {
    return regioId;
  }

  public String getTaal() {
    return taal;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(regioId)
                                .append(taal).toHashCode();
  }

  public void setRegioId(Long regioId) {
    this.regioId  = regioId;
  }

  public void setTaal(String taal) {
    this.taal     = DoosUtils.stripToLowerCase(taal);
  }

  @Override
  public String toString() {
    return new StringBuilder().append("RegionaamPK")
                              .append(" (regioId=").append(regioId)
                              .append(", taal=").append(taal)
                              .append(")").toString();
  }
}
