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
package eu.debooy.sedes.domain;

import eu.debooy.doosutils.domain.Dto;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosLayer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
@Entity
@Table(name="WERELDDELEN", schema="SEDES")
public class WerelddeelDto extends Dto implements Comparable<WerelddeelDto> {
  private static final  long  serialVersionUID  = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="WERELDDEEL_ID", nullable=false)
  private Long  werelddeelId;

  @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, targetEntity=WerelddeelnaamDto.class, orphanRemoval=true)
  @JoinColumn(name="WERELDDEEL_ID", nullable=false, updatable=false, insertable=true)
  @MapKey(name="taal")
  private Map<String, WerelddeelnaamDto>  werelddeelnamen = new HashMap<>();

  @Override
  public int compareTo(WerelddeelDto werelddeelnaamDto) {
    return new CompareToBuilder().append(werelddeelId,
                                         werelddeelnaamDto.werelddeelId)
                                 .toComparison();
  }

  public void addNaam(WerelddeelnaamDto werelddeelnaamDto) {
    if (null == werelddeelnaamDto.getWerelddeelId()) {
      werelddeelnaamDto.setWerelddeelId(werelddeelId);
    }
    werelddeelnamen.put(werelddeelnaamDto.getTaal(), werelddeelnaamDto);
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof WerelddeelDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var werelddeelnaamDto = (WerelddeelDto) object;
    return new EqualsBuilder().append(werelddeelId,
                                      werelddeelnaamDto.werelddeelId)
                              .isEquals();
  }

  public Long getWerelddeelId() {
    return werelddeelId;
  }

  public WerelddeelnaamDto getWerelddeelnaam(String taal) {
    if (werelddeelnamen.containsKey(taal)) {
      return werelddeelnamen.get(taal);
    } else {
      return new WerelddeelnaamDto();
    }
  }

  public Collection<WerelddeelnaamDto> getWerelddeelnamen() {
    return werelddeelnamen.values();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(werelddeelId).toHashCode();
  }

  public void removeNaam(String taal) {
    if (werelddeelnamen.containsKey(taal)) {
      werelddeelnamen.remove(taal);
    } else {
      throw new ObjectNotFoundException(DoosLayer.PERSISTENCE, taal);
    }
  }

  public void removeNaam(WerelddeelnaamDto werelddeelnaam) {
    removeNaam(werelddeelnaam.getTaal());
  }

  public void
      setNamen(Collection<WerelddeelnaamDto> werelddeelnamen) {
    this.werelddeelnamen.clear();
    for (WerelddeelnaamDto werelddeelnaam : werelddeelnamen) {
      this.werelddeelnamen.put(werelddeelnaam.getTaal(), werelddeelnaam);
    }
  }

  public void setNamen(Map<String,
                                 WerelddeelnaamDto> werelddeelnamen) {
    this.werelddeelnamen.clear();
    this.werelddeelnamen.putAll(werelddeelnamen);
  }

  public void setWerelddeelId(Long werelddeelId) {
    this.werelddeelId = werelddeelId;
  }
}
