package modele;


public class Commande {
	private int id;
	private int idLivraison;
	public enum Etat {
	    inconnu, possible, impossible
	}
	private Etat etat;
	private int idPlageHoraire;
	public Commande(int id, int idLivraison, int idPlageHoraire, Etat etat) {
		super();
		this.id = id;
		this.idLivraison = idLivraison;
		this.idPlageHoraire = idPlageHoraire;
		this.etat = etat;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdLivraison() {
		return idLivraison;
	}
	public void setIdLivraison(int idLivraison) {
		this.idLivraison = idLivraison;
	}
	public int getIdPlageHoraire() {
		return idPlageHoraire;
	}
	public void setIdPlageHoraire(int idPlageHoraire) {
		this.idPlageHoraire = idPlageHoraire;
	}
	public Etat getEtat(){
		return etat;
	}
	public void setEtat(Etat etat){
		this.etat = etat;
	}
}
