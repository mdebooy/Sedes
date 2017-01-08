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

import eu.debooy.doosutils.DoosConstants;
import eu.debooy.doosutils.domain.Dto;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosLayer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
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
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="LAND_ID", nullable=false)
  private Long    landId;
  @Column(name="LANDNUMMER")
  private Long    landnummer;
  @Column(name="MUNT_ID", nullable=false)
  private Long    muntId;
  @Column(name="POSTKODE_SCHEIDING", length=10)
  private String  postkodeScheiding;
  @Column(name="POSTKODE_TYPE", length=1, nullable=false)
  private String  postkodeType;
  @Column(name="POST_LANDKODE", length=3, nullable=false)
  private String  postLandkode;
  @Column(name="TAAL", length=2, nullable=false)
  private String  taal;
  @Column(name="WERELDDEEL_ID", nullable=false)
  private Long    werelddeelId;

  @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, targetEntity=LandnaamDto.class, orphanRemoval=true)
  @JoinColumn(name="LAND_ID", nullable=false, updatable=false, insertable=true)
  @MapKey(name="taal")
  private Map<String, LandnaamDto>  landnamen =
      new HashMap<String, LandnaamDto>();

  public void addLandnaam(LandnaamDto landnaamDto) {
    //TODO Kijken voor 'de' JPA manier.
    if (null == landnaamDto.getLandId()) {
      landnaamDto.setLandId(landId);
    }
    landnamen.put(landnaamDto.getTaal(), landnaamDto);
  }

  public LandDto clone() throws CloneNotSupportedException {
    LandDto clone = (LandDto) super.clone();

    return clone;
  }

  public int compareTo(LandDto landDto) {
    return new CompareToBuilder().append(landId, landDto.landId)
                                 .toComparison();
  }

  public boolean containsTekst(String taal) {
    return landnamen.containsKey(taal);
  }

  public boolean equals(Object object) {
    if (!(object instanceof LandDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    LandDto landDto = (LandDto) object;
    return new EqualsBuilder().append(landId, landDto.landId)
                              .isEquals();
  }

  public boolean getBestaat() {
    return (DoosConstants.WAAR.equals(bestaat));
  }

  public String getIso2() {
    return iso2;
  }

  public String getIso3() {
    return iso3;
  }

  public Long getLandId() {
    return landId;
  }

  public LandnaamDto getLandnaam(String taal) {
    if (landnamen.containsKey(taal)) {
      return landnamen.get(taal);
    } else {
      throw new ObjectNotFoundException(DoosLayer.PERSISTENCE, taal);
    }
  }

  public Collection<LandnaamDto> getLandnamen() {
    return landnamen.values();
  }

  public Long getLandnummer() {
    return landnummer;
  }

  public Long getMuntId() {
    return muntId;
  }

  public String getPostkodeScheiding() {
    return postkodeScheiding;
  }

  public String getPostkodeType() {
    return postkodeType;
  }

  public String getPostLandkode() {
    return postLandkode;
  }

  public String getTaal() {
    return taal;
  }

  public Long getWerelddeelId() {
    return werelddeelId;
  }

  public int hashCode() {
    return new HashCodeBuilder().append(landId).toHashCode();
  }

  public void removeLandnaam(LandnaamDto landnaamDto) {
    removeLandnaam(landnaamDto.getTaal());
  }

  public void removeLandnaam(String taal) {
    if (landnamen.containsKey(taal)) {
      landnamen.remove(taal);
    } else {
      throw new ObjectNotFoundException(DoosLayer.PERSISTENCE, taal);
    }
  }

  public void setBestaat(boolean bestaat) {
    this.bestaat  = bestaat ? DoosConstants.WAAR : DoosConstants.ONWAAR;
  }

  public void setIso2(String iso2) {
    this.iso2 = iso2;
  }

  public void setIso3(String iso3) {
    this.iso3 = iso3;
  }

  public void setTeksten(Collection<LandnaamDto> landnamen) {
    for (LandnaamDto landnaam : landnamen) {
      this.landnamen.put(landnaam.getTaal(), landnaam);
    }
  }

  public void setLandId(Long landId) {
    this.landId = landId;
  }

  public void setLandnummer(Long landnummer) {
    this.landnummer = landnummer;
  }

  public void setMuntId(Long muntId) {
    this.muntId = muntId;
  }

  public void setPostkodeScheiding(String postkodeScheiding) {
    this.postkodeScheiding = postkodeScheiding;
  }

  public void setPostkodeType(String postkodeType) {
    this.postkodeType = postkodeType;
  }

  public void setPostLandkode(String postLandkode) {
    this.postLandkode = postLandkode;
  }

  public void setTaal(String taal) {
    this.taal = taal;
  }

  public void setWerelddeelId(Long werelddeelId) {
    this.werelddeelId = werelddeelId;
  }
}
