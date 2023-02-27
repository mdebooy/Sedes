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

package eu.debooy.sedes.form;

import eu.debooy.doosutils.form.Formulier;
import eu.debooy.sedes.domain.MuntDto;
import java.io.Serializable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Munt
    extends Formulier implements Comparable<Munt>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private boolean bestaat;
  private Integer decimalen;
  private String  iso3;
  private Long    muntId;
  private String  muntteken;
  private String  naam;
  private String  subeenheid;

  public Munt() {}

  public Munt(MuntDto muntDto) {
    bestaat     = muntDto.getBestaat();
    decimalen   = muntDto.getDecimalen();
    iso3        = muntDto.getIso3();
    muntId      = muntDto.getMuntId();
    muntteken   = muntDto.getMuntteken();
    naam        = muntDto.getNaam();
    subeenheid  = muntDto.getSubeenheid();
  }

  @Override
  public int compareTo(Munt andere) {
    return new CompareToBuilder().append(muntId, andere.muntId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Munt)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var andere  = (Munt) object;
    return new EqualsBuilder().append(muntId, andere.muntId).isEquals();
  }

  public boolean getBestaat() {
    return bestaat;
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
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
  public int hashCode() {
    return new HashCodeBuilder().append(muntId).toHashCode();
  }

  public boolean isBestaat() {
    return getBestaat();
  }

  public void persist(MuntDto muntDto) {
    muntDto.setBestaat(getBestaat());
    muntDto.setDecimalen(getDecimalen());
    muntDto.setIso3(getIso3());
    muntDto.setMuntId(getMuntId());
    muntDto.setMuntteken(getMuntteken());
    muntDto.setNaam(getNaam());
    muntDto.setSubeenheid(getSubeenheid());
  }

  public void setBestaat(boolean bestaat) {
    this.bestaat    = bestaat;
  }

  public void setDecimalen(Integer decimalen) {
    this.decimalen  = decimalen;
  }

  public void setIso3(String iso3) {
    this.iso3       = iso3;
  }

  public void setMuntId(Long muntId) {
    this.muntId     = muntId;
  }

  public void setMuntteken(String muntteken) {
    this.muntteken  = muntteken;
  }

  public void setNaam(String naam) {
    this.naam       = naam;
  }

  public void setSubeenheid(String subeenheid) {
    this.subeenheid = subeenheid;
  }
}
