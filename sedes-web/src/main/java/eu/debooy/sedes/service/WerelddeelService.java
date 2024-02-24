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

import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.sedes.access.WerelddeelDao;
import eu.debooy.sedes.domain.WerelddeelDto;
import eu.debooy.sedes.form.Werelddeel;
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
@Named("sedesWerelddeelService")
@Path("/werelddelen")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Lock(LockType.WRITE)
public class WerelddeelService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(WerelddeelService.class);

  @SuppressWarnings("java:S6813")
  @Inject
  private WerelddeelDao             werelddeelDao;

  public WerelddeelService() {
    LOGGER.debug("init WerelddeelService");
  }


  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void delete(Long werelddeelId) {
    WerelddeelDto dto = werelddeelDao.getByPrimaryKey(werelddeelId);
    werelddeelDao.delete(dto);
  }

  @GET
  @Path("/{werelddeelId}")
  public Response getLand(
      @PathParam(WerelddeelDto.COL_WERELDDEELID) Long werelddeelId) {
    try {
      return Response.ok()
                      .entity(werelddeelDao.getByPrimaryKey(werelddeelId))
                      .build();
    } catch (ObjectNotFoundException e) {
      var message = new Message.Builder()
                               .setAttribute(WerelddeelDto.COL_WERELDDEELID)
                               .setMessage(PersistenceConstants.NOTFOUND)
                               .setSeverity(Message.ERROR).build();
      return Response.status(400).entity(message).build();
    }
  }

  @GET
  public Response getLanden() {
    try {
      return Response.ok().entity(werelddeelDao.getAll()).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<Werelddeel> query() {
    Collection<Werelddeel>  werelddelen  = new ArrayList<>();
    try {
      Collection<WerelddeelDto> rijen    = werelddeelDao.getAll();
      for (WerelddeelDto rij : rijen) {
        werelddelen.add(new Werelddeel(rij));
      }
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return werelddelen;
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void save(WerelddeelDto werelddeel) {
    if (null == werelddeel.getWerelddeelId()) {
      werelddeelDao.create(werelddeel);
    } else {
      werelddeelDao.update(werelddeel);
    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public WerelddeelDto werelddeel(Long werelddeelId) {
    return werelddeelDao.getByPrimaryKey(werelddeelId);
  }
}
