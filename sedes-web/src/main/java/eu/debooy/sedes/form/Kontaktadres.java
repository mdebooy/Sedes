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

import eu.debooy.doosutils.Datum;
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.form.Formulier;
import eu.debooy.sedes.domain.KontaktadresDto;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Kontaktadres
    extends Formulier implements Comparable<Kontaktadres>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private Long    adresId;
  private Date    einddatum;
  private Long    kontaktadresId;
  private String  kontaktadrestype;
  private Long    kontaktId;
  private String  opmerking;
  private Date    startdatum;
  private String  subAdres;
  private String  taal;

  public Kontaktadres() {}

  public Kontaktadres(KontaktadresDto kontaktadresDto) {
    adresId           = kontaktadresDto.getAdresId();
    einddatum         = kontaktadresDto.getEinddatum();
    kontaktadresId    = kontaktadresDto.getKontaktadresId();
    kontaktadrestype  = kontaktadresDto.getKontaktadrestype();
    kontaktId         = kontaktadresDto.getKontaktId();
    opmerking         = kontaktadresDto.getOpmerking();
    startdatum        = kontaktadresDto.getStartdatum();
    subAdres          = kontaktadresDto.getSubAdres();
    taal              = kontaktadresDto.getTaal();
  }

  @Override
  public int compareTo(Kontaktadres kontaktadres) {
    return new CompareToBuilder().append(kontaktadresId,
                                         kontaktadres.kontaktadresId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Kontaktadres)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var kontaktadres = (Kontaktadres) object;
    return new EqualsBuilder().append(kontaktadresId,
                                      kontaktadres.kontaktadresId)
                              .isEquals();
  }

  public Long getAdresId() {
    return adresId;
  }

  public Date getEinddatum() {
    if (null == einddatum) {
      return null;
    }

    return new Date(einddatum.getTime());
  }

  public Long getKontaktadresId() {
    return kontaktadresId;
  }

  public String getKontaktadrestype() {
    return kontaktadrestype;
  }

  public Long getKontaktId() {
    return kontaktId;
  }

  public String getOpmerking() {
    return opmerking;
  }

  public Date getStartdatum() {
    if (null == startdatum) {
      return null;
    }

    return new Date(startdatum.getTime());
  }

  public String getSubAdres() {
    return subAdres;
  }

  public String getTaal() {
    return taal;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(kontaktadresId).toHashCode();
  }

  public void persist(KontaktadresDto kontaktadresDto) {
    kontaktadresDto.setAdresId(getAdresId());
    kontaktadresDto.setKontaktadrestype(getKontaktadrestype());
    kontaktadresDto.setEinddatum(getEinddatum());
    kontaktadresDto.setKontaktId(getKontaktId());
    kontaktadresDto.setKontaktadresId(getKontaktadresId());
    kontaktadresDto.setOpmerking(getOpmerking());
    kontaktadresDto.setStartdatum(getStartdatum());
    kontaktadresDto.setSubAdres(getSubAdres());
    kontaktadresDto.setTaal(getTaal());
  }

  public void setAdresId(Long adresId) {
    this.adresId          = adresId;
  }

  public void setEinddatum(Date einddatum) {
    this.einddatum        = Datum.stripTime(einddatum);
  }

  public void setKontaktadresId(Long kontaktadresId) {
    this.kontaktadresId   = kontaktadresId;
  }

  public void setKontaktadrestype(String kontaktadrestype) {
    this.kontaktadrestype = kontaktadrestype;
  }

  public void setKontaktId(Long kontaktId) {
    this.kontaktId        = kontaktId;
  }

  public void setOpmerking(String opmerking) {
    this.opmerking        = DoosUtils.strip(opmerking);
  }

  public void setStartdatum(Date startdatum) {
    this.startdatum       = Datum.stripTime(startdatum);
  }

  public void setSubAdres(String subAdres) {
    this.subAdres         = DoosUtils.strip(subAdres);
  }

  public void setTaal(String taal) {
    this.taal             = DoosUtils.stripToLowerCase(taal);
  }
}
