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
import eu.debooy.doosutils.domain.Dto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Comparator;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
@Entity
@Table(name="REGIONAMEN", schema="SEDES")
@IdClass(RegionaamPK.class)
@NamedQuery(name="regionamenPerTaal", query="select r from RegionaamDto r where r.taal=:taal")
public class RegionaamDto extends Dto implements Comparable<RegionaamDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  COL_REGIOID = "regioId";
  public static final String  COL_NAAM    = "naam";
  public static final String  COL_TAAL    = "taal";

  public static final String  PAR_TAAL  = "taal";

  public static final String  QRY_PERTAAL = "regionamenPerTaal";

  @Id
  @Column(name="REGIO_ID", nullable=false)
  private Long      regioId;
  @Column(name="NAAM", length=100, nullable=false)
  private String    naam;
  @Id
  @Column(name="TAAL", length=3, nullable=false)
  private String    taal;

  public static class NaamComparator
      implements Comparator<RegionaamDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(RegionaamDto regionaamDto1, RegionaamDto regionaamDto2) {
      return regionaamDto1.naam.compareTo(regionaamDto2.naam);
    }
  }

  @Override
  public int compareTo(RegionaamDto regionaamDto) {
    return new CompareToBuilder().append(regioId, regionaamDto.regioId)
                                 .append(taal, regionaamDto.taal)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof RegionaamDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var regionaamDto = (RegionaamDto) object;
    return new EqualsBuilder().append(regioId, regionaamDto.regioId)
                              .append(taal, regionaamDto.taal)
                              .isEquals();
  }

  public Long getRegioId() {
    return regioId;
  }

  public String getNaam() {
    return naam;
  }

  public String getTaal() {
    return taal;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(regioId).append(taal).toHashCode();
  }


  public void setRegioId(Long regioId) {
    this.regioId  = regioId;
  }

  public void setNaam(String naam) {
    this.naam     = DoosUtils.strip(naam);
  }


  public void setTaal(String taal) {
    this.taal     = DoosUtils.stripToLowerCase(taal);
  }
}
