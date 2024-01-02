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
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
@Entity
@Table(name="REGIOS", schema="SEDES")
@NamedQuery(name="regiosPerLand", query="select r from RegioDto r where r.landId=:landId")
@NamedQuery(name="regiosNuts0", query="select r from RegioDto r where length(r.regiokode)=2")
@NamedQuery(name="regiosNuts1", query="select r from RegioDto r where length(r.regiokode)=3")
@NamedQuery(name="regiosNuts2", query="select r from RegioDto r where length(r.regiokode)=4")
@NamedQuery(name="regiosNuts3", query="select r from RegioDto r where length(r.regiokode)=5")
public class RegioDto extends Dto implements Comparable<RegioDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  COL_LANDID    = "landId";
  public static final String  COL_NAAM      = "naam";
  public static final String  COL_REGIOKODE = "regiokode";
  public static final String  COL_REGIOID   = "regioId";

  public static final String  PAR_LANDID  = "landId";

  public static final String  QRY_PERLAND = "regiosPerLand";
  public static final String  QRY_NUTS0   = "regiosNuts0";
  public static final String  QRY_NUTS1   = "regiosNuts1";
  public static final String  QRY_NUTS2   = "regiosNuts2";
  public static final String  QRY_NUTS3   = "regiosNuts3";

  @Column(name="LAND_ID", nullable=false)
  private Long    landId;
  @Column(name="NAAM", length=100, nullable=false)
  private String  naam;
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="REGIO_ID", nullable=false)
  private Long    regioId;
  @Column(name="REGIOKODE", length=5, nullable=false)
  private String  regiokode;

  /**
   * De regiokode is toegevoegd om dubbele namen niet te laten verdwijnen in een
   * Map.
   */
  public static class NaamComparator
      implements Comparator<RegioDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(RegioDto regioDto1, RegioDto regioDto2) {
      return new CompareToBuilder().append(regioDto1.naam,
                                           regioDto2.naam)
                                   .append(regioDto1.regiokode,
                                           regioDto2.regiokode)
                                   .toComparison();
    }
  }

  @Override
  public int compareTo(RegioDto regioDto) {
    return new CompareToBuilder().append(regiokode, regioDto.regiokode)
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

  public String getNaam() {
    return naam;
  }

  public Long getRegioId() {
    return regioId;
  }

  public String getRegiokode() {
    return regiokode;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(regioId).toHashCode();
  }

  public void setLandId(Long landId) {
    this.landId     = landId;
  }

  public void setNaam(String naam) {
    this.naam       = naam;
  }

  public void setRegioId(Long regioId) {
    this.regioId    = regioId;
  }

  public void setRegiokode(String regiokode) {
    this.regiokode  = regiokode;
  }
}
