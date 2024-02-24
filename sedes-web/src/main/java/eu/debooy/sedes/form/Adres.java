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

package eu.debooy.sedes.form;

import eu.debooy.doosutils.form.Formulier;
import eu.debooy.sedes.domain.AdresDto;
import java.io.Serializable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Adres
    extends Formulier implements Comparable<Adres>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private Long    adresId;
  private String  adresdata;
  private String  opmerking;
  private Long    plaatsId;
  private String  subPostkode;

  public Adres() {}

  public Adres(AdresDto adresDto) {
    adresId     = adresDto.getAdresId();
    adresdata   = adresDto.getAdresdata();
    opmerking   = adresDto.getOpmerking();
    plaatsId    = adresDto.getPlaatsId();
    subPostkode = adresDto.getSubPostkode();
  }

  @Override
  public int compareTo(Adres adres) {
    return new CompareToBuilder().append(adresdata, adres.adresdata)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Adres)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var adres = (Adres) object;
    return new EqualsBuilder().append(adresId, adres.adresId)
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

  public void persist(AdresDto adresDto) {
    adresDto.setAdresId(getAdresId());
    adresDto.setAdresdata(getAdresdata());
    adresDto.setOpmerking(getOpmerking());
    adresDto.setPlaatsId(getPlaatsId());
    adresDto.setSubPostkode(getSubPostkode());
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
