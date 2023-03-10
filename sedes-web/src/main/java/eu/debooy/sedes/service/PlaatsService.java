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

package eu.debooy.sedes.service;

import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.sedes.access.PlaatsDao;
import eu.debooy.sedes.domain.PlaatsDto;
import java.util.ArrayList;
import java.util.List;
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
@Named("sedesPlaatsService")
@Path("/plaatsen")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Lock(LockType.WRITE)
public class PlaatsService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(PlaatsService.class);

  @Inject
  private PlaatsDao plaatsDao;

  public PlaatsService() {
    LOGGER.debug("init PlaatsService");
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void delete(Long plaatsId) {
    PlaatsDto plaats  = plaatsDao.getByPrimaryKey(plaatsId);
    plaatsDao.delete(plaats);
  }

  @GET
  @Path("/land/{landId}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getPerLand(@PathParam(PlaatsDto.COL_LANDID) Long landId) {
    try {
      return Response.ok().entity(plaatsDao.getPerLand(landId)).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @GET
  @Path("/regio/{regioId}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getPerRegio(@PathParam(PlaatsDto.COL_REGIOID) Long regioId) {
    try {
      return Response.ok().entity(plaatsDao.getPerRegio(regioId)).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @GET
  @Path("/{plaatsId}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getPlaats(@PathParam(PlaatsDto.COL_PLAATSID) Long plaatsId) {
    try {
      return Response.ok().entity(plaatsDao.getByPrimaryKey(plaatsId)).build();
    } catch (ObjectNotFoundException e) {
      var message = new Message.Builder()
                               .setAttribute(PlaatsDto.COL_PLAATSID)
                               .setMessage(PersistenceConstants.NOTFOUND)
                               .setSeverity(Message.ERROR).build();
      return Response.status(400).entity(message).build();
    }
  }

  @GET
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getRegios() {
    try {
      return Response.ok().entity(plaatsDao.getAll()).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public PlaatsDto plaats(Long plaatsId) {
    try {
      return plaatsDao.getByPrimaryKey(plaatsId);
    } catch (ObjectNotFoundException e) {
      return new PlaatsDto();
    }
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void save(PlaatsDto plaats) {
    if (null == plaats.getRegioId()) {
      plaatsDao.create(plaats);
    } else {
      plaatsDao.update(plaats);
    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<SelectItem> selectPlaatsen() {
    List<SelectItem>  items = new ArrayList<>();
    Set<PlaatsDto>    rijen = new TreeSet<>();

    try {
      rijen.addAll(plaatsDao.getAll());
      rijen.forEach(
          plaats -> items.add(new SelectItem(plaats.getPlaatsId().toString(),
                                             plaats.getPlaatsnaam())));
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }
    return items;
  }
}
