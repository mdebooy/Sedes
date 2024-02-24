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

var aanspreektitels = {};
var adressen = {};
var kontakten = {};
var kontaktadrestypes = {};
var kontakttypes = {};
var landen = {};
var plaatsen = {};
var regios = {};
var werelddelen = {};

function getAanspreektitel(aanspreekId, taal) {
  var teksten = [];
  if (aanspreektitels.hasOwnProperty(aanspreekId)) {
    teksten = aanspreektitels[aanspreekId].teksten;
  } else {
    $.ajax({ url: '/doos/i18nCodes/sedes.aanspreektitel.'+aanspreekId,
             dataType: 'json',
             async: false,
             success:  function(data) {
               aanspreektitels[aanspreekId] = data;
               teksten = data.teksten;
             }
    });
  }

  var naam = teksten.findIndex(i => i.taalKode === taal);
  if (naam < 0) {
    return aanspreekId;
  }

  return teksten[naam].tekst;
}

function getAdres(adresId) {
  var adres = {};
  if (adressen.hasOwnProperty(adresId)) {
    adres = adressen[adresId];
  } else {
    $.ajax({ url: '/sedes/adressen/'+adresId,
             dataType: 'json',
             async: false,
             success:  function(data) {
               adressen[adresId] = data;
               adres = data;
             }
    });
  }

  return adres;
}

function getAdresMetPlaats(adresId) {
  var adres = getAdres(adresId);
  if (adres.hasOwnProperty('plaatsId')) {
    return adres.adresdata + ' - ' + getPlaatsnaam(adres.plaatsId);
  }

  return adres.adresdata;
}

function getHoofdstad(land, taal) {
  var naam = land.landnamen.findIndex(i => i.taal === taal);
  if (naam < 0 || !land.landnamen[naam].hasOwnProperty('hoofdstad')) {
    return '';
  }

  return land.landnamen[naam].hoofdstad;
}

function getKontakt(kontaktId) {
  var kontakt = {};
  if (kontakten.hasOwnProperty(kontaktId)) {
    kontakt = kontakten[kontaktId];
  } else {
    $.ajax({ url: '/sedes/kontakten/'+kontaktId,
             dataType: 'json',
             async: false,
             success:  function(data) {
               kontakten[kontaktId] = data;
               kontakt = data;
             }
    });
  }

  return kontakt;
}

function getKontaktadrestype(kontaktadrestype, taal) {
  var teksten = [];
  if (kontaktadrestypes.hasOwnProperty(kontaktadrestype)) {
    teksten = kontaktadrestypes[kontaktadrestype].teksten;
  } else {
    $.ajax({ url: '/doos/i18nCodes/sedes.kontaktadres.type.'+kontaktadrestype,
             dataType: 'json',
             async: false,
             success:  function(data) {
               kontaktadrestypes[kontaktadrestype] = data;
               teksten = data.teksten;
             }
    });
  }

  var naam = teksten.findIndex(i => i.taalKode === taal);
  if (naam < 0) {
    return kontaktadrestype;
  }

  return teksten[naam].tekst;
}

function getKontakttype(kontakttype, taal) {
  var teksten = [];
  if (kontakttypes.hasOwnProperty(kontakttype)) {
    teksten = kontakttypes[kontakttype].teksten;
  } else {
    $.ajax({ url: '/doos/i18nCodes/sedes.kontakt.type.'+kontakttype,
             dataType: 'json',
             async: false,
             success:  function(data) {
               kontakttypes[kontakttype] = data;
               teksten = data.teksten;
             }
    });
  }

  var naam = teksten.findIndex(i => i.taalKode === taal);
  if (naam < 0) {
    return kontakttype;
  }

  return teksten[naam].tekst;
}

function getLand(landId) {
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

  return land;
}

function getLandIdNaam(landId, taal) {
  var land = getLand(landId);

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

function getPlaats(plaatsId) {
  var plaats = {};
  if (plaatsen.hasOwnProperty(plaatsId)) {
    plaats = plaatsen[plaatsId];
  } else {
    $.ajax({ url: '/sedes/plaatsen/'+plaatsId,
             dataType: 'json',
             async: false,
             success:  function(data) {
               plaatsen[plaatsId] = data;
               plaats = data;
             }
    });
  }

  return plaats;
}

function getPlaatsnaam(plaatsId) {
  var plaats = getPlaats(plaatsId);
  if (!plaats.hasOwnProperty('landId')) {
    return plaats.naam;
  }

  var land = getLand(plaats.landId);
  if (!land.hasOwnProperty('postLandkode')) {
    return plaats.naam;
  }

  return plaats.plaatsnaam + ' (' + land.postLandkode.toString().trim() + ')';
}

function getRegionaam(regioId) {
  var regio = {};
  if (regios.hasOwnProperty(regioId)) {
    regio = regios[regioId];
  } else {
    $.ajax({ url: '/sedes/regios/'+regioId,
             dataType: 'json',
             async: false,
             success:  function(data) {
               regios[regioId] = data;
               regio = data;
             }
    });
  }

  return regio.naam;
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
