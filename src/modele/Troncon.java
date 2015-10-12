package modele;

import utils.IntersectionHandler;

public class Troncon {
	private int idNoeudDestination;
	private String nom;
	private double vitesse;
	private double longueur;
	private int xDepart,yDepart;
	private double duration;
	
	
	public Troncon(int idNoeudDestination,int xDepart,int yDepart ,String nom, double vitesse, double longueur) {
		super();
		this.idNoeudDestination = idNoeudDestination;
		this.nom = nom;
		this.vitesse = vitesse;
		this.longueur = longueur;
		this.xDepart=xDepart;
		this.yDepart=yDepart;
		this.duration=longueur/vitesse/60;
	}
	
	
	


	public double getDuration() {
		return duration;
	}










	public int getIdNoeudDestination() {
		return idNoeudDestination;
	}





	public void setIdNoeudDestination(int idNoeudDestination) {
		this.idNoeudDestination = idNoeudDestination;
	}






	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}




	@Override
	public String toString() {
		return "Troncon [idnoeuddestination=" + idNoeudDestination + ", nom="
				+ nom + "]";
	}





	public int getX_depart() {
		return xDepart;
	}





	public void setX_depart(int x_depart) {
		this.xDepart = x_depart;
	}





	public int getY_depart() {
		return yDepart;
	}





	public void setY_depart(int y_depart) {
		this.yDepart = y_depart;
	}





	public double getVitesse() {
		return vitesse;
	}
	public void setVitesse(double vitesse) {
		this.vitesse = vitesse;
	}
	public double getLongueur() {
		return longueur;
	}
	public void setLongueur(double longueur) {
		this.longueur = longueur;
	}
}
