
--ändern von db- oder java ansicht in eclipse:
--window/perspective/open perspective/other     hier die db oder java auswählen

--für oracle: auto-commit ausschalten, damit nicht jede zeile einzeln committet wird
--weder BEGIN noch START TRANSACTION schreiben, alles ist automatisch eine TRANSACTION.

--  SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

-- 2.3 Transaktionen: alles in einem Rutsch
-- 1. Öffnen Sie eine Transaktion. 
SELECT count(*) FROM lieferung;


-- SET TRANSACTION name "hi";
START TRANSACTION;
-- BEGIN --  das zählt als "start transaction"

-- 2. Bestimmen Sie die Zahl der Lieferungen. 


-- 3. Löschen Sie alle Lieferanten.

DELETE FROM LIEFERANT;

 
-- 4. Bestimmen Sie die Zahl der Lieferungen. Wieso sind da welche verschwunden?
-- Konsultieren Sie zur Beantwortung auch die Datei hska_oracle_bike.sql.

SELECT count(*) FROM LIEFERUNG;
--  keine einzige Lieferung ist noch da, obwohl wir ja nur die Lieferanten gelöscht haben. 
--  in der angegebenen Datei steht folgender Auszug:

--CREATE TABLE lieferung (
--     teilnr      INTEGER    CONSTRAINT fk_teilestamm REFERENCES teilestamm ON DELETE CASCADE,
 
-- Wird beim Einreichten des Foreign-Key-Constraints festgelegt, dass das Löschen weitergegeben werden soll, 
-- so kann ein DELETE sehr viel mehr Zeilen als ursprünglich vermutet löschen!
-- hier werden auch die teile von teilestamm gelöscht.



-- 5. Stellen Sie durch Beenden der Transaktion den ursprünglichen Zustand wieder her.

ROLLBACK;

-- 6. Bestimmen Sie die Zahl der Lieferungen.

SELECT count(*) FROM LIEFERUNG;




-- 





