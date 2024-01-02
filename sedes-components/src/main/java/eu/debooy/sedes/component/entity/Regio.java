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


/**
 * @author Marco de Booij
 */
public class Regio implements Serializable {
  private final Long    landId;
  private final String  naam;
  private final Long    regioId;
  private final String  regiokode;

  private Regio(Builder builder) {
    landId    = builder.getLandId();
    naam      = builder.getNaam();
    regioId   = builder.getRegioId();
    regiokode = builder.getRegiokode();
  }

  public static final class Builder {
    private Long    landId;
    private String  naam;
    private Long    regioId;
    private String  regiokode;

    public Regio build() {
      return new Regio(this);
    }

    public Long getLandId() {
      return landId;
    }

    public String getNaam() {
      return naam;
    }

    public Long getRegioId() {
      return regioId;
    }

    public String getRegiokode() {
      return regiokode;
    }

    public Builder setLandId(Long landId) {
      this.landId     = landId;
      return this;
    }

    public Builder setNaam(String naam) {
      this.naam       = naam;
      return this;
    }

    public Builder setRegioId(Long regioId) {
      this.regioId    = regioId;
      return this;
    }

    public Builder setRegiokode(String regiokode) {
      this.regiokode  = regiokode;
      return this;
    }
  }

  public Long getLandId() {
    return landId;
  }

  public String getNaam() {
    return naam;
  }

  public Long getRegioId() {
    return regioId;
  }

  public String getRegiokode() {
    return regiokode;
  }

  @Override
  public String toString() {
    var sb  = new StringBuilder();

    sb.append("Kontakt (")
      .append("landId=").append(getLandId())
      .append(", naam=").append(getNaam())
      .append(", regioId=").append(getRegioId())
      .append(", regiokode=").append(getRegiokode())
      .append(")");

    return sb.toString();
  }
}
