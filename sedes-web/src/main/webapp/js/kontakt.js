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

var i18n = {};
var taal = '';

function getI18N(code) {
  var teksten = [];
  if (i18n.hasOwnProperty(code)) {
    teksten = i18n[code].teksten;
  } else {
    $.ajax({ url: '/doos/i18nCodes/'+code,
             dataType: 'json',
             async: false,
             success:  function(data) {
               i18n[code] = data;
               teksten = data.teksten;
             }
    });
  }

  var naam = teksten.findIndex(i => i.taalKode === taal);
  if (naam < 0) {
    return '??' + code+ '??';
  }

  return teksten[naam].tekst;
}

function setTaal(taalkode) {
  taal = taalkode;
}

function zetForm(kontakttype) {
  switch (kontakttype) {
    case 'G':
      zetFormGroep();
      break;
    case 'P':
      zetFormPersoon();
      break;
    case 'R':
      zetFormRechtspersoon();
      break;
    default:
      break;
  }
}

function zetFormGroep() {
  document.getElementById('lblGeboortedatum').innerText = getI18N('label.oprichtingsdatum');
  document.getElementById('lblGebruikersnaam').innerText = getI18N('label.groepscode');
  document.getElementById('lblVoornaam').innerText = getI18N('label.afdeling');

  document.getElementById('vldRoepnaam').style.display = 'none';
  document.getElementById('vldPersoon').style.display = 'none';
  document.getElementById('vldPseudoniem').style.display = 'none';
}

function zetFormPersoon() {
  document.getElementById('lblGeboortedatum').innerText = getI18N('label.geboortedatum');
  document.getElementById('lblGebruikersnaam').innerText = getI18N('label.gebruikersnaam');
  document.getElementById('lblRoepnaam').innerText = getI18N('label.roepnaam');
  document.getElementById('lblVoornaam').innerText = getI18N('label.voornaam');

  document.getElementById('vldRoepnaam').style.display = '';
  document.getElementById('vldPersoon').style.display = '';
  document.getElementById('vldPseudoniem').style.display = '';
}

function zetFormRechtspersoon() {
  document.getElementById('lblGeboortedatum').innerText = getI18N('label.oprichtingsdatum');
  document.getElementById('lblGebruikersnaam').innerText = getI18N('label.bedrijfscode');
  document.getElementById('lblRoepnaam').innerText = getI18N('label.bedrijfsnaam');
  document.getElementById('lblVoornaam').innerText = getI18N('label.afdeling');

  document.getElementById('vldRoepnaam').style.display = '';
  document.getElementById('vldPersoon').style.display = 'none';
  document.getElementById('vldPseudoniem').style.display = 'none';
}
