/*
 * Copyright (c) 2024 Marco de Booij
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
import eu.debooy.sedes.access.AdresDao;
import eu.debooy.sedes.domain.AdresDto;
import java.util.ArrayList;
import java.util.Collection;
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
@Named("sedesAdresService")
@Path("/adressen")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Lock(LockType.WRITE)
public class AdresService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(AdresService.class);

  @SuppressWarnings("java:S6813")
  @Inject
  private AdresDao  adresDao;

  public AdresService() {
    LOGGER.debug("init AdresService");
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public AdresDto adres(Long adresId) {
    return adresDao.getByPrimaryKey(adresId);
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void delete(AdresDto adres) {
    adresDao.delete(adres);
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void delete(Long adresId) {
    AdresDto adres  = adresDao.getByPrimaryKey(adresId);
    delete(adres);
  }

  @GET
  @Path("/{adresId}")
  public Response getAdres(@PathParam(AdresDto.COL_ADRESID) Long adresId) {
    try {
      return Response.ok().entity(adresDao.getByPrimaryKey(adresId))
                          .build();
    } catch (ObjectNotFoundException e) {
      var message = new Message.Builder()
                               .setAttribute(AdresDto.COL_ADRESID)
                               .setMessage(PersistenceConstants.NOTFOUND)
                               .setSeverity(Message.ERROR).build();
      return Response.status(400).entity(message).build();
    }
  }

  @GET
  public Response getAdressen() {
    try {
      return Response.ok().entity(adresDao.getAll()).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void save(AdresDto adres) {
    if (null == adres.getAdresId()) {
      adresDao.create(adres);
    } else {
      adresDao.update(adres);
    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<SelectItem> selectAdressen() {
    List<SelectItem>  items = new ArrayList<>();
    Set<AdresDto>     rijen = new TreeSet<>();
    try {
      rijen.addAll(adresDao.getAll());
      items.add(new SelectItem("", "--"));
      for (var rij : rijen) {
        items.add(new SelectItem(rij.getAdresId().toString(),
                                 rij.getAdresdata()));
      }
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }
    return items;
  }
}
