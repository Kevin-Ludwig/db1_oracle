--2.1
SELECT auftrnr, datum FROM auftrag 
	WHERE kundnr IN 
	(SELECT nr FROM kunde WHERE name = 'Fahrrad Shop');
		
--2.2
SELECT auftrnr, datum FROM auftrag 
	WHERE kundnr = SOME
	(SELECT nr FROM kunde WHERE name = 'Fahrrad Shop');
	
--2.3
SELECT auftrnr, datum FROM auftrag 
	WHERE EXISTS
	(SELECT nr FROM kunde WHERE kundnr = nr AND name = 'Fahrrad Shop');
	
--2.4
SELECT kundnr, COUNT(kundnr) AS anzahl, MIN(datum) AS von, MAX(datum) AS bis
	FROM auftrag GROUP BY kundnr ORDER BY kundnr;

--2.5
SELECT * FROM 
	(SELECT kundnr, COUNT(kundnr) AS anzahl, MIN(datum) AS von, MAX(datum) AS bis
	FROM auftrag GROUP BY kundnr ORDER BY kundnr)
	WHERE anzahl <= 1;

--2.6
SELECT k.nr, k.name, a.auftrnr FROM kunde k 
	JOIN auftrag a ON k.nr = a.kundnr;

--2.7
SELECT k.nr, k.name, a.auftrnr, p.name FROM kunde k
	JOIN auftrag a ON a.kundnr = k.nr
	JOIN personal p ON p.persnr = a.persnr;

-- Aufgabe 4.4
SELECT k.name AS kunde, k.nr AS knr, l.name AS lieferant, l.nr AS lnr FROM kunde k 
JOIN AUFTRAG a ON a.kundnr = k.nr 
JOIN AUFTRAGSPOSTEN  ap ON ap.auftrnr = a.auftrnr 
JOIN TEILESTAMM t ON t.teilnr = ap.teilnr
JOIN LIEFERUNG lg ON lg.teilnr = t.teilnr 
JOIN LIEFERANT l ON l.nr = lg.liefnr 
WHERE k.name LIKE '%';



