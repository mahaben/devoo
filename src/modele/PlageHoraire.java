package modele;

import java.util.Date;

public class PlageHoraire {
	
	private int id;
	private String heureDebut;
	private String heureFin;
	
	private Date debut;
	private Date fin;
	public Date getDebut() {
		return debut;
	}
	public void setDebut(Date debut) {
		this.debut = debut;
	}
	public Date getFin() {
		return fin;
	}
	public void setFin(Date fin) {
		this.fin = fin;
	}
	public PlageHoraire()
	{
		
	}
	public PlageHoraire(String heureDebut, String heureFin) 
	{
		super();
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return heureDebut +" --> " +heureFin;
	}
	public String getHeureDebut() {
		return heureDebut;
	}
	public void setHeureDebut(String heureDebut) {
		this.heureDebut = heureDebut;
	}
	public String getHeureFin() {
		return heureFin;
	}
	public void setHeureFin(String heureFin) {
		this.heureFin = heureFin;
	}
	
	public boolean equals(PlageHoraire plageHoraire)
	{
		return heureDebut.equals(plageHoraire.heureDebut) && heureFin.equals(plageHoraire.heureFin);
	}
}
