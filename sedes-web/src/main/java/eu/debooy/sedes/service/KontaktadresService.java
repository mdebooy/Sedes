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

import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.sedes.access.KontaktadresDao;
import eu.debooy.sedes.domain.KontaktadresDto;
import jakarta.ejb.Lock;
import jakarta.ejb.LockType;
import jakarta.ejb.Singleton;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Singleton
@Named("sedesKontaktadresService")
@Path("/kontaktadressen")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Lock(LockType.WRITE)
public class KontaktadresService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(KontaktadresService.class);

  @SuppressWarnings("java:S6813")
  @Inject
  private KontaktadresDao kontaktadresDao;

  public KontaktadresService() {
    LOGGER.debug("init KontaktadresService");
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public KontaktadresDto adres(Long kontaktadresId) {
    return kontaktadresDao.getByPrimaryKey(kontaktadresId);
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void delete(KontaktadresDto kontaktadres) {
    kontaktadresDao.delete(kontaktadres);
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void delete(Long kontaktadresId) {
    KontaktadresDto kontaktadres  =
        kontaktadresDao.getByPrimaryKey(kontaktadresId);
    delete(kontaktadres);
  }

  @GET
  @Path("/adres/{adresId}")
  public Response getAdressen(
            @PathParam(KontaktadresDto.COL_ADRESID) Long adresId) {
    try {
      return Response.ok().entity(kontaktadresDao.getPerAdres(adresId))
                          .build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @GET
  public Response getKontaktadressen() {
    try {
      return Response.ok().entity(kontaktadresDao.getAll()).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @GET
  @Path("/kontakt/{kontaktId}")
  public Response getKontakten(
            @PathParam(KontaktadresDto.COL_KONTAKTID) Long kontaktId) {
    try {
      return Response.ok().entity(kontaktadresDao.getPerKontakt(kontaktId))
                          .build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public KontaktadresDto kontaktadres(Long kontaktadresId) {
    return kontaktadresDao.getByPrimaryKey(kontaktadresId);
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void save(KontaktadresDto kontaktadres) {
    if (null == kontaktadres.getKontaktadresId()) {
      kontaktadresDao.create(kontaktadres);
    } else {
      kontaktadresDao.update(kontaktadres);
    }
  }
}
