
--�ndern von db- oder java ansicht in eclipse:
--window/perspective/open perspective/other     hier die db oder java ausw�hlen

--f�r oracle: auto-commit ausschalten, damit nicht jede zeile einzeln committet wird
--weder BEGIN noch START TRANSACTION schreiben, alles ist automatisch eine TRANSACTION.

--  SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

-- 2.3 Transaktionen: alles in einem Rutsch
-- 1. �ffnen Sie eine Transaktion. 
SELECT count(*) FROM lieferung;


-- SET TRANSACTION name "hi";
START TRANSACTION;
-- BEGIN --  das z�hlt als "start transaction"

-- 2. Bestimmen Sie die Zahl der Lieferungen. 


-- 3. L�schen Sie alle Lieferanten.

DELETE FROM LIEFERANT;

 
-- 4. Bestimmen Sie die Zahl der Lieferungen. Wieso sind da welche verschwunden?
-- Konsultieren Sie zur Beantwortung auch die Datei hska_oracle_bike.sql.

SELECT count(*) FROM LIEFERUNG;
--  keine einzige Lieferung ist noch da, obwohl wir ja nur die Lieferanten gel�scht haben. 
--  in der angegebenen Datei steht folgender Auszug:

--CREATE TABLE lieferung (
--     teilnr      INTEGER    CONSTRAINT fk_teilestamm REFERENCES teilestamm ON DELETE CASCADE,
 
-- Wird beim Einreichten des Foreign-Key-Constraints festgelegt, dass das L�schen weitergegeben werden soll, 
-- so kann ein DELETE sehr viel mehr Zeilen als urspr�nglich vermutet l�schen!
-- hier werden auch die teile von teilestamm gel�scht.



-- 5. Stellen Sie durch Beenden der Transaktion den urspr�nglichen Zustand wieder her.

ROLLBACK;

-- 6. Bestimmen Sie die Zahl der Lieferungen.

SELECT count(*) FROM LIEFERUNG;




-- 





