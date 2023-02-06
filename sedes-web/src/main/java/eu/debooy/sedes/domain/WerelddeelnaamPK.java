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
package eu.debooy.sedes.domain;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class WerelddeelnaamPK implements Serializable {
  private static final  long  serialVersionUID  = 1L;

  private String  taal;
  private Long    werelddeelId;

  public WerelddeelnaamPK() {}

  public WerelddeelnaamPK(String taal, Long werelddeelId) {
    this.taal         = taal;
    this.werelddeelId = werelddeelId;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof WerelddeelnaamPK)) {
      return false;
    }
    WerelddeelnaamPK  werelddeelnaamPK  = (WerelddeelnaamPK) object;
    return new EqualsBuilder().append(werelddeelId,
                                      werelddeelnaamPK.werelddeelId)
                              .append(taal, werelddeelnaamPK.taal)
                              .isEquals();
  }

  public String getTaal() {
    return taal;
  }

  public Long getWerelddeelId() {
    return werelddeelId;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(werelddeelId)
                                .append(taal).toHashCode();
  }

  public void setTaal(String taal) {
    this.taal = taal;
  }

  public void setWerelddeelId(Long werelddeelId) {
    this.werelddeelId = werelddeelId;
  }

  @Override
  public String toString() {
    return new StringBuilder().append("WerelddeelnaamPK")
                              .append(", taal=").append(taal)
                              .append(" (werelddeelId=").append(werelddeelId)
                              .append(")").toString();
  }
}