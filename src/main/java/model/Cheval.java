package model;

import java.time.LocalDate;

public class Cheval {

    private int id;
    private String nom;
    private LocalDate dateNaissance;

    private Race race;
  
    private Client proprietaire;

    public Cheval() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    public Race getRace() {
        return race;
    }
    public void setRace(Race race) {
        this.race = race;
    }

    public Client getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(Client proprietaire) {
        this.proprietaire = proprietaire;
    }

	public String getSexe() {
		
		return null;
	}

	public String getPoids() {
	
		return null;
	}

	public String getTaille() {
		
		return null;
	}

}