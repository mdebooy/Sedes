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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author Marco de Booij
 */
@Entity
@Table(name="LANDEN", schema="SEDES")
public class LandDto extends Dto implements Comparable<LandDto>, Cloneable {
  private static final  long  serialVersionUID  = 1L;

  @Column(name="BESTAAT", length=1, nullable=false)
  private String  bestaat;
  @Column(name="ISO2", length=2)
  private String  iso2;
  @Column(name="ISO3", length=3, nullable=false)
  private String  iso3;
  @Id
  @Column(name="LAND_ID", nullable=false)
  private Long    landId;
  @Column(name="LANDNUMMER")
  private Long    landnummer;
  // TODO Vervangen als MuntDto klaar is.
  @Column(name="MUNT_ID", nullable=false)
  private Long    muntId;
  @Column(name="POSTKODE_SCHEIDING", length=10)
  private String  postkodeScheiding;
  @Column(name="ISO3", length=1, nullable=false)
  private String  postkodeType;
  @Column(name="POST_LANDKODE", length=3, nullable=false)
  private String  postLandkode;
  @Column(name="TAAL", length=2, nullable=false)
  private String  taal;
  @Column(name="VLAG")
  private byte[]  vlag;
  // TODO Vervangen als WerelddeelDto klaar is.
  @Column(name="WERELDDEEL_ID", nullable=false)
  private Long    werelddeelId;

  public int compareTo(LandDto landDto) {
    return new CompareToBuilder().append(landId, landDto.landId)
                                 .toComparison();
  }
  
  @Override
  public LandDto clone() throws CloneNotSupportedException {
    LandDto clone = (LandDto) super.clone();

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

    LandDto landDto = (LandDto) object;
    return new EqualsBuilder().append(landId, landDto.landId)
                              .isEquals();
  }

  /**
   * @return de bestaat
   */
  public String getBestaat() {
    return bestaat;
  }

  /**
   * @return de iso2
   */
  public String getIso2() {
    return iso2;
  }

  /**
   * @return de iso3
   */
  public String getIso3() {
    return iso3;
  }

  /**
   * @return de landId
   */
  public Long getLandId() {
    return landId;
  }

  /**
   * @return de landnummer
   */
  public Long getLandnummer() {
    return landnummer;
  }

  /**
   * @return de muntId
   */
  public Long getMuntId() {
    return muntId;
  }

  /**
   * @return de postkodeScheiding
   */
  public String getPostkodeScheiding() {
    return postkodeScheiding;
  }

  /**
   * @return de postkodeType
   */
  public String getPostkodeType() {
    return postkodeType;
  }

  /**
   * @return de postLandkode
   */
  public String getPostLandkode() {
    return postLandkode;
  }

  /**
   * @return de taal
   */
  public String getTaal() {
    return taal;
  }

  /**
   * @return de vlag
   */
  public byte[] getVlag() {
    return vlag;
  }

  /**
   * @return de werelddeelId
   */
  public Long getWerelddeelId() {
    return werelddeelId;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(landId).toHashCode();
  }

  /**
   * @param bestaat de waarde van bestaat
   */
  public void setBestaat(String bestaat) {
    this.bestaat = bestaat;
  }

  /**
   * @param iso2 de waarde van iso2
   */
  public void setIso2(String iso2) {
    this.iso2 = iso2;
  }

  /**
   * @param iso3 de waarde van iso3
   */
  public void setIso3(String iso3) {
    this.iso3 = iso3;
  }

  /**
   * @param landId de waarde van landId
   */
  public void setLandId(Long landId) {
    this.landId = landId;
  }

  /**
   * @param landnummer de waarde van landnummer
   */
  public void setLandnummer(Long landnummer) {
    this.landnummer = landnummer;
  }

  /**
   * @param muntId de waarde van muntId
   */
  public void setMuntId(Long muntId) {
    this.muntId = muntId;
  }

  /**
   * @param postkodeScheiding de waarde van postkodeScheiding
   */
  public void setPostkodeScheiding(String postkodeScheiding) {
    this.postkodeScheiding = postkodeScheiding;
  }

  /**
   * @param postkodeType de waarde van postkodeType
   */
  public void setPostkodeType(String postkodeType) {
    this.postkodeType = postkodeType;
  }

  /**
   * @param postLandkode de waarde van postLandkode
   */
  public void setPostLandkode(String postLandkode) {
    this.postLandkode = postLandkode;
  }

  /**
   * @param taal de waarde van taal
   */
  public void setTaal(String taal) {
    this.taal = taal;
  }

  /**
   * @param vlag de waarde van vlag
   */
  public void setVlag(byte[] vlag) {
    this.vlag = vlag;
  }

  /**
   * @param werelddeelId de waarde van werelddeelId
   */
  public void setWerelddeelId(Long werelddeelId) {
    this.werelddeelId = werelddeelId;
  }
}
