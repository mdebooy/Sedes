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
@Table(name="LANDNAMEN", schema="SEDES")
@IdClass(LandnaamPK.class)
@NamedQueries({
  @NamedQuery(name="bestaandeLandenPerTaal", query="select n from LandnaamDto n, LandDto l where n.landId=l.landId and l.bestaat='J' and n.taal=:taal"),
  @NamedQuery(name="bestaandeLandenPerWerelddeelPerTaal", query="select n from LandnaamDto n, LandDto l where n.landId=l.landId and l.bestaat='J' and l.werelddeelId=:werelddeel and n.taal=:taal"),
  @NamedQuery(name="perLand", query="select l from LandnaamDto l where l.landId=:landId"),
  @NamedQuery(name="perTaal", query="select l from LandnaamDto l where l.taal=:taal")})
public class LandnaamDto extends Dto
    implements Comparable<LandnaamDto>, Cloneable {
  private static final  long  serialVersionUID  = 1L;

  @Column(name="HOOFDSTAD", length=100)
  private String    hoofdstad;
  @Id
  @Column(name="LAND_ID", nullable=false)
  private Long      landId;
  @Column(name="LANDNAAM", length=100, nullable=false)
  private String    landnaam;
  @Column(name="OFFICIELE_NAAM", length=255)
  private String    officieleNaam;
  @Id
  @Column(name="TAAL", length=2, nullable=false)
  private String    taal;

  /**
   * Sorteren op de naam van het land.
   */
  public static class NaamComparator
      implements Comparator<LandnaamDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(LandnaamDto landnaamDto1, LandnaamDto landnaamDto2) {
      return landnaamDto1.landnaam.compareTo(landnaamDto2.landnaam);
    }
  }

  public int compareTo(LandnaamDto landnaamDto) {
    return new CompareToBuilder().append(landId, landnaamDto.landId)
                                 .append(taal, landnaamDto.taal)
                                 .toComparison();
  }
  
  @Override
  public LandnaamDto clone() throws CloneNotSupportedException {
    LandnaamDto clone = (LandnaamDto) super.clone();

    return clone;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof LandnaamDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    LandnaamDto landnaamDto = (LandnaamDto) object;
    return new EqualsBuilder().append(landId, landnaamDto.landId)
                              .append(taal, landnaamDto.taal)
                              .isEquals();
  }

  /**
   * @return de hoofdstad
   */
  public String getHoofdstad() {
    return hoofdstad;
  }

  /**
   * @return de landId
   */
  public Long getLandId() {
    return landId;
  }

  /**
   * @return de landnaam
   */
  public String getLandnaam() {
    return landnaam;
  }

  /**
   * @return de officieleNaam
   */
  public String getOfficieleNaam() {
    return officieleNaam;
  }

  /**
   * @return de taal
   */
  public String getTaal() {
    return taal;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(landId).append(taal).toHashCode();
  }

  /**
   * @param hoofdstad de waarde van hoofdstad
   */
  public void setHoofdstad(String hoofdstad) {
    this.hoofdstad = hoofdstad;
  }

  /**
   * @param landId de waarde van landId
   */
  public void setLandId(Long landId) {
    this.landId = landId;
  }

  /**
   * @param landnaam de waarde van landnaam
   */
  public void setLandnaam(String landnaam) {
    this.landnaam = landnaam;
  }

  /**
   * @param officieleNaam de waarde van officieleNaam
   */
  public void setOfficieleNaam(String officieleNaam) {
    this.officieleNaam = officieleNaam;
  }

  /**
   * @param taal de waarde van taal
   */
  public void setTaal(String taal) {
    this.taal = taal;
  }
}
