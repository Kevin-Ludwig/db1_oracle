package de.hska.iwii.db1.jdbc;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

//import com.mysql.cj.jdbc.result.ResultSetMetaData;

//import jdk.internal.jimage.decompressor.ResourceDecompressor;

/**
 * Diese Klasse ist die Basis für Ihre Lösung. Mit Hilfe der
 * Methode reInitializeDB können Sie die beim Testen veränderte
 * Datenbank wiederherstellen.
 */
public class JDBCBikeShop {

	private final String user = "DBPRAX12";
	private final String password = "sCNglUE2";
	private Connection connection;


	/**
	 * Aufgabe 4.1
	 * Verbindung zur Datenbank herstellen
	 */
	public void openConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Properties properties = new Properties();
			properties.put("user", this.user);
			properties.put("password", this.password);

			Connection connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@iwi-lkit-db-01:1521:LAB1", properties);
			this.connection = connection;
			System.out.println("Connected to database...");

		} catch (ClassNotFoundException | SQLException ex ) {
			System.out.println("Execption: " + ex.getLocalizedMessage());
			ex.printStackTrace();
		}
	}


	/**
	 * Aufgabe 4.1
	 * MetaDaten der Datenbank ausgeben
	 */
	public void readMetaData() {
		try {
			System.out.println("---------------------------------------------------------------------------------");
			System.out.println("Product Name: " + connection.getMetaData().getDatabaseProductName());
			System.out.println("Version: " + connection.getMetaData().getDatabaseProductVersion());
			System.out.println("URL: " + connection.getMetaData().getURL());
			System.out.println("JDBC driver version: " + connection.getMetaData().getDriverVersion());
			System.out.println("---------------------------------------------------------------------------------");
			System.out.println("");

		} catch (SQLException e) {
			System.out.println("Can not read meta Data from database!");
			e.printStackTrace();
		}
	}


	/**
	 * Aufgabe 4.1
	 * Verbindung zur Datenbank schließen
	 */
	public void closeConnection(){
		try {
			connection.close();
			System.out.println("---------------------------------------------------------------------------------");
			System.out.println("Database connection closed!");
		} catch (SQLException e) {
			System.out.println("Execption: " + e.getLocalizedMessage());
			e.printStackTrace();		
		}
	}


	/**
	 * Aufgabe 4.2
	 * Tabelle anhand einer Selection ausgeben
	 * 
	 * @param path Selection Anweisung
	 */
	public void readStatement(String path) {

		try {
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet res = stmt.executeQuery(path);
			ResultSetMetaData metaData = res.getMetaData();

			ArrayList<String> columnNames =  new ArrayList<>();
			ArrayList<String> columnTypes =  new ArrayList<>();
			ArrayList<Integer> columnSizes = new ArrayList<>();
			int countColumns = metaData.getColumnCount();

			for(int i = 1; i < countColumns + 1; i++) {
				columnNames.add(metaData.getColumnName(i));
				columnTypes.add(metaData.getColumnTypeName(i));
				columnSizes.add(metaData.getColumnDisplaySize(i));		
			}


			// check if name or type is bigger than size and update sizes
			for(int i = 0; i < countColumns; i++) {
				int size = columnSizes.get(i);
				size = Math.max(size, columnNames.get(i).length());
				size = Math.max(size, columnTypes.get(i).length());
				columnSizes.set(i, size);
			}
			while(res.next()) {
				for(int i = 0; i < countColumns; i++) {
					int size = 0;
					size = Math.max(columnSizes.get(i), res.getString(i+1).length());
					columnSizes.set(i, size);
				}	
			}
			res.beforeFirst();


			// Print column names	
			for(int i = 0; i < countColumns; i++) {
				String result = String.format("%-" + columnSizes.get(i) + "s", columnNames.get(i));
				System.out.print(result + "|");
			}
			System.out.println();

			// Print column types
			for(int i = 0; i < countColumns; i++) {
				String result = String.format("%-" + columnSizes.get(i) + "s", columnTypes.get(i));
				System.out.print(result + "|");
			}
			System.out.println();

			// Print parting line
			for(int i = 0; i < countColumns; i++) {
				String result = String.format("%-" + columnSizes.get(i) + "s", "-").replace(' ' , '-');
				System.out.print(result + "+");
			}
			System.out.println();

			// Print data
			while(res.next()) {
				for(int i = 0 ; i < countColumns; i++) {
					String result = String.format("%-" + columnSizes.get(i) + "s", res.getString(i+1));
					System.out.print(result + "|");
				}
				System.out.println();				
			}
			System.out.println();				

			res.close();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Aufgabe 4.3
	 * Tabelle anhand einer Selection ausgeben und mit einem spezifischen Filter suchen 
	 * 
	 * @param path Selection Anweisung
	 * @param filter Suche nach einem spezifischen Filter(string)
	 */
	public void readPreparedStatement(String path, String filter) {

		try {
			PreparedStatement stmt = connection.prepareStatement(path,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt.setString(1, filter);
			ResultSet res = stmt.executeQuery();
			ResultSetMetaData metaData = res.getMetaData();

			ArrayList<String> columnNames =  new ArrayList<>();
			ArrayList<String> columnTypes =  new ArrayList<>();
			ArrayList<Integer> columnSizes = new ArrayList<>();
			int countColumns = metaData.getColumnCount();

			for(int i = 1; i < countColumns + 1; i++) {
				columnNames.add(metaData.getColumnName(i));
				columnTypes.add(metaData.getColumnTypeName(i));
				columnSizes.add(metaData.getColumnDisplaySize(i));		
			}

			// check if name or type is bigger than size and update sizes
			for(int i = 0; i < countColumns; i++) {
				int size = columnSizes.get(i);
				size = Math.max(size, columnNames.get(i).length());
				size = Math.max(size, columnTypes.get(i).length());
				columnSizes.set(i, size);
			}
			while(res.next()) {
				for(int i = 0; i < countColumns; i++) {
					int size = 0;
					size = Math.max(columnSizes.get(i), res.getString(i+1).length());
					columnSizes.set(i, size);
				}	
			}
			res.beforeFirst();


			// Print column names	
			for(int i = 0; i < countColumns; i++) {
				String result = String.format("%-" + columnSizes.get(i) + "s", columnNames.get(i));
				System.out.print(result + "|");
			}
			System.out.println();

			// Print column types
			for(int i = 0; i < countColumns; i++) {
				String result = String.format("%-" + columnSizes.get(i) + "s", columnTypes.get(i));
				System.out.print(result + "|");
			}
			System.out.println();

			// Print parting line
			for(int i = 0; i < countColumns; i++) {
				String result = String.format("%-" + columnSizes.get(i) + "s", "-").replace(' ' , '-');
				System.out.print(result + "+");
			}
			System.out.println();

			// Print data
			while(res.next()) {
				for(int i = 0 ; i < countColumns; i++) {
					String result = String.format("%-" + columnSizes.get(i) + "s", res.getString(i+1));
					System.out.print(result + "|");
				}
				System.out.println();				
			}
			System.out.println();				

			res.close();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Aufgabe 4.4
	 * Einen neuen Kunden einfügen. Zu dem Kunden einen neuen Auftrag einfügen.
	 * Dazu einen neuen Auftragsposten erstellen und mit einem vorhandenen Teil verbinden.
	 */
	public void insert() {

		try {
			// Add new customer (kunde)
			String einfuegenKunde = "INSERT INTO kunde (nr, name, strasse, plz, ort, sperre) VALUES (?, ?, ? ,? ,?, ?)"; 	
			PreparedStatement stmt1 = connection.prepareStatement(einfuegenKunde);
			stmt1.setInt(1,999);
			stmt1.setString(2,"Hochschule");
			stmt1.setString(3,"Moltkestraße");
			stmt1.setInt(4,76133);
			stmt1.setString(5,"Karlsruhe");
			stmt1.setInt(6,0);
			stmt1.execute();
			stmt1.close();

			// Add new order (auftrag)
			String einfuegenAuftrag = "INSERT INTO auftrag (auftrnr,datum,kundnr,persnr) VALUES (?,?,?,?)";
			PreparedStatement stmt2 = connection.prepareStatement(einfuegenAuftrag);
			stmt2.setInt(1,888);
			stmt2.setDate(2, new Date(new java.util.Date().getTime()));
			stmt2.setInt(3,999);
			stmt2.setInt(4,2);
			stmt2.execute();
			stmt2.close();

			// Add new post order (auftragsposten)
			String einfuegenAuftragsposten = "INSERT INTO auftragsposten(posnr, auftrnr, teilnr, anzahl, gesamtpreis) VALUES (?,?,?,?,?)";
			PreparedStatement stmt3 = connection.prepareStatement(einfuegenAuftragsposten);
			stmt3.setInt(1,777);
			stmt3.setInt(2,888);
			stmt3.setInt(3,200002);
			stmt3.setInt(4,2);
			stmt3.setInt(5,800);
			stmt3.execute();
			stmt3.close();

			//

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Aufgabe 4.4
	 * Update der Datenbank des Kundens
	 */
	public void update() {

		try {
			String updateKunde = "UPDATE kunde SET sperre = 1 where nr = 999";
			PreparedStatement stmt = connection.prepareStatement(updateKunde);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Aufgabe 4.4
	 * EInen Kunden aus der Datenbank löschen
	 */
	public void delete() {

		try {
			String loeschenKunde = "DELETE FROM kunde where nr = 999"; 
			String loeschenAuftrag = "DELETE FROM auftrag where auftrnr = 888";
			String loeschenAuftragsposten =	"DELETE FROM auftragsposten where posnr = 777";

			PreparedStatement stmt = connection.prepareStatement(loeschenAuftragsposten);
			stmt.execute();
			stmt.close();

			PreparedStatement stmt1 = connection.prepareStatement(loeschenAuftrag);
			stmt1.execute();
			stmt1.close();

			PreparedStatement stmt2 = connection.prepareStatement(loeschenKunde);
			stmt2.execute();
			stmt2.close();

			//Es müssen alle Datensätze gelöscht werden, sonst
			//Integritäts-Constraint verletzt - untergeordneter Datensatz gefunden
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	//---------------------------------------------------------------------------------------------
	// Only Getter and Setter	
	public Connection getConnection() {
		return connection;
	}

	//---------------------------------------------------------------------------------------------
	/**
	 * Stellt die Datenbank aus der SQL-Datei wieder her.
	 * - Alle Tabllen mit Inhalt ohne Nachfrage löschen.
	 * - Alle Tabellen wiederherstellen.
	 * - Tabellen mit Daten füllen.
	 * <p>
	 * Getestet mit MsSQL 12, MySql 8.0.8, Oracle 11g, PostgreSQL 10.1.
	 * <p>
	 * Das entsprechende Sql-Skript befindet sich im Ordner ./sql im Projekt.
	 * @param connection Geöffnete Verbindung zu dem DBMS, auf dem die
	 * 					Bike-Datenbank wiederhergestellt werden soll. 
	 */
	public void reInitializeDB(Connection connection) {
		try {
			System.out.println("\nInitializing DB.");
			connection.setAutoCommit(true);
			String productName = connection.getMetaData().getDatabaseProductName();
			boolean isMsSql = productName.equals("Microsoft SQL Server");
			Statement statement = connection.createStatement();
			int numStmts = 0;

			// Liest den Inhalt der Datei ein.
			String[] fileContents = new String(Files.readAllBytes(Paths.get("sql/hska_bike.sql")),
					StandardCharsets.UTF_8).split(";");

			for (String sqlString : fileContents) {
				try {
					// Microsoft kenn den DATE-Operator nicht.
					if (isMsSql) {
						sqlString = sqlString.replace(", DATE '", ", '");
					}
					statement.execute(sqlString);
					System.out.print((++numStmts % 80 == 0 ? "/\n" : "."));
				} catch (SQLException e) {
					System.out.print("\n" + sqlString.replace('\n', ' ').trim() + ": ");
					System.out.println(e.getMessage());
				}
			}
			statement.close();
			System.out.println("\nBike database is reinitialized on " + productName +
					"\nat URL " + connection.getMetaData().getURL()
					);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/*
	// Alt, Spaltenprüfung nicht dabei
	public void readStatement(String path) {

		try {
			Statement stmt = connection.createStatement();
			ResultSet res = stmt.executeQuery(path);
			ResultSetMetaData metaData = res.getMetaData();

			ArrayList<String> columnNames =  new ArrayList<>();
			ArrayList<String> columnTypes =  new ArrayList<>();
			ArrayList<Integer> columnSizes = new ArrayList<>();
			int countColumns = metaData.getColumnCount();

			for(int i = 1; i < countColumns + 1; i++) {
				columnNames.add(metaData.getColumnName(i));
				columnTypes.add(metaData.getColumnTypeName(i));
				columnSizes.add(metaData.getColumnDisplaySize(i));		
			}


			// check if name or type is bigger than size and update sizes
			for(int i = 0; i < countColumns; i++) {
				int size = columnSizes.get(i);
				size = Math.max(size, columnNames.get(i).length());
				size = Math.max(size, columnTypes.get(i).length());
				columnSizes.set(i, size);
			}


			// Print column names	
			for(int i = 0; i < countColumns; i++) {
				String result = String.format("%-" + columnSizes.get(i) + "s", columnNames.get(i));
				System.out.print(result + "|");
			}
			System.out.println();

			// Print column types
			for(int i = 0; i < countColumns; i++) {
				String result = String.format("%-" + columnSizes.get(i) + "s", columnTypes.get(i));
				System.out.print(result + "|");
			}
			System.out.println();

			// Print parting line
			for(int i = 0; i < countColumns; i++) {
				String result = String.format("%-" + columnSizes.get(i) + "s", "-").replace(' ' , '-');
				System.out.print(result + "+");
			}
			System.out.println();

			// Print data
			while(res.next()) {
				for(int i = 0 ; i < countColumns; i++) {
					String result = String.format("%-" + columnSizes.get(i) + "s", res.getString(i+1));
					System.out.print(result + "|");
				}
				System.out.println();				
			}
			System.out.println();				

			res.close();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	 */

}
