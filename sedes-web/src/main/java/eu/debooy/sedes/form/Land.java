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

import eu.debooy.doosutils.form.Formulier;
import eu.debooy.sedes.domain.LandDto;
import java.io.Serializable;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Land
    extends Formulier implements Comparable<Land>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private boolean bestaat;
  private String  iso2;
  private String  iso3;
  private Long    landId;
  private Long    landnummer;
  private Long    muntId;
  private String  postkodeScheiding;
  private String  postkodeType;
  private String  postLandkode;
  private String  taal;
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
    werelddeelId      = landDto.getWerelddeelId();
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

  public boolean getBestaat() {
    return bestaat;
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

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(landId).toHashCode();
  }

  public boolean isBestaat() {
    return getBestaat();
  }

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
    if (!new EqualsBuilder().append(werelddeelId,
                                    parameter.getWerelddeelId()).isEquals()) {
      parameter.setWerelddeelId(werelddeelId);
    }
  }

  public void setBestaat(boolean bestaat) {
    this.bestaat  = bestaat;
  }

  public void setIso2(String iso2) {
    this.iso2 = iso2;
  }

  public void setIso3(String iso3) {
    this.iso3 = iso3;
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
    this.postkodeScheiding  = postkodeScheiding;
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
