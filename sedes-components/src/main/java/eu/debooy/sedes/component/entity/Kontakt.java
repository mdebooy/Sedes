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

package eu.debooy.sedes.component.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * @author Marco de Booij
 */
public class Kontakt implements Serializable {
  private final String  aanspreekId;
  private final String  displaynaam;
  private final Date    geboortedatum;
  private final String  gebruikersnaam;
  private final String  initialen;
  private final Long    kontaktId;
  private final String  kontakttype;
  private final String  naam;
  private final String  pseudoniem;
  private final String  roepnaam;
  private final String  taal;
  private final String  tussenvoegsel;
  private final String  voornaam;

  private Kontakt(Builder builder) {
    aanspreekId     = builder.getAanspreekId();
    displaynaam     = builder.getDisplaynaam();
    geboortedatum   = builder.getGeboortedatum();
    gebruikersnaam  = builder.getGebruikersnaam();
    initialen       = builder.getInitialen();
    kontaktId       = builder.getKontaktId();
    kontakttype     = builder.getKontakttype();
    naam            = builder.getNaam();
    pseudoniem      = builder.getPseudoniem();
    roepnaam        = builder.getRoepnaam();
    taal            = builder.getTaal();
    tussenvoegsel   = builder.getTussenvoegsel();
    voornaam        = builder.getVoornaam();
  }

  public static final class Builder {
    private String  aanspreekId;
    private String  displaynaam;
    private Date    geboortedatum;
    private String  gebruikersnaam;
    private String  initialen;
    private Long    kontaktId;
    private String  kontakttype;
    private String  naam;
    private String  pseudoniem;
    private String  roepnaam;
    private String  taal;
    private String  tussenvoegsel;
    private String  voornaam;

    public Kontakt build() {
      return new Kontakt(this);
    }

    public String getAanspreekId() {
      return aanspreekId;
    }

    public String getDisplaynaam() {
      return displaynaam;
    }

    public Date getGeboortedatum() {
      return geboortedatum;
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

    public String getVoornaam() {
      return voornaam;
    }

    public Builder setAanspreekId(String aanspreekId) {
      this.aanspreekId      = aanspreekId;
      return this;
    }

    public Builder setDisplaynaam(String displaynaam) {
      this.displaynaam      = displaynaam;
      return this;
    }

    public Builder setGeboortedatum(Date geboortedatum) {
      if (null == geboortedatum) {
        this.geboortedatum  = null;
      } else {
        this.geboortedatum  = new Date(geboortedatum.getTime());
      }
      return this;
    }

    public Builder setGebruikersnaam(String gebruikersnaam) {
      this.gebruikersnaam   = gebruikersnaam;
      return this;
    }

    public Builder setInitialen(String initialen) {
      this.initialen        = initialen;
      return this;
    }

    public Builder setKontaktId(Long kontaktId) {
      this.kontaktId        = kontaktId;
      return this;
    }

    public Builder setKontakttype(String kontakttype) {
      this.kontakttype      = kontakttype;
      return this;
    }

    public Builder setNaam(String naam) {
      this.naam             = naam;
      return this;
    }

    public Builder setPseudoniem(String pseudoniem) {
      this.pseudoniem       = pseudoniem;
      return this;
    }

    public Builder setRoepnaam(String roepnaam) {
      this.roepnaam         = roepnaam;
      return this;
    }

    public Builder setTaal(String taal) {
      this.taal             = taal;
      return this;
    }

    public Builder setTussenvoegsel(String tussenvoegsel) {
      this.tussenvoegsel    = tussenvoegsel;
      return this;
    }

    public Builder setVoornaam(String voornaam) {
      this.voornaam         = voornaam;
      return this;
    }
  }

  public String getAanspreekId() {
    return aanspreekId;
  }

  public String getDisplaynaam() {
    return displaynaam;
  }

  public Date getGeboortedatum() {
    return geboortedatum;
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

  public String getVoornaam() {
    return voornaam;
  }

  @Override
  public String toString() {
    var sb  = new StringBuilder();

    sb.append("Kontakt (")
      .append("aanspreekId=").append(getAanspreekId())
      .append(", displaynaam=").append(getDisplaynaam())
      .append(", geboortedatum=").append(getGeboortedatum())
      .append(", gebruikersnaam=").append(getGebruikersnaam())
      .append(", initialen=").append(getInitialen())
      .append(", kontaktId=").append(getKontaktId())
      .append(", kontakttype=").append(getKontakttype())
      .append(", naam=").append(getNaam())
      .append(", pseudoniem=").append(getPseudoniem())
      .append(", roepnaam=").append(getRoepnaam())
      .append(", taal=").append(getTaal())
      .append(", tussenvoegsel=").append(getTussenvoegsel())
      .append(", voornaam=").append(getVoornaam())
      .append(")");

    return sb.toString();
  }
}
