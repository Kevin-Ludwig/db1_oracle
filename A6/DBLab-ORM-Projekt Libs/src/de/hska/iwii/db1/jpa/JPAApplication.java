package de.hska.iwii.db1.jpa;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import de.hska.iwii.db1.jpa.Kunde;
import de.hska.iwii.db1.jpa.Flug;
import de.hska.iwii.db1.jpa.Buchung;


public class JPAApplication {
	private EntityManagerFactory entityManagerFactory;

	public JPAApplication() {
		Logger.getLogger("org.hibernate").setLevel(Level.ALL);
		this.entityManagerFactory = Persistence.createEntityManagerFactory("DB1");
	}


	@SuppressWarnings("unchecked")
	public void testFlights() {

		EntityManager em = entityManagerFactory.createEntityManager();	
		em.getTransaction().begin();

		// Aufgabe 6.1 und 6.2
		Kunde kunde1 = new Kunde();
		kunde1.setVorname("Tim");
		kunde1.setNachname("Schmidt");
		kunde1.setEmail("timschmidt@test.de");

		Kunde kunde2 = new Kunde();
		kunde2.setVorname("Jan");
		kunde2.setNachname("Mueller");
		kunde2.setEmail("janmueller@test.de");

		Flug flug1 = new Flug();
		flug1.setNummer("435Stu1");
		flug1.setFlughafen("Stuttgart");
		flug1.setZeit("10:00:00");

		Flug flug2 = new Flug();
		flug2.setNummer("435Ber1");
		flug2.setFlughafen("Berlin");
		flug2.setZeit("15:00:00");

		Flug flug3 = new Flug();
		flug3.setNummer("435Fran1");
		flug3.setFlughafen("Frankfurt");
		flug3.setZeit("20:00:00");

		Buchung buchen1 = new Buchung();
		buchen1.setPlaetze(2);
		buchen1.setDatum(new Date());
		buchen1.setKunde(kunde1);
		buchen1.setFlug(flug1);
		kunde1.getBuchungen().add(buchen1);
		flug1.getBuchungen().add(buchen1);

		Buchung buchen2 = new Buchung();
		buchen2.setPlaetze(2);
		buchen2.setDatum(new Date());
		buchen2.setKunde(kunde1);
		buchen2.setFlug(flug2);
		kunde1.getBuchungen().add(buchen2);
		flug2.getBuchungen().add(buchen2);

		Buchung buchen3 = new Buchung();
		buchen3.setPlaetze(2);
		buchen3.setDatum(new Date());
		buchen3.setKunde(kunde2);
		buchen3.setFlug(flug3);
		kunde2.getBuchungen().add(buchen3);
		flug3.getBuchungen().add(buchen3);

		Buchung buchen4 = new Buchung();
		buchen4.setPlaetze(2);
		buchen4.setDatum(new Date());
		buchen4.setKunde(kunde2);
		buchen4.setFlug(flug3);
		kunde2.getBuchungen().add(buchen4);
		flug3.getBuchungen().add(buchen4);
		em.persist(kunde1);
		em.persist(kunde2);
		em.persist(flug1);
		em.persist(flug2);
		em.persist(flug3); 	
		
		
		em.getTransaction().commit();


		//Aufgabe 6.3
		Query query = em.createQuery("Select b FROM Buchung b JOIN Kunde k ON k.id = b.kunde.id where k.vorname =:vorname");
		query.setParameter("vorname", "Tim");
		List<Buchung> buchungen = query.getResultList();
		for (Buchung b : buchungen) {
			System.out.println("Buchungs-ID: " + b.getId() + ", Datum: " + b.getDatum() + ", Plaetze: " 
					+ b.getPlaetze() + ", Flug-ID: " + b.getFlug().getId() + ", Kunde-ID: " + b.getKunde().getId() +
					", Kunde: " + b.getKunde().getVorname() + " " + b.getKunde().getNachname());
		}
		System.out.println("##############");
	
		
		/*
		// Einzelne Tabellen auslesen
	
		List<Kunde> kunden = em.createQuery("from Kunde", Kunde.class).getResultList();
		for (Kunde  k: kunden) {
			System.out.println("Kunden-ID: " + k.getId() + ", Name: " + k.getVorname() + " " 
					+ k.getNachname() + ", Email: " + k.getEmail() + ", # " + k.getBuchungen().size());
		}
		System.out.println("##############");	

		List<Buchung> buchungen = em.createQuery("from Buchung", Buchung.class).getResultList();
		for (Buchung b : buchungen) {
			System.out.println("Buchungs-ID: " + b.getId() + ", Datum: " + b.getDatum() + ", Plaetze: " 
					+ b.getPlaetze() + ", Kunde: " + b.getKunde().getVorname() + " " + b.getKunde().getNachname() + 
					", Flug: " + b.getFlug().getNummer());
		}
		System.out.println("##############");
		
		
		// Tabelle mit WHERE Bedingungen
		Query query = em.createQuery("FROM Kunde where vorname =:vorname");
		query.setParameter("vorname", "Tim");
		List<Kunde> kunden = query.getResultList();
		for (Kunde  k: kunden) {
			System.out.println("Kunden-ID: " + k.getId() + ", Name: " + k.getVorname() + " " 
					+ k.getNachname() + ", Email: " + k.getEmail() + ", # " + k.getBuchungen().size());
		}
		System.out.println("##############");
		
		Query query1 = em.createQuery("Select b.id, b.kunde.vorname FROM Buchung b JOIN Kunde k ON k.id = b.kunde.id where k.vorname =:vorname");
		query1.setParameter("vorname", "Tim");
		List<Buchung> buchungen1 = query.getResultList();
		for (Buchung b : buchungen1) {
			System.out.println("Buchungs-ID: " + b.getId() +", Vorname:" + b.getKunde().getVorname());
		}
		System.out.println("##############");


		// Über Iterator
		@SuppressWarnings("rawtypes")
		Iterator rowIter = em.createQuery("SELECT b.id, b.plaetze, b.flug.id, b.kunde.vorname FROM Buchung b JOIN Kunde k on k.id = b.kunde.id").getResultList().iterator();
		while (rowIter.hasNext()) {
			Object[] row = (Object[]) rowIter.next();
			Integer id = (Integer) row[0];
			Integer plaetze = (Integer) row[1];
			Integer flugID = (Integer) row[2];
			String vorname = (String) row[3];
			System.out.println("Buchung-ID: " + id + ", Plätze: " + plaetze + 
					", Flug-ID: " + flugID + ", Vorname: " + vorname);
		}
		System.out.println("##############");
		 */

		em.close();
		entityManagerFactory.close();
	}


	public static void main(String[] args) {

		JPAApplication app = new JPAApplication();
		app.testFlights();

	}
}
