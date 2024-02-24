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
import eu.debooy.sedes.access.MuntDao;
import eu.debooy.sedes.domain.MuntDto;
import java.util.ArrayList;
import java.util.List;
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
@Named("sedesMuntService")
@Path("/munten")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Lock(LockType.WRITE)
public class MuntService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(MuntService.class);

  @SuppressWarnings("java:S6813")
  @Inject
  private MuntDao muntDao;

  public MuntService() {
    LOGGER.debug("init MuntService");
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void delete(Long muntId) {
    MuntDto munt  = muntDao.getByPrimaryKey(muntId);
    muntDao.delete(munt);
  }

  @GET
  @Path("/{muntId}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getMunt(@PathParam(MuntDto.COL_MUNTID) Long muntId) {
    try {
      return Response.ok().entity(muntDao.getByPrimaryKey(muntId)).build();
    } catch (ObjectNotFoundException e) {
      var message = new Message.Builder()
                               .setAttribute(MuntDto.COL_MUNTID)
                               .setMessage(PersistenceConstants.NOTFOUND)
                               .setSeverity(Message.ERROR).build();
      return Response.status(400).entity(message).build();
    }
  }

  @GET
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getMunten() {
    try {
      return Response.ok().entity(muntDao.getAll()).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public MuntDto munt(Long muntId) {
    try {
      return muntDao.getByPrimaryKey(muntId);
    } catch (ObjectNotFoundException e) {
      return new MuntDto();
    }
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void save(MuntDto munt) {
    if (null == munt.getMuntId()) {
      muntDao.create(munt);
    } else {
      muntDao.update(munt);
    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<SelectItem> selectMunten() {
    List<SelectItem>  items = new ArrayList<>();

    try {
      muntDao.getAll().forEach(
          munt -> items.add(new SelectItem(munt.getMuntId().toString(),
                                           munt.getNaam())));
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }
    return items;
  }
}
