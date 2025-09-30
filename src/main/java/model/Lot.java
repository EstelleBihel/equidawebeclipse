package model;

public class Lot {
    private int id;
    private int prixDepart;

    private Vente vente;
    private Cheval cheval;

    public void setCheval(Cheval cheval) {
        this.cheval = cheval;
    }

    public Cheval getCheval() {
        return cheval;
    }

    public void setVente(Vente vente) {
        this.vente = vente;
    }

    public Vente getVente() {
        return vente;
    }

    public Lot() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setPrixDepart(double d) {
        this.prixDepart = (int) d;
    }

    public int getPrixDepart() {
        return prixDepart;
    }

	public void setDescription(String string) {
		// TODO Auto-generated method stub
		
	}
}

