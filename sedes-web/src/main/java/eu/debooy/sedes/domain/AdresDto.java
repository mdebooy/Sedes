/*
 * Copyright (c) 2024 Marco de Booij
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
import javax.persistence.Table;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
@Entity
@Table(name="ADRESSEN", schema="SEDES")
public class AdresDto extends Dto implements Comparable<AdresDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  COL_ADRESID     = "adresId";
  public static final String  COL_ADRESDATA   = "adresdata";
  public static final String  COL_OPMERKING   = "opmerking";
  public static final String  COL_PLAATSID    = "plaatsId";
  public static final String  COL_SUBPOSTKODE = "subPostkode";

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="ADRES_ID", nullable=false)
  private Long    adresId;
  @Column(name="ADRESDATA", length=255, nullable=false)
  private String  adresdata;
  @Column(name="OPMERKING", length=2000)
  private String  opmerking;
  @Column(name="PLAATS_ID")
  private Long    plaatsId;
  @Column(name="SUB_POSTKODE", length=10)
  private String  subPostkode;

  @Override
  public int compareTo(AdresDto adresDto) {
    return new CompareToBuilder().append(adresdata, adresDto.adresdata)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof AdresDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var adresDto  = (AdresDto) object;
    return new EqualsBuilder().append(adresId, adresDto.adresId)
                              .isEquals();
  }

  public Long getAdresId() {
    return adresId;
  }

  public String getAdresdata() {
    return adresdata;
  }

  public String getOpmerking() {
    return opmerking;
  }

  public Long getPlaatsId() {
    return plaatsId;
  }

  public String getSubPostkode() {
    return subPostkode;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(adresId).toHashCode();
  }

  public void setAdresId(Long adresId) {
    this.adresId        = adresId;
  }

  public void setAdresdata(String adresdata) {
    if (null == adresdata) {
      this.adresdata    = null;
    } else {
      this.adresdata    = adresdata.trim();
    }
  }

  public void setOpmerking(String opmerking) {
    if (null == opmerking) {
      this.opmerking    = null;
    } else {
      this.opmerking    = opmerking.trim();
    }
  }

  public void setPlaatsId(Long plaatsId) {
    this.plaatsId       = plaatsId;
  }

  public void setSubPostkode(String subPostkode) {
    if (null == subPostkode) {
      this.subPostkode  = null;
    } else {
      this.subPostkode  = subPostkode.trim();
    }
    this.subPostkode  = subPostkode;
  }
}
