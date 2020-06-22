--1.1
SELECT * FROM teilestamm;

--1.2
SELECT * FROM teilestamm WHERE LOWER(bezeichnung) LIKE '%city%';

--1.3
SELECT COUNT(*) FROM kunde; -- 6
SELECT COUNT(*) FROM personal; -- 9
SELECT COUNT(*) FROM teilestamm; -- 22

--1.4
SELECT MIN(datum) AS von, MAX(datum) AS bis FROM auftrag;

--1.5
-- Kunde: Maier Ingrid,     
-- Personal: Johanna Köster	
-- Maria Forster

--1.6
SELECT * FROM lager WHERE bestand >= 1 ORDER BY bestand;

--1.7
SELECT DISTINCT(teilnr) FROM lieferung ORDER BY teilnr DESC;

--1.8
SELECT teilnr AS Artikelnummer, bezeichnung AS Atrikelbezeichnung, preis AS Bruttobetrag
	FROM teilestamm WHERE preis > 30;

--1.9
SELECT einzelteilnr FROM teilestruktur
	WHERE oberteilnr = 300001 AND anzahl > 100;





