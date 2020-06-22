package de.hska.iwii.db1.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Buchung_ORM")
public class Buchung {


	@Id	
	//@GeneratedValue(strategy=GenerationType.SEQUENCE)	
	@SequenceGenerator(name="buchung_seq",sequenceName="BUCHUNG_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="buchung_seq")
	@Column(name = "id")
	private int id;

	@Column(name = "plaetze")
	@Min(1)
	private int plaetze;

	@Column(name = "datum")
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date datum;

	@ManyToOne
	@JoinColumn(name = "id_kunde")
	private Kunde kunde;

	@ManyToOne
	@JoinColumn(name = "id_flug")
	private Flug flug;



	public int getId() {
		return id;
	}

	@SuppressWarnings("unused")
	public void setId(int id) {
		this.id = id;
	}

	public int getPlaetze() {
		return plaetze;
	}

	public void setPlaetze(int plaetze) {
		this.plaetze = plaetze;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public Kunde getKunde() {
		return kunde;
	}

	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}

	public Flug getFlug() {
		return flug;
	}

	public void setFlug(Flug flug) {
		this.flug = flug;
	}
}
