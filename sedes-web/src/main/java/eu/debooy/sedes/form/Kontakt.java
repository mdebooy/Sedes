/**
 * Copyright 2023 Marco de Booij
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
import eu.debooy.sedes.domain.KontaktDto;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Kontakt
    extends Formulier implements Comparable<Kontakt>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private String  aanspreekId;
  private Date    geboortedatum;
  private String  gebruikersnaam;
  private String  initialen;
  private Long    kontaktId;
  private String  kontakttype;
  private String  naam;
  private String  opmerking;
  private String  pseudoniem;
  private String  roepnaam;
  private String  soort;
  private String  taal;
  private String  voornaam;

  public Kontakt() {}

  public Kontakt(KontaktDto kontaktDto) {
    aanspreekId     = kontaktDto.getAanspreekId();
    geboortedatum   = kontaktDto.getGeboortedatum();
    gebruikersnaam  = kontaktDto.getGebruikersnaam();
    initialen       = kontaktDto.getInitialen();
    kontaktId       = kontaktDto.getKontaktId();
    kontakttype     = kontaktDto.getKontakttype();
    naam            = kontaktDto.getNaam();
    opmerking       = kontaktDto.getOpmerking();
    pseudoniem      = kontaktDto.getPseudoniem();
    roepnaam        = kontaktDto.getRoepnaam();
    soort           = kontaktDto.getSoort();
    taal            = kontaktDto.getTaal();
    voornaam        = kontaktDto.getVoornaam();
  }

  @Override
  public int compareTo(Kontakt andere) {
    return new CompareToBuilder().append(kontaktId, andere.kontaktId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Kontakt)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var andere  = (Kontakt) object;
    return new EqualsBuilder().append(naam, andere.naam).isEquals();
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

  public String getVoornaam() {
    return voornaam;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(naam).toHashCode();
  }

  public void persist(KontaktDto kontaktDto) {
    kontaktDto.setAanspreekId(getAanspreekId());
    kontaktDto.setGeboortedatum(getGeboortedatum());
    kontaktDto.setGebruikersnaam(getGebruikersnaam());
    kontaktDto.setInitialen(getInitialen());
    kontaktDto.setKontaktId(getKontaktId());
    kontaktDto.setKontakttype(getKontakttype());
    kontaktDto.setNaam(getNaam());
    kontaktDto.setOpmerking(getOpmerking());
    kontaktDto.setPseudoniem(getPseudoniem());
    kontaktDto.setRoepnaam(getRoepnaam());
    kontaktDto.setSoort(getSoort());
    kontaktDto.setTaal(getTaal());
    kontaktDto.setVoornaam(getVoornaam());
  }

  public void setAanspreekId(String aanspreekId) {
    this.aanspreekId  = aanspreekId;
  }

  public void setGeboortedatum(Date geboortedatum) {
    this.geboortedatum  = new Date(geboortedatum.getTime());
  }

  public void setGebruikersnaam(String gebruikersnaam) {
    this.gebruikersnaam = gebruikersnaam;
  }

  public void setInitialen(String initialen) {
    this.initialen      = initialen;
  }

  public void setKontaktId(Long kontaktId) {
    this.kontaktId      = kontaktId;
  }

  public void setKontakttype(String kontakttype) {
    this.kontakttype    = kontakttype;
  }

  public void setNaam(String naam) {
    this.naam           = naam;
  }

  public void setOpmerking(String opmerking) {
    this.opmerking      = opmerking;
  }

  public void setPseudoniem(String pseudoniem) {
    this.pseudoniem     = pseudoniem;
  }

  public void setRoepnaam(String roepnaam) {
    this.roepnaam       = roepnaam;
  }

  public void setSoort(String soort) {
    this.soort          = soort;
  }

  public void setTaal(String taal) {
    this.taal           = taal;
  }

  public void setVoornaam(String voornaam) {
    this.voornaam       = voornaam;
  }
}
