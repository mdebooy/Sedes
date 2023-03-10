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
@Table(name="PLAATSEN", schema="SEDES")
@NamedQuery(name="plaatsenPerLand", query="select p from PlaatsDto p where p.landId=:landId")
@NamedQuery(name="plaatsenPerRegio", query="select p from PlaatsDto p where p.regioId=:regioId")
public class PlaatsDto extends Dto implements Comparable<PlaatsDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  COL_BREEDTE       = "breedte";
  public static final String  COL_BREEDTEGRAAD  = "breedtegraad";
  public static final String  COL_LANDID        = "landId";
  public static final String  COL_LENGTE        = "lengte";
  public static final String  COL_LENGTEGRAAD   = "lengtegraad";
  public static final String  COL_PLAATSID      = "plaatsId";
  public static final String  COL_POSTKODE      = "postkode";
  public static final String  COL_PLAATSNAAM    = "plaatsnaam";
  public static final String  COL_REGIOID       = "regioId";
  public static final String  COL_ZONENUMMER    = "zonenummer";

  public static final String  PAR_LANDID  = "landId";
  public static final String  PAR_REGIOID = "regioId";

  public static final String  QRY_PERLAND   = "plaatsenPerLand";
  public static final String  QRY_PERREGIO  = "plaatsenPerRegio";

  @Column(name="BREEDTE", length=1)
  private String  breedte;
  @Column(name="BREEDTEGRAAD", precision=4, scale=2)
  private Double  breedtegraad;
  @Column(name="LAND_ID", nullable=false)
  private Long    landId    = Long.valueOf(0);
  @Column(name="LENGTE", length=1)
  private String  lengte;
  @Column(name="LENGTEGRAAD", precision=4, scale=2)
  private Double  lengtegraad;
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="PLAATS_ID", nullable=false, unique=true, updatable=false)
  private Long    plaatsId;
  @Column(name="PLAATSNAAM", length=100, nullable=false)
  private String  plaatsnaam;
  @Column(name="POSTKODE", length=15)
  private String  postkode;
  @Column(name="REGIO_ID", nullable=false)
  private Long    regioId;
  @Column(name="ZONENUMMER", length=6)
  private Long    zonenummer;

  @Override
  public int compareTo(PlaatsDto plaatsDto) {
    return new CompareToBuilder().append(plaatsnaam, plaatsDto.plaatsnaam)
                                 .append(plaatsId, plaatsDto.plaatsId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof PlaatsDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var plaatsDto  = (PlaatsDto) object;
    return new EqualsBuilder().append(plaatsId, plaatsDto.plaatsId)
                              .isEquals();
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public String getBreedte() {
    return breedte;
  }

  public Double getBreedtegraad() {
    return breedtegraad;
  }

  public Long getLandId() {
    return landId;
  }

  public String getLengte() {
    return lengte;
  }

  public Double getLengtegraad() {
    return lengtegraad;
  }

  public Long getPlaatsId() {
    return plaatsId;
  }

  public String getPlaatsnaam() {
    return plaatsnaam;
  }

  public String getPostkode() {
    return postkode;
  }

  public Long getRegioId() {
    return regioId;
  }

  public Long getZonenummer() {
    return zonenummer;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(plaatsId).toHashCode();
  }

  public void setBreedte(String breedte) {
    this.breedte      = breedte;
  }

  public void setBreedtegraad(Double breedtegraad) {
    this.breedtegraad = breedtegraad;
  }

  public void setLandId(Long landId) {
    this.landId       = landId;
  }

  public void setLengte(String lengte) {
    this.lengte       = lengte;
  }

  public void setLengtegraad(Double lengtegraad) {
    this.lengtegraad  = lengtegraad;
  }

  public void setPlaatsId(Long plaatsId) {
    this.plaatsId     = plaatsId;
  }

  public void setPlaatsnaam(String plaatsnaam) {
    this.plaatsnaam   = plaatsnaam;
  }

  public void setPostkode(String postkode) {
    this.postkode     = postkode;
  }

  public void setRegioId(Long regioId) {
    this.regioId      = regioId;
  }

  public void setZonenummer(Long zonenummer) {
    this.zonenummer   = zonenummer;
  }
}
