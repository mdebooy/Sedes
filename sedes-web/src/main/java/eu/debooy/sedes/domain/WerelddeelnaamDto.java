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

import eu.debooy.doosutils.domain.Dto;
import java.io.Serializable;
import java.util.Comparator;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
@Entity
@Table(name="WERELDDEELNAMEN", schema="SEDES")
@IdClass(WerelddeelnaamPK.class)
@NamedQuery(name="wereldeelnamenPerTaal", query="select w from WerelddeelnaamDto w where w.taal=:taal")
@NamedQuery(name="wereldeelnamenPerWerelddeel", query="select w from WerelddeelnaamDto w where w.werelddeelId=:werelddeelId")
public class WerelddeelnaamDto extends Dto
    implements Comparable<WerelddeelnaamDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  COL_TAAL            = "taal";
  public static final String  COL_WERELDDEELID    = "werelddeelId";
  public static final String  COL_WERELDDEELNAAM  = "werelddeelnaam";

  public static final String  PAR_TAAL          = "taal";
  public static final String  PAR_WERELDDEELID  = "werelddeelId";

  public static final String  QRY_PERWERELDDEEL = "wereldeelnamenPerTaal";
  public static final String  QRY_PERTAAL       = "wereldeelnamenPerWerelddeel";

  @Id
  @Column(name="TAAL", length=2, nullable=false)
  private String  taal;
  @Id
  @Column(name="WERELDDEEL_ID", nullable=false)
  private Long    werelddeelId;
  @Column(name="WERELDDEELNAAM", length=100, nullable=false)
  private String  werelddeelnaam;

  public static class NaamComparator
      implements Comparator<WerelddeelnaamDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(WerelddeelnaamDto werelddeelnaamDto1,
                       WerelddeelnaamDto werelddeelnaamDto2) {
      return werelddeelnaamDto1.werelddeelnaam
                               .compareTo(werelddeelnaamDto2.werelddeelnaam);
    }
  }

  @Override
  public int compareTo(WerelddeelnaamDto werelddeelnaamDto) {
    return new CompareToBuilder().append(werelddeelId,
                                         werelddeelnaamDto.werelddeelId)
                                 .append(taal, werelddeelnaamDto.taal)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof WerelddeelnaamDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var werelddeelnaamDto = (WerelddeelnaamDto) object;
    return new EqualsBuilder().append(werelddeelId,
                                      werelddeelnaamDto.werelddeelId)
                              .append(taal, werelddeelnaamDto.taal)
                              .isEquals();
  }

  public String getTaal() {
    return taal;
  }

  public Long getWerelddeelId() {
    return werelddeelId;
  }

  public String getWerelddeelnaam() {
    return werelddeelnaam;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(werelddeelId).append(taal).toHashCode();
  }

  public void setTaal(String taal) {
    this.taal = taal;
  }

  public void setWerelddeelId(Long werelddeelId) {
    this.werelddeelId = werelddeelId;
  }

  public void setWerelddeelnaam(String werelddeelnaam) {
    this.werelddeelnaam = werelddeelnaam;
  }
}
