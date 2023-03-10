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

package eu.debooy.sedes.form;

import eu.debooy.doosutils.form.Formulier;
import eu.debooy.sedes.domain.PlaatsDto;
import java.io.Serializable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Plaats extends Formulier
                    implements Comparable<Plaats>, Serializable {
  private String  breedte;
  private Double  breedtegraad;
  private Long    landId;
  private String  lengte;
  private Double  lengtegraad;
  private Long    plaatsId;
  private String  plaatsnaam;
  private String  postkode;
  private Long    regioId;
  private Long    zonenummer;

  public Plaats() {}

  public Plaats(PlaatsDto plaatsDto) {
    breedte       = plaatsDto.getBreedte();
    breedtegraad  = plaatsDto.getBreedtegraad();
    landId        = plaatsDto.getLandId();
    lengte        = plaatsDto.getLengte();
    lengtegraad   = plaatsDto.getLengtegraad();
    plaatsId      = plaatsDto.getPlaatsId();
    plaatsnaam    = plaatsDto.getPlaatsnaam();
    postkode      = plaatsDto.getPostkode();
    regioId       = plaatsDto.getRegioId();
    zonenummer    = plaatsDto.getZonenummer();
  }

  @Override
  public int compareTo(Plaats andere) {
    return new CompareToBuilder().append(plaatsnaam, andere.plaatsnaam)
                                 .append(plaatsId, andere.plaatsId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Plaats)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var andere  = (Plaats) object;
    return new EqualsBuilder().append(plaatsId, andere.plaatsId)
                              .isEquals();
  }

  public String getBreedte() {
    return breedte;
  }

  public Double getBreedtegraad() {
    return breedtegraad;
  }

  public Long getLandId() {
    return landId;
  }

  public String getLengte() {
    return lengte;
  }

  public Double getLengtegraad() {
    return lengtegraad;
  }

  public Long getPlaatsId() {
    return plaatsId;
  }

  public String getPlaatsnaam() {
    return plaatsnaam;
  }

  public String getPostkode() {
    return postkode;
  }

  public Long getRegioId() {
    return regioId;
  }

  public Long getZonenummer() {
    return zonenummer;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(plaatsId).toHashCode();
  }

  public void persist(PlaatsDto plaatsDto) {
    plaatsDto.setBreedte(breedte);
    plaatsDto.setBreedtegraad(breedtegraad);
    plaatsDto.setLandId(landId);
    plaatsDto.setLengte(lengte);
    plaatsDto.setLengtegraad(lengtegraad);
    plaatsDto.setPlaatsId(plaatsId);
    plaatsDto.setPlaatsnaam(plaatsnaam);
    plaatsDto.setPostkode(postkode);
    plaatsDto.setRegioId(regioId);
    plaatsDto.setZonenummer(zonenummer);
  }

  public void setBreedte(String breedte) {
    this.breedte      = breedte;
  }

  public void setBreedtegraad(Double breedtegraad) {
    this.breedtegraad = breedtegraad;
  }

  public void setLandId(Long landId) {
    this.landId       = landId;
  }

  public void setLengte(String lengte) {
    this.lengte       = lengte;
  }

  public void setLengtegraad(Double lengtegraad) {
    this.lengtegraad  = lengtegraad;
  }

  public void setPlaatsId(Long plaatsId) {
    this.plaatsId     = plaatsId;
  }

  public void setPlaatsnaam(String plaatsnaam) {
    this.plaatsnaam   = plaatsnaam;
  }

  public void setPostkode(String postkode) {
    this.postkode     = postkode;
  }

  public void setRegioId(Long regioId) {
    this.regioId      = regioId;
  }

  public void setZonenummer(Long zonenummer) {
    this.zonenummer   = zonenummer;
  }
}
