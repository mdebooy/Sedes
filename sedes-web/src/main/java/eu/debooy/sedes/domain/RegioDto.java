/*
 * Copyright (c) 2023 Marco de Booij
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

import eu.debooy.doosutils.domain.Dto;
import java.io.Serializable;
import java.util.Comparator;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
@Entity
@Table(name="REGIOS", schema="SEDES")
public class RegioDto extends Dto implements Comparable<RegioDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  COL_LANDID    = "landId";
  public static final String  COL_REGIO     = "regio";
  public static final String  COL_REGIOKODE = "regiokode";
  public static final String  COL_REGIOID   = "regioId";

  @Column(name="LAND_ID", nullable=false)
  private Long    landId;
  @Column(name="REGIO", length=100, nullable=false)
  private String  regio;
  @Column(name="REGIOKODE", length=5, nullable=false)
  private String  regiokode;
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="REGIO_ID", nullable=false)
  private Long    regioId;

  /**
   * De regiokode is toegevoegd om dubbele namen niet te laten verdwijnen in een
   * Map.
   */
  public static class NaamComparator
      implements Comparator<RegioDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(RegioDto regioDto1, RegioDto regioDto2) {
      return new CompareToBuilder().append(regioDto1.regio, regioDto2.regio)
                                   .append(regioDto1.regiokode,
                                           regioDto2.regiokode)
                                   .toComparison();
    }
  }

  @Override
  public int compareTo(RegioDto regioDto) {
    return new CompareToBuilder().append(regioId, regioDto.regioId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof RegioDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var regioDto  = (RegioDto) object;
    return new EqualsBuilder().append(regioId, regioDto.regioId)
                              .isEquals();
  }

  public Long getLandId() {
    return landId;
  }

  public String getRegio() {
    return regio;
  }

  public String getRegiokode() {
    return regiokode;
  }

  public Long getRegioId() {
    return regioId;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(regioId).toHashCode();
  }

  public void setLandId(Long landId) {
    this.landId     = landId;
  }

  public void setRegio(String regio) {
    this.regio      = regio;
  }

  public void setRegiokode(String regiokode) {
    this.regiokode  = regiokode;
  }

  public void setRegioId(Long regioId) {
    this.regioId    = regioId;
  }
}
