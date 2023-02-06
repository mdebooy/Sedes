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
package eu.debooy.sedes.service;

import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.sedes.access.LandDao;
import eu.debooy.sedes.domain.LandDto;
import eu.debooy.sedes.form.Land;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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
@Named("sedesLandService")
@Path("/landen")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Lock(LockType.WRITE)
public class LandService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(LandService.class);

  @Inject
  private LandDao landDao;

  public LandService() {
    LOGGER.debug("init LandService");
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void delete(Long landId) {
    LandDto land  = landDao.getByPrimaryKey(landId);
    landDao.delete(land);
  }

  @GET
  @Path("/{landId}")
  public Response getLand(@PathParam(LandDto.COL_LANDID) Long landId) {
    try {
      return Response.ok().entity(landDao.getLand(landId)).build();
    } catch (ObjectNotFoundException e) {
      var message = new Message.Builder()
                               .setAttribute(LandDto.COL_LANDID)
                               .setMessage(PersistenceConstants.NOTFOUND)
                               .setSeverity(Message.ERROR).build();
      return Response.status(400).entity(message).build();
    }
  }

  @GET
  public Response getLanden() {
    try {
      return Response.ok().entity(landDao.getAll()).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public LandDto land(Long landId) {
    return landDao.getByPrimaryKey(landId);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<Land> query() {
    Collection<Land>      landen  = new ArrayList<>();
    try {
      Collection<LandDto> rijen   = landDao.getAll();
      for (LandDto rij : rijen) {
        landen.add(new Land(rij));
      }
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return landen;
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void save(LandDto land) {
    if (null == land.getLandId()) {
      landDao.create(land);
      land.setLandId(land.getLandId());
    } else {
      landDao.update(land);
    }
  }
}
