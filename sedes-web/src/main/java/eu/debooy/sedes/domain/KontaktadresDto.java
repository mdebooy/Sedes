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

import eu.debooy.doosutils.Datum;
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.domain.Dto;
import java.util.Date;
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
@Table(name="KONTAKTADRESSEN", schema="SEDES")
@NamedQuery(name="kontaktadressenPerAdres", query="select k from KontaktadresDto k where k.adresId=:adresId")
@NamedQuery(name="kontaktadressenPerKontakt", query="select k from KontaktadresDto k where k.kontaktId=:kontaktId")
public class KontaktadresDto extends Dto implements Comparable<KontaktadresDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  PAR_ADRES   = "adresId";
  public static final String  PAR_KONTAKT = "kontaktId";

  public static final String  QRY_PERADRES    = "kontaktadressenPerAdres";
  public static final String  QRY_PERKONTAKT  = "kontaktadressenPerKontakt";

  public static final String  COL_ADRESID           = "adresId";
  public static final String  COL_EINDDATUM         = "einddatum";
  public static final String  COL_KONTAKTADRESID    = "kontaktadresId";
  public static final String  COL_KONTAKTADRESTYPE  = "kontaktadrestype";
  public static final String  COL_KONTAKTID         = "kontaktId";
  public static final String  COL_OPMERKING         = "opmerking";
  public static final String  COL_STARTDATUM        = "startdatum";
  public static final String  COL_SUBADRES          = "subAdres";
  public static final String  COL_TAAL              = "taal";

  @Column(name="ADRES_ID", nullable=false)
  private Long    adresId;
  @Column(name="EINDDATUM")
  private Date    einddatum;
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="KONTAKTADRES_ID", nullable=false)
  private Long    kontaktadresId;
  @Column(name="KONTAKTADRESTYPE", length=10, nullable=false)
  private String  kontaktadrestype;
  @Column(name="KONTAKT_ID", nullable=false)
  private Long    kontaktId;
  @Column(name="OPMERKING", length=2000)
  private String  opmerking;
  @Column(name="STARTDATUM", nullable=false)
  private Date    startdatum;
  @Column(name="SUB_ADRES", length=255, nullable=false)
  private String  subAdres;
  @Column(name="TAAL", length=3)
  private String  taal;

  @Override
  public int compareTo(KontaktadresDto kontaktadresDto) {
    return new CompareToBuilder().append(kontaktadresId,
                                         kontaktadresDto.kontaktadresId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof KontaktadresDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var kontaktadresDto = (KontaktadresDto) object;
    return new EqualsBuilder().append(kontaktadresId,
                                      kontaktadresDto.kontaktadresId)
                              .isEquals();
  }

  public Long getAdresId() {
    return adresId;
  }

  public Date getEinddatum() {
    if (null == einddatum) {
      return null;
    }

    return einddatum;
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

    return startdatum;
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
    this.taal             = DoosUtils.stripToLowerCase(subAdres);
  }
}
