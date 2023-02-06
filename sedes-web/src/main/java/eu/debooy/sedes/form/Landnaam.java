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
    extends Formulier implements Comparable<Landnaam>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private String    hoofdstad;
  private Long      landId;
  private String    naam;
  private String    officieleNaam;
  private String    taal;

  public Landnaam() {}

  public Landnaam(LandnaamDto landnaamDto) {
    hoofdstad     = landnaamDto.getHoofdstad();
    landId        = landnaamDto.getLandId();
    naam          = landnaamDto.getLandnaam();
    officieleNaam = landnaamDto.getOfficieleNaam();
    taal          = landnaamDto.getTaal();
  }

  public static class NaamComparator
      implements Comparator<Landnaam>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(Landnaam landnaam1, Landnaam landnaam2) {
      return landnaam1.naam.compareTo(landnaam2.naam);
    }
  }

  @Override
  public int compareTo(Landnaam andere) {
    return new CompareToBuilder().append(landId, andere.landId)
                                 .append(taal, andere.taal)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Landnaam)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var andere  = (Landnaam) object;
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
    return naam;
  }

  public String getOfficieleNaam() {
    return officieleNaam;
  }

  public String getTaal() {
    return taal;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(landId).append(taal).toHashCode();
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
    if (!new EqualsBuilder().append(naam,
                                    parameter.getLandnaam()).isEquals()) {
      parameter.setLandnaam(naam);
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
    this.hoofdstad  = hoofdstad;
  }

  public void setLandId(Long landId) {
    this.landId = landId;
  }

  public void setLandnaam(String naam) {
    this.naam = naam;
  }

  public void setOfficieleNaam(String officieleNaam) {
    this.officieleNaam  = officieleNaam;
  }

  public void setTaal(String taal) {
    this.taal = taal;
  }
}
