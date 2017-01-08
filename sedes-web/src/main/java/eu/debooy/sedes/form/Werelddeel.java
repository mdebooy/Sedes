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
import eu.debooy.sedes.domain.WerelddeelDto;

import java.io.Serializable;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Werelddeel
    extends Formulier implements Cloneable, Comparable<Werelddeel>, Serializable{
  private static final  long  serialVersionUID  = 1L;

  private boolean gewijzigd = false;

  private Long  werelddeelId;

  public Werelddeel() {}

  public Werelddeel(WerelddeelDto werelddeelDto) {
    werelddeelId    = werelddeelDto.getWerelddeelId();
  }

  public Werelddeel clone() throws CloneNotSupportedException {
    Werelddeel  clone = (Werelddeel) super.clone();

    return clone;
  }

  public int compareTo(Werelddeel andere) {
    return new CompareToBuilder().append(werelddeelId, andere.werelddeelId)
                                 .toComparison();
  }

  public boolean equals(Object object) {
    if (!(object instanceof Werelddeel)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    Werelddeel  andere  = (Werelddeel) object;
    return new EqualsBuilder().append(werelddeelId, andere.werelddeelId)
                              .isEquals();
  }

  public Long getWerelddeelId() {
    return werelddeelId;
  }

  public int hashCode() {
    return new HashCodeBuilder().append(werelddeelId).toHashCode();
  }

  public boolean isGewijzigd() {
    return gewijzigd;
  }

  public void persist(WerelddeelDto parameter) {
    if (!new EqualsBuilder().append(werelddeelId,
                                    parameter.getWerelddeelId()).isEquals()) {
      parameter.setWerelddeelId(werelddeelId);
    }
  }

  public void setWerelddeelId(Long werelddeelId) {
    if (!new EqualsBuilder().append(this.werelddeelId, werelddeelId)
                            .isEquals()) {
      gewijzigd         = true;
      this.werelddeelId = werelddeelId;
    }
  }
}
