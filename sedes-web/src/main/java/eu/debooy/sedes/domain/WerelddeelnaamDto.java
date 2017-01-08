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
import javax.persistence.NamedQueries;
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
@NamedQueries({
  @NamedQuery(name="perWerelddeel", query="select w from WerelddeelnaamDto w where w.werelddeelId=:werelddeelId"),
  @NamedQuery(name="wereldeelnamenPerTaal", query="select w from WerelddeelnaamDto w where w.taal=:taal")})
public class WerelddeelnaamDto extends Dto
    implements Comparable<WerelddeelnaamDto>, Cloneable {
  private static final  long  serialVersionUID  = 1L;

  @Id
  @Column(name="TAAL", length=2, nullable=false)
  private String  taal;
  @Id
  @Column(name="WERELDDEEL_ID", nullable=false)
  private Long    werelddeelId;
  @Column(name="WERELDDEELNAAM", length=100, nullable=false)
  private String  werelddeelnaam;

  /**
   * Sorteren op de naam van het werelddeel.
   */
  public static class NaamComparator
      implements Comparator<WerelddeelnaamDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    public int compare(WerelddeelnaamDto werelddeelnaamDto1,
                       WerelddeelnaamDto werelddeelnaamDto2) {
      return werelddeelnaamDto1.werelddeelnaam
                               .compareTo(werelddeelnaamDto2.werelddeelnaam);
    }
  }
  
  public WerelddeelnaamDto clone() throws CloneNotSupportedException {
    WerelddeelnaamDto clone = (WerelddeelnaamDto) super.clone();

    return clone;
  }

  public int compareTo(WerelddeelnaamDto werelddeelnaamDto) {
    return new CompareToBuilder().append(werelddeelId,
                                         werelddeelnaamDto.werelddeelId)
                                 .append(taal, werelddeelnaamDto.taal)
                                 .toComparison();
  }

  public boolean equals(Object object) {
    if (!(object instanceof WerelddeelnaamDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    WerelddeelnaamDto werelddeelnaamDto = (WerelddeelnaamDto) object;
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
