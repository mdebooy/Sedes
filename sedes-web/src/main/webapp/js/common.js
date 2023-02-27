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

var landen = {};
var werelddelen = {};

function getHoofdstad(land, taal) {
  var naam = land.landnamen.findIndex(i => i.taal === taal);
  if (naam < 0 || !land.landnamen[naam].hasOwnProperty('hoofdstad')) {
    return '';
  }

  return land.landnamen[naam].hoofdstad;
}

function getLandIdNaam(landId, taal) {
  var land = {};
  if (landen.hasOwnProperty(landId)) {
    land = landen[landId];
  } else {
    $.ajax({ url: '/sedes/landen/'+landId,
             dataType: 'json',
             async: false,
             success:  function(data) {
               landen[landId] = data;
               land = data;
             }
    });
  }

  return getLandnaam(land, taal);
}

function getLandnaam(land, taal) {
  var naam = land.landnamen.findIndex(i => i.taal === taal);
  if (naam < 0 || !land.landnamen[naam].hasOwnProperty('naam')) {
    return land.iso3;
  }

  return land.landnamen[naam].naam;
}

function getOfficielenaam(land, taal) {
  var naam = land.landnamen.findIndex(i => i.taal === taal);
  if (naam < 0 || !land.landnamen[naam].hasOwnProperty('officieleNaam')) {
    return '';
  }

  return land.landnamen[naam].officieleNaam;
}

function getWerelddeelIdNaam(werelddeelId, taal) {
  var werelddeel = {};
  if (werelddelen.hasOwnProperty(werelddeelId)) {
    werelddeel = werelddelen[werelddeelId];
  } else {
    $.ajax({ url: '/sedes/werelddelen/'+werelddeelId,
             dataType: 'json',
             async: false,
             success:  function(data) {
               werelddelen[werelddeelId] = data;
               werelddeel = data;
             }
    });
  }

  return getWerelddeelnaam(werelddeel.werelddeelnamen, taal);
}

function getWerelddeelnaam(werelddeelnamen, taal) {
  var naam = werelddeelnamen.findIndex(i => i.taal === taal);
  if (naam < 0) {
    return '';
  }

  return werelddeelnamen[naam].naam;
}
