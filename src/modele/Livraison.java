package modele;

public class Livraison {
	private int id;
	private int idClient;
	private int idIntersection;
	
	private String date;
	private PlageHoraire plageHoraire;
	
	public enum Etat {
	    enCours, effectue, aVenir, enRetard, annule
	}
	private Etat etat;
	
	public Livraison()
	{
		
	}

	

	
	public Livraison(int id, int idClient, int idIntersection, String date, Etat etat)
	{
		super();
		this.id = id;
		this.idClient = idClient;
		this.idIntersection = idIntersection;
		this.date = date;

		this.etat = etat;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdClient() {
		return idClient;
	}
	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}
	public int getIdIntersection() {
		return idIntersection;
	}
	public String getDate(){
		return date;
	}
	public void setDate(String date){
		this.date = date;
	}
	public void setIdIntersection(int idIntersection) {
		this.idIntersection = idIntersection;
	}

	public Etat getEtat(){
		return etat;
	}
	public void setEtat(Etat etat){
		this.etat = etat;
	}




	public PlageHoraire getPlageHoraire() {
		return plageHoraire;
	}




	public void setPlageHoraire(PlageHoraire plageHoraire) {
		this.plageHoraire = plageHoraire;
	}



   
	@Override
	public String toString() {
		return "Livraison [id=" + id + ", idClient=" + idClient
				+ ", adresse=" + idIntersection + ", plagehoraire="
				+ plageHoraire + "]";
	}
	
}

