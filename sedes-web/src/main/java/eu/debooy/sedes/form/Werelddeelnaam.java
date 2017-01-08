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
import eu.debooy.sedes.domain.WerelddeelnaamDto;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Werelddeelnaam
    extends Formulier implements Cloneable, Comparable<Werelddeelnaam>, Serializable{
  private static final  long  serialVersionUID  = 1L;

  private boolean gewijzigd = false;

  private String    taal;
  private Long      werelddeelId;
  private String    werelddeelnaam;

  public Werelddeelnaam() {}

  public Werelddeelnaam(WerelddeelnaamDto werelddeelnaamDto) {
    taal            = werelddeelnaamDto.getTaal();
    werelddeelId    = werelddeelnaamDto.getWerelddeelId();
    werelddeelnaam  = werelddeelnaamDto.getWerelddeelnaam();
  }

  public static class NaamComparator
      implements Comparator<Werelddeelnaam>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    public int compare(Werelddeelnaam werelddeelnaam1,
                       Werelddeelnaam werelddeelnaam2) {
      return werelddeelnaam1.werelddeelnaam
                               .compareTo(werelddeelnaam2.werelddeelnaam);
    }
  }

  public Werelddeelnaam clone() throws CloneNotSupportedException {
    Werelddeelnaam  clone = (Werelddeelnaam) super.clone();

    return clone;
  }

  public int compareTo(Werelddeelnaam andere) {
    return new CompareToBuilder().append(werelddeelId, andere.werelddeelId)
                                 .append(taal, andere.taal)
                                 .toComparison();
  }

  public boolean equals(Object object) {
    if (!(object instanceof Werelddeelnaam)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    Werelddeelnaam  andere  = (Werelddeelnaam) object;
    return new EqualsBuilder().append(werelddeelId, andere.werelddeelId)
                              .append(taal, andere.taal).isEquals();
  }

  public String getTaal() {
    return taal;
  }

  public Long getWerelddeelId() {
    return werelddeelId;
  }

  public String getWerelddeelnaam() {
    return werelddeelnaam;
  }

  public int hashCode() {
    return new HashCodeBuilder().append(werelddeelId).append(taal).toHashCode();
  }

  public boolean isGewijzigd() {
    return gewijzigd;
  }

  public void persist(WerelddeelnaamDto parameter) {
    if (!new EqualsBuilder().append(taal,
                                    parameter.getTaal()).isEquals()) {
      parameter.setTaal(taal);
    }
    if (!new EqualsBuilder().append(werelddeelId,
                                    parameter.getWerelddeelId()).isEquals()) {
      parameter.setWerelddeelId(werelddeelId);
    }
    if (!new EqualsBuilder().append(werelddeelnaam,
                                    parameter.getWerelddeelnaam()).isEquals()) {
      parameter.setWerelddeelnaam(werelddeelnaam);
    }
  }

  public void setTaal(String taal) {
    if (!new EqualsBuilder().append(this.taal, taal).isEquals()) {
      gewijzigd = true;
      this.taal = taal;
    }
  }

  public void setWerelddeelId(Long werelddeelId) {
    if (!new EqualsBuilder().append(this.werelddeelId, werelddeelId)
                            .isEquals()) {
      gewijzigd         = true;
      this.werelddeelId = werelddeelId;
    }
  }

  public void setWerelddeelnaam(String werelddeelnaam) {
    if (!new EqualsBuilder().append(this.werelddeelnaam, werelddeelnaam)
                            .isEquals()) {
      gewijzigd           = true;
      this.werelddeelnaam = werelddeelnaam;
    }
  }
}
