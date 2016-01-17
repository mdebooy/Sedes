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
package eu.debooy.sedes.form;

import eu.debooy.sedes.domain.LandDto;

import java.io.Serializable;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Land implements Cloneable, Comparable<Land>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private boolean gewijzigd = false;

  private String  bestaat;
  private String  iso2;
  private String  iso3;
  private Long    landId;
  private Long    landnummer;
  private Long    muntId;
  private String  postkodeScheiding;
  private String  postkodeType;
  private String  postLandkode;
  private String  taal;
  private byte[]  vlag;
  private Long    werelddeelId;

  public Land() {}

  public Land(LandDto landDto) {
    bestaat           = landDto.getBestaat();
    iso2              = landDto.getIso2();
    iso3              = landDto.getIso3();
    landId            = landDto.getLandId();
    landnummer        = landDto.getLandnummer();
    muntId            = landDto.getMuntId();
    postkodeScheiding = landDto.getPostkodeScheiding();
    postkodeType      = landDto.getPostkodeType();
    postLandkode      = landDto.getPostLandkode();
    taal              = landDto.getTaal();
    vlag              = landDto.getVlag();
    werelddeelId      = landDto.getWerelddeelId();
  }
  
  @Override
  public Land clone() throws CloneNotSupportedException {
    Land  clone = (Land) super.clone();

    return clone;
  }

  @Override
  public int compareTo(Land andere) {
    return new CompareToBuilder().append(landId, andere.landId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Land)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    Land  andere  = (Land) object;
    return new EqualsBuilder().append(landId, andere.landId).isEquals();
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
   * @return de gewijzigd
   */
  public boolean isGewijzigd() {
    return gewijzigd;
  }

  /**
   * Zet de gegevens in een LandDto
   *
   * @param LandDto
   */
  public void persist(LandDto parameter) {
    if (!new EqualsBuilder().append(bestaat,
                                    parameter.getBestaat()).isEquals()) {
      parameter.setBestaat(bestaat);
    }
    if (!new EqualsBuilder().append(iso2,
                                    parameter.getIso2()).isEquals()) {
      parameter.setIso2(iso2);
    }
    if (!new EqualsBuilder().append(iso3,
                                    parameter.getIso3()).isEquals()) {
      parameter.setIso3(iso3);
    }
    if (!new EqualsBuilder().append(landId,
                                    parameter.getLandId()).isEquals()) {
      parameter.setLandId(landId);
    }
    if (!new EqualsBuilder().append(landnummer,
                                    parameter.getLandnummer()).isEquals()) {
      parameter.setLandnummer(landnummer);
    }
    if (!new EqualsBuilder().append(muntId,
                                    parameter.getMuntId()).isEquals()) {
      parameter.setMuntId(muntId);
    }
    if (!new EqualsBuilder().append(postkodeScheiding,
                                    parameter.getPostkodeScheiding())
                            .isEquals()) {
      parameter.setPostkodeScheiding(postkodeScheiding);
    }
    if (!new EqualsBuilder().append(postkodeType,
                                    parameter.getPostkodeType()).isEquals()) {
      parameter.setPostkodeType(postkodeType);
    }
    if (!new EqualsBuilder().append(postLandkode,
                                    parameter.getPostLandkode()).isEquals()) {
      parameter.setPostLandkode(postLandkode);
    }
    if (!new EqualsBuilder().append(taal,
                                    parameter.getTaal()).isEquals()) {
      parameter.setTaal(taal);
    }
    if (!new EqualsBuilder().append(vlag,
                                    parameter.getVlag()).isEquals()) {
      parameter.setVlag(vlag);
    }
    if (!new EqualsBuilder().append(werelddeelId,
                                    parameter.getWerelddeelId()).isEquals()) {
      parameter.setWerelddeelId(werelddeelId);
    }
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

  @Override
  public String toString() {
    StringBuilder resultaat = new StringBuilder();
    resultaat.append("Land (")
             .append("bestaat=[").append(bestaat).append("], ")
             .append("iso2=[").append(iso2).append("], ")
             .append("iso3=[").append(iso3).append("], ")
             .append("landId=[").append(landId).append("], ")
             .append("landnummer=[").append(landnummer).append("], ")
             .append("muntId=[").append(muntId).append("], ")
             .append("postkodeScheiding=[").append(postkodeScheiding)
                                           .append("], ")
             .append("postkodeType=[").append(postkodeType).append("], ")
             .append("postLandkode=[").append(postLandkode).append("], ")
             .append("taal=[").append(taal).append("], ")
             .append("vlag=").append(vlag.length).append("b, ")
             .append("werelddeelId=[").append(werelddeelId).append("], ")
             .append("class=[").append(this.getClass().getSimpleName())
             .append("])");

    return resultaat.toString();
  }
}
