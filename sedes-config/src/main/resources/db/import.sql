delete from sedes.tijdelijk;

COPY sedes.tijdelijk(iso, naam) from '/tmp/ar.csv' DELIMITERS ',' CSV;
delete from sedes.tijdelijk where length(iso) > 3;
update sedes.tijdelijk set taal='ar' where taal is null;

COPY sedes.tijdelijk(naam, officieel, hoofdstad, iso) from '/tmp/cs.csv' DELIMITERS ',' CSV;
delete from sedes.tijdelijk where length(iso) > 3;
update sedes.tijdelijk set taal='cs' where taal is null;

COPY sedes.tijdelijk(naam, officieel, hoofdstad, iso) from '/tmp/et.csv' DELIMITERS ',' CSV;
delete from sedes.tijdelijk where length(iso) > 3;
update sedes.tijdelijk set taal='et' where taal is null;

COPY sedes.tijdelijk(naam, officieel, hoofdstad, iso) from '/tmp/lv.csv' DELIMITERS ',' CSV;
delete from sedes.tijdelijk where length(iso) > 3;
update sedes.tijdelijk set taal='lv' where taal is null;

delete from sedes.tijdelijk where iso is null;

