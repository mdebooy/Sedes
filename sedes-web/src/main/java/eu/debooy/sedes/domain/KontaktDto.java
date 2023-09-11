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
import java.util.Date;
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
@Table(name="KONTAKTEN", schema="SEDES")
public class KontaktDto extends Dto implements Comparable<KontaktDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  COL_AANSPREEKID     = "aanspreekId";
  public static final String  COL_GEBOORTEDATUM   = "geboortedatum";
  public static final String  COL_GEBRUIKERSNAAM  = "gebruikersnaam";
  public static final String  COL_INITIALEN       = "initialen";
  public static final String  COL_KONTAKTID       = "kontaktId";
  public static final String  COL_KONTAKTTYPE     = "kontakttype";
  public static final String  COL_NAAM            = "naam";
  public static final String  COL_OPMERKING       = "opmerking";
  public static final String  COL_PSEUDONIEM      = "pseudoniem";
  public static final String  COL_ROEPNAAM        = "roepnaam";
  public static final String  COL_SOORT           = "soort";
  public static final String  COL_TAAL            = "taal";
  public static final String  COL_VOORNAAM        = "voornaam";

  @Column(name="AANSPREEK_ID", length=10)
  private String  aanspreekId;
  @Column(name="GEBOORTEDATUM")
  private Date    geboortedatum;
  @Column(name="GEBRUIKERSNAAM", length=20)
  private String  gebruikersnaam;
  @Column(name="INITIALEN", length=20)
  private String  initialen;
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="KONTAKT_ID", nullable=false)
  private Long    kontaktId;
  @Column(name="KONTAKTTYPE", length=1, nullable=false)
  private String  kontakttype;
  @Column(name="NAAM", length=255, nullable=false)
  private String  naam;
  @Column(name="OPMERKING", length=2000)
  private String  opmerking;
  @Column(name="PSEUDONIEM", length=255)
  private String  pseudoniem;
  @Column(name="ROEPNAAM", length=255)
  private String  roepnaam;
  @Column(name="SOORT", length=10)
  private String  soort;
  @Column(name="TAAL", length=3)
  private String  taal;
  @Column(name="VOORNAAM", length=255)
  private String  voornaam;

  @Override
  public int compareTo(KontaktDto kontaktDto) {
    return new CompareToBuilder().append(naam, kontaktDto.naam)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof KontaktDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var kontaktDto  = (KontaktDto) object;
    return new EqualsBuilder().append(kontaktId, kontaktDto.kontaktId)
                              .isEquals();
  }

  public String getAanspreekId() {
    return aanspreekId;
  }

  public Date getGeboortedatum() {
    return new Date(geboortedatum.getTime());
  }

  public String getGebruikersnaam() {
    return gebruikersnaam;
  }

  public String getInitialen() {
    return initialen;
  }

  public Long getKontaktId() {
    return kontaktId;
  }

  public String getKontakttype() {
    return kontakttype;
  }

  public String getNaam() {
    return naam;
  }

  public String getOpmerking() {
    return opmerking;
  }

  public String getPseudoniem() {
    return pseudoniem;
  }

  public String getRoepnaam() {
    return roepnaam;
  }

  public String getSoort() {
    return soort;
  }

  public String getTaal() {
    return taal;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(kontaktId).toHashCode();
  }

  public String getVoornaam() {
    return voornaam;
  }

  public void setAanspreekId(String aanspreekId) {
    this.aanspreekId    = aanspreekId;
  }

  public void setGeboortedatum(Date geboortedatum) {
    this.geboortedatum  = new Date(geboortedatum.getTime());
  }

  public void setGebruikersnaam(String gebruikersnaam) {
    this.gebruikersnaam   = gebruikersnaam;
  }

  public void setInitialen(String initialen) {
    this.initialen        = initialen;
  }

  public void setKontaktId(Long kontaktId) {
    this.kontaktId        = kontaktId;
  }

  public void setKontakttype(String kontakttype) {
    this.kontakttype      = kontakttype;
  }

  public void setNaam(String naam) {
    this.naam             = naam;
  }

  public void setOpmerking(String opmerking) {
    this.opmerking        = opmerking;
  }

  public void setPseudoniem(String pseudoniem) {
    this.pseudoniem       = pseudoniem;
  }

  public void setRoepnaam(String roepnaam) {
    this.roepnaam         = roepnaam;
  }

  public void setSoort(String soort) {
    this.soort            = soort;
  }

  public void setTaal(String taal) {
    this.taal             = taal;
  }

  public void setVoornaam(String voornaam) {
    this.voornaam         = voornaam;
  }
}
