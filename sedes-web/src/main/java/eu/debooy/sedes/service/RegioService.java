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
import eu.debooy.sedes.access.RegioDao;
import eu.debooy.sedes.domain.RegioDto;
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
@Named("sedesRegioService")
@Path("/regios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Lock(LockType.WRITE)
public class RegioService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(RegioService.class);

  @SuppressWarnings("java:S6813")
  @Inject
  private RegioDao  regioDao;

  public RegioService() {
    LOGGER.debug("init RegioService");
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void delete(Long regioId) {
    RegioDto regio  = regioDao.getByPrimaryKey(regioId);
    regioDao.delete(regio);
  }

  @GET
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  @Path("/NUTS0")
  public Response getNuts0() {
    try {
      return Response.ok().entity(regioDao.getNuts0()).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @GET
  @Path("/NUTS1")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getNuts1() {
    try {
      return Response.ok().entity(regioDao.getNuts1()).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @GET
  @Path("/NUTS2")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getNuts2() {
    try {
      return Response.ok().entity(regioDao.getNuts2()).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @GET
  @Path("/NUTS3")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getNuts3() {
    try {
      return Response.ok().entity(regioDao.getNuts3()).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @GET
  @Path("/land/{landId}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getPerLand(@PathParam(RegioDto.COL_LANDID) Long landId) {
    try {
      return Response.ok().entity(regioDao.getPerLand(landId)).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @GET
  @Path("/{regioId}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getRegio(@PathParam(RegioDto.COL_REGIOID) Long regioId) {
    try {
      return Response.ok().entity(regioDao.getByPrimaryKey(regioId)).build();
    } catch (ObjectNotFoundException e) {
      var message = new Message.Builder()
                               .setAttribute(RegioDto.COL_REGIOID)
                               .setMessage(PersistenceConstants.NOTFOUND)
                               .setSeverity(Message.ERROR).build();
      return Response.status(400).entity(message).build();
    }
  }

  @GET
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getRegios() {
    try {
      return Response.ok().entity(regioDao.getAll()).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @GET
  @Path("/ddlb")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getSelectRegios() {
    return Response.ok().entity(selectRegios()).build();
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public RegioDto regio(Long regioId) {
    try {
      return regioDao.getByPrimaryKey(regioId);
    } catch (ObjectNotFoundException e) {
      return new RegioDto();
    }
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void save(RegioDto regio) {
    if (null == regio.getRegioId()) {
      regioDao.create(regio);
    } else {
      regioDao.update(regio);
    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<SelectItem> selectRegios() {
    List<SelectItem>  items = new ArrayList<>();
    Set<RegioDto>     rijen =
        new TreeSet<>(new RegioDto.NaamComparator());

    try {
      rijen.addAll(regioDao.getAll());
      rijen.forEach(
          regio -> items.add(new SelectItem(regio.getRegioId().toString(),
                                            regio.getNaam())));
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }
    return items;
  }
}
