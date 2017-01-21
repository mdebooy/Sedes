Sedes
=====

Dit project is bedoeld voor het bijhouden van Kontakten en hun adressen. Op dit moment is enkel het gedeelte voor werelddelen, landen en hun namen beschikbaar (inclusief een quiz met de hoofdsteden).

Het project bestaat uit 3 modules:

sedes-components
----------------

Deze produceert een jar die in alle web applicaties moet worden toegevoegd die landen nodig hebben.

sedes-config
------------

Deze produceert een jar met hierin alle bestanden die nodig zijn om met de applicatie te kunnen werken:
* `resources_XX.properties` bevat de teksten in taal `XX`.
* `parameters.properties` bevat de parameters voor de applicatie.
* `reports` directory bevat de rapporten voor de applicatie.
* `db` directory bevat de database scripts. Er is een sub-directory per database type. Scripts met de naam `XXXX-patch.sql` moeten enkel worden uitgevoerd als er wordt overgegaan naar een nieuwere versie. Sla geen versie over.

sedes-web
---------

Dit is de eigenlijke applicatie. Zet het war-bestand in de `webapps` directory van Tomee. De eerste maal dat de applicatie gebruikt wordt zijn er geen teksten of parameters aanwezig. Laad ze in via de juiste menu opties. Van zodra ze geladen zijn worden ze in de applicatie gebruikt.

<hr />

This project is meant to store Contacts and their addresses. At this moment only the part for continents, countries, and their names available (including a quiz of capitals).

The project consists of thre modules:

sedes-components
----------------

This produces a jar that must be used by all web applications that need countries.

sedes-config
------------

This produces a jar that includes all files that are needed to work with the application:
* `resources_XX.properties` contains the texts in language `XX`;
* `parameters.properties` contains the paramleters for the application;
* `reports` directory contains the reports for the application;
* `db` directory contains the database scripts. There is a sub-directory per database type. Scripts the name `XXXX-patch.sql` should only be executed when it is for an upgrade from one version to another. Do not skip a version.

sedes-web
---------

This is the application. Put the war-file in the `webapps` directory of Tomee. The first time that you use the application there will be no texts or parameters available. Load them through the right menu options. As soon as they are loaded they will be used by the application.
