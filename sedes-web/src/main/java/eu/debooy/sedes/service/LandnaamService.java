/**
 * Copyright 2015 Marco de Booij
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
package eu.debooy.sedes.service;

import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.sedes.access.LandnaamDao;
import eu.debooy.sedes.domain.LandnaamDto;
import eu.debooy.sedes.domain.LandnaamPK;
import eu.debooy.sedes.form.Landnaam;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Singleton
@Named("sedesLandnaamService")
@Path("/landnamen")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Lock(LockType.WRITE)
public class LandnaamService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(LandnaamService.class);

  private final Map<Long, Map<String, String>>  landnamenCache;
  private final String                          standaardTaal;

  public void clear() {
    landnamenCache.clear();
  }

  @SuppressWarnings("java:S6813")
  @Inject
  private LandnaamDao   landnaamDao;

  public LandnaamService() {
    landnamenCache  = new HashMap<>();
    // TODO Ophalen uit database.
    standaardTaal   = "nl";

    LOGGER.debug("init LandnaamService");
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<Landnaam> bestaandeLandnamen(String taal) {
    Set<Landnaam> landnamen = new HashSet<>();

    try {
      landnaamDao.getBestaandeLandnamenPerTaal(taal)
                 .forEach(rij -> landnamen.add(new Landnaam(rij)));
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return landnamen;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<Landnaam>
      bestaandeLandnamenPerWerelddeel(String taal, Long werelddeelId) {
    Set<Landnaam> landnamen = new HashSet<>();

    try {
      landnaamDao.getBestaandeLandnamenPerWerelddeelPerTaal(taal,
                                                            werelddeelId)
                 .forEach(rij -> landnamen.add(new Landnaam(rij)));
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return landnamen;
  }

  @GET
  @Path("/{landId}/{taal}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getLandnaam(@PathParam(LandnaamDto.COL_LANDID) Long landId,
                               @PathParam(LandnaamDto.COL_TAAL) String taal) {
    Map<String, String> landnamen = new HashMap<>();
    if (landnamenCache.containsKey(landId)) {
      landnamen = landnamenCache.get(landId);
    } else {
      try {
        var rijen = landnaamDao.getPerLand(landId);
        for (var rij: rijen) {
          landnamen.put(rij.getTaal(), rij.getNaam());
        }
        landnamenCache.put(landId, landnamen);
      } catch (ObjectNotFoundException e) {
        // Land onbekend.
      }
    }

    if (landnamen.containsKey(taal)) {
      return Response.ok().entity(landnamen.get(taal)).build();
    }

    if (landnamen.containsKey(getStandaardTaal())) {
      return Response.ok().entity(landnamen.get(getStandaardTaal())).build();
    }

    return Response.ok().entity("??? " + landId + ":" + taal + " ???").build();
  }

  private String getStandaardTaal() {
    return standaardTaal;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public LandnaamDto landnaam(Long landId, String taal) {
    return landnaamDao.getByPrimaryKey(new LandnaamPK(landId, taal));
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<Landnaam> landnamen(String taal) {
    Set<Landnaam> landnamen = new HashSet<>();

    landnaamDao.getPerTaal(taal)
               .forEach(rij -> landnamen.add(new Landnaam(rij)));

    return landnamen;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<Landnaam> query(Long landId) {
    Set<Landnaam> landnamen = new HashSet<>();

    landnaamDao.getPerLand(landId)
               .forEach(rij -> landnamen.add(new Landnaam(rij)));

    return landnamen;
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void save(LandnaamDto landnaam) {
    landnaamDao.update(landnaam);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<SelectItem> selectLandnamen(String taal) {
    Collection<SelectItem>  items = new LinkedList<>();
    Set<LandnaamDto>        rijen =
        new TreeSet<>(new LandnaamDto.NaamComparator());
    try {
      rijen.addAll(landnaamDao.getPerTaal(taal));
      for (var rij : rijen) {
        items.add(new SelectItem(rij.getLandId(), rij.getNaam()));
      }
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return items;
  }
}
