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

import eu.debooy.doosutils.DoosConstants;
import eu.debooy.doosutils.domain.Dto;
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
@Table(name="MUNTEN", schema="SEDES")
public class MuntDto extends Dto implements Comparable<MuntDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  COL_BESTAAT     = "bestaat";
  public static final String  COL_DECIMALEN   = "decimalen";
  public static final String  COL_ISO3        = "iso3";
  public static final String  COL_MUNTID      = "muntId";
  public static final String  COL_MUNTTEKEN   = "muntteken";
  public static final String  COL_NAAM        = "naam";
  public static final String  COL_SUBEENHEID  = "subeenheid";

  @Column(name="BESTAAT", length=1, nullable=false)
  private String  bestaat;
  @Column(name="DECIMALEN", precision=2, nullable=false)
  private Integer decimalen;
  @Column(name="ISO3", length=3, nullable=false)
  private String  iso3;
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="MUNT_ID", nullable=false)
  private Long    muntId;
  @Column(name="MUNTTEKEN", length=3)
  private String  muntteken;
  @Column(name="NAAM", length=100)
  private String  naam;
  @Column(name="SUBEENHEID", length=100)
  private String  subeenheid;

  @Override
  public int compareTo(MuntDto muntDto) {
    return new CompareToBuilder().append(muntId, muntDto.muntId)
                                 .toComparison();
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public boolean getBestaat() {
    return (DoosConstants.WAAR.equals(bestaat));
  }

  public Integer getDecimalen() {
    return decimalen;
  }

  public String getIso3() {
    return iso3;
  }

  public Long getMuntId() {
    return muntId;
  }

  public String getMuntteken() {
    return muntteken;
  }

  public String getNaam() {
    return naam;
  }

  public String getSubeenheid() {
    return subeenheid;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof MuntDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var muntDto = (MuntDto) object;
    return new EqualsBuilder().append(muntId, muntDto.muntId)
                              .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(muntId).toHashCode();
  }

  public void setBestaat(boolean bestaat) {
    this.bestaat  = bestaat ? DoosConstants.WAAR : DoosConstants.ONWAAR;
  }

  public void setDecimalen(Integer decimalen) {
    this.decimalen = decimalen;
  }

  public void setIso3(String iso3) {
    this.iso3 = iso3;
  }

  public void setMuntId(Long muntId) {
    this.muntId = muntId;
  }

  public void setMuntteken(String muntteken) {
    this.muntteken = muntteken;
  }

  public void setNaam(String naam) {
    this.naam     = naam;
  }

  public void setSubeenheid(String subeenheid) {
    this.subeenheid = subeenheid;
  }
}
