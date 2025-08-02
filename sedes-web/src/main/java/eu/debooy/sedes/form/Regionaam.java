/*
 * Copyright (c) 2025 Marco de Booij
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

package eu.debooy.sedes.form;

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.form.Formulier;
import eu.debooy.sedes.domain.RegionaamDto;
import java.io.Serializable;
import java.util.Comparator;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Regionaam
    extends Formulier implements Comparable<Regionaam>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private String    naam;
  private Long      regioId;
  private String    taal;

  public Regionaam() {}

  public Regionaam(RegionaamDto regionaamDto) {
    naam    = regionaamDto.getNaam();
    regioId = regionaamDto.getRegioId();
    taal    = regionaamDto.getTaal();
  }

  public static class NaamComparator
      implements Comparator<Regionaam>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(Regionaam regionaam1,
                       Regionaam regionaam2) {
      return regionaam1.naam.compareTo(regionaam2.naam);
    }
  }

  @Override
  public int compareTo(Regionaam andere) {
    return new CompareToBuilder().append(regioId, andere.regioId)
                                 .append(taal, andere.taal)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Regionaam)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var andere  = (Regionaam) object;
    return new EqualsBuilder().append(regioId, andere.regioId)
                              .append(taal, andere.taal).isEquals();
  }

  public String getNaam() {
    return naam;
  }

  public String getTaal() {
    return taal;
  }

  public Long getRegioId() {
    return regioId;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(regioId).append(taal).toHashCode();
  }

  public void persist(RegionaamDto parameter) {
    parameter.setNaam(naam);
    parameter.setRegioId(regioId);
    parameter.setTaal(taal);
  }

  public void setNaam(String naam) {
    this.naam     = DoosUtils.strip(naam);
  }

  public void setRegioId(Long regioId) {
    this.regioId  = regioId;
  }

  public void setTaal(String taal) {
    this.taal     = DoosUtils.stripToLowerCase(taal);
  }
}
