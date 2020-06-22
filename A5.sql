ALTER TABLE stundenplan
	DROP CONSTRAINT fk_studiengang;
ALTER TABLE veranstaltung
	DROP CONSTRAINT fk_studiengang2;
DROP TABLE studiengang;

ALTER TABLE veranstaltung
	DROP CONSTRAINT fk_stundenplan2;
DROP TABLE stundenplan;

DROP TABLE dozentenBetrVerantstaltungen;
DROP TABLE veranstFindenStattRaeumen;
DROP TABLE veranstaltung;

ALTER TABLE raum
 DROP CONSTRAINT fk_dozent;
DROP TABLE dozent;
DROP TABLE raum;

CREATE TABLE studiengang (
     kuerzel     	VARCHAR(4) PRIMARY KEY,
     name     		VARCHAR(100),
     stundenplanId 	INTEGER
);

CREATE TABLE stundenplan (
     id    		 			INTEGER PRIMARY KEY,
     studiengangKuerzel     VARCHAR(4)
);

--muss danach hinzugefügt werden, ansonsten gibt es einen error, weil die tables noch nicht existieren
ALTER TABLE studiengang ADD 
	CONSTRAINT fk_stundenplan FOREIGN KEY (stundenplanId) REFERENCES stundenplan(id);

ALTER TABLE stundenplan ADD 
	CONSTRAINT fk_studiengang FOREIGN KEY (studiengangKuerzel) REFERENCES studiengang(kuerzel);



CREATE TABLE veranstaltung (
     id     		INTEGER    PRIMARY KEY,
     name    		VARCHAR(100),
     wochentag     	VARCHAR(30),
     startzeit     	DATE,
     endzeit     	DATE,
     veranstaltungsart   	VARCHAR(30),
     fachsemester   INTEGER,
     haeufigkeit 	VARCHAR(30),
     studiengangskuerzel	VARCHAR(4),
     stundenplanId	INTEGER,     
          	CONSTRAINT fk_studiengang2 FOREIGN KEY (studiengangskuerzel) REFERENCES studiengang(kuerzel),
			CONSTRAINT fk_stundenplan2 FOREIGN KEY (stundenplanId) REFERENCES stundenplan(id),
     	    CONSTRAINT check_veranstaltungsart CHECK (veranstaltungsart 
          								IN ('Labor', 'Übung','Uebung', 'Vorlesung'))
);


CREATE TABLE raum (
	 gebaeudeRaumNrr	VARCHAR(4)  PRIMARY KEY,
     gebaeudeName	VARCHAR(1),
     dozentenId    	INTEGER	
);
    
CREATE TABLE dozent (
     id     		INTEGER    PRIMARY KEY,
     diePosition		VARCHAR(30),
     name    		VARCHAR(30),
     vorname     	VARCHAR(30),
 	 akademischerGrad    	VARCHAR(30),
 	 mailAdresse	VARCHAR(30),
 	 gebaeudeRaumNr	VARCHAR(4),
          	CONSTRAINT fk_raum FOREIGN KEY (gebaeudeRaumNr) REFERENCES raum(gebaeudeRaumNrr),
          	CONSTRAINT check_diePosition CHECK (diePosition 
          								IN ('Professor', 'Lehrbeauftragter','Mitarbeiter', 'Lehrbeauftragte')),
	      	CONSTRAINT check_gebaeudeRaumNr CHECK (gebaeudeRaumNr IS NOT NULL AND diePosition IN ('Professor', 'Mitarbeiter'))
);

--INSERT INTO dozent (diePosition ) VALUES ("Professer");

ALTER TABLE raum ADD 
	CONSTRAINT fk_dozent FOREIGN KEY (dozentenId) REFERENCES dozent(id);


CREATE TABLE dozentenBetrVerantstaltungen (
    dozentenId			INTEGER PRIMARY KEY, --abgabwnkorrektur
    veranstaltungsId	INTEGER PRIMARY KEY,
     CONSTRAINT fk_dozent2 FOREIGN KEY (dozentenId) REFERENCES dozent(id),
	 CONSTRAINT fk_veranstaltung FOREIGN KEY (veranstaltungsId) REFERENCES veranstaltung(id) 
);

CREATE TABLE veranstFindenStattRaeumen (
    veranstaltungsId	INTEGER PRIMARY KEY, --abgabenkorrektur
    gebaeudeRaumNr		VARCHAR(4) PRIMARY KEY,
    	 CONSTRAINT fk_veranstaltung2 FOREIGN KEY (veranstaltungsId) REFERENCES veranstaltung(id),
    	 CONSTRAINT fk_raum2 FOREIGN KEY (gebaeudeRaumNr) REFERENCES raum(gebaeudeRaumNrr) 
);
