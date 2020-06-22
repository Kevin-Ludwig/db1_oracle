package de.hska.iwii.db1.jpa;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;



@Entity
@Table(name = "Flug_ORM")
public class Flug {

	@Id
	//@GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@SequenceGenerator(name="flug_seq",sequenceName="FLUG_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="flug_seq")
	@Column(name = "id")
	private int id;

	@Column(name = "nummer")
	@NotNull
	private String nummer;

	@Column(name = "zeit")
	@NotNull
	private String zeit;

	@Column(name = "flughafen")
	@NotNull
	private String flughafen;


	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_flug")
	private Set<Buchung> buchungen = new HashSet<>();



	public int getId() {
		return id;
	}

	@SuppressWarnings("unused")
	public void setId(int id) {
		this.id = id;
	}

	public String getNummer() {
		return nummer;
	}

	public void setNummer(String nummer) {
		this.nummer = nummer;
	}

	public String getZeit() {
		return zeit;
	}

	public void setZeit(String zeit) {
		this.zeit = zeit;
	}

	public String getFlughafen() {
		return flughafen;
	}

	public void setFlughafen(String flughafen) {
		this.flughafen = flughafen;
	}

	public Set<Buchung> getBuchungen() {
		return buchungen;
	}

	public void setBuchungen(Set<Buchung> buchungen) {
		this.buchungen = buchungen;
	}
}
