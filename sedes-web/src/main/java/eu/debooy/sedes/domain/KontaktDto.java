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
import eu.debooy.sedes.SedesUtils;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
@Entity
@Table(name="KONTAKTEN", schema="SEDES")
@NamedQuery(name="kontaktPerType", query="select k from KontaktDto k where k.kontakttype=:kontakttype")
public class KontaktDto extends Dto implements Comparable<KontaktDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  PAR_KONTAKTTYPE     = "ouder";

  public static final String  QRY_PERTYPE         = "kontaktPerType";

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
  public static final String  COL_TAAL            = "taal";
  public static final String  COL_TUSSENVOEGSEL   = "tussenvoegsel";
  public static final String  COL_VOORNAAM        = "voornaam";

  public static final String  VAR_DISPLAYNAAM     = "displaynaam";

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
  @Column(name="TAAL", length=3, nullable=false)
  private String  taal;
  @Column(name="TUSSENVOEGSEL", length=255)
  private String  tussenvoegsel;
  @Column(name="VOORNAAM", length=255)
  private String  voornaam;

  public static class DisplaynaamComparator
      implements Comparator<KontaktDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(KontaktDto kontaktDto1, KontaktDto kontaktDto2) {
      return kontaktDto1.getDisplaynaam().toLowerCase()
                        .compareTo(kontaktDto2.getDisplaynaam().toLowerCase());
    }
  }

  @Override
  public int compareTo(KontaktDto kontaktDto) {
    return new CompareToBuilder().append(naam, kontaktDto.naam)
                                 .append(voornaam, kontaktDto.voornaam)
                                 .append(tussenvoegsel,
                                         kontaktDto.tussenvoegsel)
                                 .append(roepnaam, kontaktDto.roepnaam)
                                 .append(kontaktId, kontaktDto.kontaktId)
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

  @Transient
  public String getDisplaynaam() {
    return SedesUtils.getKontaktnaam(kontakttype, naam, voornaam,
                                     tussenvoegsel, initialen, roepnaam);
  }

  public Date getGeboortedatum() {
    if (null == geboortedatum) {
      return null;
    }

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

  public String getTaal() {
    return taal;
  }

  public String getTussenvoegsel() {
    return tussenvoegsel;
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
    if (null == geboortedatum) {
      this.geboortedatum  = null;
    } else {
      this.geboortedatum  = new Date(geboortedatum.getTime());
    }
  }

  public void setGebruikersnaam(String gebruikersnaam) {
    if (null == gebruikersnaam) {
      this.gebruikersnaam = null;
    } else {
      this.gebruikersnaam = gebruikersnaam.trim();
    }
  }

  public void setInitialen(String initialen) {
    if (null == initialen) {
      this.initialen      = null;
    } else {
      this.initialen      = initialen.trim();
    }
  }

  public void setKontaktId(Long kontaktId) {
    this.kontaktId        = kontaktId;
  }

  public void setKontakttype(String kontakttype) {
    this.kontakttype      = kontakttype;
  }

  public void setNaam(String naam) {
    if (null == naam) {
      this.naam           = null;
    } else {
      this.naam           = naam.trim();
    }
  }

  public void setOpmerking(String opmerking) {
    if (null == opmerking) {
      this.opmerking      = null;
    } else {
      this.opmerking      = opmerking.trim();
    }
  }

  public void setPseudoniem(String pseudoniem) {
    if (null == pseudoniem) {
      this.pseudoniem     = null;
    } else {
      this.pseudoniem     = pseudoniem.trim();
    }
  }

  public void setRoepnaam(String roepnaam) {
    if (null == roepnaam) {
      this.roepnaam       = null;
    } else {
      this.roepnaam       = roepnaam.trim();
    }
  }

  public void setTaal(String taal) {
    this.taal             = taal;
  }

  public void setTussenvoegsel(String tussenvoegsel) {
    if (null == tussenvoegsel) {
      this.tussenvoegsel  = null;
    } else {
      this.tussenvoegsel  = tussenvoegsel.trim();
    }
  }

  public void setVoornaam(String voornaam) {
    if (null == voornaam) {
      this.voornaam       = null;
    } else {
      this.voornaam       = voornaam.trim();
    }
  }
}
