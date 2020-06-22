package de.hska.iwii.db1.jdbc;

//import java.sql.SQLException;

//import com.mysql.cj.api.jdbc.Statement;

public class Main {

	public static void main(String[] argv) {

		JDBCBikeShop newConnection = new JDBCBikeShop();

		System.out.println("Aufgabe 4.1");
		newConnection.openConnection();
		newConnection.readMetaData();
		
		System.out.println("Aufgabe 4.2");
		newConnection.readStatement("Select persnr, name, ort, aufgabe FROM personal");
		newConnection.readStatement("SELECT * FROM kunde");
		
		System.out.println("Aufgabe 4.3");
		String filter = "%";
		newConnection.readPreparedStatement("SELECT k.name AS kunde, k.nr AS knr, l.name AS lieferant, l.nr AS lnr FROM kunde k " +
		"JOIN AUFTRAG a ON a.kundnr = k.nr " + 
		"JOIN AUFTRAGSPOSTEN  ap ON ap.auftrnr = a.auftrnr " + 
		"JOIN TEILESTAMM t ON t.teilnr = ap.teilnr " +
		"JOIN LIEFERUNG lg ON lg.teilnr = t.teilnr " +
		"JOIN LIEFERANT l ON l.nr = lg.liefnr " +
		"WHERE k.name LIKE ?", filter);
		
		filter = "Rafa%";
		newConnection.readPreparedStatement("SELECT k.name AS kunde, k.nr AS knr, l.name AS lieferant, l.nr AS lnr FROM kunde k " +
		"JOIN AUFTRAG a ON a.kundnr = k.nr " + 
		"JOIN AUFTRAGSPOSTEN  ap ON ap.auftrnr = a.auftrnr " + 
		"JOIN TEILESTAMM t ON t.teilnr = ap.teilnr " +
		"JOIN LIEFERUNG lg ON lg.teilnr = t.teilnr " +
		"JOIN LIEFERANT l ON l.nr = lg.liefnr " +
		"WHERE k.name LIKE ?", filter);

		System.out.println("Aufgabe 4.4 - Tabellen vor dem Einfügen");
		newConnection.readStatement("SELECT * FROM kunde");
		newConnection.readStatement("SELECT * FROM auftrag");
		newConnection.readStatement("SELECT * FROM auftragsposten");
		newConnection.insert();

		System.out.println("Tabellen nach dem Einfügen");
		newConnection.readStatement("SELECT * FROM kunde");
		newConnection.readStatement("SELECT * FROM auftrag");
		newConnection.readStatement("SELECT * FROM auftragsposten");

		newConnection.update();
		System.out.println("Tabelle nach dem Update der Sperren");
		newConnection.readStatement("SELECT * FROM kunde");

		newConnection.delete();
		System.out.println("Tabellen nach dem Löschen der Einträge");
		newConnection.readStatement("SELECT * FROM kunde");
		newConnection.readStatement("SELECT * FROM auftrag");
		newConnection.readStatement("SELECT * FROM auftragsposten");


		newConnection.reInitializeDB(newConnection.getConnection());
		newConnection.closeConnection();
	}
}


