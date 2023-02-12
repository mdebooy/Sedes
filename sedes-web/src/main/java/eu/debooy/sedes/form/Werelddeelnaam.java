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
    extends Formulier implements Comparable<Werelddeelnaam>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private String    taal;
  private Long      werelddeelId;
  private String    naam;

  public Werelddeelnaam() {}

  public Werelddeelnaam(WerelddeelnaamDto werelddeelnaamDto) {
    taal          = werelddeelnaamDto.getTaal();
    werelddeelId  = werelddeelnaamDto.getWerelddeelId();
    naam          = werelddeelnaamDto.getWerelddeelnaam();
  }

  public static class NaamComparator
      implements Comparator<Werelddeelnaam>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(Werelddeelnaam werelddeelnaam1,
                       Werelddeelnaam werelddeelnaam2) {
      return werelddeelnaam1.naam.compareTo(werelddeelnaam2.naam);
    }
  }

  @Override
  public int compareTo(Werelddeelnaam andere) {
    return new CompareToBuilder().append(werelddeelId, andere.werelddeelId)
                                 .append(taal, andere.taal)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Werelddeelnaam)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var andere  = (Werelddeelnaam) object;
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
    return naam;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(werelddeelId).append(taal).toHashCode();
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
    if (!new EqualsBuilder().append(naam,
                                    parameter.getWerelddeelnaam()).isEquals()) {
      parameter.setWerelddeelnaam(naam);
    }
  }

  public void setTaal(String taal) {
    this.taal         = taal;
  }

  public void setWerelddeelId(Long werelddeelId) {
    this.werelddeelId = werelddeelId;
  }

  public void setWerelddeelnaam(String naam) {
    this.naam         = naam;
  }
}
