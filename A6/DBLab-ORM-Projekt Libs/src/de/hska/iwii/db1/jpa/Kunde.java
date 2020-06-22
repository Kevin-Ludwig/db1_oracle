package de.hska.iwii.db1.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;



@Entity
@Table(name = "Kunde_ORM")
public class Kunde {

	@Id
	//@GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@SequenceGenerator(name="kunde_seq",sequenceName="KUNDE_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="kunde_seq")
	@Column(name = "id")
	private int id;

	@Column(name = "vorname")
	@NotNull
	private String vorname;

	@Column(name = "nachname")
	@NotNull
	private String nachname;

	@Column(name = "email")
	@Email
	@NotNull
	private String email;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_kunde")
	private Set<Buchung> buchungen = new HashSet<>();



	public int getId() {
		return id;
	}

	@SuppressWarnings("unused")
	public void setId(int id) {
		this.id = id;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Buchung> getBuchungen() {
		return buchungen;
	}

	public void setBuchungen(Set<Buchung> buchungen) {
		this.buchungen = buchungen;
	}
}
