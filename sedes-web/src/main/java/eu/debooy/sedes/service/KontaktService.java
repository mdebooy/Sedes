/**
 * Copyright 2023 Marco de Booij
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
import eu.debooy.sedes.access.KontaktDao;
import eu.debooy.sedes.domain.KontaktDto;
import eu.debooy.sedes.form.Kontakt;
import java.util.ArrayList;
import java.util.Collection;
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
@Named("sedesKontaktService")
@Path("/kontakten")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Lock(LockType.WRITE)
public class KontaktService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(KontaktService.class);

  @Inject
  private KontaktDao  kontaktDao;

  public KontaktService() {
    LOGGER.debug("init KontaktService");
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void delete(Long kontaktId) {
    KontaktDto kontakt  = kontaktDao.getByPrimaryKey(kontaktId);
    kontaktDao.delete(kontakt);
  }

  @GET
  @Path("/{kontaktId}")
  public Response getKontakt(
      @PathParam(KontaktDto.COL_KONTAKTID) Long kontaktId) {
    try {
      return Response.ok().entity(kontaktDao.getKontakt(kontaktId)).build();
    } catch (ObjectNotFoundException e) {
      var message = new Message.Builder()
                               .setAttribute(KontaktDto.COL_KONTAKTID)
                               .setMessage(PersistenceConstants.NOTFOUND)
                               .setSeverity(Message.ERROR).build();
      return Response.status(400).entity(message).build();
    }
  }

  @GET
  public Response getKontakten() {
    try {
      return Response.ok().entity(kontaktDao.getAll()).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public KontaktDto kontakt(Long kontaktId) {
    return kontaktDao.getByPrimaryKey(kontaktId);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<Kontakt> query() {
    Collection<Kontakt>      kontakten  = new ArrayList<>();
    try {
      Collection<KontaktDto> rijen   = kontaktDao.getAll();
      for (KontaktDto rij : rijen) {
        kontakten.add(new Kontakt(rij));
      }
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return kontakten;
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void save(KontaktDto kontakt) {
    if (null == kontakt.getKontaktId()) {
      kontaktDao.create(kontakt);
    } else {
      kontaktDao.update(kontakt);
    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<SelectItem> selectKontakten() {
    List<SelectItem>  items = new ArrayList<>();

    try {
      kontaktDao.getAll().forEach(
          kontakt -> items.add(new SelectItem(kontakt.getKontaktId().toString(),
                                              kontakt.getDisplaynaam())));
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }
    return items;
  }
}
