/**
 * Copyright 2016 Marco de Booij
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
import eu.debooy.sedes.domain.LandnaamDto;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author Marco de Booij
 */
public class Landnaam
    extends Formulier implements Cloneable, Comparable<Landnaam>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private boolean gewijzigd = false;

  private String    hoofdstad;
  private Long      landId;
  private String    landnaam;
  private String    officieleNaam;
  private String    taal;

  public Landnaam() {}

  public Landnaam(LandnaamDto landnaamDto) {
    hoofdstad     = landnaamDto.getHoofdstad();
    landId        = landnaamDto.getLandId();
    landnaam      = landnaamDto.getLandnaam();
    officieleNaam = landnaamDto.getOfficieleNaam();
    taal          = landnaamDto.getTaal();
  }

  public static class NaamComparator
      implements Comparator<Landnaam>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    public int compare(Landnaam landnaam1, Landnaam landnaam2) {
      return landnaam1.landnaam.compareTo(landnaam2.landnaam);
    }
  }

  public Landnaam clone() throws CloneNotSupportedException {
    Landnaam  clone = (Landnaam) super.clone();

    return clone;
  }

  public int compareTo(Landnaam andere) {
    return new CompareToBuilder().append(landId, andere.landId)
                                 .append(taal, andere.taal)
                                 .toComparison();
  }

  public boolean equals(Object object) {
    if (!(object instanceof Landnaam)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    Landnaam  andere  = (Landnaam) object;
    return new EqualsBuilder().append(landId, andere.landId)
                              .append(taal, andere.taal).isEquals();
  }

  public String getHoofdstad() {
    return hoofdstad;
  }

  public Long getLandId() {
    return landId;
  }

  public String getLandnaam() {
    return landnaam;
  }

  public String getOfficieleNaam() {
    return officieleNaam;
  }

  public String getTaal() {
    return taal;
  }

  public int hashCode() {
    return new HashCodeBuilder().append(landId).append(taal).toHashCode();
  }

  public boolean isGewijzigd() {
    return gewijzigd;
  }

  public void persist(LandnaamDto parameter) {
    if (!new EqualsBuilder().append(hoofdstad,
                                    parameter.getHoofdstad()).isEquals()) {
      parameter.setHoofdstad(hoofdstad);
    }
    if (!new EqualsBuilder().append(landId,
                                    parameter.getLandId()).isEquals()) {
      parameter.setLandId(landId);
    }
    if (!new EqualsBuilder().append(landnaam,
                                    parameter.getLandnaam()).isEquals()) {
      parameter.setLandnaam(landnaam);
    }
    if (!new EqualsBuilder().append(officieleNaam,
                                    parameter.getOfficieleNaam()).isEquals()) {
      parameter.setOfficieleNaam(officieleNaam);
    }
    if (!new EqualsBuilder().append(taal,
                                    parameter.getTaal()).isEquals()) {
      parameter.setTaal(taal);
    }
  }

  public void setHoofdstad(String hoofdstad) {
    if (!new EqualsBuilder().append(this.hoofdstad, hoofdstad).isEquals()) {
      gewijzigd       = true;
      this.hoofdstad  = hoofdstad;
    }
  }

  public void setLandId(Long landId) {
    if (!new EqualsBuilder().append(this.landId, landId).isEquals()) {
      gewijzigd   = true;
      this.landId = landId;
    }
  }

  public void setLandnaam(String landnaam) {
    if (!new EqualsBuilder().append(this.landnaam, landnaam).isEquals()) {
      gewijzigd     = true;
      this.landnaam = landnaam;
    }
  }

  public void setOfficieleNaam(String officieleNaam) {
    if (!new EqualsBuilder().append(this.officieleNaam, officieleNaam)
                            .isEquals()) {
      gewijzigd           = true;
      this.officieleNaam  = officieleNaam;
    }
  }

  public void setTaal(String taal) {
    if (!new EqualsBuilder().append(this.taal, taal).isEquals()) {
      gewijzigd = true;
      this.taal = taal;
    }
  }
}
