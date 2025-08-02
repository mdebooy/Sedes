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

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.domain.Dto;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosLayer;
import eu.debooy.sedes.SedesConstants;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKey;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
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
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="REGIO_ID", nullable=false)
  private Long    regioId;
  @Column(name="REGIOKODE", length=5, nullable=false)
  private String  regiokode;

  @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, targetEntity=RegionaamDto.class, orphanRemoval=true)
  @JoinColumn(name="REGIO_ID", nullable=false, updatable=false, insertable=true)
  @MapKey(name="taal")
  private Map<String, RegionaamDto>  regionamen = new HashMap<>();

  /**
   * De regiokode is toegevoegd om dubbele namen niet te laten verdwijnen in een
   * Map.
   */
  public static class NaamComparator
      implements Comparator<RegioDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    private String  taal  = SedesConstants.DEF_TAAL;

    public void setTaal(String taal) {
      this.taal = taal;
    }

    @Override
    public int compare(RegioDto regioDto1, RegioDto regioDto2) {
      return new CompareToBuilder().append(regioDto1.getNaam(taal),
                                           regioDto2.getNaam(taal))
                                   .append(regioDto1.regiokode,
                                           regioDto2.regiokode)
                                   .toComparison();
    }
  }

  public void addRegionaam(RegionaamDto regionaamDto) {
    if (null == regionaamDto.getRegioId()) {
      regionaamDto.setRegioId(landId);
    }
    regionamen.put(regionaamDto.getTaal(), regionaamDto);
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

  @Transient
  public String getNaam(String taal) {
    if (regionamen.containsKey(taal)) {
      return regionamen.get(taal).getNaam();
    }

    return "";
  }

  public Long getRegioId() {
    return regioId;
  }

  public String getRegiokode() {
    return regiokode;
  }

  public RegionaamDto getRegionaam(String taal) {
    if (regionamen.containsKey(taal)) {
      return regionamen.get(taal);
    } else {
      throw new ObjectNotFoundException(DoosLayer.PERSISTENCE, taal);
    }
  }

  public Collection<RegionaamDto> getRegionamen() {
    return regionamen.values();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(regioId).toHashCode();
  }

  public boolean hasRegionaam(String taal) {
    return regionamen.containsKey(taal);
  }

  public void removeRegionaam(String taal) {
    if (regionamen.containsKey(taal)) {
      regionamen.remove(taal);
    } else {
      throw new ObjectNotFoundException(DoosLayer.PERSISTENCE, taal);
    }
  }

  public void setLandId(Long landId) {
    this.landId     = landId;
  }

  public void setRegioId(Long regioId) {
    this.regioId    = regioId;
  }

  public void setRegiokode(String regiokode) {
    this.regiokode  = DoosUtils.stripToUpperCase(regiokode);
  }
}
