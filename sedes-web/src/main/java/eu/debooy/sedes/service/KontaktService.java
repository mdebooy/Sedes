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
import jakarta.ejb.Lock;
import jakarta.ejb.LockType;
import jakarta.ejb.Singleton;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
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
@Lock(LockType.READ)
public class KontaktService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(KontaktService.class);

  @SuppressWarnings("java:S6813")
  @Inject
  private KontaktDao  kontaktDao;

  public KontaktService() {
    LOGGER.debug("init KontaktService");
  }

  @Lock(LockType.WRITE)
  public void delete(Long kontaktId) {
    KontaktDto kontakt  = kontaktDao.getByPrimaryKey(kontaktId);
    kontaktDao.delete(kontakt);
  }

  @GET
  @Path("/{kontaktId}")
  public Response getKontakt(
      @PathParam(KontaktDto.COL_KONTAKTID) Long kontaktId) {
    try {
      return Response.ok().entity(kontaktDao.getByPrimaryKey(kontaktId))
                          .build();
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

  @GET
  @Path("/type/{kontakttype}")
  public Response getKontaktenPerType(
      @PathParam(KontaktDto.COL_KONTAKTTYPE) String  kontakttype) {
    try {
      return Response.ok().entity(kontaktDao.getPerKontakttype(kontakttype))
                     .build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  public KontaktDto kontakt(Long kontaktId) {
    return kontaktDao.getByPrimaryKey(kontaktId);
  }

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

  public void save(KontaktDto kontakt) {
    if (null == kontakt.getKontaktId()) {
      kontaktDao.create(kontakt);
    } else {
      kontaktDao.update(kontakt);
    }
  }

  public Collection<SelectItem> selectKontakten() {
    Collection<SelectItem>  items = new ArrayList<>();
    Set<KontaktDto>         rijen =
        new TreeSet<>(new KontaktDto.DisplaynaamComparator());
    try {
      rijen.addAll(kontaktDao.getAll());
      items.add(new SelectItem("", "--"));
      for (var rij : rijen) {
        items.add(new SelectItem(rij.getKontaktId().toString(),
                                 rij.getDisplaynaam()));
      }
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }
    return items;
  }
}
